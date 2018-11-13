package com.jy.service;

import com.jy.common.JsonResult;
import com.jy.model.SysUser;
import com.jy.vo.SysUserVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserService {


    SysUser insertSysUser(SysUser sysUser);

    void sendRabbitMq();

    JsonResult<Object> login(SysUserVO sysUserVO, JsonResult jsonResult, HttpServletRequest request, HttpServletResponse response);

    void logout(HttpServletRequest request);

    JsonResult<Object> queryUserByToken(HttpServletRequest request,JsonResult<Object> jsonResult);
}
