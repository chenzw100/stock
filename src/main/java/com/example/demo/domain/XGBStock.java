package com.example.demo.domain;



import com.example.demo.utils.MyUtils;

import javax.persistence.*;
import java.util.Date;

@Entity(name="xgb_stock")
public class XGBStock implements Comparable<XGBStock> {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false,columnDefinition="varchar(10) COMMENT 'yyyymmdd'")
    private String dayFormat;
    @Column(nullable = false,columnDefinition="varchar(8)")
    private String code;
    @Column(nullable = false,columnDefinition="varchar(8)")
    private String name;
    @Column(nullable = true,columnDefinition="int(11) DEFAULT 0 COMMENT '开板次数'")
    private Integer openCount;
    @Column(nullable = true,columnDefinition="int(11) DEFAULT 0 COMMENT '连板'")
    private Integer continueBoardCount;
    @Column(nullable = true,columnDefinition="varchar(200) COMMENT '板块'")
    private String plateName;
    @Column(nullable = false,columnDefinition="int(11) DEFAULT 0 COMMENT '昨日收盘'")
    private Integer yesterdayClosePrice;
    @Column(nullable = false)
    private Date created;
    @Transient
    private int downRate;
    @Transient
    private String price;

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.dayFormat= MyUtils.getDayFormat(created);
        this.created = created;
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

    public Integer getYesterdayClosePrice() {
        return yesterdayClosePrice;
    }

    public void setYesterdayClosePrice(Integer yesterdayClosePrice) {
        this.yesterdayClosePrice = yesterdayClosePrice;
    }

    public String getPlateName() {
        return plateName;
    }

    public void setPlateName(String plateName) {
        this.plateName = plateName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        yesterdayClosePrice=MyUtils.getCentBySinaPriceStr(price);
        this.price = price;
    }

    public int getDownRate() {
        return downRate;
    }

    public void setDownRate(int downRate) {
        this.downRate = downRate;
    }

    public Integer getContinueBoardCount() {
        if(continueBoardCount == null){
            continueBoardCount =-1;
        }
        return continueBoardCount;
    }

    public void setContinueBoardCount(Integer continueBoardCount) {
        this.continueBoardCount = continueBoardCount;
    }

    public Integer getOpenCount() {
        return openCount;
    }

    public void setOpenCount(Integer openCount) {
        this.openCount = openCount;
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

     public int compareTo(XGBStock o) {
          // TODO Auto-generated method stub
          return this.downRate-o.downRate;
     }
    public DownStock coverDownStock(){
        DownStock downStock = new DownStock(getCode(),getName());
        downStock.setYesterdayClosePrice(MyUtils.getCentBySinaPriceStr(getPrice()));
        downStock.setDownRate(downRate);
        return downStock;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(code).append(name).append(",open").append(getOpenCount()).append(",continue:").append(getContinueBoardCount()).append(",price:").append(getPrice()).append(",rate:").append(getDownRate()).append(plateName);
        return sb.toString();
    }
}
