package com.example.c195projectv2.UI.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.c195projectv2.Database.Database;
import com.example.c195projectv2.Models.Note;
import com.example.c195projectv2.R;
import com.example.c195projectv2.UI.Fragments.NoteDetailFragment;

/**
 *
 */
public class SharingActivity extends AppCompatActivity {
    public static final String SHARED_PREFS_FILENAME = "com.example.c195projectv2.sharingactivity.sharedpreffilename";
    public static final String SHARED_PREF_SHARE = "com.example.c195projectv2.sharingactivity.sharedprefshare";
    SharedPreferences sharedPref;
    private String shareFromSharedPref;
    private Note selectedNote;
    public Database db;

    /**
     * This method sets the contentView and toolbar & textView & editText action items
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharing);
        db = new Database(this);
        db.open();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPref = this.getSharedPreferences(SHARED_PREFS_FILENAME, 0);
        shareFromSharedPref = sharedPref.getString(SHARED_PREF_SHARE, "EMAIL");

        final int selectedNoteId = getIntent().getIntExtra(NoteDetailFragment.ARG_NOTE_ID, -1);
        selectedNote = db.noteDAO.getNoteById(selectedNoteId);

        TextView shareLabel = findViewById(R.id.share_label);
        EditText shareTarget = findViewById(R.id.share_target);
        if (shareFromSharedPref.equals("EMAIL")) {
            shareLabel.setText("Share via Email");
            shareTarget.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        } else {
            shareLabel.setText("Share via SMS");
            shareTarget.setInputType(InputType.TYPE_CLASS_PHONE);
        }

    }

    /**
     * This method saves the entered and selected information when called
     * @param view
     */
    public void onSave(View view) {
        EditText shareTarget = findViewById(R.id.share_target);
        String shareTargetText = shareTarget.getText().toString();

        if (shareFromSharedPref.equals("EMAIL")) {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("text/html");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, shareTargetText);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, selectedNote.getTitle());
            emailIntent.putExtra(Intent.EXTRA_TEXT, selectedNote.getText());

            startActivity(Intent.createChooser(emailIntent, "Send Email"));
        } else {
            Intent smsIntent = new Intent(Intent.ACTION_VIEW);
            smsIntent.setData(Uri.parse("sms:" + shareTargetText));
            smsIntent.putExtra("sms_body", selectedNote.getText());

            startActivity(smsIntent);
        }
    }

    /**
     * This method handles action to take place based on the item selected
     * @param view
     */
    public void onSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
