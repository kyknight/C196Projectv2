package com.example.c195projectv2.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.example.c195projectv2.Database.Database;
import com.example.c195projectv2.Models.Note;
import com.example.c195projectv2.R;
import com.example.c195projectv2.UI.Fragments.CourseDetailFragment;
import com.example.c195projectv2.UI.Fragments.NoteDetailFragment;

/**
 *
 */
public class NoteEditorActivity extends AppCompatActivity {
    private Note modifiedNote;
    private int courseId;
    public Database db;

    /**
     * This method sets the contentView and toolbar action items
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);
        db = new Database(this);
        db.open();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        int modifiedNoteId = intent.getIntExtra(NoteDetailFragment.ARG_NOTE_ID, -1);
        courseId = intent.getIntExtra(CourseDetailFragment.ARG_COURSE_ID, -1);

        if (modifiedNoteId != -1) {
            modifiedNote = db.noteDAO.getNoteById(modifiedNoteId);

            String noteTitle = modifiedNote.getTitle();
            String noteText = modifiedNote.getText();
            EditText editTitle = findViewById(R.id.note_title);
            EditText editText = findViewById(R.id.note_text);

            editTitle.setText(noteTitle);
            editText.setText(noteText);
            findViewById(R.id.delete_note).setVisibility(View.VISIBLE);
        }
    }

    /**
     * This method closes the database when called
     */
    @Override
    public void onDestroy() {
        db.close();
        super.onDestroy();
    }

    /**
     * This method removes the selected note when called
     * @param view
     */
    public void onDelete(View view) {
        int noteCourse = modifiedNote.getCourseId();
        boolean removed = db.noteDAO.removeNote(modifiedNote);
        if (removed) {
            Intent intent = new Intent(this, CourseDetailActivity.class);
            intent.putExtra(CourseDetailFragment.ARG_COURSE_ID, noteCourse);
            startActivity(intent);
        }
    }

    /**
     * This method saves the entered and selected information when called
     * @param view
     */
    public void onSave(View view) {
        EditText editTitle = findViewById(R.id.note_title);
        EditText editText = findViewById(R.id.note_text);
        String noteTitle = editTitle.getText().toString();
        String noteText = editText.getText().toString();
        int noteId = db.noteDAO.getNoteCount();
        int modifiedNoteId = -1;

        if (modifiedNote != null) {
            modifiedNoteId = modifiedNote.getId();
            noteId = modifiedNoteId;
        }
        Note newNote = new Note(noteId, noteTitle, noteText, courseId);

        boolean soSave = false;
        boolean isValid = newNote.isValid();

        if (isValid && modifiedNoteId == -1) {
            soSave = db.noteDAO.addNote(newNote);
        } else if (isValid) {
            soSave = db.noteDAO.updateNote(newNote);
        } else {
            Snackbar invalidSnack = Snackbar.make(findViewById(R.id.note_coordinator),
                    "Missing required fields", Snackbar.LENGTH_SHORT);
            invalidSnack.show();
        }
        if (soSave) {
            Intent intent = new Intent(this, CourseDetailActivity.class);
            intent.putExtra(CourseDetailFragment.ARG_COURSE_ID, courseId);
            startActivity(intent);
        }
    }
}
