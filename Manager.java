package ContactManager;

import java.util.Set;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Date;
import java.util.*;//TODO fix this

class Manager {

	static Set<Contact> contacts;
	static Set<Meeting> meetings;
	static String tempName;
	static Date tempDate;
	static int tempId;

	static enum Page {
		/* enum which stores all the possible pages of the ui
		 * HOME - main / welcome menu
		 * LIST - display contents of the hashset
		 * ADD  - put something into the hashset
		 * EDIT - change the objects in the hashset
		 * DEL  - remove something from a hashset
		 * FIND - search for a name / notes / date in an object in the hashset
		 * VIEWM - shows the conacts the attend a meeting
		 * ADDTOM - adds to a meeting
		 * DELFROMM - deletes from m
		 */
		HOME,
		LISTC, ADDC_1, ADDC_2, EDITC_1, EDITC_2, EDITC_3, DELC, FINDC,
		LISTM, ADDM_1, ADDM_2, EDITM_1, EDITM_2, EDITM_3, DELM, FINDM,
		VIEWM, ADDTOM_1, ADDTOM_2, DELFROMM
	}

	public static void main(String[] args) {
		initializeSets(); //initialize the contacts / meetings HashSets TODO remove?
		fillSets(); //from text file
		setUi(Page.HOME); //home page
	}

	static void print(String str) {
		System.out.print(str);
	}

	static String readScreen() { //maybe make a version for nextInt
		Scanner sc = new Scanner(System.in);
		return sc.nextLine().trim();
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
		//flush contacts
		for (int i = 0; i < contacts.size(); i ++) {
			writeToFile(findContact(i).toString(), true);
			//write a new line (unless it is the last contact)
			if (i != contacts.size() - 1) writeToFile("\n", true);
		}
		//TODO flush meetings
		/*for (int i = 0; i < meetings.size(); i ++) {
			writeToFile(findMeeting(i).toString(), true);
			//write a new line (unless it is the last contact)
			if (i != meeeting.size() - 1) writeToFile("\n", true);
		}*/
	}

	static void fillSets() {
		TextFileManager tm = new TextFileManager();
		int i = 0;

		print ("DONE");	
		while (!tm.readTextFile()[i].equals("*END-CONTACTS*")) {
			contacts.add(new Contact(Integer.parseInt(tm.readTextFile()[i]), tm.readTextFile()[i + 1], tm.readTextFile()[i + 2]));
			i += 3;
		}

		//TODO is a for loop with i=i bad?
		for (i = i + 1; i < tm.getLineCount(); i += 3) {
			meetings.add(new Meeting(new Date(), tm.readTextFile()[i + 1], tm.readTextFile()[i + 2]));
		}
	}

	//find a contact based on an array position
	static Contact findContact(int n) {
		return contacts.toArray(new Contact[contacts.size()])[n];
	}

	//find a contact based on an array position
	static Meeting findMeeting(int n) {
		return meetings.toArray(new Meeting[meetings.size()])[n];
	}

	//search for an id in contacts
	static Contact searchContacts(int id) {
		for (Contact contact : contacts) {
			if (contact.getId() == id) {
				return contact;
			}
		}
		return null;//TODO fix this, its super bad
	}

	static Contact searchContacts(String name) {
		//search contacts for a name
		for (Contact contact : contacts) {
			if (contact.getName().toLowerCase().equals(name.toLowerCase())) {
				//result.concat(contact);
				return contact;
			}
		}
		return null;
		//return result; //TODO ditto + multiple contacts return
	}

	//TODO
	static Meeting searchMeetings(int id) {return null;}

	//TODO
	static Meeting searchMeetings(String name) {return null;}

