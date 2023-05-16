package com.ujn.studyweb.mapper;

import com.ujn.studyweb.pojo.Judgement;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface JudgementMapper {
    List<Judgement> queryJudgementList();
    Judgement queryJudgementById(int id);
    List<Judgement> queryJudgementByExercise(int exercise);
    List<Judgement> queryJudgementByExam(int exam);
    int addJudgement(Judgement judgement);
    int deleteJudgement(int id);
    int updateJudgement(Judgement judgement);
}
