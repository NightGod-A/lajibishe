package com.ujn.studyweb.config;

import com.ujn.studyweb.service.IPPTService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class GetFormulaTask implements ApplicationRunner {


    @Autowired
    private IPPTService pptService;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        pptService.startOfficeManager();
    }
}
