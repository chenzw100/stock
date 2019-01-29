package com.example.demo.controller;

import com.example.demo.dao.*;
import com.example.demo.domain.*;
import com.example.demo.enums.NumberEnum;
import com.example.demo.utils.MyChineseWorkDay;
import com.example.demo.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class HelloController {
    @Autowired
    MyStockRepository myStockRepository;
    @Autowired
    FiveTgbStockRepository fiveTgbStockRepository;
    @Autowired
    TemperatureRepository temperatureRepository;
    @Autowired
    DownStockRepository downStockRepository;
    @Autowired
    MyFiveTgbStockRepository myFiveTgbStockRepository;
    @RequestMapping("/m/{end}")
    String m(@PathVariable("end")String end) {
        String desc ="查询20190124之后的数据，坚持模式！！！<br>查询日期";
        Date endDate =  MyUtils.getFormatDate(end);
        String yesterday =MyUtils.getDayFormat(MyChineseWorkDay.preDaysWorkDay(1,endDate));
        List<Temperature> yesterdays = temperatureRepository.findByDayFormatAndType(yesterday, NumberEnum.TemperatureType.CLOSE.getCode());
        List<DownStock> downStocks =downStockRepository.findByDayFormatOrderByOpenBidRate(end);

        List<FiveTgbStock> hotSortFive = fiveTgbStockRepository.findByDayFormatOrderByOpenBidRateDesc(end);
        List<MyFiveTgbStock> myTgbStockFive = myFiveTgbStockRepository.findByDayFormatOrderByOpenBidRateDesc(end);
        List<Temperature> temperatures = temperatureRepository.findByDayFormatOrderByIdDesc(end);
        List<DownStock> downBeforeStocks =downStockRepository.findByPreFormatOrderByOpenBidRateDesc(end);

        return desc+end+"昨日情况:<br>"+downStocks+"<br>"+yesterdays+"<br>股吧竞价:<br>"+hotSortFive+"end"+end+"<br>我的竞价:<br>"+myTgbStockFive+":<br>"+temperatures+end+"当日:<br>"+downBeforeStocks;
    }
    @RequestMapping("/f/{f}")
    String e(@PathVariable("f")String f){
        List<FiveTgbStock> stockList = fiveTgbStockRepository.findByDayFormatOrderByOpenBidRateDesc(f);
        return "success<br>"+stockList;
    }
    @RequestMapping("/d/{end}")
    String d(@PathVariable("end")String end){

        boolean f = true;
        while (f){
            System.out.println("==========["+end);
            if(end.equals("20181220")){
                f=false;
            }
            end=my(end);
        }
        return "success<br>";
    }
    String my(String end){
            Date endDate =  MyUtils.getFormatDate(end);
         String myEnd = MyUtils.getDayFormat(endDate);
         String start =MyUtils.getDayFormat(MyChineseWorkDay.preDaysWorkDay(4,endDate));
         System.out.println(start+":"+end+",my:"+myEnd);
         List<TotalStock> totalStocks =myStockRepository.fiveStock(start,end);
         for(TotalStock totalStock:totalStocks){
             System.out.println(totalStock.getName()+totalStock.getTotal());
             FiveTgbStock fiveTgbStock = new FiveTgbStock(totalStock.getCode(),totalStock.getName());
             fiveTgbStock.setHotSort(totalStock.getTotal());
             MyStock myStock =findByCodeAndDayFormat(totalStock.getCode(),end);
             if(myStock!=null){
                 fiveTgbStock.setTodayClosePrice(myStock.getTodayClosePrice());
                 fiveTgbStock.setTodayOpenPrice(myStock.getTodayOpenPrice());
                 fiveTgbStock.setTomorrowClosePrice(myStock.getTomorrowClosePrice());
                 fiveTgbStock.setTomorrowOpenPrice(myStock.getTomorrowOpenPrice());
                 fiveTgbStock.setYesterdayClosePrice(myStock.getYesterdayClosePrice());
                 fiveTgbStock.setOpenBidRate(myStock.getOpenBidRate());
             }
             fiveTgbStock.setCreated(new Date());
             fiveTgbStock.setDayFormat(myEnd);
             fiveTgbStockRepository.save(fiveTgbStock);
         }
        endDate =  MyUtils.getFormatDate(end);
        return MyUtils.getDayFormat(MyChineseWorkDay.preDaysWorkDay(1,endDate));
     }
    @RequestMapping("/t/{end}")
    String t(@PathVariable("end")String end){
      System.out.println(end);
        return "success<br>"+end;
    }
    MyStock findByCodeAndDayFormat(String code, String dayFormat){
      List<MyStock>  myStocks= myStockRepository.findByCodeAndDayFormat(code,dayFormat);
      if(myStocks!=null && myStocks.size()>0){
          return myStocks.get(0);
      }
      return null;

    }
}
