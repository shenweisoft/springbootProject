package com.jy.config.interceptor;
import org.springframework.messaging.handler.HandlerMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        AuthIgnore annotation;

        System.out.println(handler instanceof HandlerMethod);
        if(handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod)handler;
            System.out.println(handlerMethod);
            annotation = ((HandlerMethod) handler).getMethodAnnotation(AuthIgnore.class);
        }else{
            return true;
        }

        //如果有@AuthIgnore注解，则不验证token
       /* if(annotation != null){
            return true;
        }*/

        /*//获取用户凭证
        String token = request.getHeader(Constants.USER_TOKEN);
        if(StringUtils.isBlank(token)){
            token = request.getParameter(Constants.USER_TOKEN);
        }
        if(StringUtils.isBlank(token)){
            Object obj = request.getAttribute(Constants.USER_TOKEN);
            if(null!=obj){
                token=obj.toString();
            }
        }

        //token凭证为空
        if(StringUtils.isBlank(token)){
            throw new AuthException(Constants.USER_TOKEN + "不能为空", HttpStatus.UNAUTHORIZED.value());
        }*/

        return true;
    }
}