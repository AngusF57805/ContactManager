import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
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
		String[] contents = new String[5];
		int i = 0;
		Scanner sc;
		try {
			sc = new Scanner (textFile);
			
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
			bw.write(content + "\n");
			bw.close();

			System.out.println("Added '" + content + "' to the text file.");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
