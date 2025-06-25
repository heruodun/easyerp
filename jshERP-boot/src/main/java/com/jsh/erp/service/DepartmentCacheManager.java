package com.jsh.erp.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jsh.erp.datasource.entities.Department;
import com.jsh.erp.datasource.entities.DepartmentTree;
import com.jsh.erp.datasource.mappers.DepartmentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 部门 缓存相关
 *
 * @Author 1024创新实验室-主任: 卓大
 * @Date 2022-01-12 20:37:48
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@Slf4j
@Service
public class DepartmentCacheManager {

    @Resource
    private DepartmentMapper departmentDao;

    private void logClearInfo(String cache) {
        log.info("clear " + cache);
    }

    //    @CacheEvict(value = {AdminCacheConst.Department.DEPARTMENT_LIST_CACHE, AdminCacheConst.Department.DEPARTMENT_MAP_CACHE, AdminCacheConst.Department.DEPARTMENT_SELF_CHILDREN_CACHE, AdminCacheConst.Department.DEPARTMENT_TREE_CACHE, AdminCacheConst.Department.DEPARTMENT_PATH_CACHE,}, allEntries = true)
    public void clearCache() {
        logClearInfo("Clear Cache");
    }


    /**
     * 部门列表
     */
//    @Cacheable(AdminCacheConst.Department.DEPARTMENT_LIST_CACHE)
    public List<Department> getDepartmentList() {
        return departmentDao.listAll();
    }

    public List<Department> getDepartmentListByTenantId(Long tenantId) {
        return departmentDao.listByTenantId(tenantId);
    }

    /**
     * 部门map
     *
     */
//    @Cacheable(AdminCacheConst.Department.DEPARTMENT_MAP_CACHE)
    public Map<Long, Department> getDepartmentMap() {
        return departmentDao.listAll().stream().collect(Collectors.toMap(Department::getDepartmentId, Function.identity()));
    }


    /**
     * 缓存部门树结构
     *
     */
//    @Cacheable(AdminCacheConst.Department.DEPARTMENT_TREE_CACHE)
    public List<DepartmentTree> getDepartmentTree(Long tenantId) {
        List<Department> departmentVOList = departmentDao.listByTenantId(tenantId);
        return this.buildTree(departmentVOList);
    }

    /**
     * 缓存某个部门的下级id列表
     *
     */
//    @Cacheable(AdminCacheConst.Department.DEPARTMENT_SELF_CHILDREN_CACHE)
    public List<Long> getDepartmentSelfAndChildren(Long departmentId) {
        List<Department> departmentVOList = departmentDao.listAll();
        return this.selfAndChildrenIdList(departmentId, departmentVOList);
    }


    /**
     * 部门的路径名称
     *
     */
//    @Cacheable(AdminCacheConst.Department.DEPARTMENT_PATH_CACHE)
    public Map<Long, String> getDepartmentPathMap() {
        List<Department> departmentVOList = departmentDao.listAll();
        Map<Long, Department> departmentMap = departmentVOList.stream().collect(Collectors.toMap(Department::getDepartmentId, Function.identity()));

        Map<Long, String> pathNameMap = Maps.newHashMap();
        for (Department departmentVO : departmentVOList) {
            String pathName = this.buildDepartmentPath(departmentVO, departmentMap);
            pathNameMap.put(departmentVO.getDepartmentId(), pathName);
        }

        return pathNameMap;
    }

    /**
     * 构建父级考点路径
     */
    private String buildDepartmentPath(Department departmentVO, Map<Long, Department> departmentMap) {
        if (Objects.equals(departmentVO.getParentId(), 0L)) {
            return departmentVO.getName();
        }
        //父节点
        Department parentDepartment = departmentMap.get(departmentVO.getParentId());
        if (parentDepartment == null) {
            return departmentVO.getName();
        }
        String pathName = buildDepartmentPath(parentDepartment, departmentMap);
        return pathName + "/" + departmentVO.getName();

    }
    // ---------------------- 构造树的一些方法 ------------------------------

