package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @Description: 线路图片Dao的实现类
 * @Author: pengfei.wang
 * @CreateDate: 2020-03-02
 */
public class RouteImgDaoImpl implements RouteImgDao {

    //创建Jdbc连接池
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据route的id查询图片的方法
     * @param rid
     * @return
     */
    @Override
    public List<RouteImg> findByRid(int rid) {
        //1.定义sql
        String sql = "select * from tab_route_img where rid = ?";
        //2.执行sql
        return template.query(sql, new BeanPropertyRowMapper<RouteImg>(RouteImg.class), rid);
    }
}
