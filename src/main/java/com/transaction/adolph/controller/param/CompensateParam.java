package com.transaction.adolph.controller.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * @author cuizhiquan
 * @Description
 * @date 2019/1/29 16:22
 * @Copyright (c) 2017, DaChen All Rights Reserved.
 */
@ApiModel(description = "注册补偿事务参数")
@Data
public class CompensateParam {

    @ApiModelProperty(dataType = "String",name = "checkUri",notes = "校验事务发起者是否成功的URL",required = true)
    @NotBlank(message = "补偿URL不能为空")
    private String checkUri;

    @ApiModelProperty(dataType = "List",name = "participantsUri",notes = "事务参与者补偿URL",required = true)
    @NotEmpty(message = "事务参与者URL不能为空")
    private List<String> participantsUri;

    @ApiModelProperty(dataType = "Long", name = "executeTime",notes = "注册补偿事务多长时间后开始校验补偿")
    private Long executeTime;

    @ApiModelProperty(dataType = "Long", name = "expirationTime",notes = "注册补偿事务后多长时间过期")
    private Long expirationTime;

}
