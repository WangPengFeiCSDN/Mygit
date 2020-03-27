package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

/**
 * @Description: 用户的Service接口
 * @Author: pengfei.wang
 * @CreateDate: 2020-02-20
 */
public interface UserService {

    /**
     *  用户的注册方法
     * @param user
     * @return
     */
    public boolean regist(User user);

    /**
     *  用户激活的方法
     * @param code
     * @return
     */
    public boolean active(String code);

    /**
     * 用户登录的方法
     * @param user
     * @return
     */
    public User login(User user);
}
