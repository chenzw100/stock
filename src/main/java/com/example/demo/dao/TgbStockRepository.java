package com.example.demo.dao;


import com.example.demo.domain.MyTotalStock;
import com.example.demo.domain.TgbStock;
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
public interface TgbStockRepository extends JpaRepository<TgbStock,Long> {
    List<TgbStock> findAll();
    List<TgbStock> findByCodeAndDayFormat(String code, String dayFormat);
    List<TgbStock> findByDayFormatOrderByHotSort(String dayFormat);
    List<TgbStock> findByDayFormatOrderByHotSevenDesc(String dayFormat);
    List<TgbStock> findByDayFormatOrderByOpenBidRateDesc(String dayFormat);
    TgbStock save(TgbStock tgbStock);
    @Query(value="SELECT * FROM ( SELECT *, COUNT(id) as total_count from tgb_stock WHERE day_format BETWEEN ?1 AND ?2  GROUP BY code) as temp WHERE temp.total_count>2 ORDER BY total_count DESC ", nativeQuery = true)
    public List<TgbStock> totalCount(String start, String end);

    @Query(value="SELECT * FROM ( SELECT code, name,sum(hot_seven) as hot_seven,sum(hot_value)as hot_value, COUNT(id) as total_count from tgb_stock WHERE day_format BETWEEN ?1 AND ?2  GROUP BY code) as temp WHERE temp.total_count>2 ORDER BY total_count DESC ", nativeQuery = true)
    public List<MyTotalStock> stockInfo(String start, String end);

}
