package cn.itcast.travel.web.servlet;

import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: 邮件激活的Servlet
 * @Author: pengfei.wang
 * @CreateDate: 2020-02-21
 */
@WebServlet("/activeUserServlet")
public class ActiveUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取激活码(uuid)
        String code = request.getParameter("code");
        //进行非空判断
        if (code != null) {
            //说明用户注册完成，将进行激活操作
        //2.调用Service完成用户的激活操作
            UserService service = new UserServiceImpl();
            boolean flag = service.active(code);

        //3.判断标记
            String msg = null;
         if (flag) {
             //结果为true，说明用户已经成功激活
             msg = "激活成功，请<a href='login.html' style='color: green;'>登录</a>";

         }else {
             //结果为false，说明用户没有成功激活
             msg = "激活失败，请联系管理员";
         }

         response.setContentType("text/html;charset=utf-8");
            System.out.println(msg); //本机控制台打印提示信息
         response.getWriter().write(msg);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
