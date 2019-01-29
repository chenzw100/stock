package com.example.demo.service;


import com.example.demo.dao.CurrentStockRepository;
import com.example.demo.dao.LimitUpStockRepository;
import com.example.demo.dao.MyFiveTgbStockRepository;
import com.example.demo.dao.MyTgbStockRepository;
import com.example.demo.domain.MyFiveTgbStock;
import com.example.demo.domain.MyTgbStock;
import com.example.demo.domain.MyTotalStock;
import com.example.demo.domain.XGBStock;
import com.example.demo.utils.MyChineseWorkDay;
import com.example.demo.utils.MyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class MyTgbService extends PriceService{
    Log log = LogFactory.getLog(MyTgbService.class);
    @Autowired
    MyTgbStockRepository myTgbStockRepository;
    @Autowired
    CurrentStockRepository currentStockRepository;
    @Autowired
    LimitUpStockRepository limitUpStockRepository;
    @Autowired
    MyFiveTgbStockRepository myFiveTgbStockRepository;

    public void choice(){
        choiceFive();
        String start = MyUtils.getDayFormat(MyChineseWorkDay.preWorkDay());
        String end = MyUtils.getDayFormat();
        List<MyTotalStock> totalStocks =  currentStockRepository.oneDayInfo(start, end);
        for(MyTotalStock myTotalStock : totalStocks){
            MyTgbStock myTgbStock = new MyTgbStock(myTotalStock.getCode(),myTotalStock.getName());
            myTgbStock.setHotSort(myTotalStock.getTotalCount());
            myTgbStock.setHotValue(myTotalStock.getHotValue());
            myTgbStock.setHotSeven(myTotalStock.getHotSeven());
            String currentPrice = currentPrice(myTotalStock.getCode());
            myTgbStock.setYesterdayClosePrice(MyUtils.getCentBySinaPriceStr(currentPrice));
            List<XGBStock> xgbStocks = limitUpStockRepository.findByCodeAndDayFormat(myTotalStock.getCode(),MyUtils.getDayFormat(MyUtils.getYesterdayDate()));
            if(xgbStocks!=null && xgbStocks.size()>0){
                XGBStock xgbStock =xgbStocks.get(0);
                myTgbStock.setPlateName(xgbStock.getPlateName());
                myTgbStock.setOneFlag(xgbStock.getOpenCount());
                myTgbStock.setContinuous(xgbStock.getContinueBoardCount());
                myTgbStock.setLimitUp(1);
            }else {
                myTgbStock.setPlateName("");
                myTgbStock.setOneFlag(1);
                myTgbStock.setContinuous(0);
                myTgbStock.setLimitUp(0);
            }
            myTgbStock.setCreated(MyUtils.getCurrentDate());

            myTgbStockRepository.save(myTgbStock);
        }
    }
    public void choiceFive(){
        String end = MyUtils.getDayFormat();
        String start =MyUtils.getDayFormat(MyChineseWorkDay.preDaysWorkDay(4, MyUtils.getCurrentDate()));
        List<MyTotalStock> totalStocks =  currentStockRepository.fiveDayInfo(start, end);
        for(MyTotalStock myTotalStock : totalStocks){
            MyFiveTgbStock myFiveTgbStock = new MyFiveTgbStock(myTotalStock.getCode(),myTotalStock.getName());
            myFiveTgbStock.setHotSort(myTotalStock.getTotalCount());
            myFiveTgbStock.setHotValue(myTotalStock.getHotValue());
            myFiveTgbStock.setHotSeven(myTotalStock.getHotSeven());
            String currentPrice = currentPrice(myTotalStock.getCode());
            myFiveTgbStock.setYesterdayClosePrice(MyUtils.getCentBySinaPriceStr(currentPrice));
            List<XGBStock> xgbStocks = limitUpStockRepository.findByCodeAndDayFormat(myTotalStock.getCode(),MyUtils.getDayFormat(MyUtils.getYesterdayDate()));
            if(xgbStocks!=null && xgbStocks.size()>0){
                XGBStock xgbStock =xgbStocks.get(0);
                myFiveTgbStock.setPlateName(xgbStock.getPlateName());
                myFiveTgbStock.setOneFlag(xgbStock.getOpenCount());
                myFiveTgbStock.setContinuous(xgbStock.getContinueBoardCount());
                myFiveTgbStock.setLimitUp(1);
            }else {
                myFiveTgbStock.setPlateName("");
                myFiveTgbStock.setOneFlag(1);
                myFiveTgbStock.setContinuous(0);
                myFiveTgbStock.setLimitUp(0);
            }
            myFiveTgbStock.setCreated(MyUtils.getCurrentDate());

            myFiveTgbStockRepository.save(myFiveTgbStock);
        }
    }

    public void openFive(){
        List<MyFiveTgbStock> todayStocks = myFiveTgbStockRepository.findByDayFormatOrderByHotSort(MyUtils.getDayFormat());
        if(todayStocks!=null){
            for(MyFiveTgbStock myStock :todayStocks){
                String currentPrice = currentPrice(myStock.getCode());
                myStock.setTodayOpenPrice(MyUtils.getCentBySinaPriceStr(currentPrice));
                myFiveTgbStockRepository.save(myStock);
            }
        }
        List<MyFiveTgbStock> myStocks = myFiveTgbStockRepository.findByDayFormatOrderByHotSort(MyUtils.getDayFormat(MyUtils.getYesterdayDate()));
        if(myStocks!=null){
            for(MyFiveTgbStock myStock :myStocks){
                String currentPrice = currentPrice(myStock.getCode());
                myStock.setTomorrowOpenPrice(MyUtils.getCentBySinaPriceStr(currentPrice));
                myFiveTgbStockRepository.save(myStock);
            }
        }

    }
    public void open(){
        openFive();
        List<MyTgbStock> todayStocks = myTgbStockRepository.findByDayFormatOrderByHotSort(MyUtils.getDayFormat());
        if(todayStocks!=null){
            for(MyTgbStock myStock :todayStocks){
                String currentPrice = currentPrice(myStock.getCode());
                myStock.setTodayOpenPrice(MyUtils.getCentBySinaPriceStr(currentPrice));
                myTgbStockRepository.save(myStock);
            }
        }
        List<MyTgbStock> myStocks = myTgbStockRepository.findByDayFormatOrderByHotSort(MyUtils.getDayFormat(MyUtils.getYesterdayDate()));
        if(myStocks!=null){
            for(MyTgbStock myStock :myStocks){
                String currentPrice = currentPrice(myStock.getCode());
                myStock.setTomorrowOpenPrice(MyUtils.getCentBySinaPriceStr(currentPrice));
                myTgbStockRepository.save(myStock);
            }
        }

    }
    public void closeFive(){
        List<MyFiveTgbStock> myStocksTomorrow = myFiveTgbStockRepository.findByDayFormatOrderByHotSort(MyUtils.getDayFormat(MyUtils.getYesterdayDate()));
        if(myStocksTomorrow!=null){
            for(MyFiveTgbStock myStock :myStocksTomorrow){
                String currentPrice = currentPrice(myStock.getCode());
                myStock.setTomorrowClosePrice(MyUtils.getCentBySinaPriceStr(currentPrice));
                myFiveTgbStockRepository.save(myStock);
            }
        }
        List<MyFiveTgbStock> myStocks = myFiveTgbStockRepository.findByDayFormatOrderByHotSort(MyUtils.getDayFormat());
        if(myStocks!=null){
            for(MyFiveTgbStock myStock :myStocks){
                String currentPrice = currentPrice(myStock.getCode());
                myStock.setTodayClosePrice(MyUtils.getCentBySinaPriceStr(currentPrice));
                String code = myStock.getCode();
                List<XGBStock> xgbStocks = limitUpStockRepository.findByCodeAndDayFormat(code, MyUtils.getDayFormat());
                if(xgbStocks!=null && xgbStocks.size()>0){
                    XGBStock xgbStock =xgbStocks.get(0);
                    myStock.setOpenCount(xgbStock.getOpenCount());
                }else {
                    myStock.setOpenCount(-1);
                }
                myFiveTgbStockRepository.save(myStock);
            }
        }
    }
    public void close(){
        closeFive();
        List<MyTgbStock> myStocksTomorrow = myTgbStockRepository.findByDayFormatOrderByHotSort(MyUtils.getDayFormat(MyUtils.getYesterdayDate()));
        if(myStocksTomorrow!=null){
            for(MyTgbStock myStock :myStocksTomorrow){
                String currentPrice = currentPrice(myStock.getCode());
                myStock.setTomorrowClosePrice(MyUtils.getCentBySinaPriceStr(currentPrice));
                myTgbStockRepository.save(myStock);
            }
        }
        List<MyTgbStock> myStocks = myTgbStockRepository.findByDayFormatOrderByHotSort(MyUtils.getDayFormat());
        if(myStocks!=null){
            for(MyTgbStock myStock :myStocks){
                String currentPrice = currentPrice(myStock.getCode());
                myStock.setTodayClosePrice(MyUtils.getCentBySinaPriceStr(currentPrice));
                String code = myStock.getCode();
                List<XGBStock> xgbStocks = limitUpStockRepository.findByCodeAndDayFormat(code, MyUtils.getDayFormat());
                if(xgbStocks!=null && xgbStocks.size()>0){
                    XGBStock xgbStock =xgbStocks.get(0);
                    myStock.setOpenCount(xgbStock.getOpenCount());
                }else {
                    myStock.setOpenCount(-1);
                }
                myTgbStockRepository.save(myStock);
            }
        }
    }

}
