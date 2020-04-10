package com.wink.web.servlet;

import com.wink.domain.User;
import com.wink.service.UserService;
import com.wink.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1.设置编码
        request.setCharacterEncoding("utf-8");

        //2.获取数据，封装User对象

        Map<String, String[]> map = request.getParameterMap();
        User user = new User();

        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //3.调用Service查询
        UserService service = new UserServiceImpl();
        User loginUser = service.login(user);

        //4.判断是否登录成功
        if(loginUser != null){

            //5.验证码校验
            //获取验证码
            String verifycode = request.getParameter("verifycode");
            //获取Session中的验证码
            String session_code = (String)request.getSession().getAttribute("CHECKCODE_SERVER");
            request.getSession().removeAttribute("CHECKCODE_SERVER");
            if(session_code.equalsIgnoreCase(verifycode)){
                //将用户存入session
                request.getSession().setAttribute("user",loginUser);
                //跳转页面
                response.sendRedirect(request.getContextPath()+"/index.jsp");

            }else{
                //验证码不正确
                request.setAttribute("login_msg","验证码不正确");
                request.getRequestDispatcher("/login.jsp").forward(request,response);
            }

        }else{
            request.setAttribute("login_msg","用户名或密码不正确");
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
