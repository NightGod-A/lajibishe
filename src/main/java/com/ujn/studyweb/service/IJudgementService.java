package com.ujn.studyweb.service;

import com.ujn.studyweb.pojo.Judgement;

import java.util.List;

public interface IJudgementService {
    List<Judgement> queryJudgementList();
    Judgement queryJudgementById(int id);
    List<Judgement> queryJudgementByExercise(int exercise);
    List<Judgement> queryJudgementByExam(int exam);
    int addJudgement(Judgement judgement);
    int deleteJudgement(int id);
    int updateJudgement(Judgement judgement);
}
