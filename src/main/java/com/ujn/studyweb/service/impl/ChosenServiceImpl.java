package com.ujn.studyweb.service.impl;

import com.ujn.studyweb.mapper.ChosenMapper;
import com.ujn.studyweb.pojo.Chosen;
import com.ujn.studyweb.service.IChosenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChosenServiceImpl implements IChosenService {

    @Autowired
    private ChosenMapper chosenMapper;


    @Override
    public List<Chosen> queryChosenList() {
        return chosenMapper.queryChosenList();
    }

    @Override
    public Chosen queryChosenById(int id) {
        return chosenMapper.queryChosenById(id);
    }

    @Override
    public List<Chosen> queryChosenByExercise(int exercise) {
        return chosenMapper.queryChosenByExercise(exercise);
    }

    @Override
    public List<Chosen> queryChosenByExam(int exam) {
        return chosenMapper.queryChosenByExam(exam);
    }

    @Override
    public int addChosen(Chosen chosen) {
        return chosenMapper.addChosen(chosen);
    }

    @Override
    public int deleteChosen(int id) {
        return chosenMapper.deleteChosen(id);
    }

    @Override
    public int updateChosen(Chosen chosen) {
        return chosenMapper.updateChosen(chosen);
    }
}