    /**
     * 构建部门树结构
     *
     */
    public List<DepartmentTree> buildTree(List<Department> voList) {
        if (CollectionUtils.isEmpty(voList)) {
            return Lists.newArrayList();
        }
        List<Department> rootList = voList.stream().filter(e -> e.getParentId() == null
                || Objects.equals(e.getParentId(), 0L)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(rootList)) {
            return Lists.newArrayList();
        }
        List<DepartmentTree> treeVOList = copyList(rootList, DepartmentTree.class);
        this.recursiveBuildTree(treeVOList, voList);
        return treeVOList;
    }

    /**
     * 构建所有根节点的下级树形结构
     *
     */
    private void recursiveBuildTree(List<DepartmentTree> nodeList, List<Department> allDepartmentList) {
        int nodeSize = nodeList.size();
        for (int i = 0; i < nodeSize; i++) {
            int preIndex = i - 1;
            int nextIndex = i + 1;
            DepartmentTree node = nodeList.get(i);
            if (preIndex > -1) {
                node.setPreId(nodeList.get(preIndex).getDepartmentId());
            }
            if (nextIndex < nodeSize) {
                node.setNextId(nodeList.get(nextIndex).getDepartmentId());
            }

            ArrayList<Long> selfAndAllChildrenIdList = Lists.newArrayList();
            selfAndAllChildrenIdList.add(node.getDepartmentId());
            node.setSelfAndAllChildrenIdList(selfAndAllChildrenIdList);

            List<DepartmentTree> children = getChildren(node.getDepartmentId(), allDepartmentList);
            if (!CollectionUtils.isEmpty(children)) {
                node.setChildren(children);
                this.recursiveBuildTree(children, allDepartmentList);
            }
        }
    }


    /**
     * 获取子元素
     *
     */
    private List<DepartmentTree> getChildren(Long departmentId, List<Department> voList) {
        List<Department> childrenEntityList = voList.stream().filter(e -> departmentId.equals(e.getParentId())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(childrenEntityList)) {
            return Lists.newArrayList();
        }
        return copyList(childrenEntityList, DepartmentTree.class);
    }


    /**
     * 通过部门id,获取当前以及下属部门
     *
     */
    public List<Long> selfAndChildrenIdList(Long departmentId, List<Department> voList) {
        List<Long> selfAndChildrenIdList = Lists.newArrayList();
        if (CollectionUtils.isEmpty(voList)) {
            return selfAndChildrenIdList;
        }
        selfAndChildrenIdList.add(departmentId);
        List<DepartmentTree> children = this.getChildren(departmentId, voList);
        if (CollectionUtils.isEmpty(children)) {
            return selfAndChildrenIdList;
        }
        List<Long> childrenIdList = children.stream().map(DepartmentTree::getDepartmentId).collect(Collectors.toList());
        selfAndChildrenIdList.addAll(childrenIdList);
        for (Long childId : childrenIdList) {
            this.selfAndChildrenRecursion(selfAndChildrenIdList, childId, voList);
        }
        return selfAndChildrenIdList;
    }

    /**
     * 递归查询
     */
    public void selfAndChildrenRecursion(List<Long> selfAndChildrenIdList, Long departmentId, List<Department> voList) {
        List<DepartmentTree> children = this.getChildren(departmentId, voList);
        if (CollectionUtils.isEmpty(children)) {
            return;
        }
        List<Long> childrenIdList = children.stream().map(DepartmentTree::getDepartmentId).collect(Collectors.toList());
        selfAndChildrenIdList.addAll(childrenIdList);
        for (Long childId : childrenIdList) {
            this.selfAndChildrenRecursion(selfAndChildrenIdList, childId, voList);
        }
    }

    public static <T, K> List<K> copyList(List<T> source, Class<K> target) {
        if (null == source || source.isEmpty()) {
            return Collections.emptyList();
        }
        return source.stream().map(e -> copy(e, target)).collect(Collectors.toList());
    }

    public static <T> T copy(Object source, Class<T> target) {
        if (source == null || target == null) {
            return null;
        }
        try {
            T newInstance = target.newInstance();
            BeanUtils.copyProperties(source, newInstance);
            return newInstance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
