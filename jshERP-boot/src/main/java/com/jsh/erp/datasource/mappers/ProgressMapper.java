package com.jsh.erp.datasource.mappers;

import com.jsh.erp.datasource.entities.Progress;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProgressMapper {
    List<Progress> getProgressByNumberList(
            @Param("numberList") List<String> numberList);
}
