package com.jsh.erp.service;

import com.jsh.erp.datasource.entities.PrintTemplate;
import com.jsh.erp.datasource.mappers.PrintTemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrintTemplateService {

    @Autowired
    private PrintTemplateMapper printTemplateMapper;

    public int upsertTemplate(PrintTemplate template) {
        int rowsAffected = printTemplateMapper.insertOrUpdate(template);
        return rowsAffected;
    }

    public PrintTemplate getTemplate(String printType, Long tenantId) {
        return printTemplateMapper.selectByType(printType, tenantId);
    }

}

