package com.example.demo.dao;


import com.example.demo.domain.MyTgbStock;
import com.example.demo.domain.MyTotalStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
public interface MyTgbStockRepository extends JpaRepository<MyTgbStock,Long> {
    List<MyTgbStock> findAll();
    List<MyTgbStock> findByCodeAndDayFormat(String code, String dayFormat);
    List<MyTgbStock> findByDayFormatOrderByHotSort(String dayFormat);
    List<MyTgbStock> findByDayFormatOrderByHotSevenDesc(String dayFormat);
    List<MyTgbStock> findByDayFormatOrderByOpenBidRateDesc(String dayFormat);
    MyTgbStock save(MyTgbStock tgbStock);
    @Query(value="SELECT * FROM ( SELECT *, COUNT(id) as total_count from my_tgb_stock WHERE day_format BETWEEN ?1 AND ?2  GROUP BY code) as temp WHERE temp.total_count>2 ORDER BY total_count DESC ", nativeQuery = true)
    public List<MyTgbStock> totalCount(String start, String end);

    @Query(value="SELECT * FROM ( SELECT code, name, COUNT(id) as total_count from my_tgb_stock WHERE day_format BETWEEN ?1 AND ?2  GROUP BY code) as temp WHERE temp.total_count>2 ORDER BY total_count DESC ", nativeQuery = true)
    public List<MyTotalStock> stockInfo(String start, String end);

}
