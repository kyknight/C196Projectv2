package com.example.c195projectv2.Database.Daos;

import com.example.c195projectv2.Models.Mentor;

import java.util.List;

public interface MentorDAOInterface {

    boolean addMentor(Mentor mentor);

    Mentor getMentorById(int mentorId);

    List<Mentor> getMentorsByCourse(int courseId);

    int getMentorCount();

    List<Mentor> getMentors();

    boolean removeMentor(Mentor mentor);
    boolean updateMentor(Mentor mentor);
}
