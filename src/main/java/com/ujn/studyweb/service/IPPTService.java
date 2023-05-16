package com.ujn.studyweb.service;

import com.ujn.studyweb.pojo.PPT;

import java.util.List;

public interface IPPTService {
    List<PPT> queryPPTList();
    PPT queryPPTById(int id);
    List<PPT> queryPPTByCourse(int course);
    int addPPT(PPT ppt);
    int deletePPT(int id);
    int updatePPT(PPT ppt);
}
