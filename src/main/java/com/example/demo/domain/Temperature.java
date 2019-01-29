package com.example.demo.domain;

import com.example.demo.utils.MyUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by chenzw on 2018/10/22.
 */
@Entity(name="temperature")
public class Temperature {
    @Id
    @GeneratedValue
   private long id;
    @Column(nullable = false,columnDefinition="varchar(10)")
    private String dayFormat;
    @Column(nullable = false)
    private Date created;
    @Column(nullable = false)
    private int yesterdayShow;
    @Column(nullable = false)
    private int nowTemperature;
    @Column(nullable = false)
    private int raiseUp;
    @Column(nullable = false)
    private int downUp;
    @Column(nullable = false)
    private int open;
    @Column(nullable = false)
    private int raise;
    @Column(nullable = false)
    private int down;
    @Column(nullable = false)
    private int type;
    public Temperature(){
    }
    public Temperature(int type){
        this.type = type;
        this.created = MyUtils.getCurrentDate();
        this.dayFormat= MyUtils.getDayFormat();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public int getYesterdayShow() {
        return yesterdayShow;
    }

    public void setYesterdayShow(int yesterdayShow) {
        this.yesterdayShow = yesterdayShow;
    }

    public int getNowTemperature() {
        return nowTemperature;
    }

    public void setNowTemperature(int nowTemperature) {
        this.nowTemperature = nowTemperature;
    }

    public int getRaiseUp() {
        return raiseUp;
    }

    public void setRaiseUp(int raiseUp) {
        this.raiseUp = raiseUp;
    }

    public int getDownUp() {
        return downUp;
    }

    public void setDownUp(int downUp) {
        this.downUp = downUp;
    }

    public int getOpen() {
        return open;
    }

    public void setOpen(int open) {
        this.open = open;
    }

    public int getRaise() {
        return raise;
    }

    public void setRaise(int raise) {
        this.raise = raise;
    }

    public int getDown() {
        return down;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        String dateStr = DateFormatUtils.format(getCreated(), "HH:mm:ss");
        sb.append(dateStr+"==>> [昨现:").append(MyUtils.getYuanByCent(getYesterdayShow()));
        sb.append("] [温度:").append(getNowTemperature());
        sb.append("] [涨停:").append(getRaiseUp()).append(", 跌停:").append(getDownUp()).append(", 炸版:").append(getOpen());
        sb.append("] [涨:").append(getRaise()).append(", 跌:").append(getDown()).append("]<br>");
        return sb.toString();
    }
}
