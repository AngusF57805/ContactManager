import java.util.Set;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Date;
import java.util.*;

class Manager {

	static Set<Contact> contacts;
	static Set<Meeting> meetings;

	public static void main (String[] args) {
		
		/*
		 * read file
		 * put contents into sets
		 * start ui stuff
		 * save afterwards
		 */
		
		initializeSets ();
		createContacts ();
		createMeetings ();

		setUi (0);
	
	}

	static void print (String str) {
		System.out.println(str);
	} 
	
	static String readScreen () {
		Scanner sc = new Scanner (System.in);
		return sc.nextLine();
	}

	static void writeToFile (String str, boolean additiveWrite) {
		TextFileManager textReader = new TextFileManager ();
		textReader.writeTextFile (str, additiveWrite);
	}

	static String readFile (int n) {
		TextFileManager textWriter = new TextFileManager ();
		return textWriter.readTextFile ()[n];
	}

	static void clearFile () {
		TextFileManager textWriter = new TextFileManager ();
		textWriter.clearTextFile ();
	}

	static void initializeSets () {
		contacts = new HashSet<Contact>();
		meetings = new HashSet<Meeting>();
	}

	static void flush () {
		clearFile ();
		for (int i = 0; i < 1; i ++) {
			writeToFile(findContact(i).toString(), true);
		}
	}

	static void createContacts () {
		int i = 0;		
			contacts.add (new Contact (Integer.parseInt(readFile (i)), readFile (i + 1), readFile (i + 2)));
	}

	static void createMeetings () {
		meetings.add (new Meeting (new Date(), "test meeting"));
	}

	static Contact findContact (int n) {
		return contacts.toArray(new Contact[contacts.size()])[n];
	}	

	static void setUi (int page) {

		switch (page) {
			case 0:
				print ("Welcome to the Contacts Manager, what would you like to do?");
				//get input
				checkInput (page);
				break;
			case 1:
				print ("LIST OF CONTACTS:");
				for (int i = 0; i < 1 ; i ++) {
					print (findContact(i).id +  " : " + findContact(i).name + " : " + findContact(i).notes);
				}
				setUi (0);
				break;
			default:
				print ("no ui page exists");
				setUi (0);
				break;
		}
	}

	static void checkInput (int page) {
		switch (page) {
			case 0:
				switch (readScreen ()) {
					case "list":
						setUi (1);
						break;
					case "quit":
						flush ();
						return;
					default:
						print ("unknown command - type help for a list of commands");
						setUi (0);
						break;
				}

				break;
			default:
				print ("appliacation tried to switch to a page that does not exist!");
				break;
		}
	}
}
