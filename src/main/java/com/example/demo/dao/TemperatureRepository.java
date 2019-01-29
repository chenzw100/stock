package com.example.demo.dao;

import com.example.demo.domain.Temperature;
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
public interface TemperatureRepository extends JpaRepository<Temperature,Long> {
    List<Temperature> findAll();
    List<Temperature> findByDayFormatAndType(String dayFormat,int type);
    List<Temperature> findByDayFormatOrderByIdDesc(String dayFormat);
    Temperature save(Temperature temperature);

}
