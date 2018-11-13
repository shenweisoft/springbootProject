package com.jy.service.serviceImpl;

import com.jy.config.rabbit.RabbitMessage;
import com.jy.mapper.SysUserMapper;
import com.jy.model.SysUser;
import com.jy.service.UserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserMapper sysUserMapper;//这里会报错，但是并不会影响

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public SysUser insertSysUser(SysUser sysUser){

        sysUserMapper.deleteByPrimaryKey(10000);
        return sysUser;
    }

    public void sendRabbitMq(){

        SysUser sysUser = new SysUser();

        sysUser.setMobile("13717924030");
        sysUser.setLoginAccount("shenwei");

        RabbitMessage rabbitMessage = new RabbitMessage();
        rabbitMessage.setPrimaryKey("shenwei");
        rabbitMessage.setMessage(sysUser);
        rabbitMessage.setSource("wangzhandong");

        rabbitTemplate.convertAndSend("helloQueue",rabbitMessage);
    }


}
