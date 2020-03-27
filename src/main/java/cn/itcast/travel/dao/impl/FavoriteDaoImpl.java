package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @Description: 线路收藏的Dao接口实现类
 * @Author: pengfei.wang
 * @CreateDate: 2020-03-04
 */
public class FavoriteDaoImpl implements FavoriteDao{

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据线路rid和用户的uid查询收藏情况
     * @param rid
     * @param uid
     * @return
     */
    @Override
    public Favorite findByRidAndUid(int rid, int uid) {

        Favorite favorite = null;

        try {
            //1.定义sql
            String sql = "select * from tab_favorite where rid = ? and uid = ?";
            //2.执行sql
            //可能会查询不到结果

            favorite = template.queryForObject(sql,new BeanPropertyRowMapper<Favorite>(Favorite.class),rid,uid);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return favorite;
    }

    /**
     *  根据线路rid查询出线路收藏的次数
     * @param rid
     * @return
     */
    @Override
    public int findCountByRid(int rid) {
        //1.定义sql
        String sql = "select count(*) from tab_favorite where rid = ?";
        //2，执行sql
        return template.queryForObject(sql,Integer.class,rid);
    }
}
