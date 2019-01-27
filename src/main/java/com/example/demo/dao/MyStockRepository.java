package com.example.demo.dao;

import com.example.demo.domain.MyStock;
import com.example.demo.domain.TotalStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MyStockRepository extends JpaRepository<MyStock,Long> {
    @Query(value="SELECT * FROM ( SELECT code, name,COUNT(id) as total from up_stock WHERE day_format BETWEEN ?1 AND ?2  GROUP BY code) as temp WHERE temp.total>2 ORDER BY total DESC ", nativeQuery = true)
    public List<TotalStock> fiveStock(String start , String end);
    List<MyStock> findByCodeAndDayFormat(String code,String dayFormat);
}
