package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @Description:  栏目分类接口的实现类
 * @Author: pengfei.wang
 * @CreateDate: 2020-02-23
 */

/**
 *  查询所有栏目的方法
 */
public class CategoryDaoImpl implements CategoryDao {

    //声明jdbc的模型
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 查询所有栏目的方法
     * @return
     */
    @Override
    public List<Category> findAll() {
        //1.定义sql
        String sql = "select * from tab_category ";
        //2.执行sql，返回list集合
        return template.query(sql,new BeanPropertyRowMapper<Category>(Category.class));
    }
}
