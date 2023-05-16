package com.ujn.studyweb.service;

import com.ujn.studyweb.pojo.Chosen;

import java.util.List;

public interface IChosenService {
    List<Chosen> queryChosenList();
    Chosen queryChosenById(int id);
    List<Chosen> queryChosenByExercise(int exercise);
    List<Chosen> queryChosenByExam(int exam);
    int addChosen(Chosen chosen);
    int deleteChosen(int id);
    int updateChosen(Chosen chosen);
}
