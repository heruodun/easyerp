package com.jsh.erp.datasource.mappers;

import com.jsh.erp.datasource.entities.Department;

public interface DepartmentMapper {
    int insertSelective(Department record);

}