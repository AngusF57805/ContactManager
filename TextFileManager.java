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
	
	// TODO add file path
	private final String filePath = "/Users/angusfindlay/Desktop/ContactsManagerSaveFile.txt";
	private File textFile;
	
	public TextFileManager () {
		try {
			setUpTextFile ();
		} catch (FileNotFoundException e) {
			// throws an error if file is not found
			e.printStackTrace();
		}
	}
	
	private void setUpTextFile () throws FileNotFoundException {
		textFile = new File (filePath);
		
		try {
			if (!textFile.exists()) {
				textFile.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String[] readTextFile() {
		//set the contents
		String[] contents = new String[100];
		Scanner sc;
		try {
			sc = new Scanner (textFile);
			contents = new String[getLineCount()];
				
			int i = 0;
			while (sc.hasNextLine()) {
				//cycle through the text file, adding it to contents
				contents[i] = sc.nextLine();
				i ++;
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
		try {
			// just overwrites the text file with nothing
			FileWriter fw = new FileWriter(textFile.getAbsoluteFile(), false);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("");
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getLineCount () {
		try {
			int lines = 0;

			FileReader fr = new FileReader(textFile);
			LineNumberReader lnr = new LineNumberReader(fr);

			while (lnr.readLine() != null){
				lines ++;
			}

			lnr.close();

			return lines;
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//not 100% sure why this is in here
		}
		return 0;
	}
}
