package com.jsh.erp.service;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 部门 service
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2022-01-12 20:37:48
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@Service
public class DepartmentService {


    @Resource
    private DepartmentCacheManager departmentCacheManager;






    /**
     * 清除自身以及下级的id列表缓存
     */
    private void clearCache() {
        departmentCacheManager.clearCache();
    }

    // ---------------------------- 查询 ----------------------------




    /**
     * 自身以及所有下级的部门id列表
     *
     */
    public List<Long> selfAndChildrenIdList(Long departmentId) {
        return departmentCacheManager.getDepartmentSelfAndChildren(departmentId);
    }





}
