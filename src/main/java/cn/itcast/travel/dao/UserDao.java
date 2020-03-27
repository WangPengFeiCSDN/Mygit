package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

/**
 * @Description: 用户的Dao接口
 * @Author: pengfei.wang
 * @CreateDate: 2020-02-20
 */
public interface UserDao {

    /**
     *  根据用户名查询用户信息
     * @param username
     * @return
     */
    public User findByUsername(String username);

    /**
     * 保存用户的方法
     * @param user
     */
    public void save(User user);

    /**
     * 根据激活码查询用户的方法
     * @param code
     * @return
     */
    public User findByCode(String code);

    /**
     * 修改指定用户激活状态的方法
     * @param user
     */
    public void updateStatus(User user);

    /**
     *  根据用户名和密码对象用户
     * @param username
     * @param password
     * @return
     */
    public User findByUsernameAndPassword(String username, String password);
}
