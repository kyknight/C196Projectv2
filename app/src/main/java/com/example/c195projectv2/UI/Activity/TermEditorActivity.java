package com.example.c195projectv2.UI.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.c195projectv2.Database.Database;
import com.example.c195projectv2.Models.Course;
import com.example.c195projectv2.Models.Term;
import com.example.c195projectv2.R;
import com.example.c195projectv2.UI.Fragments.TermDetailFragment;


import java.util.Calendar;
import java.util.List;

/**
 *
 */
public class TermEditorActivity extends AppCompatActivity implements View.OnClickListener{
    private Term modifiedTerm;
    public Database db;
    Button btnStartDatePicker, btnEndDatePicker;
    private int sYear, sMonth, sDay, eYear, eMonth, eDay;
    EditText editStart, editEnd;

    /**
     * This method sets the contentView and toolbar & spinner action items
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_editor);
        db = new Database(this);
        db.open();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        int modifiedTermId = intent.getIntExtra(TermDetailFragment.ARG_TERM_ID, -1);
        //for the date picker to display
        editStart = findViewById(R.id.term_start);
        editEnd = findViewById(R.id.term_end);
        btnStartDatePicker = findViewById(R.id.term_start_button) ;
        btnEndDatePicker = findViewById(R.id.term_end_button);
        btnStartDatePicker.setOnClickListener(this);
        btnEndDatePicker.setOnClickListener(this);

        if (modifiedTermId != -1) {
            modifiedTerm = db.termDAO.getTermById(modifiedTermId);

            String termTitle = modifiedTerm.getTitle();
            String termStart = modifiedTerm.getStartDate();
            String termEnd = modifiedTerm.getEndDate();
            EditText editTitle = findViewById(R.id.term_title);
            editStart = findViewById(R.id.term_start);
            //for the date picker to display
            editEnd = findViewById(R.id.term_end);
            editTitle.setText(termTitle);
            editStart.setText(termStart);
            editEnd.setText(termEnd);
            findViewById(R.id.delete_term).setVisibility(View.VISIBLE);
            btnStartDatePicker.setOnClickListener(this);
            btnEndDatePicker.setOnClickListener(this);
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
     * This method removes the selected term after checking that the term does not have a course associated with it
     * @param view
     */
    public void onDelete(View view) {
        List<Course> courseList = db.courseDAO.getCoursesByTerm(modifiedTerm.getId());
        boolean removed = false;
        if (courseList.size() == 0 ) {
            removed = db.termDAO.removeTerm(modifiedTerm);
        }
        if (removed) {
            Intent intent = new Intent(this, TermListActivity.class);
            startActivity(intent);
        }
    }

    /**
     * This method saves the entered and selected information when called
     * @param view
     */
    public void onSave(View view) {
        EditText editTitle = findViewById(R.id.term_title);
        EditText editStart = findViewById(R.id.term_start);
        EditText editEnd = findViewById(R.id.term_end);
        String termTitle = editTitle.getText().toString();
        String termStart = editStart.getText().toString();
        String termEnd = editEnd.getText().toString();

        int termId = db.termDAO.getTermCount();
        int modifiedTermId = -1;

        if (modifiedTerm != null) {
            modifiedTermId = modifiedTerm.getId();
            termId = modifiedTermId;
        }
        Term newTerm = new Term(termId, termTitle, termStart, termEnd);

        boolean soSave = false;
        boolean isValid = newTerm.isValid();

        if (isValid && modifiedTermId == -1) {
            soSave = db.termDAO.addTerm(newTerm);
        } else if (isValid) {
            soSave = db.termDAO.updateTerm(newTerm);
        }
        if (soSave) {
            Intent intent = new Intent(this, TermListActivity.class);
            startActivity(intent);
        }
    }

    /**
     * This method enables for the date picker to display and to set in text field
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v == btnStartDatePicker) {
            //"Is this 10-25-1985?" "No! This is [current date]!"
            final Calendar c = Calendar.getInstance();
            sYear = c.get(Calendar.YEAR);
            sMonth = c.get(Calendar.MONTH);
            sDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            editStart.setText((monthOfYear + 1) + "-" + dayOfMonth + "-" + year);
                        }
                    }, sYear, sMonth, sDay);
            datePickerDialog.show();
        }
        if (v == btnEndDatePicker) {
            //"Is this 10-25-1985?" "No! This is [current date]!"
            final Calendar c = Calendar.getInstance();
            eYear = c.get(Calendar.YEAR);
            eMonth = c.get(Calendar.MONTH);
            eDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            editEnd.setText((monthOfYear + 1) + "-" + dayOfMonth + "-" + year);
                        }
                    }, eYear, eMonth, eDay);
            datePickerDialog.show();
        }
    }
}
