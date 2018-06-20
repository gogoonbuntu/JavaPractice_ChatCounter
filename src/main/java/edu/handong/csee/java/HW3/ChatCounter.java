package edu.handong.csee.java.HW3;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatCounter {

	String inputPath;
	String outputPath;
	boolean help;

	public static void main(String[] args) {

		ChatCounter myChatCounter = new ChatCounter();
		myChatCounter.run(args);
	}

	private void run(String[] args) {
		Options options = createOptions();
		//definition, parsing, interlogging
		
		if(parseOptions(options, args)){
			if (help){
				printHelp(options);
				return;
			}
			
			// TODO show the number of files in the path
			
			// TODO list all files in the path
			DataReader dataReader = new DataReader();
			HashMap<String, ArrayList<Message>> messages = dataReader.getData(inputPath);
			HashMap<String,HashMap<String,String>> result = new HashMap<String,HashMap<String,String>>();
			
			// CHECK REPEAT
			for(String key:messages.keySet())
			{
				for(Message msg:messages.get(key))
				{
					if(!result.containsKey(msg.getName()))
					{
						result.put(msg.getName(), new HashMap<String,String>());
					}
					result.get(msg.getName()).put(msg.getTime(), msg.getLine());	
				}
			}
			/* I used double hashmap to remove repeated messages.
			 * but it cannot receive different messages in single minute
			 * but i could not think of better method.
			 */
			for(String key:result.keySet())
			{
				for(String key2:result.get(key).keySet())
				{
					System.out.println("*"+key+", "+key2+", "+result.get(key).get(key2));
				}
			}
			
			HashMap<String,Integer> counter = countMessages(result);
				
			List<String> ids = sortByValue(counter);
				
			ArrayList<String> linesToWrite = new ArrayList<String>();
			for(String key:ids) {
				linesToWrite.add(key+","+counter.get(key));
			}
			DataWriter writer = new DataWriter();
			writer.writeAFile(linesToWrite, outputPath);
			System.out.println("Your program is terminated. (This message is shown because you turned on -v option!");
	
		}
	}

	private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();

		try {

			CommandLine cmd = parser.parse(options, args);

			outputPath = cmd.getOptionValue("o");
			inputPath = cmd.getOptionValue("i");
			help = cmd.hasOption("h");

		} catch (Exception e) {
			printHelp(options);
			return false;
		}

		return true;
	}
	
	// Definition Stage
	private Options createOptions() {
		Options options = new Options();

		// add options by using OptionBuilder
		options.addOption(Option.builder("i").longOpt("input")
				.desc("Set a path of a directory or a file to display")
				.hasArg()
				.argName("a directory path")
				.required()
				.build());

		// add options by using OptionBuilder
		options.addOption(Option.builder("o").longOpt("output")
				.desc("a file path name where the count results are saved")
				.hasArg()     // this option is intended not to have an option value but just an option
				.argName("a file path")
				.required() // this is an optional option. So disabled required().
				.build());
		
		// add options by using OptionBuilder
		options.addOption(Option.builder("h").longOpt("help")
		        .desc("Help")
		        .build());

		return options;
	}
	
	private void printHelp(Options options) {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		String header = "CLI test program";
		String footer ="\nPlease report issues at https://github.com/lifove/CLIExample/issues";
		formatter.printHelp("CLIExample", header, options, footer, true);
	}
	private List<String> sortByValue(HashMap<String,Integer> k) {
		List<String> q = new ArrayList();
		q.addAll(k.keySet());
		
		Collections.sort(q,new Comparator<Object>(){
			public int compare(Object o1, Object o2) {
				Object v1 = k.get(o1);
				Object v2 = k.get(o2);
				return (((Comparable<Object>) v2).compareTo(v1));
			}
		});
		return q;
	}
	
	private HashMap<String,Integer> countMessages(HashMap<String,HashMap<String,String>> result) {
		HashMap<String, Integer> counter = new HashMap<String, Integer>();
		for(String key:result.keySet())
		{
			counter.put(key,result.get(key).size());
		}
		return counter;
	}
}