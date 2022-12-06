package com.stackroute.keepnote.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.keepnote.dao.CategoryDAO;
import com.stackroute.keepnote.dao.NoteDAO;
import com.stackroute.keepnote.dao.ReminderDAO;
import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.exception.NoteNotFoundException;
import com.stackroute.keepnote.exception.ReminderNotFoundException;
import com.stackroute.keepnote.model.Category;
import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.model.Reminder;

/*
* Service classes are used here to implement additional business logic/validation 
* This class has to be annotated with @Service annotation.
* @Service - It is a specialization of the component annotation. It doesn�t currently 
* provide any additional behavior over the @Component annotation, but it�s a good idea 
* to use @Service over @Component in service-layer classes because it specifies intent 
* better. Additionally, tool support and additional behavior might rely on it in the 
* future.
* */
@Service
public class NoteServiceImpl implements NoteService {

	/*
	 * Autowiring should be implemented for the NoteDAO,CategoryDAO,ReminderDAO.
	 * (Use Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */

	NoteDAO noteDAO;

	CategoryDAO categoryDAO;

	ReminderDAO reminderDAO;

	public NoteServiceImpl() {
		super();
	}

	@Autowired
	public NoteServiceImpl(NoteDAO noteDAO, CategoryDAO categoryDAO, ReminderDAO reminderDAO) {
		this.noteDAO = noteDAO;
		this.categoryDAO = categoryDAO;
		this.reminderDAO = reminderDAO;
	}

	/*
	 * This method should be used to save a new note.
	 */

	public boolean createNote(Note note) throws ReminderNotFoundException, CategoryNotFoundException {
		Reminder reminder = note.getReminder();
		Category category = note.getCategory();
		try {
			if (reminder != null)
				reminderDAO.getReminderById(reminder.getReminderId());
		} catch (ReminderNotFoundException rnf) {
			throw new ReminderNotFoundException("ReminderNotFoundException");
		}
		try {
			if (category != null)
				categoryDAO.getCategoryById(category.getCategoryId());
		} catch (CategoryNotFoundException cnf) {
			throw new CategoryNotFoundException("CategoryNotFoundException");
		}

		return noteDAO.createNote(note);

	}

	/* This method should be used to delete an existing note. */

	public boolean deleteNote(int noteId) {
		return noteDAO.deleteNote(noteId);

	}
	/*
	 * This method should be used to get a note by userId.
	 */

	public List<Note> getAllNotesByUserId(String userId) {
		return noteDAO.getAllNotesByUserId(userId);
	}

	/*
	 * This method should be used to get a note by noteId.
	 */
	public Note getNoteById(int noteId) throws NoteNotFoundException {
		Note note = noteDAO.getNoteById(noteId);
		if (note == null) {
			throw new NoteNotFoundException(noteId + " Not found");
		}
		return note;
	}

	/*
	 * This method should be used to update a existing note.
	 */

	public Note updateNote(Note note, int id)
			throws ReminderNotFoundException, NoteNotFoundException, CategoryNotFoundException {
		if (note.getReminder() != null) {
			Reminder reminder = reminderDAO.getReminderById(note.getReminder().getReminderId());
			if (reminder == null) {
				throw new ReminderNotFoundException(note.getReminder().getReminderName() + " Not Found");
			}
		}
		if (note.getCategory() != null) {
			Category category = categoryDAO.getCategoryById(note.getCategory().getCategoryId());
			if (category == null) {
				throw new CategoryNotFoundException(note.getCategory().getCategoryName() + " Not Found");
			}
		}
		noteDAO.UpdateNote(note);
		return noteDAO.getNoteById(id);
	}

}