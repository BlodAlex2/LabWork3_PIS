package com.main.lab3.start;

public class MainService {
	@LogExecutionTime
	public void selectionSort(int[] arr) throws InterruptedException{
		Thread.sleep(1000);
	    for (int i = 0; i < arr.length; i++) {
	        int min = arr[i];
	        int min_i = i;
	        for (int j = i+1; j < arr.length; j++) {
	            if (arr[j] < min) {
	                min = arr[j];
	                min_i = j;
	            }
	        }
	        if (i != min_i) {
	            int tmp = arr[i];
	            arr[i] = arr[min_i];
	            arr[min_i] = tmp;
	        }
	     }
	}
	@LogExecutionTime
	public void bubbleSort(int[] arr){
	    for(int t = arr.length-1 ; t > 0 ; t--){
	        for(int e = 0 ; e < t ; e++){
	            if( arr[e] > arr[e+1] ){
	                int tmp = arr[e];
	                arr[e] = arr[e+1];
	                arr[e+1] = tmp;
	            }
	        }
	    }
	}
	@LogExecutionTime
	public void printMass(int[] array){
		String str = new String();
		for(int w = 0; w <  array.length; w++) {
			str += (array[w] + "  ");
		}
		System.out.println(str);
		System.out.println();
	}
}
