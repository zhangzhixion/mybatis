package com.powernode.mybatis.mapper;

import com.powernode.mybatis.pojo.Car;

import java.util.List;

public interface CarMapper {
    /**
     * 新增
     * @param car
     * @return
     */
    int insert(Car car);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     * 修改
     * @param car
     * @return
     */
    int update(Car car);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Car selectById(Long id);

    /**
     * 查询全部
     * @return
     */
    List<Car> selectAll();
}
