package com.main.lab3.start;

import com.main.lab3.start.ExampleSQL.Userss;

public class LabWork3 {
	public static void main(String[] args) {
		MainService mainServer = new MainService();
		ExampleSQL exampleSQL = new ExampleSQL();
		Object obj = new Object();
		int arrSelection[] = {62, 84, 32, 5, 0, 14, 52, 82, 58, 71}; 
		int arrBubble[] = {52, 7, 43, 54, 9, 67, 52, 2, 40, 54};
		try {
			mainServer.selectionSort(arrSelection);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		mainServer.bubbleSort(arrBubble);
		mainServer.printMass(arrSelection);
		mainServer.printMass(arrBubble);
		
		obj = "Object_Example";
		System.out.println(obj);
		System.out.println();
		
		//Userss userss = new Userss();
		//userss.ExampleSQLUsers("SELECT * FROM Users");
		exampleSQL.ExampleSQLData("SELECT * FROM DATA");
		exampleSQL.ExampleSQLUsers("SELECT * FROM Users");
		exampleSQL.ExampleSQLRoles("SELECT * FROM Roles");
		
		try { 
			//man-made error to throw an exception
            int[] errMass = new int[5]; 
            errMass[5] = 1; 
        } 
        catch (Exception e) { 
             e.printStackTrace(System.out);
        } 
	}
}
