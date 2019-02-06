package midterm;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class CategoryFiles {
	static BufferedReader reader;
	static ArrayList<String> categories = new ArrayList<String>();//list of files
	static ArrayList<String> categoryList = new ArrayList<String>();//list of words in category file

	public static ArrayList<String> categoryList() {//gets file names and stores in list		
		File[] files = new File("categories").listFiles();
		for (File file : files) {
		    if (file.isFile()) {
		        categories.add(file.getName());
		    }
		}
		return categories;
	}
	
	public static ArrayList<String> readFile(String dir) {	
		ArrayList<String> aList = new ArrayList<String>();
		try {
			reader = new BufferedReader(new FileReader(dir));
			String line = reader.readLine();
			while (line != null) {
				aList.add(line);
				line = reader.readLine();	
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return aList;
	}
	
	public static void writeToFile(ArrayList<String> list, String fileName) {
		Path txt = Paths.get("HighScores");
		File remove = new File(fileName);
		remove.delete();

		try {
			for (String line : list) {
				line = line + "\n";
				Files.write(txt, line.getBytes(), StandardOpenOption.APPEND);
			}
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	public static void clearArrays() {
		categories.clear();
		categoryList.clear();
	}
	
	public static void closeReader() {
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
