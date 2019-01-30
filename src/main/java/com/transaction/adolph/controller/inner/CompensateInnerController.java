package com.transaction.adolph.controller.inner;

import com.transaction.adolph.controller.param.CompensateParam;
import com.transaction.adolph.service.CompensateService;
import com.transaction.adolph.utils.JSONMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author cuizhiquan
 * @Description
 * @date 2019/1/29 14:15
 * @Copyright (c) 2017, DaChen All Rights Reserved.
 */

@RestController
@RequestMapping("/inner/compensate")
@Api(tags = "补偿事务")
public class CompensateInnerController {

    @Autowired
    private CompensateService compensateService;

    @ApiOperation(value = "注册补偿事务",notes = "事务发起者调用该接口注册补偿事务",httpMethod = "POST")
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public JSONMessage register(@RequestBody @Valid CompensateParam param) {
        return JSONMessage.success("success",compensateService.register(param));
    }
}
