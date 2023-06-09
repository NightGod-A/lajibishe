package com.ujn.studyweb.service;

import com.ujn.studyweb.pojo.PPT;
import org.jodconverter.core.office.OfficeException;

import java.io.IOException;
import java.util.List;

public interface IPPTService {
    int generatePPT(String inputPath,String pdfPath,int id) throws OfficeException, IOException;
    void startOfficeManager() throws OfficeException;
    List<PPT> queryPPTList();
    PPT queryPPTById(int id);
    List<PPT> queryPPTByCourse(int course);
    int addPPT(PPT ppt);
    int deletePPT(int id);
    int updatePPT(PPT ppt);
}
