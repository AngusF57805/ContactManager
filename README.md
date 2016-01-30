# ContactManager
Contact Manager application, used to manage a list of contacts, meetings and attendees.

*By Angus Findlay and Rhys Harris.*

###How to use this application
|**COMMAND**|**DECRIPTION**|
|-----------|-------------|
|list contacts|displays a list of the contacts stored in the hashset|
|add contact|adds a contact to the hashset|
|edit contact|change the name and / or notes of a contact|
|remove contact|deletes a contact from the hashset|
|find contact|search for a contact by name to get the id and notes|
|list meetings|displays a list of the meetings stored in the hashset|
|add meeting|adds a meeting to the hashset|
|edit meeting|change the date and / or notes of a meeting|
|remove meeting|deletes a meeting from the hashset|
|find meeting|search for a meeting by notes to get the id and date|
|view meeting|see all the attendees of a meeting|
|add to meeting|adds one of your contacts to attend a meeting|
|remove from meeting|removes one of the contacts from a meeting|
|quit|saves and exits the program|
|help|shows all commands|
	
####SaveFile.txt layout
```
contact_id
contact_name
contact_notes
contact_id
contact_name
contact_notes
*END-CONTACTS*
meeting_id
meeting_date
meeting_notes
attendee_idattendee_idattendee_id
meeting_id
meeting_date
meeting_notes
attendee_idattendee_idattendee_id
```
#####For Example
```
100
Bob Ham
Manager of some company
101
Joe Bloggs
Best Friend
*END-CONTACTS*
100
01/11/75
business meeting
100101
```
