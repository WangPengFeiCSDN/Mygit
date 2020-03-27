package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 线路查询的Dao接口实现类
 * @Author: pengfei.wang
 * @CreateDate: 2020-02-25
 */

    public class RouteDaoImpl implements RouteDao {

        private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     *  根据cid查询总的记录数
      * @param cid
     * @param rname
     * @return
     */
    @Override
    public int getTotalCount(int cid, String rname) {
        //1.定义sql
        //String sql = "select count(*) from tab_route where cid = ?";
        //1.定义sql模板
        String sql = "SELECT COUNT(*) FROM tab_route WHERE 1=1";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList(); //条件们
        //2.判断参数是否有值
        if (cid != 0) {
            sb.append(" and cid = ?");
            params.add(cid); //添加? 对应的值
        }
        if (rname != null && rname.length() > 0) {
            sb.append(" and rname like ?");
            params.add("%"+rname+"%");
        }
        sql = sb.toString();

        //2.执行sql
        return template.queryForObject(sql, Integer.class, params.toArray());
    }

    /**
     *
     * @param cid
     * @param start
     * @param pageSize
     * @param rname
     * @return
     */
    @Override
    public List<Route> findByPage(int cid, int start, int pageSize, String rname) {
        //String sql = "select * from tab_route where cid = ? and rname like ? limit ? , ?";
        //1.定义sql模板
        String sql = "SELECT * from tab_route where 1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList(); //条件们
        //2.判断参数是否有值
        if (cid != 0) {
            sb.append(" and cid = ? ");
            params.add(cid); //添加? 对应的值
        }
        if (rname != null && rname.length() > 0) {
            sb.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }
        sb.append(" limit ? , ? "); //后面再加上分页查询条件
        sql = sb.toString();

        params.add(start);
        params.add(pageSize);
        //2.执行sql
        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
    }

    /**
     *  根据id查询线路对象的方法
     * @param rid
     * @return
     */
    @Override
    public Route findOne(int rid) {
        //1.定义sql
        String sql = "select * from tab_route where rid = ?";
        //2.执行sql
        return template.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);
    }
}
