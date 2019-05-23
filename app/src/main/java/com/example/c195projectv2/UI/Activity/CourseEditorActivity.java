package com.example.c195projectv2.UI.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.c195projectv2.Database.Database;
import com.example.c195projectv2.Models.Course;
import com.example.c195projectv2.Models.Term;
import com.example.c195projectv2.R;
import com.example.c195projectv2.UI.Fragments.CourseDetailFragment;
import com.example.c195projectv2.UI.Fragments.TermDetailFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 */
public class CourseEditorActivity extends AppCompatActivity implements View.OnClickListener{
    private Course modifiedCourse;
    private Spinner termSpinner;
    private Spinner statusSpinner;
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
        setContentView(R.layout.activity_course_editor);
        db = new Database(this);
        db.open();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        termSpinner = findViewById(R.id.term_spinner);
        List<Term> courseList = db.termDAO.getTerms();
        ArrayAdapter<Term> courseDataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, courseList);
        courseDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        termSpinner.setAdapter(courseDataAdapter);

        statusSpinner = findViewById(R.id.type_spinner);
        List<String> statusList = new ArrayList<>();
        statusList.add("Plan to Take");
        statusList.add("In Progress");
        statusList.add("Completed");
        statusList.add("Dropped");
        ArrayAdapter<String> statusDataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, statusList);
        statusDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusDataAdapter);

        Intent intent = getIntent();

        int modifiedCourseId = intent.getIntExtra(CourseDetailFragment.ARG_COURSE_ID, -1);
        int selectedTermId = intent.getIntExtra(TermDetailFragment.ARG_TERM_ID, -1);
        //for the date picker to display
        editStart = findViewById(R.id.course_start);
        editEnd = findViewById(R.id.course_end);
        btnStartDatePicker = findViewById(R.id.course_start_button) ;
        btnEndDatePicker = findViewById(R.id.course_end_button);
        btnStartDatePicker.setOnClickListener(this);
        btnEndDatePicker.setOnClickListener(this);

        if (modifiedCourseId != -1) {
            modifiedCourse = db.courseDAO.getCourseById(modifiedCourseId);

            String courseTitle = modifiedCourse.getTitle();
            String courseStart = modifiedCourse.getStartDate();
            String courseEnd = modifiedCourse.getEndDate();
            EditText editTitle = findViewById(R.id.course_title);
            editStart = findViewById(R.id.course_start);
            editEnd = findViewById(R.id.course_end);
            editTitle.setText(courseTitle);
            editStart.setText(courseStart);
            editEnd.setText(courseEnd);

            findViewById(R.id.delete_course).setVisibility(View.VISIBLE);
            btnStartDatePicker.setOnClickListener(this);
            btnEndDatePicker.setOnClickListener(this);
        }
        if (selectedTermId != -1) {
            termSpinner.setSelection(selectedTermId);
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
     * This method removes the selected course when called
     * @param view
     */
    public void onDelete(View view) {
        boolean removed = db.courseDAO.removeCourse(modifiedCourse);
        if (removed) {
            Intent intent = new Intent(this, CourseListActivity.class);
            startActivity(intent);
        }
    }

    /**
     * This method saves the entered and selected information when called
     * @param view
     */
    public void onSave(View view) {
        EditText editTitle = findViewById(R.id.course_title);
        EditText editStart = findViewById(R.id.course_start);
        EditText editEnd = findViewById(R.id.course_end);
        String courseTitle = editTitle.getText().toString();
        String courseStart = editStart.getText().toString();
        String courseEnd = editEnd.getText().toString();
        String courseStatus = String.valueOf(statusSpinner.getSelectedItem());
        Term courseTerm = (Term) termSpinner.getSelectedItem();

        int courseId = db.courseDAO.getCourseCount();
        int modifiedCourseId = -1;

        if (modifiedCourse != null) {
            modifiedCourseId = modifiedCourse.getId();
            courseId = modifiedCourseId;
        }
        Course newCourse = new Course(courseId, courseTitle, courseStart, courseEnd, courseStatus, courseTerm.getId());

        boolean soSave = false;
        boolean isValid = newCourse.isValid();

        if (isValid && modifiedCourseId == -1) {
            //on the newsies course added to dao
            soSave = db.courseDAO.addCourse(newCourse);
        } else if (isValid) {
            //up... up... updated assessment
            soSave = db.courseDAO.updateCourse(newCourse);
        }
        if (soSave) {
            Intent intent = new Intent(this, CourseDetailActivity.class);
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
