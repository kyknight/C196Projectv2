package com.example.c195projectv2.UI.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.c195projectv2.Database.Database;
import com.example.c195projectv2.Models.Note;
import com.example.c195projectv2.R;

public class NoteDetailFragment extends Fragment {
    public static final String ARG_NOTE_ID = "com.example.c195projectv2.assessmentdetailfragment.assessmentid";
    private Note mNote;
    public Database db;

    public NoteDetailFragment() {
    }

    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new Database(getContext());
        db.open();

        if (getArguments().containsKey(ARG_NOTE_ID)) {
            mNote = db.noteDAO.getNoteById(getArguments().getInt(ARG_NOTE_ID));

            Activity activity = this.getActivity();
            //for using master/detail layout. still deciding whether or not to use
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mNote.getTitle());
            }
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
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.note_detail, container, false);

        if (mNote != null) {
            ((TextView) rootView.findViewById(R.id.note_title_field)).setText(mNote.getTitle());
            ((TextView) rootView.findViewById(R.id.note_text_field)).setText(mNote.getText());
        }
        return rootView;
    }
}
