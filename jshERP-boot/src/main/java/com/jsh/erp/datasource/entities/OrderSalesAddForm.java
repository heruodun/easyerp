package com.jsh.erp.datasource.entities;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 销售订单 新建表单
 *
 * @Author dahang
 * @Date 2024-12-12 23:44:48
 * @Copyright dahang
 */

@Data
public class OrderSalesAddForm {

    private String address;

    private Integer addressId;

    private List<OrderGuigeEntity> guiges;

    private String remark;

    private Integer type;

    private String billNumber;

    private String userName;

    private Long userId;
    private Long tenantId;

}