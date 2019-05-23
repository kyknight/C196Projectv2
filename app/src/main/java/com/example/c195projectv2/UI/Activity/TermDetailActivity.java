package com.example.c195projectv2.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.c195projectv2.Database.Database;
import com.example.c195projectv2.Models.Term;
import com.example.c195projectv2.R;
import com.example.c195projectv2.UI.Fragments.NoteDetailFragment;
import com.example.c195projectv2.UI.Fragments.TermDetailFragment;

/**
 *
 */
public class TermDetailActivity extends AppCompatActivity {
    public static final String TERM_ID = "com.example.c195projectv2.termdetailactivity.termid";
    private Term selectedTerm;
    public Database db;

    /**
     * This method sets the contentView and toolbar & floating action items
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);
        db = new Database(this);
        db.open();
        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        final int selectedTermId = getIntent().getIntExtra(TermDetailFragment.ARG_TERM_ID, 0);
        selectedTerm = db.termDAO.getTermById(selectedTermId);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermDetailActivity.this, TermEditorActivity.class);

                intent.putExtra(TermDetailFragment.ARG_TERM_ID, selectedTermId);

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
            arguments.putInt(TermDetailFragment.ARG_TERM_ID,
                    getIntent().getIntExtra(TermDetailFragment.ARG_TERM_ID, 0));

            TermDetailFragment fragment = new TermDetailFragment();

            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.term_detail_container, fragment)
                    .commit();
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
                NavUtils.navigateUpTo(this, new Intent(this, TermListActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This method directs the user to the course edit activity/layout to enable the user to edit the selected course
     * @param view
     */
    public void onCourseEdit(View view) {
        Intent intent = new Intent(this, CourseEditorActivity.class);
        intent.putExtra(TermDetailFragment.ARG_TERM_ID, selectedTerm.getId());
        startActivity(intent);
    }
}
