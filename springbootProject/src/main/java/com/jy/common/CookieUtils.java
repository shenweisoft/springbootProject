package com.jy.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CookieUtils {

    /**
     * 设置cookie
     * @param response
     * @param key
     * @param token
     */
    public static void setCookie(HttpServletResponse response, String key,String token){

        Cookie cookie = new Cookie(key,token);
        cookie.setPath("/");

        response.addCookie(cookie);
    }

    public static String getCookie(HttpServletRequest request, String key){

        String cookie = null;

        Cookie[]  cookies = request.getCookies();
        if(cookies != null && cookies.length > 0){
            List<Cookie> cookieList = Stream.of(request.getCookies()).filter(item-> item.getName().equals(key)).collect(Collectors.toList());
            if(cookieList != null){
                cookie = cookieList.get(0).getValue();
            }
        }

        return cookie;
    }
}
