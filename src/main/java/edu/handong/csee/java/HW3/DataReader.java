package edu.handong.csee.java.HW3;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DataReader {
	/* GetData to call all the inputs */
	public static void main(String args[]) {
		DataReader dr = new DataReader();
		dr.getData(args[3]);
	}

	public ArrayList<String> getData(String strDir){

		/* 1. make a file in the directory */
		File myDirectory = getDirectory(strDir);

		/* 2. file type Array to contain file names and directory*/
		File[] files = getListOfFilesFromDirectory(myDirectory);

		/* 3. String Arraylist to save all the chat data */
		ArrayList<String> message = readFiles(files);

		System.out.println("hello" + message);
		return message;
	}

	private File getDirectory(String Directory) {

		File myDirectory = new File(Directory);
		return myDirectory;
	}

	private File[] getListOfFilesFromDirectory(File dataDir) {

		for(File file:dataDir.listFiles()) {
			//System.out.println(file.getAbsolutePath());
		}
		return dataDir.listFiles();
	}

	private ArrayList<String> readFiles(File[] dataDir){

		ArrayList<String> message = new ArrayList<String>();
		
		for(int i=0;i<dataDir.length;i++)
		{
			String fileName = dataDir[i].getAbsolutePath();
			System.out.println (fileName+" contains : ");
			Scanner inputStream = null;
			try {
				inputStream = new Scanner(new File(fileName));
			}  catch (FileNotFoundException e) {
				System.out.println ("Error opening the file " + fileName);
				System.exit (0);
			}
			
			while (inputStream.hasNextLine()) {
				String line = inputStream.nextLine ();
				System.out.println (line);
			}
			inputStream.close ();
		}
		
		return message;
	}

}