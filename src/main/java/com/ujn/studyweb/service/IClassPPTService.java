package com.ujn.studyweb.service;

import com.ujn.studyweb.pojo.ClassPPT;
import com.ujn.studyweb.pojo.PPT;

import java.util.List;

public interface IClassPPTService {
    List<ClassPPT> queryClassPPTList();
    List<ClassPPT> queryClassPPTListByClass(int classId);
    List<ClassPPT> queryClassPPTListByPPT(int ppt);
    ClassPPT queryClassPPTById(int id);
    ClassPPT queryClassPPTByClassAndPPT(int classId,int pptId);
    int addClassPPT(ClassPPT classPPT);
    int deleteClassPPT(int id);
    int updatePClassPPT(ClassPPT classPPT);
}
