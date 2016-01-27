package ContactManager;

class Contact {

	private int id;
	private String name;
	private String notes;

	public Contact(int id, String name, String notes) {
		this.id = id;
		this.name = name;
		this.notes = notes;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getNotes() {
		return notes;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String toString() { //for writing to the text file (overrides toString)
		return id + "\n" + name + "\n" + notes;
	}

	public String toFancyString() { //for list contact
		return "- " + id +  " : " + name + " : " + notes;
	}
}
