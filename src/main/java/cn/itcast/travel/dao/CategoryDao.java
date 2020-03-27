package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Category;

import java.util.List;

/**
 * @Description:  栏目分类的Dao接口
 * @Author: pengfei.wang
 * @CreateDate: 2020-02-23
 */
public interface CategoryDao {

    /**
     *  查询所有栏目的方法
     * @return
     */
    public List<Category> findAll();
}
