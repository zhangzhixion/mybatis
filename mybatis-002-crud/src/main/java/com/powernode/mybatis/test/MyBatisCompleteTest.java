package com.powernode.mybatis.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;

/**
 * 完整的mybatis项目
 */
public class MyBatisCompleteTest {
    public static void main(String[] args) {
        SqlSession sqlSession = null;
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        try {
            SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
            //开启会话（底层会开启事务）
            sqlSession = sqlSessionFactory.openSession();
            //执行sql语句，处理相关业务
            int count = sqlSession.insert("insertCar");
            System.out.println(count);
            //执行到这里，没有发生任何异常，提交事务，终止事务
            sqlSession.commit();
        } catch (IOException e) {
            //最好回滚事务
            if (sqlSession != null){
                sqlSession.rollback();
            }
            e.printStackTrace();
        } finally {
            //关闭会话（释放资源）
            if (sqlSession !=null){
                sqlSession.close();
            }
            System.out.println("hello git");
            System.out.println("hello github");
            System.out.println("hello hot-fix");
            System.out.println("hello master");
        }
    }
}
