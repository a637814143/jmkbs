//package com.dali.movie.config;
//
//// 在这里运用拦截器
//import com.dali.movie.interceptor.LoginInterceptor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import java.util.Arrays;
//import java.util.List;
//
////交给spring boot托管
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//    @Autowired
//    private LoginInterceptor loginInterceptor;
//
//    private final List excludes =  Arrays.asList(
//            "/**/*.html",
//            "/blog-editormd/**",
//            "/css/**",
//            "/js/**",
//            "/pic/**",
//            "/user/login",
//            "/user/register",
//            "/favicon.ico",
//            "/error"
//    );
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(loginInterceptor)
//                .addPathPatterns("/**")
//                .excludePathPatterns(excludes);
//    }
//}
//
