package com.example.demo.domain;


import com.example.demo.utils.MyUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * 股市看三日富可敌国，股市看两日稳定复利。
 * 昨天，今天，明天
 * 今天竞价涨幅，相对于昨天收盘的涨幅 (todayOpenPrice-yesterdayPrice)/yesterdayPrice
 * 明天竞价涨幅，相对于今天开盘的涨幅 (tomorrowPrice-todayOpenPrice)/todayOpenPrice;此处就代表了盈利幅度
 */
@Entity(name="current_stock")
public class CurrentStock {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false,columnDefinition="varchar(10) COMMENT 'yyyymmdd'")
    private String dayFormat;
    @Column(nullable = false,columnDefinition="varchar(8)")
    private String code;
    @Column(nullable = false,columnDefinition="varchar(8)")
    private String name;
    @Column(nullable = true,columnDefinition="int(11) DEFAULT 0 COMMENT '热门排序'")
    private Integer hotSort;
    @Column(nullable = true,columnDefinition="int(11) DEFAULT 0 COMMENT '今日热搜'")
    private Integer hotValue;
    @Column(nullable = true,columnDefinition="int(11) DEFAULT 0 COMMENT '最近7日'")
    private Integer hotSeven;


    private Date created;


    public CurrentStock(){
    }
    public CurrentStock(String code, String name){
        this.code =code;
        this.name = name;

    }

    public Integer getHotSort() {
        return hotSort;
    }

    public void setHotSort(Integer hotSort) {
        this.hotSort = hotSort;
    }

    public Integer getHotValue() {
        return hotValue;
    }

    public void setHotValue(Integer hotValue) {
        this.hotValue = hotValue;
    }

    public Integer getHotSeven() {
        return hotSeven;
    }

    public void setHotSeven(Integer hotSeven) {
        this.hotSeven = hotSeven;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDayFormat() {
        return dayFormat;
    }

    public void setDayFormat(String dayFormat) {
        this.dayFormat = dayFormat;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.dayFormat= MyUtils.getDayFormat(created);
        this.created = created;

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("热门:").append(code).append(name).append(",热排:").append(hotSort).append(",热值:").append(hotValue).
                append("七日:").append(hotSeven);
        return sb.toString();
    }

}
