package com.ujn.studyweb.mapper;

import com.ujn.studyweb.pojo.PPT;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PPTMapper {
    List<PPT> queryPPTList();
    PPT queryPPTById(int id);
    List<PPT> queryPPTByCourse(int course);
    int addPPT(PPT ppt);
    int deletePPT(int id);
    int updatePPT(PPT ppt);
}
