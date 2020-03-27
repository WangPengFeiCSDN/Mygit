package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Description: 用户退出的Servlet
 * @Author: pengfei.wang
 * @CreateDate: 2020-02-22
 */
@WebServlet("/exitServlet")
public class ExitServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取当前用户
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        //将Session中的当前用户移除
        session.invalidate(); //Session的自我销毁方法
        //用户重定向到登录页面
        response.sendRedirect(request.getContextPath()+"/login.html"); //重定向必须添加上项目的虚拟目录



    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
