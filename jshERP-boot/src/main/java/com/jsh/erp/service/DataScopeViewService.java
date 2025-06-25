package com.jsh.erp.service;

import com.google.common.collect.Lists;
import com.jsh.erp.datasource.entities.RoleDataScopeEntity;
import com.jsh.erp.datasource.entities.User;
import com.jsh.erp.datasource.mappers.RoleDataScopeDao;
import com.jsh.erp.datasource.mappers.UserMapper;
import com.jsh.erp.utils.Constants;
import com.jsh.erp.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 数据范围
 *
 */
@Service
public class DataScopeViewService {


    @Resource
    private UserMapper userMapper;

    @Resource
    private DepartmentService departmentService;

    @Resource
    private UserService userService;

    @Resource
    private RoleDataScopeDao roleDataScopeDao;

    /**
     * 获取某人可以查看的所有人员信息
     */
    public List<Long> getCanViewEmployeeId(int viewType, Long employeeId) {
        if (Constants.VIEW_SCOPE_ME == viewType) {
            return this.getMeEmployeeIdList(employeeId);
        }
        if (Constants.VIEW_SCOPE_DEPARTMENT == viewType) {
            return this.getDepartmentEmployeeIdList(employeeId);
        }
        if (Constants.VIEW_SCOPE_DEPARTMENT_AND_SUB == viewType) {
            return this.getDepartmentAndSubEmployeeIdList(employeeId);
        }
        return Lists.newArrayList();
    }

    public String getCreatorStr(int dataScopeType) throws Exception {
        String creator = "";
        User user = userService.getCurrentUser();

        List<RoleDataScopeEntity> roleDataScopeList = roleDataScopeDao.listByUserId(user.getEmployeeId());
        if(roleDataScopeList == null || roleDataScopeList.size() == 0) {
            //默认个人
            creator = user.getEmployeeId().toString();
        }

        else {
            for (RoleDataScopeEntity rd : roleDataScopeList) {
                if(rd.getViewType() == null) {
                    continue;
                }
                //财务数据
                if(rd.getDataScopeType() == dataScopeType) {
                    List<Long> creatorList =  getCanViewEmployeeId(rd.getViewType(), user.getEmployeeId());
                    if(creatorList != null && creatorList.size() > 0){
                        for (Long employeeId : creatorList){
                            creator += employeeId + ",";
                        }
                    }
                }
            }
        }
        return creator;
    }

    public String[] getCreatorArray(int dataScopeType) throws Exception {
        String creator = getCreatorStr(dataScopeType);
        String [] creatorArray=null;
        if(StringUtil.isNotEmpty(creator)){
            creatorArray = creator.split(",");
        }
        return creatorArray;
    }


    public List<Long> getDepartmentAndSubIdList(Long employeeId) {
        User employeeEntity = userMapper.selectByPrimaryKey(employeeId);
        return departmentService.selfAndChildrenIdList(employeeEntity.getDepartmentId());
    }


    /**
     * 获取本人相关 可查看员工id
     */
    private List<Long> getMeEmployeeIdList(Long employeeId) {
        return Lists.newArrayList(employeeId);
    }

    /**
     * 获取本部门相关 可查看员工id
     */
    private List<Long> getDepartmentEmployeeIdList(Long employeeId) {
        User employeeEntity = userMapper.selectByPrimaryKey(employeeId);
        return userMapper.getEmployeeIdByDepartmentId(employeeEntity.getDepartmentId(), false);
    }

    /**
     * 获取本部门及下属子部门相关 可查看员工id
     */
    private List<Long> getDepartmentAndSubEmployeeIdList(Long employeeId) {
        List<Long> allDepartmentIds = getDepartmentAndSubIdList(employeeId);
        return userMapper.getEmployeeIdByDepartmentIdList(allDepartmentIds, false);
    }
}
