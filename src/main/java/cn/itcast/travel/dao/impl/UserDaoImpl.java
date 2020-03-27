package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @Description:  用户Doa接口的实现类
 * @Author: pengfei.wang
 * @CreateDate: 2020-02-20
 */
public class UserDaoImpl implements UserDao {

    //jdbc连接池对象
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    /**
     * 根据用户名查询用户的方法
     * @param username
     * @return
     */
    @Override
    public User findByUsername(String username) {
        User user = null;
        try {
            //1.定义sql语句
            String sql = "select * from tab_user where username = ?";
            //2.执行sql
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        }catch (Exception e){

        }
        return user;
    }

    /**
     * 保存用户的方法
     * @param user
     */
    @Override
    public void save(User user) {
        //1.定义sql
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) values(?,?,?,?,?,?,?,?,?)";
        //2.执行sql
        template.update(sql,user.getUsername(),
                            user.getPassword(),
                            user.getName(),
                            user.getBirthday(),
                            user.getSex(),
                            user.getTelephone(),
                            user.getEmail(),
                            user.getStatus(),
                            user.getCode()
                            );
        }

    /**
     * 根据激活码查询用户的方法
     * @param code
     * @return
     */
    @Override
    public User findByCode(String code) {
        User user = null;
        //1.定义sql
        String sql = "select * from tab_user where code = ?";
        //2.执行sql，并进行结果异常抓取
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
            return user;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 修改指定用户激活状态的方法
     * @param user
     */
    @Override
    public void updateStatus(User user) {
        //1.定义sql
        String sql = "update tab_user set status = 'Y' where uid = ?";
        //2.执行sql
        template.update(sql,user.getUid());

    }

    /**
     *  根据用户名和密码查询用户
     * @param username
     * @param password
     * @return
     */
    @Override
    public User findByUsernameAndPassword(String username, String password) {
        User user = null;
        try {
            //1.定义sql
            String sql = "select * from tab_user where username = ? and password = ?";
            //2.执行sql
            user =  template.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),username,password);
        }catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

}
