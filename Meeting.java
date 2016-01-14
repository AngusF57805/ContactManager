class Meeting {
	
	int id;
	Date date;
	String notes;
	Contact[] attendees;

	Meeting (Date date, String notes) {
		//for now no way to put contacts in a meeting via the constructor
		this.date = date;
		this.notes = notes;

	}

}
