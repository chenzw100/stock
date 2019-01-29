package com.example.demo.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.example.demo.dao.DownStockRepository;
import com.example.demo.domain.DownStock;
import com.example.demo.domain.XGBStock;
import com.example.demo.enums.NumberEnum;
import com.example.demo.utils.MyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * http://qt.gtimg.cn/q=s_sh600519
 * v_s_sh600519="1~贵州茅台~600519~358.74~-2.55~-0.71~27705~99411~~4506.49";
 1  0: 未知
 2  1: 股票名称
 3  2: 股票代码
 4  3: 当前价格
 5  4: 涨跌
 6  5: 涨跌%
 7  6: 成交量（手）
 8  7: 成交额（万）
 9  8:
 10  9: 总市值

 //https://www.cnblogs.com/skating/p/6424342.html
 //当日涨停写库
 //热门涨停
 //计算竞价金额
 */
@Component
public class DownService extends PriceService{
    Log log = LogFactory.getLog(DownService.class);
    @Autowired
    DownStockRepository downStockRepository;

    private static String multi_stock_url="https://wows-api.wallstreetcn.com/v2/sheet/multi_stock";
    private static String boom_stock_url ="https://wows-api.wallstreetcn.com/v2/sheet/boom_stock";

    public void choice(){
        multiStock();
        boomStock();
    }

    //3.10执行
    private void boomStock(){
        Object response =  restTemplate.getForObject(boom_stock_url, String.class);
        JSONArray closeLimitUp = JSONObject.parseObject(response.toString()).getJSONObject("data").getJSONArray("items");
        Set<XGBStock> ds=new TreeSet<XGBStock>();

        for(int i=0;i<closeLimitUp.size();i++){
            JSONArray jsonArray =  closeLimitUp.getJSONArray(i);
            XGBStock xgbStock = new XGBStock();
            xgbStock.setName(jsonArray.toArray()[1].toString());
            String codeStr = jsonArray.toArray()[0].toString();
            String code = codeStr.substring(0, 6);
            if(codeStr.contains("Z")){
                xgbStock.setCode("sz"+code);
            }else {
                xgbStock.setCode("sh"+code);
            }
            xgbStock.setPrice(jsonArray.toArray()[3].toString());
            String down = jsonArray.toArray()[4].toString();
            int downRate=MyUtils.getCentByYuanStr(down);
            xgbStock.setDownRate(downRate);
            if(downRate<90){
                ds.add(xgbStock);
            }
            log.info("open:"+code +",downRate:"+downRate);

        }

        for(XGBStock xgbStock:ds){
            DownStock downStock = xgbStock.coverDownStock();
            downStock.setStockType(NumberEnum.StockType.OPEN.getCode());
            if(xgbStock.getDownRate()<-900){
                List<DownStock> downStocks = downStockRepository.findByCodeAndDayFormat(downStock.getCode(), downStock.getDayFormat());
                if(downStocks!=null&& downStocks.size()>0){
                    downStock = downStocks.get(0);
                    downStock.setStockType(NumberEnum.StockType.DOWN.getCode());
                }
            }
            downStock.setDayFormat(MyUtils.getDayFormat(MyUtils.getTomorrowDate()));
            downStock.setCreated(MyUtils.getCurrentDate());
            downStock.setPreFormat(MyUtils.getDayFormat());
            downStockRepository.save(downStock);
        }

    }
    //3.10执行
    private void multiStock(){
        Object response =  restTemplate.getForObject(multi_stock_url, String.class);
        JSONArray closeLimitUp = JSONObject.parseObject(response.toString()).getJSONObject("data").getJSONArray("items");
        Set<XGBStock> ds=new TreeSet<XGBStock>();

        for(int i=0;i<closeLimitUp.size();i++){
            JSONArray jsonArray =  closeLimitUp.getJSONArray(i);
            XGBStock xgbStock = new XGBStock();
            xgbStock.setName(jsonArray.toArray()[1].toString());
            String codeStr = jsonArray.toArray()[0].toString();
            String code = codeStr.substring(0, 6);
            if(codeStr.contains("Z")){
                xgbStock.setCode("sz"+code);
            }else {
                xgbStock.setCode("sh"+code);
            }
            xgbStock.setPrice(jsonArray.toArray()[3].toString());
            String down = jsonArray.toArray()[4].toString();
            int downRate=MyUtils.getCentByYuanStr(down);
            xgbStock.setDownRate(downRate);
            if(downRate<-900){
                ds.add(xgbStock);
            }
            log.info("strong:"+code + ",downRate:"+downRate);

        }

        for(XGBStock xgbStock:ds){
            DownStock downStock = xgbStock.coverDownStock();
            downStock.setCreated(MyUtils.getCurrentDate());
            downStock.setDayFormat(MyUtils.getDayFormat(MyUtils.getTomorrowDate()));
            downStock.setPreFormat(MyUtils.getDayFormat());
            downStock.setStockType(NumberEnum.StockType.STRONG.getCode());
            downStockRepository.save(downStock);
        }
    }


    public void open(){
        List<DownStock> downStocks = downStockRepository.findByDayFormatOrderByOpenBidRateDesc(MyUtils.getDayFormat());
        for (DownStock downStock:downStocks){
            //选出来后，新的价格新的一天
            String currentPrice = currentPrice(downStock.getCode());
            downStock.setTodayOpenPrice(MyUtils.getCentByYuanStr(currentPrice));
            downStockRepository.save(downStock);
        }
        List<DownStock> downStocksTomorrow = downStockRepository.findByDayFormatOrderByOpenBidRateDesc(MyUtils.getDayFormat(MyUtils.getYesterdayDate()));
        if(downStocksTomorrow!=null){
            for(DownStock downStock :downStocksTomorrow){
                String currentPrice = currentPrice(downStock.getCode());
                downStock.setTomorrowOpenPrice(MyUtils.getCentByYuanStr(currentPrice));
                downStockRepository.save(downStock);
            }
        }
    }
    public void close(){
        List<DownStock> myStocksTomorrow = downStockRepository.findByDayFormatOrderByOpenBidRateDesc(MyUtils.getDayFormat(MyUtils.getYesterdayDate()));
        if(myStocksTomorrow!=null){
            for(DownStock downStock :myStocksTomorrow){
                String currentPrice = currentPrice(downStock.getCode());
                downStock.setTomorrowClosePrice(MyUtils.getCentByYuanStr(currentPrice));
                downStockRepository.save(downStock);
            }
        }
        List<DownStock> myStocks = downStockRepository.findByDayFormatOrderByOpenBidRateDesc(MyUtils.getDayFormat());
        if(myStocks!=null){
            for(DownStock downStock :myStocks){
                String currentPrice = currentPrice(downStock.getCode());
                downStock.setTodayClosePrice(MyUtils.getCentByYuanStr(currentPrice));
                downStockRepository.save(downStock);
            }
        }
        choice();
    }

}
