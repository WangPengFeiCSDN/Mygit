package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Description:  栏目分类的Service接口的实现类
 * @Author: pengfei.wang
 * @CreateDate: 2020-02-23
 */
public class CategoryServiceImpl implements CategoryService {

    //创建Dao的业务对象
    private CategoryDao categoryDao = new CategoryDaoImpl();


    /**
     *  查询栏目的方法，含有Redis的方法
     * @return
     */
/*    @Override
    public List<Category> findAll() {

        *//*
         *栏目的分类不经常改变，所有可以使用Redis来缓存，减轻数据库查询的压力
         * 但是Rdeis服务器关闭就无法使用，暂时隐藏起来
         *//*


        //1.从Redis中查询
        //1.1获取Jedis的客户端
        Jedis jedis = JedisUtil.getJedis();
        //1.2可使用sortedset排序查询
        //Set<String> categorys = jedis.zrange("category", 0, -1);
        //1.3查询sortedset中的分数(cid)和值(cname)
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);
        List<Category> cs = null;
        //2.判断查询的集合是否为空
        if (categorys == null || categorys.size() == 0) {
            System.out.println("Redis缓存为空，从数据库查询栏目");
            //3，如果为空，需要从数据库中查询，再将数据存入Redis
            //3.1从数据库中查询
            cs = categoryDao.findAll();
            //3.2将集合数据存储到redis中的category的key
            for (int i = 0; i < cs.size(); i++) {

                jedis.zadd("category",cs.get(i).getCid(),cs.get(i).getCname()); //按照id的大小排序存储
            }
        }else {
            System.out.println("Redis缓存有数据...");
            //4.如果不为空，将set集合的数据存入list集合
            cs = new ArrayList<Category>();
            for (Tuple tuple : categorys) {
                Category category = new Category();
                category.setCname(tuple.getElement());
                category.setCid((int)tuple.getScore());
                cs.add(category);

            }
        }

        //4.如果不为空。直接返回
        return cs;
    }*/

    /**
     * 查询栏目的方法，不含有Redis的方法，直接从数据库获取
     *  @return
     */
    public List<Category> findAll() {
        return categoryDao.findAll();
    }
}
