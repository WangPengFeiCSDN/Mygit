package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @Description: 卖家Dao的实现类
 * @Author: pengfei.wang
 * @CreateDate: 2020-03-02
 */
public class SellerDaoImpl implements SellerDao {

    //创建Jdbc连接池
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     *  根据sid查询对应的卖家方法
     * @param sid
     * @return
     */
    @Override
    public Seller findById(int sid) {
        //1.定义sql
        String sql = "select * from tab_seller where sid = ?";
        //2.执行sql
        return template.queryForObject(sql,new BeanPropertyRowMapper<Seller>(Seller.class),sid);
    }
}
