package ContactManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.io.IOException;
import java.util.Scanner;

public class TextFileManager {
	//converted to method used by cave of programming [https://youtu.be/Sm9yoju1me0]
	
	private File textFile;
	//what the file should be called
	private final String filename = "SaveFile.txt";
	//converted into a path that will work on any os / user
	private final String filePath = System.getProperty("user.dir") + File.separator + "ContactManager" +  File.separator + filename; 
	
	public TextFileManager () {
		//just sets up the textfile
		try {
			textFile = new File (filePath);
			if (!textFile.exists()) {
				textFile.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String[] readTextFile() {
		//set the contents
		String[] contents = new String[getLineCount()];
		
		try {
			Scanner sc = new Scanner (textFile);
			
			for (int i = 0; i < getLineCount(); i ++) {
				//cycle through the text file, adding it to contents
				contents[i] = sc.nextLine();
			}
	
			sc.close();			

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return contents;
	}
	
	public void writeTextFile(String content, boolean additiveWrite) {
		try {
			// set additiveWrite to false for overwrite, true for additive)
			FileWriter fw = new FileWriter(textFile.getAbsoluteFile(), additiveWrite);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();

			System.out.println("Added '" + content + "' to the text file.");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void clearTextFile() {
		//just overwrites the text file with an empty string
		writeTextFile("", false);
	}

	public int getLineCount () {
		try {
			int lines = 0;

			FileReader fr = new FileReader(textFile);
			LineNumberReader lnr = new LineNumberReader(fr);

			while (lnr.readLine() != null){
				lines ++;
			}

			lnr.close();// does this close fr too?

			return lines;
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//not 100% sure what to put here
		}
		
		return 0;
	}
}
