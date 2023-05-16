package com.ujn.studyweb.service.impl;

import com.ujn.studyweb.mapper.PPTMapper;
import com.ujn.studyweb.pojo.PPT;
import com.ujn.studyweb.service.IPPTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PPTServiceImpl implements IPPTService {

    @Autowired
    private PPTMapper pptMapper;

    @Override
    public List<PPT> queryPPTList() {
        return pptMapper.queryPPTList();
    }

    @Override
    public PPT queryPPTById(int id) {
        return pptMapper.queryPPTById(id);
    }

    @Override
    public List<PPT> queryPPTByCourse(int course) {
        return pptMapper.queryPPTByCourse(course);
    }

    @Override
    public int addPPT(PPT ppt) {
        return pptMapper.addPPT(ppt);
    }

    @Override
    public int deletePPT(int id) {
        return pptMapper.deletePPT(id);
    }

    @Override
    public int updatePPT(PPT ppt) {
        return pptMapper.updatePPT(ppt);
    }
}
