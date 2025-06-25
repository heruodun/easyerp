package com.jsh.erp.datasource.mappers;

import com.jsh.erp.datasource.entities.Department;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DepartmentMapper {
    int insertSelective(Department record);


    /**
     * 根据部门id，查询此部门直接子部门的数量
     *
     */
    Integer countSubDepartment(@Param("departmentId") Long departmentId);

    /**
     * 获取全部部门列表
     */
    List<Department> listAll();

    /**
     * 基于租户ID获取部门列表
     */
    List<Department> listByTenantId(Long tenantId);

}