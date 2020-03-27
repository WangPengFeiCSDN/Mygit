package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

/**
 * @Description:  用户Service接口的实现类
 * @Author: pengfei.wang
 * @CreateDate: 2020-02-20
 */
public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    /**
     * 用户的注册方法
     * @param user
     * @return
     */
    @Override
    public boolean regist(User user) {
        //1.根据用户名查询用户的方法
      User u = userDao.findByUsername(user.getUsername());
      //判断u用户是否为null
        if (u != null) {
        //说明用户已经存在，不可重新注册
        return false;
    }
       //说明用户不存在，进行新用户注册
        //2.保存用户信息
        //2.1设置激活码(用户的唯一标识)
        user.setCode(UuidUtil.getUuid());
        //2.2设置激活状态
        user.setStatus("N"); //默认未激活
        userDao.save(user);
        //3.激活发送邮件
        String content = "<a href='http://localhost/travel/user/active?code="+user.getCode()+"'>点击激活【黑马旅游网】</a>";
        MailUtils.sendMail(user.getEmail(),content,"激活邮件");
        return true;
    }

    /**
     * 用户激活的方法
     * @param code
     * @return
     */
    @Override
    public boolean active(String code) {
        //1.根据激活码查询用户对象
        User user = userDao.findByCode(code);
        //判断根据激活码获取到的用户是否为空
        if (user != null) { //结果为true，说明存在用户，即可修改激活状态
            //2.调用dao修改用户激活状态的方法
            userDao.updateStatus(user);
            return true;
        }else {
            return false;
        }

    }

    /**
     * 用户登录的方法
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        //调用Dao查询用户
        return userDao.findByUsernameAndPassword(user.getUsername(),user.getPassword());
    }


}


