package com.example.demo.dao;

import com.example.demo.domain.MyFiveTgbStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by czw on 2018/10/19.
 * JpaRepository default method
 * User user=new User();
 userRepository.findAll();
 userRepository.findOne(1l);
 userRepository.save(user);
 userRepository.delete(user);
 userRepository.count();
 userRepository.exists(1l);
 */
public interface MyFiveTgbStockRepository extends JpaRepository<MyFiveTgbStock,Long> {
    List<MyFiveTgbStock> findAll();
    List<MyFiveTgbStock> findByCodeAndDayFormat(String code, String dayFormat);
    List<MyFiveTgbStock> findByDayFormatOrderByHotSort(String dayFormat);
    List<MyFiveTgbStock> findByDayFormatOrderByHotSevenDesc(String dayFormat);
    List<MyFiveTgbStock> findByDayFormatOrderByOpenBidRateDesc(String dayFormat);
    MyFiveTgbStock save(MyFiveTgbStock tgbStock);


}
