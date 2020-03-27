package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;

/**
 * @Description:  线路接口的实现类
 * @Author: pengfei.wang
 * @CreateDate: 2020-02-25
 */
public class RouteServiceImpl implements RouteService {

    //声明RouteDao，调用合适的方法
    private RouteDao routeDao = new RouteDaoImpl();

    //声明RouteImgDao，调用合适的方法
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();

    //声明SellerDao，调用合适的方法
    private SellerDao sellerDao = new SellerDaoImpl();

    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    /**
     *  根据类别进行分页查询的方法
     * @param cid
     * @param currentPage
     * @param pageSize
     * @param rname
     * @return
     */
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname) {

        //封装PageBean
        PageBean<Route> pb = new PageBean<Route>();

        //当前页面
        pb.setCurrentPage(currentPage);

        //设置每页显示的条数
        pb.setPageSize(pageSize);

        //设置总记录数
        //声明总记录数的变量，需要调用Dao进行查询
        int totalCount = routeDao.getTotalCount(cid,rname);
        pb.setTotalCount(totalCount);
        //设置当前页显示的数据集合
        //调用Dao进行对象查询，其中start参数需要计算
        int start = (currentPage - 1) * pageSize;
        List<Route> list = routeDao.findByPage(cid, start, pageSize,rname);
        pb.setList(list);

        //设置总页数 = 总记录数/每页显示的条数
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize :(totalCount / pageSize) + 1 ;
        pb.setTotalPage(totalPage);

        return pb;
    }

    /**
     *  根据id查询线路对象的方法
     * @param rid
     * @return
     */
    @Override
    public Route findOne(String rid) {
        //1.根据id去tab_route表中，去查询Route对象
        Route route= routeDao.findOne(Integer.parseInt(rid));
        //2.根据route的rid 查询图片的集合信息
        List<RouteImg> routeImgList = routeImgDao.findByRid(route.getRid());
        //2.2 将获取到的图片集合添加到route对象中
        route.setRouteImgList(routeImgList);
        //3.根据route的sid，查询卖家的信息
        Seller seller = sellerDao.findById(route.getSid());
        //3.2 将获取到的卖家对象添加到route对象中
        route.setSeller(seller);

        //4.查询收藏次数
        int count = favoriteDao.findCountByRid(route.getRid());
        //将获取到的收藏次数，添加到route对象中
        route.setCount(count);

        //最后再返回完整的route对象
        return route;
    }
}
