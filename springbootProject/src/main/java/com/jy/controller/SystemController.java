package com.jy.controller;

import com.jy.common.JsonResult;
import com.jy.common.ResultCode;
import com.jy.config.redis.RedisUtils;
import com.jy.model.SysUser;
import com.jy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/system")
public class SystemController {

    private static Logger logger = LoggerFactory.getLogger(SystemController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtils redisUtils;

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public JsonResult<Object> addUser(@RequestBody SysUser sysUser){

        JsonResult<Object> result = new JsonResult<Object>();
        try {
            //插入数据
           userService.insertSysUser(sysUser);

           result.setResult(sysUser);

           redisUtils.set("maliyuan","shenwei");

           String str = (String)redisUtils.get("maliyuan");System.out.println(str);

           redisUtils.set("shenwei",sysUser);

           SysUser sysUser1 = (SysUser) redisUtils.get("shenwei");System.out.println(sysUser1);

        } catch (Exception e) {
            logger.error("ERROR in queryFilingInfo: ", e);
            result.setResultCode(ResultCode.ERROR);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/sendRabbitMq", method = RequestMethod.POST)
    public JsonResult<Object> sendRabbitMq(@RequestBody SysUser sysUser){

        JsonResult<Object> result = new JsonResult<Object>();
        try {
           userService.sendRabbitMq();

        } catch (Exception e) {
            result.setResultCode(ResultCode.ERROR);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JsonResult<Object> login(@RequestBody SysUser sysUser){

        JsonResult<Object> result = new JsonResult<Object>();

        try {
            



            System.out.println(sysUser);

        } catch (Exception e) {
            e.printStackTrace();
            result.setResultCode(ResultCode.ERROR);
        }
        return result;
    }

}


