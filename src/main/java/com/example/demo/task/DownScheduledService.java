package com.example.demo.task;


import com.example.demo.service.DownService;
import com.example.demo.service.TemperatureService;
import com.example.demo.utils.ChineseWorkDay;
import com.example.demo.utils.MyUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DownScheduledService {
    Log log = LogFactory.getLog(DownScheduledService.class);
    /*private static final String openCron = "40 26 9 ? * MON-FRI";
    private static final String closeCron ="20 10 15 ? * MON-FRI";
    private static final String temperatureCron="0 45 9,10,13,14 ? * MON-FRI";
    private static final String temperatureOpenCron="0 35 9 ? * MON-FRI";*/
    private static final String openCron = "40 26 1 ? * MON-FRI";
    private static final String closeCron ="20 10 7 ? * MON-FRI";
    private static final String temperatureCron="0 45 1,2,5,6 ? * MON-FRI";
    private static final String temperatureOpenCron="0 35 1 ? * MON-FRI";
    @Autowired
    private TemperatureService temperatureService;
    @Autowired
    private DownService downService;


    @Scheduled(cron = openCron)
    //@Scheduled(cron = "0 26 1 ? * MON-FRI")
    public void open(){
        log.info("==>>exe open==>"+ DateFormatUtils.format(MyUtils.getCurrentDate(), "yyMMdd HH:mm:ss"));
        ChineseWorkDay chineseWorkDay = new ChineseWorkDay(MyUtils.getCurrentDate());
        try {
            if(chineseWorkDay.isWorkday()){
                downService.open();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    @Scheduled(cron = closeCron)
    //@Scheduled(cron = "0 10 7 ? * MON-FRI")
    public void close(){
        log.info("==>>exe t close==>"+ DateFormatUtils.format(MyUtils.getCurrentDate(), "yyMMdd HH:mm:ss"));
        ChineseWorkDay chineseWorkDay = new ChineseWorkDay(MyUtils.getCurrentDate());
        try {
            if(chineseWorkDay.isWorkday()){
                temperatureService.temperatureClose();
                downService.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Scheduled(cron = temperatureOpenCron)
    //@Scheduled(cron = "0 35 1 ? * MON-FRI")
    public void topen(){
        log.info("==>>exe t open==>"+ DateFormatUtils.format(MyUtils.getCurrentDate(), "yyMMdd HH:mm:ss"));

        ChineseWorkDay chineseWorkDay = new ChineseWorkDay(MyUtils.getCurrentDate());
        try {
            if(chineseWorkDay.isWorkday()){
                temperatureService.temperatureOpen();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Scheduled(cron = temperatureCron)
    //@Scheduled(cron = "0 45 1,2,5,6 ? * MON-FRI")
    public void temperature(){
        log.info("==>>exe temperature==>" + DateFormatUtils.format(MyUtils.getCurrentDate(), "yyMMdd HH:mm:ss"));
        ChineseWorkDay chineseWorkDay = new ChineseWorkDay(MyUtils.getCurrentDate());
        try {
            if(chineseWorkDay.isWorkday()){
                temperatureService.temperatureNormal();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 一个cron表达式有至少6个（也可能7个）有空格分隔的时间元素。按顺序依次为：
     *
     * 1 秒（0~59）
     * 2 分钟（0~59）
     * 3 小时（0~23）
     * 4 天（0~31）
     * 5 月（0~11）
     * 6 星期（1~7 1=SUN 或 SUN，MON，TUE，WED，THU，FRI，SAT）
     * 7 年份（1970－2099）
     *
     */

}
