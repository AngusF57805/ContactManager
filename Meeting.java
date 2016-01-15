import java.util.Date;
import java.util.Set; //is this all i need?

class Meeting {
	
	int id;
	Date date;
	String notes;
	Set<Contact> attendees;

	Meeting (Date date, String notes) {
		//for now no way to put contacts in a meeting via the constructor
		attendees = new HashSet<Contact>();
		this.date = date;
		this.notes = notes;
	}
	
	int getId () {
		return id;
	}

	Date getDate () {
		return date;
	}

	String getNotes () {
		return notes;
	}

	Set<Contact> getAttendees () {
		return attendees;
	}

	void setDate (Date date) {
		this.date = date;
	}

	void setNotes (String notes) {
		this.notes = notes;
	}

	void setAttendees (Set<Contact> attendees) {
		this.attendees = attendees;
	}

	void addAttendees (Contact contact) {
		attendees.add (contact);
	}
}
