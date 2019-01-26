package com.example.demo.controller;

import com.example.demo.dao.FiveTgbStockRepository;
import com.example.demo.dao.MyStockRepository;
import com.example.demo.domain.FiveTgbStock;
import com.example.demo.domain.MyStock;
import com.example.demo.domain.TotalStock;
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
    @RequestMapping("/d/{end}")
    String d(@PathVariable("end")String end){

        boolean f = true;
        while (f){
            System.out.println("==========["+end);
            if(end.equals("2018-10-22")){
                f=false;
            }
            end=my(end);
        }
        return "success<br>";
    }
    String my(String end){
            Date endDate =  MyUtils.getFormatDateSys(end);
         String myEnd = MyUtils.getDayFormat(endDate);
         String start =MyUtils.getDayFormatSys(MyChineseWorkDay.preDaysWorkDay(4,endDate));
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
        endDate =  MyUtils.getFormatDateSys(end);
        return MyUtils.getDayFormatSys(MyChineseWorkDay.preDaysWorkDay(1,endDate));
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
