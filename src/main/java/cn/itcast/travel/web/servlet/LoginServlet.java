package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.print.attribute.standard.NumberUp;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sound.sampled.Line;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @Description: 用户登录的Servlet
 * @Author: pengfei.wang
 * @CreateDate: 2020-02-22
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        UserService service = new UserServiceImpl();
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

        //8.响应对象
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),info);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
