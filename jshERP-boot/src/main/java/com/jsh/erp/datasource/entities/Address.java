package com.jsh.erp.datasource.entities;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Address {


    @TableId(value = "address_id")
    private Long id;

    private String place;

    private String coordinates;

    private String price;

    private String remark;
    private String type;

    private Long supplierId;
    private Long creator;
    private Long tenantId;
    private Integer deletedFlag;

}