package com.powernode.service;

import org.junit.Assert;
import org.junit.Test;

public class MathServiceTest {
    //名字规范，你要测试的类名+test
    @Test
    public void testSum(){
        //单元测试中有2个重要的概念
        MathService mathService = new MathService();
        //获取实际值
        int actual = mathService.sum(1, 2);
        //期望值
        int expected =3;
        //加断言进行测试
        Assert.assertEquals(expected,actual);
    }
    @Test
    public void testSub(){
        MathService mathService = new MathService();
        int count = mathService.sub(10,5);
        int expected = 5;
        Assert.assertEquals(expected,count);
    }
}
