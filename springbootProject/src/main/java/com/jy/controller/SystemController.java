package com.jy.controller;

import com.jy.common.JsonResult;
import com.jy.common.ResultCode;
import com.jy.config.redis.RedisUtils;
import com.jy.model.SysUser;
import com.jy.service.UserService;
import com.jy.vo.SysUserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
            logger.error("ERROR in add: ", e);
            e.printStackTrace();
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

    /**
     * 用户登录
     * @param sysUserVO
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JsonResult<Object> login(@RequestBody SysUserVO sysUserVO, HttpServletRequest request, HttpServletResponse response){

        JsonResult<Object> result = new JsonResult<Object>();

        try {
            result = userService.login(sysUserVO,result,request,response);
        } catch (Exception e) {
            e.printStackTrace();
            result.setResultCode(ResultCode.ERROR);
        }
        return result;
    }

    /**
     * 用户退出
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public JsonResult<Object> logout(HttpServletRequest request) {

        JsonResult<Object> result = new JsonResult<Object>();

        try {
            userService.logout(request);
        } catch (Exception e) {
            e.printStackTrace();
            result.setResultCode(ResultCode.ERROR);
        }
        return result;
    }

    /**
     * 获取token
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getUserByToken", method = RequestMethod.POST)
    public JsonResult<Object> getUserByToken(HttpServletRequest request) {

        JsonResult<Object> result = new JsonResult<Object>();
        try {
            result = userService.queryUserByToken(request,result);
        } catch (Exception e) {
            e.printStackTrace();
            result.setResultCode(ResultCode.ERROR);
        }
        return result;
    }
}


