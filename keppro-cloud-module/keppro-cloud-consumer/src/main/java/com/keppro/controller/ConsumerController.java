package com.keppro.controller;

import com.keppro.response.ObjectRestResponse;
import com.keppro.service.ConsumerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yuan
 * @since 2019-05-11
 */
@RestController
@Api(value = "/consumer", tags = "测试")
@RequestMapping("/consumer")
public class ConsumerController {


    @Autowired
    private ConsumerService consumerService;

    /**
     * @return java.lang.Object
     * @author David Hong
     */
    @ApiOperation(value = "获取", notes = "获取")
    @GetMapping("/getInfo")
    public ObjectRestResponse post() {
        return consumerService.post();
    }


}
