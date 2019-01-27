package com.example.demo.domain;

import javax.persistence.*;
import java.util.Date;
@Entity(name="up_stock")
public class MyStock {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false,columnDefinition="varchar(10) COMMENT 'yyyymmdd'")
    private String dayFormat;
    @Column(nullable = false)
    private Date created;
    @Column(nullable = false,columnDefinition="varchar(8)")
    private String code;
    @Column(nullable = false,columnDefinition="varchar(8)")
    private String name;
    @Column(nullable = false)
    private int yesterdayClosePrice;

    @Column(nullable = true)
    private int todayOpenPrice;
    @Column(nullable = true)
    private int todayClosePrice;
    @Column(nullable = true)
    private int tomorrowOpenPrice;
    @Column(nullable = true)
    private int tomorrowClosePrice;
    @Column(nullable = true)
    private int openBidRate;
    @Column(nullable = true,columnDefinition="varchar(10) COMMENT '连板'")
    private String continuous;
    @Column(nullable = true)
    private Integer openCount;
    @Column(nullable = true)
    private Integer oneFlag;
    @Column(nullable = true)
    private Integer hotSort;
    @Column(nullable = true,columnDefinition="varchar(200) COMMENT '板块'")
    private String plateName;

    @Column(nullable = true,columnDefinition="int(11) DEFAULT NULL COMMENT '1实时;2一天;3两者'")
    private Integer stockType;

    @Transient
    private String todayOpenRate;
    @Transient
    private String todayCloseRate;
    @Transient
    private String tomorrowOpenRate;
    @Transient
    private String tomorrowCloseRate;

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

    public int getYesterdayClosePrice() {
        return yesterdayClosePrice;
    }

    public void setYesterdayClosePrice(int yesterdayClosePrice) {
        this.yesterdayClosePrice = yesterdayClosePrice;
    }

    public int getTodayOpenPrice() {
        return todayOpenPrice;
    }

    public void setTodayOpenPrice(int todayOpenPrice) {
        this.todayOpenPrice = todayOpenPrice;
    }

    public int getTodayClosePrice() {
        return todayClosePrice;
    }

    public void setTodayClosePrice(int todayClosePrice) {
        this.todayClosePrice = todayClosePrice;
    }

    public int getTomorrowOpenPrice() {
        return tomorrowOpenPrice;
    }

    public void setTomorrowOpenPrice(int tomorrowOpenPrice) {
        this.tomorrowOpenPrice = tomorrowOpenPrice;
    }

    public int getTomorrowClosePrice() {
        return tomorrowClosePrice;
    }

    public void setTomorrowClosePrice(int tomorrowClosePrice) {
        this.tomorrowClosePrice = tomorrowClosePrice;
    }

    public int getOpenBidRate() {
        return openBidRate;
    }

    public void setOpenBidRate(int openBidRate) {
        this.openBidRate = openBidRate;
    }

    public String getContinuous() {
        return continuous;
    }

    public void setContinuous(String continuous) {
        this.continuous = continuous;
    }

    public Integer getOpenCount() {
        return openCount;
    }

    public void setOpenCount(Integer openCount) {
        this.openCount = openCount;
    }

    public Integer getOneFlag() {
        return oneFlag;
    }

    public void setOneFlag(Integer oneFlag) {
        this.oneFlag = oneFlag;
    }

    public Integer getHotSort() {
        return hotSort;
    }

    public void setHotSort(Integer hotSort) {
        this.hotSort = hotSort;
    }

    public String getPlateName() {
        return plateName;
    }

    public void setPlateName(String plateName) {
        this.plateName = plateName;
    }

    public Integer getStockType() {
        return stockType;
    }

    public void setStockType(Integer stockType) {
        this.stockType = stockType;
    }

    public String getTodayOpenRate() {
        return todayOpenRate;
    }

    public void setTodayOpenRate(String todayOpenRate) {
        this.todayOpenRate = todayOpenRate;
    }

    public String getTodayCloseRate() {
        return todayCloseRate;
    }

    public void setTodayCloseRate(String todayCloseRate) {
        this.todayCloseRate = todayCloseRate;
    }

    public String getTomorrowOpenRate() {
        return tomorrowOpenRate;
    }

    public void setTomorrowOpenRate(String tomorrowOpenRate) {
        this.tomorrowOpenRate = tomorrowOpenRate;
    }

    public String getTomorrowCloseRate() {
        return tomorrowCloseRate;
    }

    public void setTomorrowCloseRate(String tomorrowCloseRate) {
        this.tomorrowCloseRate = tomorrowCloseRate;
    }
}
