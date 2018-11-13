package com.jy.config.interceptor;
import com.jy.common.JsonResult;
import com.jy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //根据token的值获取用户的信息
        JsonResult<Object> result = new JsonResult<Object>();
        result = userService.queryUserByToken(request,result);
        //如果result不為空表示已經登錄
        if (null == result.getResult()) {
            // 跳转到登录页面，把用户请求的url作为参数传递给登录页面。
            //response.sendRedirect("http://localhost:8081/login?redirect=" + request.getRequestURL());
            System.out.println("session没登录");
            return false;
        }
        return true;
    }
}