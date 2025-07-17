package com.jsh.erp.datasource.mappers;

import com.alibaba.fastjson.TypeReference;
import com.jsh.erp.config.ListTypeHandler;
import com.jsh.erp.datasource.entities.TraceEle;

import java.util.List;

public class TraceTypeHandler extends ListTypeHandler<TraceEle> {


    @Override
    protected TypeReference<List<TraceEle>> specificType() {
        return new TypeReference<List<TraceEle>>() {
        };

    }
}
