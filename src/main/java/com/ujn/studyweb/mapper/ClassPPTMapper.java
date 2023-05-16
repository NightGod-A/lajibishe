package com.ujn.studyweb.mapper;

import com.ujn.studyweb.pojo.ClassExam;
import com.ujn.studyweb.pojo.ClassPPT;
import com.ujn.studyweb.pojo.PPT;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ClassPPTMapper {
    List<ClassPPT> queryClassPPTList();
    List<ClassPPT> queryClassPPTListByClass(int classId);
    List<ClassPPT> queryClassPPTListByPPT(int ppt);
    ClassPPT queryClassPPTById(int id);
    ClassPPT queryClassPPTByClassAndPPT(int classId,int pptId);
    int addClassPPT(ClassPPT classPPT);
    int deleteClassPPT(int id);
    int updatePClassPPT(ClassPPT classPPT);
}
