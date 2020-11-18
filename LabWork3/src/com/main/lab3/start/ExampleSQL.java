package com.main.lab3.start;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ExampleSQL {
	String databaseURL = "jdbc:ucanaccess:///Users/ReznichenkoAlex/Documents/KR.accdb";
	String main;
	
	public static class Userss {
		String databaseURL = "jdbc:ucanaccess:///Users/ReznichenkoAlex/Documents/KR.accdb";
		String main;
		String[] names  = new String[8];
		public String ExampleSQLUsers(String sql){
			try (Connection connection = DriverManager.getConnection(databaseURL)) 
			{
		        Statement statement = connection.createStatement();
		        ResultSet result = statement.executeQuery(sql);
		        int i = 0;
		        while (result.next()) {
		        	i++;
		            int identity = result.getInt("ID");
		            String nameuser = result.getString("nameuser");
		            names[i] = nameuser;
		            main += identity + ", " + nameuser;
		        }
		        
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			printUsers(names);
			return main;
		}
		public void printUsers(String[] names){
			for(int w = 0; w <  names.length; w++) {
				System.out.println(names[w]);
			}
			System.out.println();
		}
	}
	
	@Cache
	public String ExampleSQLData(String sql){
		try (Connection connection = DriverManager.getConnection(databaseURL)) 
		{
	        Statement statement = connection.createStatement();
	        ResultSet result = statement.executeQuery(sql);
	        while (result.next()) {
	            int id = result.getInt("ID");
	            String strArg = result.getString("StrArg");
	            System.out.println(id + ", " + strArg);
	            main += id + ", " + strArg;
	        }
	        
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return main;
	}
	@Cache
	public String ExampleSQLUsers(String sql){
		try (Connection connection = DriverManager.getConnection(databaseURL)) 
		{
	        Statement statement = connection.createStatement();
	        ResultSet result = statement.executeQuery(sql);
	        while (result.next()) {
	            int id = result.getInt("ID");
	            String nameuser = result.getString("nameuser");
	            System.out.println(id + ", " + nameuser);
	            main += id + ", " + nameuser;
	        }
	        
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return main;
	}
	@Cache
	public String ExampleSQLRoles(String sql){
		try (Connection connection = DriverManager.getConnection(databaseURL)) 
		{
	        Statement statement = connection.createStatement();
	        ResultSet result = statement.executeQuery(sql);
	        while (result.next()) {
	            int id = result.getInt("ID");
	            String role = result.getString("role");
	            System.out.println(id + ", " + role);
	            main += id + ", " + role;
	        }
	        
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return main;
	}
}