	static void setUi(Page page) {

		switch (page) {
			case HOME://main menu
				print("Welcome to the Contacts Manager, what would you like to do?\n");
				//get input
				checkInput(page);
				break;
			case LISTC://list all contacts
				if (contacts.size () < 1) {
					print("No contacts found\n");
				} else {
					print("LIST OF CONTACTS:\n");
					for (int i = 0; i < contacts.size(); i ++) {
						print(findContact(i).toFancyString());
					}
				}
				setUi(Page.HOME);
				break;
			case ADDC_1://add contact part 1
				print("type the FULL NAME of the contact:");
				tempName = readScreen();
				setUi(Page.ADDC_2);
				break;
			case ADDC_2://add contact part 2
				print("type any NOTES about this person:");
				contacts.add(new Contact(tempName, readScreen()));
				print("Contact '" + tempName + "' added!\n");
				setUi(Page.HOME);
				break;
			case EDITC_1://edit c - 1
				print("type the id of the contact you want to edit:");
				tempId = Integer.parseInt(readScreen());
				setUi(Page.EDITC_2);
				break;
			case EDITC_2://edit c - 2
				print("type the new FULL NAME of the contact (leave blank to not change):");
				tempName = readScreen();
				if (tempName.equals("") || tempName == null) {
					tempName = searchContacts(tempId).getName();
				}
				setUi(Page.EDITC_3);
				break;
			case EDITC_3://edit c - 3
				print("type any new NOTES about the contact (leave blank to not change):");
				String tempNotes = readScreen();
				if (tempNotes.equals("") || tempNotes == null) {
					tempNotes = searchContacts(tempId).getNotes();
				}
				contacts.remove(searchContacts(tempId));
				contacts.add(new Contact(tempId, tempName, tempNotes));
				print("Contact '" + tempName + "' edited.\n");
				setUi(Page.HOME);
				break;
			case DELC:
				print ("type the id of the contact you want to delete:");
				//get id
				tempId = Integer.parseInt(readScreen());
				//remove the contact (this way around so the name can be printed before it was deleted)
				print ("removed contact '" + searchContacts(tempId).toFancyString());
				contacts.remove(searchContacts(tempId));
				setUi(Page.HOME);
				break;
			case FINDC://search for a contact
				print("type the name of the contact you want to search for:");
				//TODO not exact string needed
				print(searchContacts(readScreen()).toFancyString());
				setUi(Page.HOME);
				break;
			case LISTM://list c
				print("LIST OF MEETINGS:\n");
				for (int i = 0; i < meetings.size(); i ++) {
					print(findMeeting(i).toFancyString());
				}
				setUi(Page.HOME);
				break;
			case ADDM_1://add c - 1
				print("type the DATE of the meeting:");
				tempName = readScreen();
				setUi(Page.ADDM_2);
				break;
			case ADDM_2://add c - 2
				print("type the NOTES about this meeting:");
				contacts.add(new Contact(tempName, readScreen()));
				print("Meeting  on'" + tempDate + "' added!\n");
				setUi(Page.HOME);
				break;
			case EDITM_1://edit c - 1
				print("type the ID of the meeting you want to edit:");
				tempId = Integer.parseInt(readScreen());
				setUi(Page.EDITM_2);
				break;
			case EDITM_2://edit c - 2
				print("type the new DATE of the meeting (leave blank to not change):");
				tempName = readScreen();
				setUi(Page.EDITM_3);
				break;
			case EDITM_3://edit c - 3
				print("type the new NOTES about the meeting (leave blank to not change):");
				contacts.remove(searchContacts(tempId));
				contacts.add(new Contact(tempId, tempName, readScreen()));
				print("Meeting on '" + tempDate + "' edited.\n");
				setUi(Page.HOME);
				break;
			case DELM:
				print ("type the id of the meeting you want to delete:");
				//get id
				tempId = Integer.parseInt(readScreen());
				//remove the meeting (this way around so the name can be printed before it was deleted)
				print ("meeting '" + searchMeetings(tempId).toFancyString() + "' removed");
				meetings.remove(searchMeetings(tempId));
				break;
			case FINDM://search for a contact
				print("type the name of the meeting you want to search for:");
				print(searchContacts(readScreen()).toFancyString());
				setUi(Page.HOME);
				break;
			case VIEWM:
				//TODO
				break;
			case ADDTOM_1:
				//TODO
				break;
			case ADDTOM_2:
				//TODO
				break;
			case DELFROMM:
				//TODO
				break;
			default:
				print("tried to go to a ui page that does not exist\n");
				setUi(Page.HOME);
				break;
		}
	}

	static void checkInput(Page page) {
		switch (page) {
			/* all the commands you can enter from the main start page
			 * basically just converts string inputs into page enums
			 */
			case HOME:
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
					case "remove contact": case "delete contact":
						setUi(Page.DELC); //edit c - 1
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
					case "remove meeting": case "delete meeting":
						setUi(Page.DELM);
						break;
					case "find meeting"://find m
						setUi(Page.FINDM);
						break;
					case "view meeting"://view m
						setUi(Page.VIEWM);
						break;
					case "add to meeting"://add to m
						setUi(Page.ADDTOM_1);
						break;
					case "remove from meeting":
						setUi(Page.DELFROMM);
						break;
					case "help"://help - shows all commands (should really be in setUi but easyer to have it here)
						print ("Here are all the commands:\n\n");
						print ("list contacts\nadd contact\nedit contact\nremove contact\nfind contact\n\n");
						print ("list meetings\nadd meeting\nedit meeting\nremove meeting\nfind meeting\nview meeting\nadd to meeting\nremove from meeting\n\n");
						print ("quit\nhelp\n\n");
						setUi (Page.HOME);
						break;
					case "quit"://quit
						flush();
						return;
					default:
						print("unknown command - type 'help' for a list of commands\n");
						setUi(Page.HOME);
						break;
				}
				break;
			/*case ADDC_1:
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
				if (tempName.equals("") || tempName == null) {
					tempName = searchContacts(tempId).getName();
				}
				break;
			case EDITC_3:
				String tempNotes = readScreen();
				if (tempNotes.equals("") || tempNotes == null) {
					tempNotes = searchContacts(tempId).getNotes();
				}
				contacts.remove(searchContacts(tempId));
				contacts.add(new Contact(tempId, tempName, tempNotes));
				break;
			case DELC:
				tempId = Integer.parseInt(readScreen());
				break;
			case FINDC:
				//TODO not exact string
				print(searchContacts(readScreen()).toFancyString());
				break;
			case ADDM_1:
				tempName = readScreen();
				break;
			case ADDM_2:
				contacts.add(new Contact(tempName, readScreen()));
				break;
			case EDITM_1:
				tempId = Integer.parseInt(readScreen());
				break;
			case EDITM_2:
				tempName = readScreen();
				break;
			case EDITM_3:
				contacts.remove(searchContacts(tempId));
				contacts.add(new Contact(tempId, tempName, readScreen()));
				break;
			case DELM:
				tempId = Integer.parseInt(readScreen());
				break;
			case FINDM:
				print(searchContacts(readScreen()).toFancyString());
				break;
			case VIEWM:
				//TODO
				break;
			case ADDTOM_1:
				//TODO
				break;
			case ADDTOM_2:
				//TODO
				break;
			case DELFROMM:
				//TODO
				break;*/
			default:
				print("appliacation tried to switch to a page that does not exist!\n");
				break;
		}
	}
}
