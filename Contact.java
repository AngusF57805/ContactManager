class Contact {

	int id;
	String name;
	String notes;

	Contact (String name, String notes) {

		id = 100;
		this.name = name;
		this.notes = notes;
	
	}

	int getId () {
		return id;
	}

	String getName () {
		return name;
	}

	String getNotes () {
		return notes;
	}

	void setId (int id) {
		this.id = id;
	}

	void setName (String name) {
		this.name = name;
	}

	void setNotes (String notes) {
		this.notes = notes;
	}

	public String toString () {
		return id + "\n" + name + "\n" + notes;
	}

}
