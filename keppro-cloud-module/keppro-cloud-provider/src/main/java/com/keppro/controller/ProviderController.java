package com.keppro.controller;

import com.keppro.response.ObjectRestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "/provider", tags = "测试")
@RequestMapping("/provider")
public class ProviderController {
    /**
     * 添加一个新用户
     *
     * @return java.lang.Object
     * @author David Hong
     */
    @ApiOperation(value = "增加", notes = "相应的POS机")
    @GetMapping("/get")
    public ObjectRestResponse post() {

        return new ObjectRestResponse().rel(true).data(Math.random()*10);
    }


}
