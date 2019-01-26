package com.example.demo.utils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by laikui on 2018/12/14.
 */
public class MyChineseWorkDay {
    public static final long hour24 = 24*60*60*1000;
    private static String calendar;
    private static Date date = new Date();

    public static void setDate(Date mydate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        calendar= sdf.format(mydate);
        date = mydate;
    }
    // 法律规定的放假日期
    private static List<String> lawHolidays = new ArrayList<String>(Arrays.asList(
            "2018-12-31", "2019-01-01", "2019-02-04", "2019-02-05",
            "2019-02-06", "2019-02-07", "2019-02-08", "2019-04-05",
            "2019-05-01", "2019-06-07", "2019-09-13", "2019-10-01",
            "2019-10-02", "2019-10-03", "2019-10-04", "2019-10-07"));
    // 由于放假需要额外工作的周末
    private static List<String> extraWorkdays = new ArrayList<String>(Arrays.asList(
            "2018-01-01"));

    /**
     * 判断是否是法定假日
     *
     * @param
     * @return
     * @throws Exception
     */
    public static boolean isLawHoliday() throws Exception {

        if (lawHolidays.contains(calendar)) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否是周末
     *
     * @param
     * @return
     * @throws
     */
    public static boolean isWeekends() throws Exception {

        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        if (ca.get(Calendar.DAY_OF_WEEK) == 1
                || ca.get(Calendar.DAY_OF_WEEK) == 7) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否是需要额外补班的周末
     *
     * @param
     * @return
     * @throws Exception
     */
    public static boolean isExtraWorkday() throws Exception {
        if (extraWorkdays.contains(calendar)) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否是休息日（包含法定节假日和不需要补班的周末）
     *
     * @param
     * @return
     * @throws Exception
     */
    public static boolean isHoliday() throws Exception {

        // 首先法定节假日必定是休息日
        if (isLawHoliday()) {
            return true;
        }
        // 排除法定节假日外的非周末必定是工作日
        if (!isWeekends()) {
            return false;
        }
        // 所有周末中只有非补班的才是休息日
        if (isExtraWorkday()) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否是工作日
     *
     * @param
     * @return
     * @throws Exception
     */
    public static boolean isWorkday() throws Exception {

        return !(isHoliday());
    }

    public static int getTotalDays() {
        return new GregorianCalendar().isLeapYear(Calendar.YEAR) ? 366 : 365;
    }

    public static int getTotalLawHolidays() {
        return lawHolidays.size();
    }

    public static int getTotalExtraWorkdays() {
        return extraWorkdays.size();
    }

    /**
     * 获取一年中所有周末的天数
     * @return
     */
    public static int getTotalWeekends() {
        List<String> saturdays = new ArrayList<String>();
        List<String> sundays = new ArrayList<String>();
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int nextYear = 1 + calendar.get(Calendar.YEAR);
        Calendar cstart = Calendar.getInstance();
        Calendar cend = Calendar.getInstance();
        cstart.set(currentYear, 0, 1);// 今年的元旦
        cend.set(nextYear, 0, 1);// 明年的元旦
        return getTotalSaturdays(saturdays, calendar, cstart, cend,
                currentYear)
                + getTotalSundays(sundays, calendar, cstart, cend,
                currentYear);
    }

    private static int getTotalSaturdays(List<String> saturdays, Calendar calendar,
                                  Calendar cstart, Calendar cend, int currentYear) {
        // 将日期设置到上个周六
        calendar.add(Calendar.DAY_OF_MONTH, -calendar.get(Calendar.DAY_OF_WEEK));
        // 从上周六往这一年的元旦开始遍历，定位到去年最后一个周六
        while (calendar.get(Calendar.YEAR) == currentYear) {
            calendar.add(Calendar.DAY_OF_YEAR, -7);
        }
        // 将日期定位到今年第一个周六
        calendar.add(Calendar.DAY_OF_YEAR, 7);
        // 从本年第一个周六往下一年的元旦开始遍历
        for (; calendar.before(cend); calendar.add(Calendar.DAY_OF_YEAR, 7)) {
            saturdays.add(calendar.get(Calendar.YEAR) + "-"
                    + calendar.get(Calendar.MONTH) + "-"
                    + calendar.get(Calendar.DATE));
        }
        return saturdays.size();
    }

    private static int getTotalSundays(List<String> sundays, Calendar calendar,
                                Calendar cstart, Calendar cend, int currentYear) {
        // 将日期设置到上个周日
        calendar.add(Calendar.DAY_OF_MONTH,
                -calendar.get(Calendar.DAY_OF_WEEK) + 1);
        // 从上周日往这一年的元旦开始遍历，定位到去年最后一个周日
        while (calendar.get(Calendar.YEAR) == currentYear) {
            calendar.add(Calendar.DAY_OF_YEAR, -7);
        }
        // 将日期定位到今年第一个周日
        calendar.add(Calendar.DAY_OF_YEAR, 7);
        // 从本年第一个周日往下一年的元旦开始遍历
        for (; calendar.before(cend); calendar.add(Calendar.DAY_OF_YEAR, 7)) {
            sundays.add(calendar.get(Calendar.YEAR) + "-"
                    + calendar.get(Calendar.MONTH) + "-"
                    + calendar.get(Calendar.DATE));
        }
        return sundays.size();
    }

    public static int getTotalHolidays(){
        //先获取不需要补班的周末天数
        int noWorkWeekends = getTotalWeekends() - getTotalExtraWorkdays();
        return noWorkWeekends + getTotalLawHolidays();
    }
    public static Date nextWorkDay(){
        try {
            date = new Date();
            date.setTime(date.getTime()+hour24);
            MyChineseWorkDay.setDate(date);
            while (isHoliday()){
                date.setTime(date.getTime()+hour24);
                MyChineseWorkDay.setDate(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("下一个交易日:"+MyUtils.getDayFormat(date));;
        return date;
    }
    public static Date preWorkDay(){
        try {
            date = new Date();
            date.setTime(date.getTime()-hour24);
            MyChineseWorkDay.setDate(date);
            while (isHoliday()){
                date.setTime(date.getTime()-hour24);
                MyChineseWorkDay.setDate(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("上一个交易日:"+MyUtils.getDayFormat(date));;
        return date;
    }
    public static Date preDaysWorkDay(int days){
        Date d = new Date();
        for(int i=0;i<days;i++){
            d=preWorkDay(d);
        }
        return d;
    }
    public static Date preDaysWorkDay(int days,Date d){
        for(int i=0;i<days;i++){
            d=preWorkDay(d);
        }
        return d;
    }
    public static Date nextWorkDay(Date mydate){
        try {
            date = mydate;
            date.setTime(date.getTime()+hour24);
            MyChineseWorkDay.setDate(date);
            while (isHoliday()){
                date.setTime(date.getTime()+hour24);
                MyChineseWorkDay.setDate(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println("下一个工作日:"+MyUtils.getDayFormat(date));;
        return date;
    }
    public static Date preWorkDay(Date mydate){
        try {
            date = mydate;
            date.setTime(date.getTime()-hour24);
            MyChineseWorkDay.setDate(date);
            while (isHoliday()){
                date.setTime(date.getTime()-hour24);
                MyChineseWorkDay.setDate(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println("上一个工作日:"+MyUtils.getDayFormat(date));;
        return date;
    }

    public static void main(String[] args) throws Exception {

        System.out.println(MyUtils.getDayFormat(MyChineseWorkDay.preWorkDay()));
        //System.out.println( MyUtils.getDayFormat(MyChineseWorkDay.nextWorkDay()));

        /*System.out.println("是否是法定节假日：" + MyChineseWorkDay.isLawHoliday());
        System.out.println("是否是周末：" + MyChineseWorkDay.isWeekends());
        System.out.println("是否是需要额外补班的周末：" + MyChineseWorkDay.isExtraWorkday());
        System.out.println("是否是休息日：" + MyChineseWorkDay.isHoliday());
        System.out.println("是否是工作日：" + MyChineseWorkDay.isWorkday());
        System.out.println("今年总共有" + MyChineseWorkDay.getTotalDays() + "天");
        System.out.println("今年总共有" + MyChineseWorkDay.getTotalLawHolidays() + "天法定节假日");
        System.out.println("今年总共有" + MyChineseWorkDay.getTotalExtraWorkdays() + "天需要补班的周末");
        System.out.println("今年总共有" + MyChineseWorkDay.getTotalWeekends() + "天周末");
        System.out.println("今年总共有" + MyChineseWorkDay.getTotalHolidays() + "天休息日");*/
    }

}
