package com.dali.movie.interceptor;

import com.dali.movie.constant.Constants;
import com.dali.movie.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

//拦截器——用拦截器来实现强制登录
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1. 获取token
        //2. 校验token, 判断是否放行
        String token = request.getHeader(Constants.REQUEST_HEADER_TOKEN);
        log.info("从header中获取token:{},{}", token,request.getRequestURI());
        //解析token
        Claims claims = JwtUtils.parseToken(token);
        if (claims==null){
            //校验失败
            response.setStatus(401);
            return false;
        }
        return true;
    }
}



