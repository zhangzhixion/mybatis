package com.powernode.test;

import com.powernode.mybatis.pojo.Car;
import com.powernode.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarMapperTest {

    /**
     * 查询所有
     */
    @Test
    public void testSelectAll(){
        SqlSession session = SqlSessionUtil.openSession();
        List<Object> list = session.selectList("selectAll");
        list.forEach(Car -> System.out.println(Car));
        session.close();
    }

    /**
     * 查询一个
     */
    @Test
    public void testSelectById(){
        SqlSession session = SqlSessionUtil.openSession();
        Object car = session.selectOne("selectById", 1);
        System.out.println(car);
        session.close();
    }

    /**
     * 用pojo类新增
     */
    @Test
    public void testInsertCarByPojo(){
        SqlSession session = SqlSessionUtil.openSession();
        Car car =new Car("3333","比亚迪秦",30.0,"2020-11-11","新能源");
        session.insert("insertCar",car);
        session.commit();
        session.close();
    }

    /**
     * 用map集合新增
     */
    @Test
    public void testInsertCar(){
        SqlSession session = SqlSessionUtil.openSession();
        Map<String,Object> map = new HashMap<>();
        map.put("carNum","1111");
        map.put("brand","比亚迪汉");
        map.put("guidePrice",10.0);
        map.put("produceTime","2020-11-11");
        map.put("carType","电车");

        session.insert("insertCar",map);
        session.commit();
        session.close();
    }
}
