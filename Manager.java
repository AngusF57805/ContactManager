import java.util.*;
//TODO only import relevent stuff
//import java.util.Set;
//import java.util.Scanner;

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

		setUI (0);
	
	}

	static void print (String str) {
		System.out.println(str);
	} 
	
	static String readScreen () {
		Scanner sc = new Scanner (System.in);
		return sc.nextLine();
	}

	static void writeToFile (String str) {
		TextFileManager textReader = new TextFileManager ();
		textReader.writeTextFile (str, true);
	}

	static String readFile () {
		TextFileManager textWriter = new TextFileManager ();
		return textWriter.readTextFile ();
	}

	static void initializeSets () {
		contacts = new HashSet<Contact>();
		meetings = new HashSet<Meeting>();
	}

	static void flush () {
		writeToFile (contacts.toString());
		writeToFile (meetings.toString());
	}

	static void createContacts () {
		contacts.add ();
	}

	static void createMeetings () {
		meetings.add ();
	}


	static void setUI (int page) {

		switch (page) {
			case 0:
				print ("Welcome to the Contacts Manager, what would you like to do?");
				//get input
				checkInput (page);
				break;
			case 1:
				print ("Here are the contacts you have");
				print (readFile ());
				break;
			
			default:
				print ("no ui page exists");
				setUI (0);
				break;
		}
	}

	static void checkInput (int page) {

		switch (page) {
			case 0:
				switch (readScreen ()) {
					case "list":
						setUI (1);
						break;
					case "quit":
						flush:
						//System.exit(0);
						return;
						break;
					default:
						print ("unknown command - type help for a list of commands");
						break;
				}
				break;
			default:
				print ("appliacation tried to switch to a page that does not exist!");
				break;
		}
	}
}
