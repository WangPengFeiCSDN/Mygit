package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.service.FavoriteService;

/**
 * @Description: 线路收藏的Service接口的实现类
 * @Author: pengfei.wang
 * @CreateDate: 2020-03-04
 */
public class FavoriteServiceImpl implements FavoriteService {

    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    /**
     * 根据线路rid和用户的uid查询收藏情况
     * @param rid
     * @param uid
     * @return
     */
    @Override
    public boolean isFavorite(String rid, int uid) {

        //1.调用FavoriteDao进行查询
        Favorite favorite = favoriteDao.findByRidAndUid(Integer.parseInt(rid), uid);
        //2.判断获取到的Favorite对象是否为空
        if (favorite != null) {
            //结果为true，说明当前登录用户收藏了该线路
            return true;
        }else {
            //结果为false，说明当前用户并未收藏该线路
            return false;
        }
    }
}
