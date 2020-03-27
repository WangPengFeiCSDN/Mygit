package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

/**
 * @Description: 线路收藏的Dao接口
 * @Author: pengfei.wang
 * @CreateDate: 2020-03-04
 */
public interface FavoriteDao {

    /**
     *  根据线路rid和用户的uid查询收藏情况
     * @param rid
     * @param uid
     * @return
     */
    public Favorite findByRidAndUid(int rid, int uid);

    /**
     *  根据线路rid查询出线路收藏的次数
     * @param rid
     * @return
     */
    public int findCountByRid(int rid);
}
