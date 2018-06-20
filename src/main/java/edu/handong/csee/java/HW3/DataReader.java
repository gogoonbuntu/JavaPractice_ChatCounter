package edu.handong.csee.java.HW3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

		for (File f : files) {
			System.out.println("-------" + f.getName() + "-------");
			if (f.getName().endsWith(".txt")) {
				HashMap<String, ArrayList<Message>> temp = new HashMap<String, ArrayList<Message>>();
				temp = readFilesTXT(f);
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
			} else if (f.getName().endsWith(".csv")) {
				HashMap<String, ArrayList<Message>> temp = new HashMap<String, ArrayList<Message>>();
				temp = readFilesCSV(f);
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
		}

		return messages;
	}

	private File getDirectory(String Directory) {

		File myDirectory = new File(Directory);
		return myDirectory;
	}

	private File[] getListOfFilesFromDirectory(File dataDir) {

		return dataDir.listFiles();
	}

	private HashMap<String, ArrayList<Message>> readFilesTXT(File file) {

		HashMap<String, ArrayList<Message>> messages = new HashMap<String, ArrayList<Message>>();

		// for (int i = 0; i < dataDir.length; i++) {
		// String fileName = dataDir[i].getAbsolutePath();
		Scanner inputStream = null;
		String date = "";
		try {
			inputStream = new Scanner(file, "UTF-8");
		} catch (FileNotFoundException e) {
			System.out.println("Error opening the file " + file);
			System.exit(0);
		}

		while (inputStream.hasNextLine()) {
			String line = inputStream.nextLine();
			// String myStringToParse = "-------- 2018년 5월 21일 --------";

			if (line.matches("-+\\s[0-9]+.\\s[0-9]+.\\s[0-9]+.+")) {
				String pattern = "-+\\s([0-9]+).\\s([0-9]+).\\s([0-9]+).+";
				Pattern r = Pattern.compile(pattern);

				Matcher m = r.matcher(line);

				if (m.find()) {
					String month = m.group(2);
					if(Integer.parseInt(month)<10) month="0"+m.group(2);
					String day = m.group(3);
					if(Integer.parseInt(day)<10) day="0"+m.group(3);
					date = m.group(1) + "-" + month + "-" + day;
					continue;
				}
			}
			if (line.matches("\\[(.+)\\]\\s\\[(.+)\\s([0-9]+:[0-9]+)\\]\\s(.+)")) {
				String pattern = "\\[(.+)\\]\\s\\[(.+)\\s([0-9]+:[0-9]+)\\]\\s(.+)";
				Pattern r = Pattern.compile(pattern);
				Matcher m = r.matcher(line);

				if (m.find()) {
					String name = m.group(1);
					String ampm = m.group(2);
					String time = m.group(3);
					String strMessage = m.group(4);
					if (ampm.equals("오후") || ampm.equalsIgnoreCase("pm")) {
						String parts[] = time.split(":");
						int hour = Integer.parseInt(parts[0]);
						int min = Integer.parseInt(parts[1]);
						if (hour != 12)
							hour += 12;
						time = String.valueOf(hour) + ":" + String.valueOf(min);
					}
					else {
						String parts[] = time.split(":");
						int hour = Integer.parseInt(parts[0]);
						int min = Integer.parseInt(parts[1]);
						String hourS, minS;
						if(hour<10) hourS = "0"+String.valueOf(hour);
						else hourS = String.valueOf(hour);
						if(min<10) minS = "0"+String.valueOf(min);
						else minS = String.valueOf(min);
						time = hourS+":"+minS;
					}
					if (strMessage.equals("사진")) strMessage="Photo";
					Message msg = new Message(name, date, time, strMessage);
					System.out.println(msg.name + ", " + msg.date + ", " + msg.time + ", " + msg.line);
					if (!messages.containsKey(msg.getName())) {
						messages.put(name, new ArrayList<Message>());
					}
					messages.get(msg.getName()).add(msg);
				}
			}
		}
		inputStream.close();
		System.out.println("---------------파일읽기끝-----------------------");
		return messages;
	}

	private HashMap<String, ArrayList<Message>> readFilesCSV(File file) {

		// HashMap<String, ArrayList<Message>> messages = new HashMap<String,
		// ArrayList<Message>>();
		// HashMap<String, Integer> indexcounter = new HashMap<String,Integer>();
		// for (int i = 0; i < dataDir.length; i++) {
		String fileName = file.getAbsolutePath();
		Scanner inputStream = null;
		try {
			inputStream = new Scanner(file, "UTF-8");
		} catch (FileNotFoundException e) {
			System.out.println("Error opening the file " + fileName);
			System.exit(0);
		}

		while (inputStream.hasNextLine()) {
			String line = inputStream.nextLine();
			String pattern = "([0-9]+)\\-([0-9]+)\\-([0-9]+)\\s([0-9]+)\\:([0-9]+)\\:([0-9]+)\\,\\\"(.+)\\\"\\,\\\"(.+)\\\"";
			Pattern r = Pattern.compile(pattern);
			Matcher m = r.matcher(line);

			String name = "";
			String date = "";
			String time = "";
			String strMessage = "";
			if (line.matches(
					"([0-9]+)\\-([0-9]+)\\-([0-9]+)\\s([0-9]+)\\:([0-9]+)\\:([0-9]+)\\,\\\"(.+)\\\"\\,\\\"(.+)\\\"")) {
				if (m.find()) {
					name = m.group(7);
					date = m.group(1) + "-" + m.group(2) + "-" + m.group(3);
					time = m.group(4) + ":" + m.group(5);
					strMessage = m.group(8);
					if (strMessage.equals("사진")) strMessage="Photo";

					Message msg = new Message(name, date, time, strMessage);
					System.out.println(msg.name + ", " + msg.date + ", " + msg.time + ", " + msg.line);
					if (!messages.containsKey(msg.getName())) {
						messages.put(name, new ArrayList<Message>());
					}
					messages.get(msg.getName()).add(msg);
				}
			}
		}
		System.out.println("---------------파일읽기끝-----------------------");
		inputStream.close();
		// }
		return messages;

	}

}