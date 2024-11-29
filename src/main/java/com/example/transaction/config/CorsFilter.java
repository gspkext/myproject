/*
 * 跨域过滤器配置
 */
package com.example.transaction.config;

import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "CorsFilter ")
@Configuration
public class CorsFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 过滤器初始化
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws ServletException, IOException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        
        // 设置响应内容类型和字符编码
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        
        // 设置CORS相关响应头
        response.setHeader("Access-Control-Max-Age", "3600"); // 预检请求的有效期,单位为秒
        response.setHeader("Access-Control-Allow-Methods", "POST, GET,PUT, OPTIONS, DELETE");// 允许的HTTP方法
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin")); // 允许的来源域
        response.setHeader("Access-Control-Allow-Credentials", "true"); // 允许发送Cookie
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, token,AdminToken"); // 允许的请求头
        
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // 过滤器销毁
    }
}