package com.transaction.adolph.controller.inner;

import com.transaction.adolph.controller.param.ConsistencyParam;
import com.transaction.adolph.domain.enums.ConsistencyEnum;
import com.transaction.adolph.service.ConsistencyService;
import com.transaction.adolph.utils.JSONMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author cuizhiquan
 * @Description
 * @date 2019/1/30 16:45
 * @Copyright (c) 2017, DaChen All Rights Reserved.
 */

@RestController
@RequestMapping("/inner/consistency")
@Api(tags = "最终一致性事务")
public class ConsistencyInnerController {

    @Autowired
    private ConsistencyService consistencyService;

    @ApiOperation(value = "注册最终一致性事务",notes = "事务发起者调用该接口注册最终一致性事务",httpMethod = "GET")
    @ApiImplicitParam(name = "checkUri", value = "校验URL", dataType = "String",paramType = "query", required = true)
    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public JSONMessage register(@RequestParam String checkUri) {
        return JSONMessage.success("success",consistencyService.register(checkUri));
    }

    @ApiOperation(value = "新增最终一致性事务参与者",notes = "新增最终一致性事务参与者",httpMethod = "POST")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public JSONMessage add(@RequestBody @Valid List<ConsistencyParam> params) {
        consistencyService.add(params);
        return JSONMessage.success("success");
    }

    @ApiOperation(value = "当事务发起者自身成功之后调用修改事务状态")
    @ApiImplicitParam(name = "txId", value = "事务ID", dataType = "String",paramType = "path", required = true)
    @RequestMapping(value = "/{txId}", method = RequestMethod.GET)
    public void update(@PathVariable Long txId) {
        consistencyService.updateStatus(txId, ConsistencyEnum.Status.TO_BE_SENT.getIndex());
    }

    @ApiOperation(value = "当事务参与者成功之后调用修改事务状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "txId", value = "事务ID", dataType = "String", paramType = "path", required = true),
            @ApiImplicitParam(name = "txPId", value = "事务参与者ID", dataType = "String", paramType = "path", required = true)
    })
    @RequestMapping(value = "/{txId}/{txPId}", method = RequestMethod.GET)
    public void update(@PathVariable Long txId,@PathVariable Long txPId) {
        consistencyService.updateStatus(txId,txPId);
    }
}
