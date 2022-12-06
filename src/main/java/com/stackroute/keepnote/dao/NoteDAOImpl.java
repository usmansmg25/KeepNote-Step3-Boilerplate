package com.stackroute.keepnote.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.stackroute.keepnote.exception.NoteNotFoundException;
import com.stackroute.keepnote.model.Note;

/*
 * This class is implementing the UserDAO interface. This class has to be annotated with 
 * @Repository annotation.
 * @Repository - is an annotation that marks the specific class as a Data Access Object, 
 * thus clarifying it's role.
 * @Transactional - The transactional annotation itself defines the scope of a single database 
 *                     transaction. The database transaction happens inside the scope of a persistence 
 *                     context.  
 * */
@Repository
@Transactional
public class NoteDAOImpl implements NoteDAO {

    /*
     * Autowiring should be implemented for the SessionFactory.(Use
     * constructor-based autowiring.
     */
    @Autowired
    SessionFactory sessionfactory;

    public NoteDAOImpl(SessionFactory sessionFactory) {
        this.sessionfactory = sessionFactory;
    }

    /*
     * Create a new note
     */

    public boolean createNote(Note note) {
        sessionfactory.getCurrentSession().save(note);
        return true;

    }

    /*
     * Remove an existing note
     */

    public boolean deleteNote(int noteId) {
        try {
            sessionfactory.getCurrentSession().delete(getNoteById(noteId));
        } catch (HibernateException | NoteNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    /*
     * Retrieve details of all notes by userId
     */

    public List<Note> getAllNotesByUserId(String userId) {
        Query query = sessionfactory.getCurrentSession().createQuery("from Note");
        List<Note> res = query.list();
        return res.stream().filter(n -> n.getCreatedBy().equals(userId)).collect(Collectors.toList());

    }

    /*
     * Retrieve details of a specific note
     */

    public Note getNoteById(int noteId) throws NoteNotFoundException {

        Note note = sessionfactory.getCurrentSession().find(Note.class, noteId);
        if (note == null) {
            throw new NoteNotFoundException("NoteNotFoundException");
        } else {
            return note;
        }

    }

    /*
     * Update an existing note
     */

    public boolean UpdateNote(Note note) {
        sessionfactory.getCurrentSession().update(note);
        return true;

    }

}