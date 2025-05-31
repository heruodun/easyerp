package com.jsh.erp.datasource.mappers;

import com.jsh.erp.datasource.entities.PrintTemplate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PrintTemplateMapper {


    PrintTemplate selectByType(@Param("printType")String printType, @Param("tenantId")Long tenantId);


    /**
     * 新增打印模板
     *
     * @param printTemplate 模板对象
     * @return 是否成功
     */
    int insertOrUpdate(@Param("printTemplate")PrintTemplate printTemplate);

}