package com.ujn.studyweb.service.impl;

import com.ujn.studyweb.mapper.ClassPPTMapper;
import com.ujn.studyweb.pojo.ClassPPT;
import com.ujn.studyweb.pojo.PPT;
import com.ujn.studyweb.service.IClassPPTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassPPTServiceImpl implements IClassPPTService {

    @Autowired
    private ClassPPTMapper classPPTMapper;


    @Override
    public List<ClassPPT> queryClassPPTList() {
        return classPPTMapper.queryClassPPTList();
    }

    @Override
    public List<ClassPPT> queryClassPPTListByClass(int classId) {
        return classPPTMapper.queryClassPPTListByClass(classId);
    }

    @Override
    public List<ClassPPT> queryClassPPTListByPPT(int ppt) {
        return classPPTMapper.queryClassPPTListByPPT(ppt);
    }

    @Override
    public ClassPPT queryClassPPTById(int id) {
        return classPPTMapper.queryClassPPTById(id);
    }

    @Override
    public ClassPPT queryClassPPTByClassAndPPT(int classId, int pptId) {
        return classPPTMapper.queryClassPPTByClassAndPPT(classId,pptId);
    }

    @Override
    public int addClassPPT(ClassPPT classPPT) {
        return classPPTMapper.addClassPPT(classPPT);
    }

    @Override
    public int deleteClassPPT(int id) {
        return classPPTMapper.deleteClassPPT(id);
    }

    @Override
    public int updatePClassPPT(ClassPPT classPPT) {
        return classPPTMapper.updatePClassPPT(classPPT);
    }
}
