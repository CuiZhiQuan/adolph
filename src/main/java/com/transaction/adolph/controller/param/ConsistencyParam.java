package com.transaction.adolph.controller.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author cuizhiquan
 * @Description
 * @date 2019/1/31 14:28
 * @Copyright (c) 2017, DaChen All Rights Reserved.
 */
@ApiModel(description = "注册最终一致性事务参数")
@Data
public class ConsistencyParam {

    @ApiModelProperty(dataType = "Long",name = "txId",notes = "事务ID",required = true)
    @NotNull(message = "事务ID不能为空")
    private Long txId;

    @ApiModelProperty(dataType = "String",name = "exchangeName",notes = "消息需要发送到的Exchange名称",required = true)
    @NotBlank(message = "Exchange名称不能为空")
    private String exchangeName;

    @ApiModelProperty(dataType = "String",name = "routingKey",notes = "路由Key",required = true)
    @NotBlank(message = "路由Key不能为空")
    private String routingKey;

    @ApiModelProperty(dataType = "String",name = "message",notes = "具体的消息内容JSON串，包含txId",required = true)
    @NotBlank(message = "消息内容不能为空")
    private String message;
}
