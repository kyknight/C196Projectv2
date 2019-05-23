package com.example.c195projectv2.Database.Daos;

import com.example.c195projectv2.Models.Assessment;

import java.util.List;

/**
 *
 */
public interface AssessmentDAOInterface {
    boolean addAssessment(Assessment assessment);

    Assessment getAssessmentById(int assessmentId);

    List<Assessment> getAssessmentsByCourse(int courseId);

    int getAssessmentCount();

    List<Assessment> getAssessments();

    boolean removeAssessment(Assessment assessment);
    boolean updateAssessment(Assessment assessment);
}
