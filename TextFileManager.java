import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class TextFileManager {
	//converted to method used by cave of programming [https://youtu.be/Sm9yoju1me0]
	
	// TODO add file path
	private final String filePath = "~/Desktop/ContactsManagerSaveFile.txt";
	private File textFile;
	private String contents = "";
	
	public TextFileManager () {
		try {
			SetUpTextFile ();
		} catch (FileNotFoundException e) {
			// throws an error if file is not found
			e.printStackTrace();
		}
	}
	
	private void SetUpTextFile () throws FileNotFoundException {
		textFile = new File (filePath);
		
		try {
			if (!textFile.exists()) {
				textFile.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String ReadTextFile() {
		//set the contents string to nothing
		contents = "";
		Scanner sc;
		try {
			sc = new Scanner (textFile);
			
			while (sc.hasNextLine()) {
				//cycle through the text file, adding it to contents
				contents += sc.nextLine() + "\n";
			}
			
			sc.close();			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return contents;
	}
	
	public void WriteTextFile(String content) {
		try {
			//!TODO decide boolean (set to false for overwrite, true for additive)
			FileWriter fw = new FileWriter(textFile.getAbsoluteFile(),true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();

			System.out.println("Added '" + content + "' to the text file.\n");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
