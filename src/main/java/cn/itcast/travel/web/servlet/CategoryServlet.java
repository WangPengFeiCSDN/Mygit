package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Description:  关于栏目分类的Servlet
 * @Author: pengfei.wang
 * @CreateDate: 2020-02-23
 */
@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {

    //声明CategoryService的业务对象
    private CategoryService service = new CategoryServiceImpl();

    /**
     *  查询所有栏目的Servlet
     * @param request
     * @param response
     * @throws ServletException
     */
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.调用Service来查询所有的栏目
        List<Category> cs = service.findAll();
        //2.将返回的结果集(list)，进行json序列化并返回
//        ObjectMapper mapper = new ObjectMapper();
//        //设置响应数据类型和编码格式
//        response.setContentType("application/json;charset=utf-8");
//        mapper.writeValue(response.getOutputStream(),cs);
        writeValue(cs,response);

    }
}
