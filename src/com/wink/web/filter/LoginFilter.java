package com.wink.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //0.强制转换
        HttpServletRequest request = (HttpServletRequest) req;
        //1.获取资源路径
        String uri = request.getRequestURI();
        //2.判断是否包含登录相关资源,要注意排除掉css/js/图片/验证码等资源
        if(uri.contains("/login.jsp")||uri.contains("loginServlet")||uri.contains("checkCodeServlet")||uri.contains("/css/")||uri.contains("/js/")||uri.contains("/fonts/")){
            //包含，放行
            chain.doFilter(req,resp);
        }else{
            //不包含，需要验证用户是否登录
            //3.从获取session中获取user
            Object user = request.getSession().getAttribute("user");
            if(user != null){
                //登陆了，放行
                chain.doFilter(req, resp);
            }else{
                //没有登录，跳转到登录页面
                request.setAttribute("login_msg","您尚未登录，请登录");
                request.getRequestDispatcher("/login.jsp").forward(request,resp);
            }
        }

    }

    public void init(FilterConfig config) throws ServletException {

    }

}