package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Description: 分页查询的Servlet
 * @Author: pengfei.wang
 * @CreateDate: 2020-02-25
 */
@WebServlet("/route/*")
public class RouteServlet extends BaseServlet{

    //声明RouteService，调用适合的方法
    private RouteService routeService = new RouteServiceImpl();

    //声明FavoriteService，调用适合的方法
    private FavoriteService favoriteService = new FavoriteServiceImpl();

    /**
     *  分页查询的方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接受参数
        String currentPageStr = request.getParameter("currentPage"); //当前页的参数
        String pageSizeStr = request.getParameter("pageSize"); //每页显示的条数
        String cidStr = request.getParameter("cid"); //栏目的id

        //接受rname线路名称的参数
        String rname = request.getParameter("rname");
        //解决rname属性乱码
        rname = new String(rname.getBytes("iso-8859-1"),"utf-8");

        int cid = 0; //定义id的属性值
        int currentPage = 0; //定义currentPage的属性值
        int pageSize = 0; //定义当前页面的属性值
        //2.处理参数
        //2.1 处理id参数
        if (cidStr != null && cidStr.length() > 0 && !"null".equals(cidStr)) {
            //结果为true，说明获取到了栏目的id
            cid = Integer.parseInt(cidStr); //将字符型的id属性修改为int类型的数值
        }

        //处理currentPage参数
        if (currentPageStr != null && currentPageStr.length() > 0) {
           //结果为true，说明获取到了当前页码
            currentPage = Integer.parseInt(currentPageStr);
        }else {
            //设置默认的页码值
            currentPage = 1;

        }
        //处理pageSize的属性值
        if (pageSizeStr != null && pageSizeStr.length() > 0) {
            //结果为true，说明获取到了每页显示的条数
            pageSize = Integer.parseInt(pageSizeStr);
        }else {
            pageSize = 5; //如果没有给数值，默认每页显示5条数据
        }

        //3.调用Service查询pageBean对象
        PageBean<Route> pb = routeService.pageQuery(cid, currentPage, pageSize,rname);
        //4.将pageBean对象序列化为json，并返回
        writeValue(pb,response);

    }

    /**
     * 根据id查询一个旅游线路的详细信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1.接受参数id
        String rid = request.getParameter("rid");

        //2.调用Service查询一个Route对象
        Route route = routeService.findOne(rid);

        //3.转为json写回客户端
        writeValue(route,response); //调用封装的用来将对象转成json的方法
    }


    /**
     *  判断当前登录用户是否收藏该线路
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1.获取线路的rid
        String rid = request.getParameter("rid");

        //2.获取当前登录的用户
        HttpSession session = request.getSession(); //获取当前的Session
        User user = (User) session.getAttribute("user"); //获取当前登录的用户
        int uid;
        //判断当前登录的用户是否为空
        if (user != null) {
            //用户已经登录
            uid = user.getUid();
        }else {
            //用户尚未登录，并给出uid的默认属性值
            uid = 0;
        }

        //3.调用FavoriteService，查看当前用户是否收藏该线路
        boolean flag = favoriteService.isFavorite(rid, uid);

        //将结果用json的形式返回
        writeValue(flag,response);

    }
}
