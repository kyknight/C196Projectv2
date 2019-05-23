package com.example.c195projectv2.Database.Daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import com.example.c195projectv2.Database.DbContentProvider;
import com.example.c195projectv2.Models.Note;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class NoteDAO extends DbContentProvider implements NoteSchema, NoteDAOInterface {

    private Cursor cursor;
    private ContentValues initialValues;

    public NoteDAO(SQLiteDatabase db) {
        super(db);
    }

    /**
     * This method adds to the DB yo note
     * @param note
     * @return
     */
    public boolean addNote(Note note) {
        setContentValue(note);
        try {
            return super.insert(TABLE_NOTES, getContentValue()) > 0;
        } catch (SQLiteConstraintException ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }

    /**
     * This method gets all yo course's associated notes
     * @param courseId
     * @return
     */
    public List<Note> getNotesByCourse(int courseId) {
        final String selectionArgs[] = { String.valueOf(courseId) };
        final String selection = NOTE_COURSE_ID + " = ?";

        List<Note> noteList = new ArrayList<Note>();

        cursor = super.query(TABLE_NOTES, NOTES_COLUMNS, selection,
                selectionArgs, NOTE_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Note note = cursorToEntity(cursor);
                noteList.add(note);

                cursor.moveToNext();
            }
            cursor.close();
        }
        return noteList;
    }

    /**
     * This method gets yo selected note
     * @param noteId
     * @return
     */
    public Note getNoteById(int noteId) {
        final String selectionArgs[] = { String.valueOf(noteId) };
        final String selection = NOTE_ID + " = ?";

        Note note = null;

        cursor = super.query(TABLE_NOTES, NOTES_COLUMNS, selection,
                selectionArgs, NOTE_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                note = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return note;
    }

    /**
     * This method gets yo number of note (obviously, with consent)
     * @return
     */
    public int getNoteCount() {
        List<Note> noteList = getNotes();
        return noteList.size();
    }

    /**
     * This method gets all yo notes (creep [TLC, Radiohead, The Lonely Island])
     * @return
     */
    public List<Note> getNotes() {
        List<Note> noteList = new ArrayList<Note>();

        cursor = super.query(TABLE_NOTES, NOTES_COLUMNS, null,
                null, NOTE_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Note note = cursorToEntity(cursor);
                noteList.add(note);

                cursor.moveToNext();
            }
            cursor.close();
        }
        System.out.println(noteList);
        return noteList;
    }

    /**
     * This method removes yo selected note
     * @param note
     * @return
     */
    public boolean removeNote(Note note) {
        final String selectionArgs[] = { String.valueOf(note.getId()) };
        final String selection = NOTE_ID + " = ?";
        return super.delete(TABLE_NOTES, selection, selectionArgs) > 0;
    }

    /**
     * This method updates yo note
     * @param note
     * @return
     */
    public boolean updateNote(Note note) {
        final String selectionArgs[] = { String.valueOf(note.getId()) };
        final String selection = NOTE_ID + " = ?";

        setContentValue(note);
        try {
            return super.update(TABLE_NOTES, getContentValue(), selection, selectionArgs) > 0;
        } catch (SQLiteConstraintException ex){
            return false;
        }
    }

    /**
     * This method coverts a cursor to yo note
     * @param cursor
     * @return
     */
    protected Note cursorToEntity(Cursor cursor) {
        int noteIdIdx;
        int noteTitleIdx;
        int noteTextIdx;
        int noteCourseIdIdx;
        int noteId = -1;
        String noteTitle = "";
        String noteText = "";
        int noteCourseId = -1;

        if (cursor != null) {
            if (cursor.getColumnIndex(NOTE_ID) != -1) {
                noteIdIdx = cursor.getColumnIndexOrThrow(NOTE_ID);
                noteId = cursor.getInt(noteIdIdx);
            }
            if (cursor.getColumnIndex(NOTE_TITLE) != -1) {
                noteTitleIdx = cursor.getColumnIndexOrThrow(NOTE_TITLE);
                noteTitle = cursor.getString(noteTitleIdx);
            }
            if (cursor.getColumnIndex(NOTE_TEXT) != -1) {
                noteTextIdx = cursor.getColumnIndexOrThrow(NOTE_TEXT);
                noteText = cursor.getString(noteTextIdx);
            }
            if (cursor.getColumnIndex(NOTE_COURSE_ID) != -1) {
                noteCourseIdIdx = cursor.getColumnIndexOrThrow(NOTE_COURSE_ID);
                noteCourseId = cursor.getInt(noteCourseIdIdx);
            }
        }
        return new Note(noteId, noteTitle, noteText, noteCourseId);
    }

    /**
     * This method sets instance variables for insert/update into yo DB
     * @param note
     */
    private void setContentValue(Note note) {
        initialValues = new ContentValues();
        initialValues.put(NOTE_ID, note.getId());
        initialValues.put(NOTE_TITLE, note.getTitle());
        initialValues.put(NOTE_TEXT, note.getText());
        initialValues.put(NOTE_COURSE_ID, note.getCourseId());
    }

    /**
     * This method gets yo note content values
     * @return
     */
    private ContentValues getContentValue() {
        return initialValues;
    }
}
