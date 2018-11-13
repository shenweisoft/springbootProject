package com.jy.service;

import com.jy.model.SysUser;

import java.util.List;

public interface UserService {


    SysUser insertSysUser(SysUser sysUser);

    void sendRabbitMq();

}
