package com.prism.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Api("例子程序")
@RestController
@RefreshScope //注解@RefreshScope指示Config客户端在服务器配置改变时，也刷新注入的属性值
@RequestMapping("/demo")
public class DemoController {

    @ApiOperation(value = "例子",notes = "例子")
    @ApiImplicitParam(name = "", value = "", required = false, paramType = "query", dataType = "String")
    @RequestMapping(value = "/demo", method = RequestMethod.GET)
    @ResponseBody
    public String demo()
    {
         return "{status:ok}";
    }


    @Value("${profile}")
    private String profile;

    @ApiOperation(value = "例子",notes = "例子")
    @ApiImplicitParam(name = "", value = "", required = false, paramType = "query", dataType = "String")
    @RequestMapping(value = "/profile",method = RequestMethod.GET)
    public String hello(){
        return this.profile;
    }
}
