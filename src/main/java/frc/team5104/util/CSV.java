/*BreakerBots Robotics Team 2019*/
package frc.team5104.util;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

import frc.team5104.util.CrashLogger.Crash;

/**
 * Saves a csv file of specific data for tuning/debugging.
 */
public class CSV {
	public static interface CSVLoggable {
		String[] getCSVHeader();
		String[] getCSVData();
		String getCSVName();
	}
	public static class CSVLoggableObject implements CSVLoggable {
		private String[] header = null;
		private String[] lastData = null;
		public String name = null;
		public CSVLoggableObject(String name, String[] header) { 
			this.name = name; 
			this.header = header;
			init(this);
		}
		public void update(String[] data) { lastData = data; }
		public String[] getCSVData() { return lastData; }
		public String[] getCSVHeader() { return header; }
		public String getCSVName() { return name; }
	}
	
	private static class CSVRunner {
		String content;
		String fileName;
		CSVLoggable target;
		public CSVRunner(CSVLoggable target) { 
			this.target = target;
			content += stringArrayToString(target.getCSVHeader()) + '\n';
			fileName = target.getCSVName();
			if (fileName.indexOf(".") != -1)
				fileName = fileName.substring(0, fileName.indexOf("."));
			fileName += ".csv";
		}
		public void update() { 
			String data = stringArrayToString(target.getCSVData());
			if (data != null)
				content += data + '\n'; 
		}
	}
	private static ArrayList<CSVRunner> runnables = new ArrayList<CSVRunner>();
	
	/**
	 * Initialized the CSV class with a specified CSV target
	 * @param csvTarget A class that can be logged to a csv
	 */
	public static void init(CSVLoggable csvTarget) {
		if (csvTarget != null)
			runnables.add(new CSVRunner(csvTarget));
	}
	
	/**
	 * Updated with new data from the pre-specified CSV target
	 */
	public static void handle() {
		try { update(); } catch (Exception e) { CrashLogger.logCrash(new Crash("main", e)); }
	}
	private static void update() {
		for (CSVRunner runner : runnables) {
			try {
				runner.update();
			} catch (Exception e) { CrashLogger.logCrash(new Crash("main", e)); }
		}
	}
	
	/**
	 * Saves the files onto the roborio
	 * @param folder The folder on the roborio
	 */
	public static void writeFile(String folder) {
		try {
			//Anti dumb
			String path = folder;
			if (path.indexOf("lvuser") != -1)
				path = path.substring(path.indexOf("lvuser") + 6);
			if (path.charAt(0) == '/')
				path = path.substring(1);
			path = "/home/lvuser/" + path;
			if (path.charAt(path.length() - 1) != '/')
				path += "/";
			
			//Create the directory if it doesn't exist
			File directory = new File(path);
			if (!directory.exists())
				directory.mkdir();
			
			//Save the files
			for (CSVRunner runner : runnables) {
				try {
					PrintWriter writer = new PrintWriter(path + runner.fileName, "UTF-8");
					writer.print(runner.content);
					writer.close();
				} catch (Exception e) { CrashLogger.logCrash(new Crash("main", e)); }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Takes a string array and turns it into a string (separated by commas)
	private static String stringArrayToString(String[] stringArray) {
		if (stringArray != null) {
			String returnValue = "";
			for(int i = 0; i < stringArray.length; i++) 
				returnValue += stringArray[i] + (i < stringArray.length - 1 ? ", " : "");
			return returnValue;
		}
		return null;
	}
}
