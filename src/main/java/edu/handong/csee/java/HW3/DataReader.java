package edu.handong.csee.java.HW3;

import java.io.File;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataReader {
	/*
	 * GetData to call all the inputs public static void main(String[] args) {
	 * DataReader dr = new DataReader(); dr.getData(args[3]); }
	 */

	HashMap<String, ArrayList<Message>> messages = new HashMap<String, ArrayList<Message>>();
	HashMap<String, Integer> indexcounter = new HashMap<String, Integer>();

	public HashMap<String, ArrayList<Message>> getData(String strDir) {

		/* 1. make a file in the directory */
		File myDirectory = getDirectory(strDir);

		/* 2. file type Array to contain file names and directory */
		File[] files = getListOfFilesFromDirectory(myDirectory);

		/* 3. String Arraylist to save all the chat data */

		ArrayList<DataThread> dataRunners = new ArrayList<DataThread>();

		int numOfCoresInMyCPU = Runtime.getRuntime().availableProcessors();
		System.out.println("The number of cores of my system: " + numOfCoresInMyCPU);
		
		ExecutorService executor = Executors.newFixedThreadPool(numOfCoresInMyCPU);
		ArrayList<DataThread> allData = new ArrayList<DataThread>();
		ArrayList<Callable<Object>> calls = new ArrayList<Callable<Object>>();
		
		for (File f : files) {
			Runnable worker = new DataThread(f);
			executor.execute(worker);
			allData.add((DataThread)worker);
		}
		
		executor.shutdown();
		
		while(!executor.isTerminated()) {}
		
		for(DataThread runner:allData) {
			HashMap<String, ArrayList<Message>> temp = new HashMap<String, ArrayList<Message>>();
			temp = runner.messages;
			for (String keyt : temp.keySet()) {
				boolean done = false;
				for (String key : messages.keySet()) {
					if (keyt.equals(key)) {
						messages.get(key).addAll(temp.get(keyt));
						done = true;
					}
				}
				if (done == false)
					messages.put(keyt, temp.get(keyt));
			}
		}
		System.out.println("eerreer");
		return messages;
	}

	private File getDirectory(String Directory) {

		File myDirectory = new File(Directory);
		return myDirectory;
	}

	private File[] getListOfFilesFromDirectory(File dataDir) {

		for (File file : dataDir.listFiles()) {
			// System.out.println(file.getAbsolutePath());
		}
		return dataDir.listFiles();
	}
}