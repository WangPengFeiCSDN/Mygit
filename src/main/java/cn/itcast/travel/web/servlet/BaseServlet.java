package cn.itcast.travel.web.servlet;

import com.alibaba.druid.sql.dialect.sqlserver.visitor.SQLServerParameterizedOutputVisitor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Description: 创建的基本的Servlet
 * @Author: pengfei.wang
 * @CreateDate: 2020-02-23
 */
public class BaseServlet extends HttpServlet {

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
       // System.out.println("baseServlet中的Service方法被执行了");

        //完成方法的分发

        //1.获取请求路径
        String uri = req.getRequestURI();
        //System.out.println("请求的路径："+uri); //请求的路径：/travel/user/add
        //2.获取方法名称
        String methodName = uri.substring(uri.lastIndexOf('/') + 1);
        //System.out.println("方法的名称：" + methodName); //方法的名称：add
        //3.获取方法对象Method
        //System.out.println(this); //UserServlet的对象
        try {
            //忽略访问权限修饰符(非public权限)，获取方法
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);//通过反射获取到对象中的方法
            //4.执行方法
            //暴力反射
            //method.setAccessible(true);
            method.invoke(this, req, res);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
        }

    }

    /**
     * 直接将传入的对象序列化为json，并写回客户端
     * @param obj
     */
    public void writeValue(Object obj,HttpServletResponse response) throws IOException {
        //2.将返回的结果集(list)，进行json序列化并返回
        ObjectMapper mapper = new ObjectMapper();
        //设置响应数据类型和编码格式
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),obj);

    }

    /**
     * 将传入的对象序列化为json，并返回
     * @param obj
     * @return
     */
    public String writeValueAsString(Object obj,HttpServletResponse response) throws JsonProcessingException {
        //2.将返回的结果集(list)，进行json序列化并返回
        ObjectMapper mapper = new ObjectMapper();
        //设置响应数据类型和编码格式
        response.setContentType("application/json;charset=utf-8");
        return mapper.writeValueAsString(obj);

    }
}
