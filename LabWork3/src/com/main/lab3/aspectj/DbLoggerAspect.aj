package com.main.lab3.aspectj;
import com.main.lab3.start.LogExecutionTime;
import com.main.lab3.start.Cache;
import com.main.lab3.start.ExampleSQL;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public aspect DbLoggerAspect {

	String databaseURL = "jdbc:ucanaccess:///Users/ReznichenkoAlex/Documents/KR.accdb";
	String sql = "INSERT INTO Data (StrArg) VALUES (?)";
	private Map<String, Object> cache;
	
	// System.out.print() и System.out.println()
	pointcut DbLogger(Object obj):
		call(public void java.io.PrintStream.print*(..)) && args(obj) && !within(DbLoggerAspect) 
		&& !within(ExampleSQL);

	void around(Object obj):DbLogger(obj)
	{
		System.out.println("Argument: " + obj + "\n Aspect Check for print functions");

		try (Connection connection = DriverManager.getConnection(databaseURL)) 
		{
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, obj.toString());
			System.out.println(preparedStatement.getQueryTimeout());
			int row = preparedStatement.executeUpdate();

			if (row > 0) {
				System.out.println("A row has been inserted successfully.");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		proceed(obj);
	}
	
	// printStackTrace() 
	pointcut ExLogger():
		call(public void printStackTrace(..)) && !within(DbLoggerAspect);

	void around():ExLogger() {System.out.println("Aspect Check for StackTrace");proceed();}
	
	// stringProcessingMethods()
	private Logger logger = Logger.getLogger(DbLoggerAspect.class.getName());
	
	pointcut stringProcessingMethods() : within(com.main.lab3.start.MainService);
	after():stringProcessingMethods(){
		String methodName = thisJoinPoint.getSignature().getName();
	    logger.log(Level.INFO, "method name: " + methodName);
	    
		try (Connection connection = DriverManager.getConnection(databaseURL)) 
		{
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1,(Level.INFO + " method name: " + methodName.toString()));
			int row = preparedStatement.executeUpdate();
			if (row > 0) {
				System.out.println("A method name has been inserted successfully.");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	after() returning(Object result): execution(public String com.main.lab3.start.MainService.*(..)){
		logger.log(Level.INFO, "returned value: " + result.toString());
	}
	
	// LogExecutionTime()
	pointcut logExecutionTime(): @annotation (LogExecutionTime);
	Object around() : logExecutionTime() {
		  long start = System.currentTimeMillis();
		  Object proceed = proceed();
	      long executionTime = System.currentTimeMillis() - start;
	      logger.log(Level.INFO, thisJoinPoint.getSignature() + " complited in " + executionTime + "ms");
	      
	      try (Connection connection = DriverManager.getConnection(databaseURL)) 
			{
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1,(thisJoinPoint.getSignature() + " complited in " + executionTime + "ms"));
				int row = preparedStatement.executeUpdate();
				if (row > 0) {
					System.out.println("A execution time has been inserted successfully.");
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
	      return proceed;
	 }
	
	// @Cache()
	pointcut cache(): execution(@Cache * *.*(..));
	Object around() : cache(){
		 cache = new HashMap<String, Object>();
		 System.out.println("Execution of Cacheable method catched");
	  
	     // generate the key under which cached value is stored
	     // will look like caching.aspectj.Calculator.sum(Integer=1;Integer=2;)
	     StringBuilder keyBuff = new StringBuilder();
	  
	     // append name of the class
	     keyBuff.append(thisJoinPoint.getTarget().getClass().getName());
	  
	     // append name of the method
	     keyBuff.append(".").append(thisJoinPoint.getSignature().getName());
	  
	     keyBuff.append("(");
	     // loop through cacheable method arguments
	     for (final Object arg : thisJoinPoint.getArgs()) {
	         // append argument type and value
	         keyBuff.append(arg.getClass().getSimpleName() + "=" + arg + ";");
	     }
	     keyBuff.append(")");
	     String key = keyBuff.toString();
	  
	     System.out.println("Key = " + key);
	     Object result = cache.get(key);
	     if (result == null) {
	    	 System.out.println("Result not yet cached. Must be calculated...");
	         result = proceed();
	         logger.info("Storing calculated value '" + result + "' to cache");
	         cache.put(key, result);
	     } else {
	    	 System.out.println("Result '" + result + "' was found in cache");
	     }
	     return result;
	 }
}


