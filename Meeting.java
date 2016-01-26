package ContactManager;

import java.util.Date;
//import java.util.Set; //is this all i need?
import java.util.*;

class Meeting {

	private int id;
	private Date date;
	private String notes;
	private Set<Contact> attendees;

	public Meeting(int id, Date date, String notes, String attendeesString) {
		//for now no way to put contacts in a meeting via the constructor
		this.id = id;
		this.date = date;
		this.notes = notes;
		//turn string of attendees into a hashset of contacts
		attendees = new HashSet<Contact>();
		for (int i = 0; i < attendeesString.length(); i += 3) {
			if (Manager.searchContacts(attendeesString.substring(i,3)) != null) {
				attendees.add(Manager.searchContacts(attendeesString.substring(i,3)));
			}
		}
	}

	/*
	public Meeting(Date date, String notes, String attendeesString) {
		//TODO not hardcoded id
		this(100, date, notes, attendeesString);
	}
	*/

	public int getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public String getNotes() {
		return notes;
	}

	public Set<Contact> getAttendees() {
		return attendees;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setAttendees(Set<Contact> attendees) {
		this.attendees = attendees;
	}

	public void addAttendees(Contact contact) {
		attendees.add(contact);
	}

	public String toString() { //overrides deafult .toString
		return id + "\n" + date.toString() + "\n" + notes; //TODO AGGGGHHH
	}

	public String toFancyString() { //more ui friendly
		return "- " + id +  " : " + date.toString() + " : " + notes + "\n"; //TODO WHAT DO I DO TO SHOW ATTENDEES
	}

	public String getAttendeesString() {
		String str = "";
		for (Contact contact: attendees) {
			str += contact.getId();
		}
		return "100";
	}

	public String getFancyAttendeesString() {
		String str = "";
		for (Contact contact: attendees) {
			str += contact.toFancyString() + "\n";
		}
		return str;
	}
}
