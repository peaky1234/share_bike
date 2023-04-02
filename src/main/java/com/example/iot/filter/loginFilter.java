package com.example.iot.filter;

//检查用户是否完成登录

import com.alibaba.fastjson.JSON;


import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter(filterName = "loginFilter",urlPatterns = "/*")
public class loginFilter implements Filter {

    //路径匹配器
    public  static final AntPathMatcher PATH_MATCHER=new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest=(HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse=(HttpServletResponse) servletResponse;

        //1，获取本次请求的URL
        String requestURL=httpServletRequest.getRequestURI();

        //需要放行的url路径
        String [] urls=new String[]{
                "/admin/login",
                "/",
                "/login.html",
                "/css/**",
                "/images/**",
                "/js/**",
                "/layui/**",
                "/vue/**",

        };
        //2,判断本次请求是否需要处理
        boolean check = check(urls, requestURL);
        //放行，不需要拦截
        if(check){
            //该句作用是 将请求转发给过滤器链上的下一个对象，如果没有，那就是你请求的资源。
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }

        //3.1，管理人员登录状态不在放行路径里，先判断登录状态。
        if(httpServletRequest.getSession().getAttribute("loginInfo")!=null){

            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }




        //4,如果未登录则返回未登录结果 ，通过输出流向客户端页面响应数据
        System.out.println(requestURL);
        log.info("未登录");

    }
     /*
     *路径匹配，检查此次请求是否放行
     * */
    public boolean check(String[] urls,String requestURL) {
        for(String url :urls){
             boolean match = PATH_MATCHER.match(url, requestURL);
             if(match)
                 return true;
        }
        return false;
    }
}
