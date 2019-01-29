package com.example.demo.task;


import com.example.demo.dao.TgbStockRepository;
import com.example.demo.domain.TgbStock;
import com.example.demo.service.LimitUpService;
import com.example.demo.service.MyTgbService;
import com.example.demo.service.TgbHotService;
import com.example.demo.utils.ChineseWorkDay;
import com.example.demo.utils.MyUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

//5.0
@Component
public class MyScheduledService {
    /*private static final String openCron = "50 25 9 ? * MON-FRI";
    private static final String closeCron ="0 8 15 ? * MON-FRI";
    private static final String choiceMy="42 1 9 ? * MON-FRI";
    private static final String choiceWorkDayCron="42 48 4 ? * MON-FRI";
    private static final String choiceHolidayCron="43 48 4 ? * SAT";
    private static final String currentTimeCron="1 59 0/2 ? * MON-FRI";*/
    private static final String openCron = "50 25 1 ? * MON-FRI";
    private static final String choiceMy="49 1 1 ? * MON-FRI";
    private static final String closeCron ="0 8 7 ? * MON-FRI";
    private static final String choiceWorkDayCron="42 48 20 ? * MON-FRI";
    private static final String choiceHolidayCron="43 48 20 ? * SAT";
    private static final String currentTimeCron="1 59 0/2 ? * MON-FRI";
    //reset
    Log log = LogFactory.getLog(MyScheduledService.class);
    //选择，开盘，收盘
    @Autowired
    protected TgbHotService tgbHotService;
    @Autowired
    TgbStockRepository tgbStockRepository;
    @Autowired
    MyTgbService myTgbService;
    @Autowired
    LimitUpService limitUpService;
    //服务器时间 1-9；7-15，差8小时
    //0 0 9 ? * MON-FRI
    @Scheduled(cron = choiceWorkDayCron)
    //@Scheduled(cron = "0 45 20 ? * MON-FRI")
    public void choiceWorkDay(){
        log.info("==>>exe choice new Workday"+ DateFormatUtils.format(MyUtils.getCurrentDate(), "yyMMdd HH:mm:ss"));
        ChineseWorkDay chineseWorkDay = new ChineseWorkDay(MyUtils.getCurrentDate());
        try {
            if(chineseWorkDay.isWorkday()){
                tgbHotService.dayTimeStockWorkday();
            }else {
                log.info("==>>no exe choice new dayTimeStockWorkday HOLIDAY"+ DateFormatUtils.format(MyUtils.getCurrentDate(), "yyMMdd HH:mm:ss"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Scheduled(cron = choiceHolidayCron)
    //@Scheduled(cron = "0 45 20 ? * MON-FRI")
    public void choiceHoliday(){
        log.info("==>>exe choice new Holiday"+ DateFormatUtils.format(MyUtils.getCurrentDate(), "yyMMdd HH:mm:ss"));
        tgbHotService.dayTimeStockHoliday();

    }
    @Scheduled(cron = choiceMy)
    public void choice(){
        log.info("==>>exe choice new "+ DateFormatUtils.format(MyUtils.getCurrentDate(), "yyMMdd HH:mm:ss"));
        ChineseWorkDay chineseWorkDay = new ChineseWorkDay(MyUtils.getCurrentDate());
        try {
            if(chineseWorkDay.isWorkday()){
                log.info("==>>exe choice go.. "+ DateFormatUtils.format(MyUtils.getCurrentDate(), "yyMMdd HH:mm:ss"));
                myTgbService.choice();
                tgbHotService.choiceFive();
            }else {
                log.info("==>>no exe choice  HOLIDAY"+ DateFormatUtils.format(MyUtils.getCurrentDate(), "yyMMdd HH:mm:ss"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Scheduled(cron = openCron)
    //@Scheduled(cron = "0 26 1 ? * MON-FRI")
    public void open(){
        log.info("==>>exe open new "+ DateFormatUtils.format(MyUtils.getCurrentDate(), "yyMMdd HH:mm:ss"));
        ChineseWorkDay chineseWorkDay = new ChineseWorkDay(MyUtils.getCurrentDate());
        try {
            if(chineseWorkDay.isWorkday()){
                tgbHotService.open();
                myTgbService.open();
            }else {
                log.info("==>>no exe open  HOLIDAY"+ DateFormatUtils.format(MyUtils.getCurrentDate(), "yyMMdd HH:mm:ss"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Scheduled(cron = closeCron)
    //@Scheduled(cron = "0 10 7 ? * MON-FRI")
    public void close(){
        log.info("==>>exe t close new "+ DateFormatUtils.format(MyUtils.getCurrentDate(), "yyMMdd HH:mm:ss"));
        ChineseWorkDay chineseWorkDay = new ChineseWorkDay(MyUtils.getCurrentDate());
        try {
            if(chineseWorkDay.isWorkday()){
                limitUpService.closeLimitUp();
                tgbHotService.close();
                myTgbService.close();
                List<TgbStock> hotSort = tgbStockRepository.findByDayFormatOrderByHotSort(MyUtils.getDayFormat(MyUtils.getYesterdayDate()));
            }else {
                log.info("==>>no exe close new  HOLIDAY"+ DateFormatUtils.format(MyUtils.getCurrentDate(), "yyMMdd HH:mm:ss"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Scheduled(cron =currentTimeCron)
    //@Scheduled(cron = "0 15,31,49 23,0 ? * MON-FRI")
    public void currentTime(){
        log.info("==>>exe new currentTime" + DateFormatUtils.format(MyUtils.getCurrentDate(), "yyMMdd HH:mm:ss"));
        ChineseWorkDay chineseWorkDay = new ChineseWorkDay(MyUtils.getCurrentDate());
        try {
            if(chineseWorkDay.isWorkday()){
                tgbHotService.taogubaCurrent();
            }else {
                log.info("==>>no exe  currentTime2 HOLIDAY"+ DateFormatUtils.format(MyUtils.getCurrentDate(), "yyMMdd HH:mm:ss"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


 /*   *
     * 一个cron表达式有至少6个（也可能7个）有空格分隔的时间元素。按顺序依次为：
     *
     * 1 秒（0~59）
     * 2 分钟（0~59）
     * 3 小时（0~23）
     * 4 天（0~31）
     * 5 月（0~11）
     * 6 星期（1~7 1=SUN 或 SUN，MON，TUE，WED，THU，FRI，SAT）
     * 7 年份（1970－2099）
     **/

}
