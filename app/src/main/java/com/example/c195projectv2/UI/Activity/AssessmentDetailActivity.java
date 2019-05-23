package com.example.c195projectv2.UI.Activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.c195projectv2.Database.Database;
import com.example.c195projectv2.Models.Assessment;
import com.example.c195projectv2.R;
import com.example.c195projectv2.Receivers.AlarmReceiver;
import com.example.c195projectv2.UI.Fragments.AssessmentDetailFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 */
public class AssessmentDetailActivity extends AppCompatActivity {
    private Assessment selectedAssessment;
    public Database db;

    /**
     * This method sets the contentView and toolbar & floating action items
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);
        db = new Database(this);
        db.open();
        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        final int selectedAssessmentId = getIntent().getIntExtra(AssessmentDetailFragment.ARG_ASSESSMENT_ID, 0);
        selectedAssessment = db.assessmentDAO.getAssessmentById(selectedAssessmentId);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AssessmentDetailActivity.this, AssessmentEditorActivity.class);
                intent.putExtra(AssessmentDetailFragment.ARG_ASSESSMENT_ID, selectedAssessmentId);
                startActivity(intent);
            }
        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //fragments api guide: http://developer.android.com/guide/components/fragments.html ***For my reference
        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putInt(AssessmentDetailFragment.ARG_ASSESSMENT_ID,
                    getIntent().getIntExtra(AssessmentDetailFragment.ARG_ASSESSMENT_ID, 0));

            AssessmentDetailFragment fragment = new AssessmentDetailFragment();

            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.assessment_detail_container, fragment)
                    .commit();
        }
    }

    /**
     * This method inflates the menu to add items to the action bar if it is present
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_reminder, menu);
        return true;
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
     * This method handles action to take place based on the item selected
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                // ID represents the Home or Up button; basically if this case activity, up button is shown
                // uses NavUtils to allow users to nav up a level in app structure.
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back  ***For my reference
                NavUtils.navigateUpTo(this, new Intent(this, AssessmentListActivity.class));
                return true;
            case R.id.action_reminder:
                String alarmTitle = "Assessment Reminder";
                String alarmText = "Assessment '" + selectedAssessment.getName() + "' is today.";

                Intent alarmIntent = new Intent(this.getApplicationContext(), AlarmReceiver.class);
                alarmIntent.putExtra("mNotificationTitle", alarmTitle);
                alarmIntent.putExtra("mNotificationContent", alarmText);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 123456789, alarmIntent, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
                    Date date = dateFormat.parse(selectedAssessment.getDate());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);

                    alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.action_home:
                Intent iHome = new Intent(this, MainActivity.class);
                startActivity(iHome);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
