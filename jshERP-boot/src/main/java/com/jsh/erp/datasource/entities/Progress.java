package com.jsh.erp.datasource.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.jsh.erp.datasource.mappers.TraceTypeHandler;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class Progress {

    /**
     * 编号
     */
    @Id
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 订单编号
     */
    private String orderId;

    private String billNumber;


    /**
     * 轨迹
     */
    @TableField(typeHandler = TraceTypeHandler.class)
    private List<TraceEle> trace;

    /**
     * 当前状态
     */
    private String curStatus;

    /**
     * 当前时间
     */
    private LocalDateTime curTime;

    /**
     * 当前处理人
     */
    private String curOperator;

    /**
     * 当前处理人id
     */
    private Long curOperatorId;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建人id
     */
    private Long creatorId;

    /**
     * 删除标记
     */
    private Boolean deletedFlag;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 波次ID
     */
    private Long waveId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;
}