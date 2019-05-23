package com.example.c195projectv2.Database.Daos;

import com.example.c195projectv2.Models.Term;

import java.util.List;

public interface TermDAOInterface {

    boolean addTerm(Term term);

    Term getTermById(int termId);

    int getTermCount();

    List<Term> getTerms();

    boolean removeTerm(Term term);
    boolean updateTerm(Term term);
}
