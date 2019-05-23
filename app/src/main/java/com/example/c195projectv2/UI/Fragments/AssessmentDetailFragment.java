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
import com.example.c195projectv2.Models.Assessment;
import com.example.c195projectv2.R;

/**
 *
 */
public class AssessmentDetailFragment extends Fragment {
    public static final String ARG_ASSESSMENT_ID = "com.example.c195projectv2.assessmentdetailfragment.assessmentid";
    private Assessment mAssessment;
    public Database db;

    public AssessmentDetailFragment() {
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

        if (getArguments().containsKey(ARG_ASSESSMENT_ID)) {
            mAssessment = db.assessmentDAO.getAssessmentById(getArguments().getInt(ARG_ASSESSMENT_ID));

            Activity activity = this.getActivity();
            //for using master/detail layout
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mAssessment.getName());
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
        View rootView = inflater.inflate(R.layout.assessment_detail, container, false);

        if (mAssessment != null) {
            ((TextView) rootView.findViewById(R.id.assessment_title_field)).setText(mAssessment.getName());
            ((TextView) rootView.findViewById(R.id.assessment_type_field)).setText(mAssessment.getType());
            ((TextView) rootView.findViewById(R.id.assessment_date_field)).setText(mAssessment.getDate());
        }
        return rootView;
    }
}
