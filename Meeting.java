import java.util.Date;
//import java.util.Set; //is this all i need?
import java.util.*;

class Meeting {

	private int id;
	private Date date;
	private String notes;
	private Set<Contact> attendees;

	public Meeting (Date date, String notes) {
		//for now no way to put contacts in a meeting via the constructor
		this.date = date;
		this.notes = notes;
		attendees = new HashSet<Contact>();
	}

	public int getId () {
		return id;
	}

	public Date getDate () {
		return date;
	}

	public String getNotes () {
		return notes;
	}

	public Set<Contact> getAttendees () {
		return attendees;
	}

	public void setDate (Date date) {
		this.date = date;
	}

	public void setNotes (String notes) {
		this.notes = notes;
	}

	public void setAttendees (Set<Contact> attendees) {
		this.attendees = attendees;
	}

	public void addAttendees (Contact contact) {
		attendees.add (contact);
	}

	public String toString() { //overrides deafult .toString
		return id + "\n" + date.toString() + "\n" + notes; //TODO AGGGGHHH
	}

	public String toFancyString() { //more ui friendly
		return "- " + id +  " : " + date.toString() + " : " + notes + "\n"; //TODO WHAT DO I DO TO SHOW ATTENDEES
	}
}
