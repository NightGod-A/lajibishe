package com.ujn.studyweb.mapper;

import com.ujn.studyweb.pojo.Chosen;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ChosenMapper {
    List<Chosen> queryChosenList();
    Chosen queryChosenById(int id);
    List<Chosen> queryChosenByExercise(int exercise);
    List<Chosen> queryChosenByExam(int exam);
    int addChosen(Chosen chosen);
    int deleteChosen(int id);
    int updateChosen(Chosen chosen);
}
