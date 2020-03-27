package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @Description:  统一管理用户的Servlet
 * @Author: pengfei.wang
 * @CreateDate: 2020-02-23
 */
@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    //声明UserService业务对象
    UserService service = new UserServiceImpl();

    /**
     * 用户注册功能的Servlet
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void regist(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        //校验验证码(优先验证)
        String check = request.getParameter("check");
        //从seesion中获取到生成的验证码
        HttpSession session = request.getSession();
        String checkCode = (String) session.getAttribute("CHECKCODE_SERVER");
        //为了保证验证码只能使用一次
        session.removeAttribute("CHECKCODE_SERVER");
        //判断用户输入的验证码是否与用户输入的一致
        if (checkCode == null || !checkCode.equalsIgnoreCase(check)) {
            //结果为true，说明验证码不一致
            ResultInfo info = new ResultInfo();
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            //将info对象序列化为json
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            //设置响应类型
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return;
        }

        //1.获取数据
        Map<String, String[]> map = request.getParameterMap();

        //2.封装对象
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //3.调用service完成注册
        //UserService service = new UserServiceImpl();  成员变量已经声明，无需再创建
        boolean flag = service.regist(user);
        ResultInfo info = new ResultInfo();
        //4.响应结果
        if(flag){
            //注册成功
            info.setFlag(true);
            info.setErrorMsg("注册成功");
        }else{
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("注册失败!");
        }

        //将info对象序列化为json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);

        //将json数据写回客户端
        //设置content-type
        response.setContentType("application/json;charset=utf-8");
        System.out.println(json);
        response.getWriter().write(json);


    }

    /**
     *  用户登录功能的Servlet
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        //校验验证码是否正确(优先判断)
        String check = request.getParameter("check"); //获取验证码
        HttpSession session = request.getSession(); //获取Session对象
        String checkCode = (String)session.getAttribute("CHECKCODE_SERVER"); //从session中获取生成的验证码
        session.removeAttribute("CHECKCODE_SERVER"); //将验证码及时删除，保证一次性
        //判断用户输入的验证码和生成的是否一致
        if (checkCode == null || !checkCode.equalsIgnoreCase(check) ) {
            //结果为true，说明验证码未通过校验
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误，请刷新重新输入！");
            //将info对象序列化为json
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            //设置响应类型
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return;

        }

        //1.获取用户名和密码数据
        Map<String, String[]> map = request.getParameterMap();
        //2.封装User对象
        User user = new User();
        //调用工具类将User对象进行封装
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //3.调用Service进行用户的查询
        //UserService service = new UserServiceImpl(); 成员变量已经声明，无需再创建
        User u = service.login(user);
        ResultInfo info = new ResultInfo();
        //4.判断用户对象是否为null
        if (u == null) {
            //结果为true，说明没有从数据库中查询到对象
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误！！！");
        }

        //5.判断用户是否激活
        if (u != null && !"Y".equals(u.getStatus())) {
            //结果为true，说明用户存在但尚未进行邮件激活
            info.setFlag(false);
            info.setErrorMsg("您尚未激活，叫爸爸即可激活！！！");
        }

        //6.判断登录成功
        if (u != null && "Y".equals(u.getStatus())) {
            //结果为true，说明用户已成功登录
            request.getSession().setAttribute("user",u); //将当前用户添加到session中
            info.setFlag(true);
            info.setErrorMsg("登录成功，欢迎回来！！！");

        }

        //7.将info对象序列化为json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),info);

    }

    /**
     *  查询单个用户功能的Servlet
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        //从Session获取登录用户
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        //将user写回客户端
/*        ObjectMapper mapper = new ObjectMapper();
        //设置响应类型
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),user);*/
        writeValue(user,response);
    }

    /**
     *  用户退出的Servlet
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        //获取当前用户
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        //将Session中的当前用户移除
        session.invalidate(); //Session的自我销毁方法
        //用户重定向到登录页面
        response.sendRedirect(request.getContextPath()+"/login.html"); //重定向必须添加上项目的虚拟目录
    }

    /**
     * 用户激活功能的Servlet
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void active(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        //1.获取激活码(uuid)
        String code = request.getParameter("code");
        //进行非空判断
        if (code != null) {
            //说明用户注册完成，将进行激活操作
            //2.调用Service完成用户的激活操作
            //UserService service = new UserServiceImpl(); 成员变量已经声明，无需再创建
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
}
