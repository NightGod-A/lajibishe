package com.ujn.studyweb.service.impl;

import com.ujn.studyweb.mapper.JudgementMapper;
import com.ujn.studyweb.pojo.Judgement;
import com.ujn.studyweb.service.IJudgementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JudgementServiceImpl implements IJudgementService {

    @Autowired
    private JudgementMapper judgementMapper;

    @Override
    public List<Judgement> queryJudgementList() {
        return judgementMapper.queryJudgementList();
    }

    @Override
    public Judgement queryJudgementById(int id) {
        return judgementMapper.queryJudgementById(id);
    }

    @Override
    public List<Judgement> queryJudgementByExercise(int exercise) {
        return judgementMapper.queryJudgementByExercise(exercise);
    }

    @Override
    public List<Judgement> queryJudgementByExam(int exam) {
        return judgementMapper.queryJudgementByExam(exam);
    }

    @Override
    public int addJudgement(Judgement judgement) {
        return judgementMapper.addJudgement(judgement);
    }

    @Override
    public int deleteJudgement(int id) {
        return judgementMapper.deleteJudgement(id);
    }

    @Override
    public int updateJudgement(Judgement judgement) {
        return judgementMapper.updateJudgement(judgement);
    }
}
