package com.powernode.mybatis.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

public class MyBatisIntroductionTest {
    public static void main(String[] args) throws Exception {

        //获取SqlSessionFactoryBuilder对象
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();

        //获取SqlSessionFactory对象
        //Resources.getResourceAsStream默认就是从类的根目录下开始查找资源
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlsessionfactory = sqlSessionFactoryBuilder.build(is);

        //获取SqlSession对象
        SqlSession sqlSession = sqlsessionfactory.openSession();

        //执行sql语句
        int count = sqlSession.insert("insertCar");
        System.out.println("插入几条数据："+count);

        sqlSession.commit(); //提交事务
    }
}
