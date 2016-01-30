package ContactManager;

import java.util.Set;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.text.ParseException;
import java.lang.Exception;

class Manager {
	/* TODO LIST
	 * detailed help
	 * better search
	 */
	static Set<Contact> contacts;
	static Set<Meeting> meetings;

	static String tempName;
	static Date tempDate;
	static int tempId;
	static int tempAttendeeId;

	public static final String dateFormatString = "yyyy-MM-dd HH:mm:ss";

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
		 * DELFROMM_1 - deletes from m
		 * DELFROMM_2 - deletes from m
		 */
		HOME,
		LISTC, ADDC_1, ADDC_2, EDITC_1, EDITC_2, EDITC_3, DELC, FINDC,
		LISTM, ADDM_1, ADDM_2, EDITM_1, EDITM_2, EDITM_3, DELM, FINDM,
		VIEWM, ADDTOM_1, ADDTOM_2, DELFROMM_1, DELFROMM_2
	}

	public static void main(String[] args) {
		//initialize the contacts / meetings HashSets
		contacts = new HashSet<Contact>();
		meetings = new HashSet<Meeting>();

		fillSets(); //fill hashsets with data from text file
		print("~ Welcome to the Contacts Manager. Type 'help' for a list of commands.\n");
		setUi(Page.HOME); //start the console ui on the home page
	}

	static void print(String str) {
		//shortcut print method
		System.out.print(str);
	}

	static String readScreen() {
		//shortcut scan method
		Scanner sc = new Scanner(System.in);
		return sc.nextLine().trim();
	}
	
	static Date toDate(String str) {
		//used steves email
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(Manager.dateFormatString, Locale.UK);
		try {
			date = sdf.parse(str);
			return date;
		} catch (ParseException e) {
			print("* ERROR: Date conversion failed for date:" + str + "\n");
		}

		return null;
	}

	static void writeToFile(String str, boolean additiveWrite) {
		TextFileManager tm = new TextFileManager();
		tm.writeTextFile(str, additiveWrite);
	}

	static void clearFile() {
		TextFileManager tm = new TextFileManager();
		tm.clearTextFile();
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
			writeToFile("\n" + findMeeting(i).toString(), true);
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

		for (i = ++i; i < tm.getLineCount(); i += 4) {
			meetings.add(new Meeting(Integer.parseInt(tm.readTextFile()[i]), toDate(tm.readTextFile()[i + 1]), tm.readTextFile()[i + 2], tm.readTextFile()[i + 3]));
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
		return null;
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
		//return result; //TODO less precise multiple contacts return
	}

	//search for an id in meetings
	static Meeting searchMeetings(int id) {
		//search meetings for an id
		for (Meeting meeting : meetings) {
			if (meeting.getId() == id) {
				return meeting;
			}
		}
		return null;
	}

	static Meeting searchMeetings(String notes) {
		//search meetings for Notes
		for (Meeting meeting : meetings) {
			if (meeting.getNotes().toLowerCase().equals(notes.toLowerCase())) {
				return meeting;
			}
		}
		return null;
	}

	static int getNextContactId() {
		int nextId = 100;
		for (int i = 0; i < contacts.size(); i++) {
			if (searchContacts(nextId) == null) {
				return nextId;
			}
			if (nextId >= 999) {
				print("* ERROR: You can't have more than 999 contacts!\n");
			}
			nextId ++;
		}
		return nextId;
	}

	static int getNextMeetingId() {
		int nextId = 100;
		for (int i = 0; i < meetings.size(); i++) {
			if (searchMeetings(nextId) == null) {
				return nextId;
			}
			if (nextId >= 999) {
				print("* ERROR: You can't have more than 999 meetings!\n");
			}
			nextId ++;
		}
		return nextId;
	}
	
	static void setUi(Page page) {

		switch (page) {
			case HOME://main menu
				print("\n~ What would you like to do?\n");
				//get input
				checkInput(page);
				break;
			case LISTC://list all contacts
				if (contacts.size () < 1) {
					print("No contacts found!\n");
				} else {
					print("LIST OF CONTACTS:\n");
					print("   ID | NAME | NOTES\n");
					for (int i = 0; i < contacts.size(); i ++) {
						print(findContact(i).toFancyString() + "\n");
					}
				}
				setUi(Page.HOME);
				break;
			case ADDC_1://add contact part 1
				print("# Type the FULL NAME of the contact:");
				tempName = readScreen();
				setUi(Page.ADDC_2);
				break;
			case ADDC_2://add contact part 2
				print("# Type any NOTES about this person:");
				tempId = getNextContactId();
				contacts.add(new Contact(tempId, tempName, readScreen()));
				print("Contact '" + tempName + "' added! (id = " + tempId + ")\n");
				setUi(Page.HOME);
				break;
			case EDITC_1://edit contact part 1
				print("# Type the ID of the contact you want to edit:");
				try {
					tempId = Integer.parseInt(readScreen());
				} catch (Exception e) {
					System.out.println("* ERROR: Contact ID needs to be a number");
					setUi(Page.HOME);
					break;
				}
				if (searchContacts(tempId) == null) {
					//make sure the contact exits, if not cancel operation
					print("* ERROR: Contact with ID '" + tempId + "' does not exist\n");
					setUi(Page.HOME);
					break;
				}
				setUi(Page.EDITC_2);
				break;
			case EDITC_2://edit contact part 2
				print("# Type the new FULL NAME of the contact (leave blank to not change):");
				tempName = readScreen();
				if (tempName.equals("") || tempName == null) {
					//if the name was blank, just set the name to what it was before
					tempName = searchContacts(tempId).getName();
				}
				setUi(Page.EDITC_3);
				break;
			case EDITC_3://edit contact part 3
				print("# Type any new NOTES about the contact (leave blank to not change):");
				String tempNotes = readScreen();
				if (tempNotes.equals("") || tempNotes == null) {
					tempNotes = searchContacts(tempId).getNotes();
				}
				Contact newContact = new Contact(tempId, tempName, tempNotes);
				contacts.remove(searchContacts(tempId));
				contacts.add(newContact);
				print("Contact '" + tempName + "' edited.\n");
				setUi(Page.HOME);
				break;
			case DELC:
				print ("# Type the ID of the contact you want to delete:");
				//get id
				tempId = Integer.parseInt(readScreen());
			
				if (searchContacts(tempId) == null) {
					//make sure the contact exits, if not cancel operation
					print("* ERROR: Contact with ID '" + tempId + "' does not exist\n");
					setUi(Page.HOME);
					break;
				}
					
				//remove the contact (this way around so the name can be printed before it was deleted)
				print ("Removed contact '" + searchContacts(tempId).toFancyString() + "'\n");
				contacts.remove(searchContacts(tempId));
				setUi(Page.HOME);
				break;
			case FINDC://search for a contact
				print("# Type the name of the contact you want to search for:");
				//TODO not exact string needed
				String query = readScreen();
				if (searchContacts(query) == null) {
					//check contact can be found
					print ("Sorry, no contact with name '" + query + "' was found\n");
				} else {
					print(searchContacts(query).toFancyString() + "\n");
				}
				setUi(Page.HOME);
				break;
			case LISTM://list all meetings
				if (meetings.size() > 0) {
					print("LIST OF MEETINGS:\n");
					print("   ID | DATE + TIME | NOTES\n");
					for (int i = 0; i < meetings.size(); i ++) {
						print(findMeeting(i).toFancyString() + "\n");
					}
				} else {
					print("No meetings found!\n");
				}
			
				setUi(Page.HOME);
				break;
			case ADDM_1://add meeting part 11
				print("# Type the DATE and TIME of the meeting (format = '" + Manager.dateFormatString + "'):");
				tempDate = toDate(readScreen());
				if (tempDate == null) {
					setUi(Page.ADDM_1);
					break;
				}
				setUi(Page.ADDM_2);
				break;
			case ADDM_2://add meeting part 2
				print("# Type the NOTES about this meeting:");
				tempId = getNextMeetingId(); 
				meetings.add(new Meeting(tempId, tempDate, readScreen(), "000"));
				print("Meeting on '" + tempDate.toString() + "' added! (id = " + tempId + ")\n");
				setUi(Page.HOME);
				break;
			case EDITM_1://edit contact part 1
				print("# Type the ID of the meeting you want to edit:");
				//get the tempId of the meeting
				try {
					tempId = Integer.parseInt(readScreen());
				} catch (Exception e) {
					System.out.println("* ERROR: Meeting ID needs to be a number");
					setUi(Page.HOME);
					break;
				}
				//make sure it is a meeting
				if (searchMeetings(tempId) == null) {
					print("* ERROR: Meeting with ID '" + tempId + "' does not exist\n");
					setUi(Page.HOME);
					break;
				}
				setUi(Page.EDITM_2);
				break;
			case EDITM_2://edit contact part 2
				print("# Type the new DATE and TIME of the meeting (leave blank to not change):");
				//get the temp date as a string
				String tempDateString = readScreen();
				if (tempDateString.equals("")) {
					//leave blank to not change
					tempDate = searchMeetings(tempId).getDate();	
				} else {
					//convert it into a date
					tempDate = toDate(tempDateString);
					if (tempDate == null) {
						setUi(Page.EDITM_2);
						break;
					}
				}
				setUi(Page.EDITM_3);
				break;
			case EDITM_3://edit c - 3
				print("# Type the new NOTES about the meeting (leave blank to not change):");
				tempNotes = readScreen();
				if (tempNotes.equals("") || tempNotes == null) {
					//dont change the notes if user input is blank
					tempNotes = searchMeetings(tempId).getNotes();
				}
				//make a new meeting with this data
				Meeting newMeeting = new Meeting(tempId, tempDate, tempNotes, searchMeetings(tempId).getAttendeesString());
				//delete the old one
				meetings.remove(searchMeetings(tempId));
				//add the new one
				meetings.add(newMeeting);
				print("Meeting on '" + tempDate + "' edited.\n");
				setUi(Page.HOME);
				break;
			case DELM:
				print ("# Type the ID of the meeting you want to delete:");
				//get id
				try {
					tempId = Integer.parseInt(readScreen());
				} catch (Exception e) {
					System.out.println("* ERROR: Meeting ID needs to be a number");
					setUi(Page.HOME);
					break;
				}
				if (searchMeetings(tempId) == null) {
					print("* ERROR: Meeting with ID '" + tempId + "' does not exist\n");
					setUi(Page.HOME);
					break;
				}
				//remove the meeting (this way around so the name can be printed before it was deleted)
				print ("Meeting '" + searchMeetings(tempId).toFancyString() + "' removed\n");
				meetings.remove(searchMeetings(tempId));
				setUi(Page.HOME);
				break;
			case FINDM://search for a contact
				print("# Type the NOTES of the meeting you want to search for:");
				String meetingSearchQuery = readScreen();
				if (searchMeetings(meetingSearchQuery) == null) {
					print("Sorry, no meeting with name '" + meetingSearchQuery + "' was found\n");
				}
				else {
					print(searchMeetings(meetingSearchQuery).toFancyString() +"\n");
				}
				setUi(Page.HOME);
				break;
			case VIEWM:
				print("# Type the ID of the meeting you want to view:");
				try {
					tempId = Integer.parseInt(readScreen());
				} catch (Exception e) {
					System.out.println("* ERROR: Meeting ID needs to be a number");
					setUi(Page.HOME);
					break;
				}
				//using the ID get the meeting, then print attendees
				if (searchMeetings(tempId) == null) {
					print("* ERROR: No contact with ID '" + tempId + "' contact exists\n");
					setUi(Page.HOME);
					break;
				}
				if (searchMeetings(tempId).getAttendeesString() == "") {
					print("No one is attending this meeting\n");
				} else {
					print("LIST OF ATTENDEES TO MEETING WITH ID " + tempId + ":\n");
					print("   ID | NAME | NOTES\n");
					print(searchMeetings(tempId).getFancyAttendeesString());
				}
				setUi(Page.HOME);
				break;
			case ADDTOM_1:
				print("# Type the ID of the MEETING you want to add a contact to:");
				try {
					tempId = Integer.parseInt(readScreen());
				} catch (Exception e) {
					System.out.println("* ERROR: Meeting ID needs to be a number");
					setUi(Page.HOME);
					break;
				}
				if (searchMeetings(tempId) == null) {
					print("A meeting with the ID '" + tempId + "' does not exist\n");
					setUi(Page.HOME);
					break;
				}
				setUi(Page.ADDTOM_2);
				break;
			case ADDTOM_2:
				print("# Type the ID of the contact you want to add to the meeting:");
				try {
					tempAttendeeId = Integer.parseInt(readScreen());
				} catch (Exception e) {
					System.out.println("* ERROR: Contact ID needs to be a number");
					setUi(Page.HOME);
					break;
				}
				if (searchContacts(tempAttendeeId) == null) {
					print("A contact with the ID '" + tempAttendeeId + "' does not exist\n");
					setUi(Page.HOME);
					break;
				}
				//check if the ID is already attending the meeting TODO
				//finally add the contact as an attendee to the meeting
				searchMeetings(tempId).addAttendee(searchContacts(tempAttendeeId));	
				print("Contact '" + searchContacts(tempAttendeeId).toFancyString() + "' added to meeting with id " + tempId + "!\n");
				setUi(Page.HOME);
				break;
			case DELFROMM_1:
				print("# Type the ID of the MEETING you want to remove a contact from:");
				try {
					tempId = Integer.parseInt(readScreen());
				} catch (Exception e) {
					System.out.println("* ERROR: Meeting ID needs to be a number");
					setUi(Page.HOME);
					break;
				}
				if (searchMeetings(tempId) == null) {
					print("A meeting with the ID '" + tempId + "' does not exist\n");
					setUi(Page.HOME);
					break;
				}
				setUi(Page.DELFROMM_2);
				break;
			case DELFROMM_2:
				print("# Type the ID of the contact you want to remove from the meeting:");
				try {
					tempAttendeeId = Integer.parseInt(readScreen());
				} catch (Exception e) {
					System.out.println("* ERROR: Contact ID needs to be a number");
					setUi(Page.HOME);
					break;
				}
				if (searchContacts(tempAttendeeId) == null) {
					print("A contact with the ID '" + tempAttendeeId + "' does not exist\n");
					setUi(Page.HOME);
					break;
				}
				//test to see if that id even attends the meeting TODO
				//finally remove the contact as an attendee to the meeting
				searchMeetings(tempId).removeAttendee(searchContacts(tempAttendeeId));	
				setUi(Page.HOME);
				break;
			default:
				print("* ERROR: Tried to go to a ui page that does not exist\n");
				setUi(Page.HOME);
				break;
		}
	}

	static void checkInput(Page page) {
		print ("> ");//just looks cool

		if (page == Page.HOME) {
			/* all the commands you can enter from the main start page
			 * basically just converts string inputs into page enums
			 */
			String command = readScreen().toLowerCase();
			print("\n"); //newline to make it easyer to read

			switch (command) {
				case "list contacts":
					setUi(Page.LISTC);
					break;
				case "add contact":
					setUi(Page.ADDC_1);
					break;
				case "edit contact":
					setUi(Page.EDITC_1);
					break;
				case "remove contact": case "delete contact":
					setUi(Page.DELC); 
					break;
				case "find contact":
					setUi(Page.FINDC);
					break;
				case "list meetings":
					setUi(Page.LISTM);
					break;
				case "add meeting":
					setUi(Page.ADDM_1);
					break;
				case "edit meeting":
					setUi(Page.EDITM_1);
					break;
				case "remove meeting": case "delete meeting":
					setUi(Page.DELM);
					break;
				case "find meeting":
					setUi(Page.FINDM);
					break;
				case "view meeting":
					setUi(Page.VIEWM);
					break;
				case "add to meeting": case "add attendee":
					setUi(Page.ADDTOM_1);
					break;
				case "remove from meeting": case "remove attendee":
					setUi(Page.DELFROMM_1);
					break;
				case "help": case "?"://help - shows all commands (doesn't need own ui page)
					print ("Here are all the commands:\n\n");
					print ("list contacts\nadd contact\nedit contact\nremove contact\nfind contact\n\n");
					print ("list meetings\nadd meeting\nedit meeting\nremove meeting\nfind meeting\nview meeting\nadd to meeting\nremove from meeting\n\n");
					print ("quit\nhelp\n");
					setUi (Page.HOME);
					break;
				case "quit": case "exit": case "close":
					flush();
					return;
				default:
					print("Unknown command - type 'help' for a list of commands\n");
					setUi(Page.HOME);
					break;
			}
		} else {
			print("* ERROR: application get input from a page that does not exist!\n");
		}
	}
}
