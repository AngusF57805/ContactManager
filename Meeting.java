package ContactManager;

import java.util.Date;
import java.util.Locale;
import java.util.HashSet;
import java.util.Set;
import java.text.SimpleDateFormat;

class Meeting {

	private int id;
	private Date date;
	private String notes;
	private Set<Contact> attendees;

	SimpleDateFormat sdf = new SimpleDateFormat(Manager.dateFormatString, Locale.UK);

	public Meeting(int id, Date date, String notes, String attendeesString) {
		//there is no way to put contacts in a meeting via the constructor
		this.id = id;
		this.date = date;
		this.notes = notes;
		//turn string of attendees into a hashset of contacts
		attendees = new HashSet<Contact>();
		for (int i = 0; i < attendeesString.length(); i += 3) {
			if (Manager.searchContacts(Integer.parseInt(attendeesString.substring(i,i + 3))) != null) {
				attendees.add(Manager.searchContacts(Integer.parseInt(attendeesString.substring(i,i + 3))));
			}
		}
	}

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

	public void addAttendee(Contact contact) {
		attendees.add(contact);
	}

	public void removeAttendee(Contact contact) {
		attendees.remove(contact);
	}

	public String toString() { //overrides deafult .toString
		String a = getAttendeesString();
		if (a.equals("")) {
			a = "000";
		}
		return id + "\n" + sdf.format(date) + "\n" + notes + "\n" + a;
	}

	public String toFancyString() { //more ui friendly
		return "- " + id +  " : " + sdf.format(date) + " : " + notes;
	}

	public String getAttendeesString() {
		String str = "";
		for (Contact contact : attendees) {
			str += contact.getId() + "";
		}
		return str;
	}

	public String getFancyAttendeesString() {
		String str = "";
		for (Contact contact : attendees) {
			str += contact.toFancyString() + "\n";
		}
		return str;
	}
}
