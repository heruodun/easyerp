package com.jsh.erp.utils;

import java.util.UUID;

/**
 * by jishenghua qq-752718920 2018-10-7 12:01:36
 */
public class Constants {

    //查询参数
    public final static String PAGE_SIZE = "pageSize";
    public final static String CURRENT_PAGE = "currentPage";
    public final static String ORDER = "order";
    public final static String FILTER = "filter";
    public final static String SPLIT = ",";
    public final static String SEARCH = "search";
    public final static String DEVICE_ID = "deviceId";
    public final static String OFFSET = "offset";
    public final static String ROWS = "rows";
    public final static String IS_RECURSION = "isRecursion";
    public final static String IS_RECURSION_VALUE = "1";
    public final static String IS_QUERYBYNODEID = "isquerybyid";
    public final static String IS_QUERYBYNODEID_VALUE = "1";

    //级联类别
    public final static String TYPE = "type";

    //转发
    public final static String TEAM = "team";

    //增加了角色等级常量
    public final static String LEVEL="level";

    /**
     * NOTICE(1, 1, "系统通知", "系统通知数据范围"),
     * FINANCIAL(2, 2, "财务数据", "系统财务数据范围"),
     * BILL(3, 3, "账单数据", "系统账单数据范围"),
     *
     *
     * ME(0, 0, "本人"),
     * DEPARTMENT(1, 5, "本部门"),
     * DEPARTMENT_AND_SUB(2, 10, "本部门及下属子部门"),
     * ALL(10, 100, "全部");
     */


    public static final int VIEW_SCOPE_ME = 0;
    public static final int VIEW_SCOPE_DEPARTMENT = 1;
    public static final int VIEW_SCOPE_DEPARTMENT_AND_SUB = 2;
    public static final int VIEW_SCOPE_ALL = 10;

    public static final int DATA_SCOPE_NOTICE = 1;
    public static final int DATA_SCOPE_FINANCIAL = 2;
    public static final int DATA_SCOPE_BILL = 3;


}