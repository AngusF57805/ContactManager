class Contact {

	int id;
	String name;
	String notes;

	public Contact (int id, String name, String notes) {
		this.id = id;
		this.name = name;
		this.notes = notes;
	}

	public Contact (String name, String notes) {
		//TODO make the id stuff work
		this (100, name, notes);
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
