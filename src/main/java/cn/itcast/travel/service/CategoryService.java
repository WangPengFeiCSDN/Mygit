package cn.itcast.travel.service;

import cn.itcast.travel.domain.Category;

import java.util.List;

/**
 * @Description:  栏目分类的Service接口
 * @Author: pengfei.wang
 * @CreateDate: 2020-02-23
 */
public interface CategoryService {

    /**
     *  查询所有栏目的方法
     * @return
     */
    public List<Category> findAll();
}
