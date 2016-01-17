import java.util.Set;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Date;
import java.util.*;//TODO fix this

class Manager {

	static Set<Contact> contacts;
	static Set<Meeting> meetings;
	static String tempName;

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
		System.out.print(str);
	} 
	
	static String readScreen () {
		Scanner sc = new Scanner (System.in);
		return sc.nextLine();
	}

	static void writeToFile (String str, boolean additiveWrite) {
		TextFileManager tm = new TextFileManager ();
		tm.writeTextFile (str, additiveWrite);
	}

	static void clearFile () {
		TextFileManager tm = new TextFileManager ();
		tm.clearTextFile ();
	}

	static void initializeSets () {
		contacts = new HashSet<Contact>();
		meetings = new HashSet<Meeting>();
	}

	static void flush () {
		clearFile ();
		for (int i = 0; i < contacts.size(); i ++) {
			writeToFile(findContact(i).toString(), true);
			if (i != contacts.size() - 1) writeToFile ("\n", true);
		}
	}

	static void createContacts () {
		TextFileManager tm = new TextFileManager ();
		print (tm.getLineCount() + " = linecount");
		for (int i = 0; i < 6 /*tm.getLineCount()*/; i += 3) {
			contacts.add (new Contact (Integer.parseInt(/*tm.readTextFile ()[i])*/"10"), tm.readTextFile ()[i + 1], tm.readTextFile ()[i + 2]));
		}
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
				print ("Welcome to the Contacts Manager, what would you like to do?\n");
				//get input
				checkInput (page);
				break;
			case 1:
				print ("LIST OF CONTACTS:\n");
				for (int i = 0; i < contacts.size(); i ++) {
					print (findContact(i).id +  " : " + findContact(i).name + " : " + findContact(i).notes + "\n");
				}
				setUi (0);
				break;
			case 2:
				print ("type the FULL NAME of the contact:");
				checkInput (page);
				setUi (3);
				break;
			case 3:
				print ("type any NOTES about this person:");
				checkInput (page);
				print ("Contact '" + tempName + "' added!\n");
				setUi (0);
				break;
			default:
				print ("tried to go to a ui page that does not exist\n");
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
					case "add contact":
						setUi (2);
						break;
					case "quit":
						flush ();
						return;
					default:
						print ("unknown command - type help for a list of commands\n");
						setUi (0);
						break;
				}

				break;
			case 2:
				tempName = readScreen ();
				break;
			case 3:
				contacts.add (new Contact (tempName, readScreen ()));
				break;
			default:
				print ("appliacation tried to switch to a page that does not exist!\n");
				break;
		}
	}
}
