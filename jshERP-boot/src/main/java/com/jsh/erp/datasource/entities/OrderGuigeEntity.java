package com.jsh.erp.datasource.entities;

import lombok.Data;

import java.util.List;

/**
 * 订单规格 实体类
 *
 * @Author dahang
 * @Date 2024-12-12 23:44:48
 * @Copyright dahang
 */

@Data
public class OrderGuigeEntity {
    /**
     * 规格
     */
    private String guige;

    /**
     * 数量
     */
    private Integer count;

    /**
     * 单位
     */
    private String danwei;

    /**
     * 条数明细
     */
    private List<OrderTiaoEntity> tiaos;

    /**
     * 其他明细 如 盘
     */

}