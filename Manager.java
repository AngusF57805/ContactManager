import java.util.Set;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Date;
import java.util.*;//TODO fix this

class Manager {

	static Set<Contact> contacts;
	static Set<Meeting> meetings;
	static String tempName;
	static int tempId;

	static enum Page {
		HOME,
		LISTC, ADDC_1, ADDC_2, EDITC_1, EDITC_2, EDITC_3, FINDC,
		LISTM, ADDM_1, ADDM_2, EDITM_1, EDITM_2, EDITM_3, FINDM
	}

	public static void main(String[] args) {
		initializeSets(); //initialize the contacts/ meetings HashSets
		createContacts(); //from text file
		createMeetings(); //from text file
		setUi(Page.HOME); //home page
	}

	static void print(String str) {
		System.out.print(str);
	}

	static String readScreen() { //maybe make a version for nextInt
		Scanner sc = new Scanner(System.in);
		return sc.nextLine();
	}
	
	static void writeToFile(String str, boolean additiveWrite) {
		TextFileManager tm = new TextFileManager();
		tm.writeTextFile(str, additiveWrite);
	}

	static void clearFile() {
		TextFileManager tm = new TextFileManager();
		tm.clearTextFile();
	}

	static void initializeSets() {
		contacts = new HashSet<Contact>();
		meetings = new HashSet<Meeting>();
	}

	static void flush() {
		clearFile();
		for (int i = 0; i < contacts.size(); i ++) {
			writeToFile(findContact(i).toString(), true);
			if (i != contacts.size() - 1) writeToFile("\n", true);
		}
	}

	static void createContacts() {
		TextFileManager tm = new TextFileManager();
		for (int i = 0; i < tm.getLineCount(); i += 3) {
			contacts.add(new Contact(Integer.parseInt(tm.readTextFile()[i]), tm.readTextFile()[i + 1], tm.readTextFile()[i + 2]));
		}
	}

	static void createMeetings() {
		meetings.add(new Meeting(new Date(), "test meeting"));
	}

	static Contact findContact(int n) {//find a contact based on an array position
		return contacts.toArray(new Contact[contacts.size()])[n];
	}

	static Contact searchContacts(int id) {
		for (Contact contact : contacts) {
			if (contact.getId() == id) {
				return contact;
			}	
		}
		return null;//TODO fix this, its super bad
	}

	static Contact searchContacts(String name) {
		for (Contact contact : contacts) {
			if (contact.getName().equals(name)) {
				return contact;
			}
		}
		return null; //TODO ditto
	}

	static void setUi(Page page) {

		switch (page) {
			case HOME://home
				print("Welcome to the Contacts Manager, what would you like to do?\n");
				//get input
				checkInput(page);
				break;
			case LISTC://list c
				print("LIST OF CONTACTS:\n");
				for (int i = 0; i < contacts.size(); i ++) {
					print("- " + findContact(i).id +  " : " + findContact(i).name + " : " + findContact(i).notes + "\n");
				}
				setUi(Page.HOME);
				break;
			case ADDC_1://add c - 1
				print("type the FULL NAME of the contact:");
				checkInput(page);
				setUi(Page.ADDC_2);
				break;
			case ADDC_2://add c - 2
				print("type any NOTES about this person:");
				checkInput(page);
				print("Contact '" + tempName + "' added!\n");
				setUi(Page.HOME);
				break;
			case EDITC_1://edit c - 1
				print("type the id of the contact you want to replace:");
				checkInput(page);
				setUi(Page.EDITC_2);
				break;
			case EDITC_2://edit c - 2
				print("type the new FULL NAME of the contact (leave blank to not change):");
				checkInput(page);
				setUi(Page.EDITC_3);
				break;
			case EDITC_3://edit c - 3
				print("type any new NOTES about the contact (leave blank to not change):");
				checkInput(page);
				print("Contact '" + tempName + "' edited.\n");
				setUi(Page.HOME);
				break;
			case FINDC://search for a contact
				print("type the name of the contact you want to search for:");
				checkInput(page);
				setUi(Page.HOME);
				break;
			/*case LISTM://
				print("");
				checkInput(page);
				setUi(Page.HOME);
				break;
			case ADDM_1://
				print("");
				checkInput(page);
				setUi(Page.HOME);
				break;
			case ADDM_2://
				print("");
				checkInput(page);
				setUi(Page.HOME);
				break;
			case EDITM_1://
				print("");
				checkInput(page);
				setUi(Page.HOME);
				break;
			case EDITM_2://
				print("");
				checkInput(page);
				setUi(Page.HOME);
				break;
			case EDITM_3://
				print("");
				checkInput(page);
				setUi(Page.HOME);
				break;
			case FINDM://
				print("");
				checkInput(page);
				setUi(Page.HOME);
				break;*/
			default:
				print("tried to go to a ui page that does not exist\n");
				setUi(Page.HOME);
				break;
		}
	}

	static void checkInput(Page page) {
		switch (page) {
			case HOME://all the commands you can enter from the main start page
				switch (readScreen()) {
					case "list contacts": //list c
						setUi(Page.LISTC);//list c
						break;
					case "add contact"://add c
						setUi(Page.ADDC_1);//add c - 1
						break;
					case "edit contact"://edit c
						setUi(Page.EDITC_1); //edit c - 1
						break;
					case "find contact"://find c
						setUi(Page.FINDC);
						break;
					case "list meetings"://list m
						setUi(Page.LISTM);
						break;
					case "add meeting"://add m
						setUi(Page.ADDM_1);
						break;
					case "edit meeting"://edit m
						setUi(Page.EDITM_1);
						break;
					case "find meeting"://find m
						setUi(Page.FINDM);
						break;
					case "quit"://quit
						flush();
						return;
					default:
						print("unknown command - type help for a list of commands\n");
						setUi(Page.HOME);
						break;
				}

				break;
			case ADDC_1: 
				tempName = readScreen();
				break;
			case ADDC_2:
				contacts.add(new Contact(tempName, readScreen()));
				break;
			case EDITC_1:
				tempId = Integer.parseInt(readScreen());
				break;
			case EDITC_2:
				tempName = readScreen();
				break;
			case EDITC_3:
				contacts.remove(searchContacts(tempId));
				contacts.add(new Contact(tempId, tempName, readScreen()));
				break;
			case FINDC:
				print(searchContacts(readScreen()).toString());
			/*case Page.:
				break;
			case Page.:
				break;
			case Page.:
				break;
			case Page.:
				break;
			case Page.:
				break;*/
			default:
				print("appliacation tried to switch to a page that does not exist!\n");
				break;
		}
	}
}
