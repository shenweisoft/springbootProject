package com.jy.service.serviceImpl;

import com.jy.common.Constants;
import com.jy.common.CookieUtils;
import com.jy.common.JsonResult;
import com.jy.common.MD5;
import com.jy.config.rabbit.RabbitMessage;
import com.jy.config.redis.RedisUtils;
import com.jy.mapper.SysUserMapper;
import com.jy.model.SysUser;
import com.jy.service.UserService;
import com.jy.vo.SysUserVO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Service(value = "userService")
@ConfigurationProperties(prefix = "redisConfig")
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserMapper sysUserMapper;//这里会报错，但是并不会影响

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RedisUtils redisUtils;

    @Value("${redisConfig.REDIS_USER_SESSION_KEY}")
    private String REDIS_USER_SESSION_KEY;

    @Value("${redisConfig.SSO_SESSION_EXPIRE}")
    private Integer SSO_SESSION_EXPIRE;

    public SysUser insertSysUser(SysUser sysUser){

        sysUserMapper.insert(sysUser);
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

    /**
     * 验证用户登录操作
     * @param sysUserVO
     * @param jsonResult
     * @param request
     * @param response
     * @return
     */
    @Transactional
    public JsonResult<Object> login(SysUserVO sysUserVO, JsonResult jsonResult, HttpServletRequest request, HttpServletResponse response){

        SysUser sysUser = sysUserMapper.querySysUser(sysUserVO);
        if(sysUser == null){
            jsonResult.setMessage("暂无此用户");
            return jsonResult;
        }else{

            String password = MD5.encrypt(sysUserVO.getPassword() + sysUserVO.getLoginAccount()+sysUser.getSalt());
            if(!sysUser.getPassword().equals(password)){
                jsonResult.setMessage("密码错误");
                return jsonResult;
            }
        }
        // 生成token
        String token = UUID.randomUUID().toString();
        // 清空密码和盐避免泄漏
        String userPassword = sysUserVO.getPassword();
        String userSalt = sysUserVO.getSalt();
        sysUserVO.setPassword(null);
        sysUserVO.setSalt(null);

        // 把用户信息写入 redis
        redisUtils.set(REDIS_USER_SESSION_KEY + ":" + token, sysUserVO);
        // user 已经是持久化对象，被保存在session缓存当中，若user又重新修改属性值，那么在提交事务时，此时 hibernate对象就会拿当前这个user对象和保存在session缓存中的user对象进行比较，如果两个对象相同，则不会发送update语句，否则会发出update语句。
        sysUserVO.setPassword(userPassword);
        sysUserVO.setSalt(userSalt);
        // 设置 session 的过期时间 30分钟
        redisUtils.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE * 60);
        // 添加写 cookie 的逻辑，cookie 的有效期是关闭浏览器就失效。
        CookieUtils.setCookie(response, Constants.COOKIE_NAME, token);
        return jsonResult;
    }

    public void logout(HttpServletRequest request) {

        //根据token的键获取token的值
        String token = CookieUtils.getCookie(request, Constants.COOKIE_NAME);
        //删除redis中token
        redisUtils.del(REDIS_USER_SESSION_KEY + ":" + token);
    }

    public JsonResult<Object> queryUserByToken(HttpServletRequest request,JsonResult<Object> jsonResult) {

        //根据token的键获取token的值
        String token = CookieUtils.getCookie(request, Constants.COOKIE_NAME);

        if(!StringUtils.isEmpty(token)){
            // 根据token从redis中查询用户信息
            Object object = redisUtils.get(REDIS_USER_SESSION_KEY + ":" + token);
            SysUserVO sysUserVO = null;
            if(object != null){
                sysUserVO = (SysUserVO)object;
            }
            // 判断是否为空
            if (sysUserVO == null) {
                jsonResult.setMessage("此session已经过期，请重新登录");
                return jsonResult;
            }
            // 更新过期时间
            redisUtils.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);
            // 返回用户信息
            jsonResult.setResult(sysUserVO);
        }

        return jsonResult;
    }

}
