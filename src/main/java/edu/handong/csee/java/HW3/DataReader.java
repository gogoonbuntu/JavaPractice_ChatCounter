package edu.handong.csee.java.HW3;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ListIterator;


public class DataReader {
	/* GetData to call all the inputs 
	public static void main(String[] args) {
		DataReader dr = new DataReader();
		dr.getData(args[3]);
	}*/
	public HashMap<String, ArrayList<Message>> getData(String strDir){

		/* 1. make a file in the directory */
		File myDirectory = getDirectory(strDir);

		/* 2. file type Array to contain file names and directory*/
		File[] files = getListOfFilesFromDirectory(myDirectory);
		
		/* 3. String Arraylist to save all the chat data */
		HashMap<String, ArrayList<Message>> messages = null;
		for(File f:files)
		{
			System.out.println("-------"+f.getName()+"-------");
			if(f.getName().endsWith(".txt")) 
				messages = readFilesTXT(files);
			else if(f.getName().endsWith(".csv")){
				messages = readFilesCSV(files);	
			}
		}
		
		return messages;
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

	private HashMap<String, ArrayList<Message>> readFilesTXT(File[] dataDir){

		HashMap<String, ArrayList<Message>> messages = new HashMap<String, ArrayList<Message>>();
		HashMap<String, Integer> indexcounter = new HashMap<String,Integer>();
		for(int i=0;i<dataDir.length;i++)
		{
			String fileName = dataDir[i].getAbsolutePath();
			Scanner inputStream = null;
			try {
				inputStream = new Scanner(new File(fileName),"UTF-8");
			}  catch (FileNotFoundException e) {
				System.out.println ("Error opening the file " + fileName);
				System.exit (0);
			}
			
			while (inputStream.hasNextLine()) {
				String line = inputStream.nextLine();
				//String myStringToParse = "-------- 2018년 5월 21일 --------";
				String pattern ="\\[(.+)\\]\\s\\[..\\s([0-9]+:[0-9]+)\\]\\s(.+)";
				Pattern r = Pattern.compile(pattern);
				Matcher m = r.matcher(line);

				String name = "";
				String time = "";
				String strMessage = "";
					
				if(m.find()) {
					name = m.group(1);
					time = m.group(2);
					strMessage = m.group(3);
					Message msg = new Message(name,time,strMessage);
					ArrayList<Message> msgList = messages.get(name);
					//System.out.println("name: "+msg.name+", time: "+msg.time+", \n\""+msg.line+"\"");
					if(msgList == null)
					{
						msgList = new ArrayList<Message>();
						msgList.add(msg);
						messages.put(name,msgList);
						indexcounter.put(name, 0);
					}
					else
					{
						if(!msgList.contains(msg)) {
							//System.out.println("이전: "+msgList.get(indexcounter.get(name)).name+", "+ msgList.get(indexcounter.get(name)).time+", "+msgList.get(indexcounter.get(name)).line);
							//System.out.println("현재: "+msg.name+", "+ msg.time+", "+msg.line);
							if((msgList.get(indexcounter.get(name)).time.equals(msg.time))&&(msgList.get(indexcounter.get(name)).line.equals(msg.line))){
								System.out.println("EQUAL!! :"+msg.name+", "+msg.time+", "+msg.line);
							}
							else {
								msgList.add(msg); 
								indexcounter.put(name, indexcounter.get(name)+1);
								System.out.println("확인완료, "+name+indexcounter.get(name));
							}
						}
					}
				}
			}
			inputStream.close ();
			
		}
		return messages;
		
	}
	private HashMap<String, ArrayList<Message>> readFilesCSV(File[] dataDir){

		HashMap<String, ArrayList<Message>> messages = new HashMap<String, ArrayList<Message>>();
		HashMap<String, Integer> indexcounter = new HashMap<String,Integer>();
		for(int i=0;i<dataDir.length;i++)
		{
			String fileName = dataDir[i].getAbsolutePath();
			Scanner inputStream = null;
			try {
				inputStream = new Scanner(new File(fileName),"UTF-8");
			}  catch (FileNotFoundException e) {
				System.out.println ("Error opening the file " + fileName);
				System.exit (0);
			}
			
			while (inputStream.hasNextLine()) {
				String line = inputStream.nextLine();
				String pattern ="([0-9]+)\\-([0-9]+)\\-([0-9]+)\\s([0-9]+)\\:([0-9]+)\\:([0-9]+)\\,\\\"(.+)\\\"\\,\\\"(.+)\\\"";
				Pattern r = Pattern.compile(pattern);
				Matcher m = r.matcher(line);

				String name = "";
				String time = "";
				String strMessage = "";
					
				if(m.find()) {
					name = m.group(7);
					time = m.group(4)+":"+m.group(5)+":"+m.group(6);
					strMessage = m.group(8);
					
					Message msg = new Message(name,time,strMessage);
					ArrayList<Message> msgList = messages.get(name);
					
					if(msgList == null)
					{
						msgList = new ArrayList<Message>();
						msgList.add(msg);
						messages.put(name,msgList);
						indexcounter.put(name, 0);
					}
					else
					{
						if(!msgList.contains(msg)) {
							//System.out.println("이전: "+msgList.get(indexcounter.get(name)).name+", "+ msgList.get(indexcounter.get(name)).time+", "+msgList.get(indexcounter.get(name)).line);
							//System.out.println("현재: "+msg.name+", "+ msg.time+", "+msg.line);
							
							if((msgList.get(indexcounter.get(name)).time.equals(msg.time))&&(msgList.get(indexcounter.get(name)).line.equals(msg.line))){
								System.out.println("EQUAL!! :"+msg.name+", "+msg.time+", "+msg.line);
							}
							else {
								msgList.add(msg); 
								indexcounter.put(name, indexcounter.get(name)+1);
								System.out.println("확인완료, "+name+indexcounter.get(name));
							}
						}
					}
				}
			}
			inputStream.close ();
			
		}
		return messages;
		
	}

}