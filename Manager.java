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
		setUi(Page.HOME); //start the console ui on the home page
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
			writeToFile(findContact(i).toString() + "\n", true);
		}

		//read by fillSets method to see where meetings begin
		writeToFile("*END-CONTACTS*", true);

		//flush meetings
		for (int i = 0; i < meetings.size(); i ++) {
			writeToFile("\n" + findMeeting(i).toString() + "\n" + findMeeting(i).getAttendeesString(), true);
		}
	}

	static void fillSets() {
		
		TextFileManager tm = new TextFileManager();
		
		if (tm.getLineCount() < 1) {
			//dont bother if there are no lines in the text file
			return;
		}
		int i = 0;

		while (!tm.readTextFile()[i].equals("*END-CONTACTS*")) {
			contacts.add(new Contact(Integer.parseInt(tm.readTextFile()[i]), tm.readTextFile()[i + 1], tm.readTextFile()[i + 2]));
			i += 3;
		}

		//TODO is a for loop with i=i bad?
		for (i = ++i; i < tm.getLineCount(); i += 4) {
			meetings.add(new Meeting(Integer.parseInt(tm.readTextFile()[i]), new Date(), tm.readTextFile()[i + 2], tm.readTextFile()[i + 3]));
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
	static Meeting searchMeetings(int id) {
		//search meetings for an id
		for (Meeting meeting : meetings) {
			print ("id : " + id + " not equal to " + meeting.getId());
			if (meeting.getId() == id) {
				return meeting;
			}
		}
		return null;//TODO fix this, its super bad
	}

	//TODO
	static Meeting searchMeetings(String notes) {
		//search meetings for Notes? TODO no idea how to implement
		for (Meeting meeting : meetings) {
			if (meeting.getNotes().toLowerCase().equals(notes.toLowerCase())) {
				//result.concat(meeting);
				return meeting;
			}
		}
		return null;
		//return result; //TODO ditto + multiple contacts return
	}

	static int getNextContactId() {
		if (contacts.size() < 1) return 100;

		int highestId = findContact(0).getId();

		for (int i = 1; i < contacts.size(); i++) {
			if (findContact(i).getId() > highestId) {
				highestId = findContact(i).getId();
			}
		}
		
		return highestId + 1;
	}

	static int getNextMeetingId() {
		if (meetings.size() < 1) return 100;

		int highestId = findMeeting(0).getId();

		for (int i = 1; i < meetings.size() - 1 /*TODO not sure why i did this */; i++) {
			if (findMeeting(i).getId() > highestId) {
				highestId = findContact(i).getId();
			}
		}
		
		return highestId + 1;
	}
	
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
						print(findContact(i).toFancyString() + "\n");
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
				contacts.add(new Contact(getNextContactId(), tempName, readScreen()));
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
				print ("removed contact '" + searchContacts(tempId).toFancyString() + "'\n");
				contacts.remove(searchContacts(tempId));
				setUi(Page.HOME);
				break;
			case FINDC://search for a contact
				print("type the name of the contact you want to search for:");
				//TODO not exact string needed
				print(searchContacts(readScreen()).toFancyString() + "\n");
				setUi(Page.HOME);
				break;
			case LISTM://list c
				if (mettings.size() > 0) {
					print("LIST OF MEETINGS:\n");
				} else {
					print("NO MEETINGS FOUND!\n");
				}
				
				for (int i = 0; i < meetings.size(); i ++) {
					print(findMeeting(i).toFancyString() + "\n");
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
				meetings.add(new Meeting(getNextMeetingId(), new Date()/*TODO*/, readScreen(), ""));
				print("Meeting on '" + tempDate + "' added!\n");
				setUi(Page.HOME);
				break;
			case EDITM_1://edit c - 1
				print("type the ID of the meeting you want to edit:");
				tempId = Integer.parseInt(readScreen());
				setUi(Page.EDITM_2);
				break;
			case EDITM_2://edit c - 2
				print("type the new DATE of the meeting (leave blank to not change):");
				String oldTempDate = readScreen();
				tempDate = new Date();//TODO
				setUi(Page.EDITM_3);
				break;
			case EDITM_3://edit c - 3
				print("type the new NOTES about the meeting (leave blank to not change):");
				tempNotes = readScreen();
				if (tempNotes.equals("") || tempNotes == null) {
					//dont change the notes if user input is blank
					tempNotes = searchMeetings(tempId).getNotes();
				}
				meetings.add(new Meeting(tempId, new Date()/*TODO*/, tempNotes, ""));
				print("Meeting on '" + tempDate + "' edited.\n");
				meetings.remove(searchMeetings(tempId));
				setUi(Page.HOME);
				break;
			case DELM:
				print ("type the id of the meeting you want to delete:");
				//get id
				tempId = Integer.parseInt(readScreen());
				//remove the meeting (this way around so the name can be printed before it was deleted)
				print ("meeting '" + searchMeetings(tempId).toFancyString() + "' removed\n");
				meetings.remove(searchMeetings(tempId));
				setUi(Page.HOME);
				break;
			case FINDM://search for a contact
				//TODO dont know how to implment
				print("type the NOTES of the meeting you want to search for:");
				print(searchContacts(readScreen()).toFancyString() +"\n");
				setUi(Page.HOME);
				break;
			case VIEWM:
				//TODO
				print("type the id of the meeting you want to view:");
				tempId = Integer.parseInt(readScreen());
				//using the id get the meeting, then print attendees
				print(searchMeetings(tempId).getFancyAttendeesString());
				setUi(Page.HOME);
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
			default:
				print("appliacation get input from a page that does not exist!\n");
				break;
		}
	}
}
