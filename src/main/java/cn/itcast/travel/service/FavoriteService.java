package cn.itcast.travel.service;

/**
 * @Description: 线路收藏的Service接口
 * @Author: pengfei.wang
 * @CreateDate: 2020-03-04
 */
public interface FavoriteService {

    /**
     *  根据线路rid和用户的uid查询收藏情况
     * @param rid
     * @param uid
     * @return
     */
    public boolean isFavorite(String rid, int uid);
}
