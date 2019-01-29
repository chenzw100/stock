package com.example.demo.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.dao.LimitUpStockRepository;
import com.example.demo.domain.XGBStock;
import com.example.demo.utils.MyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by laikui on 2019/1/28.
 */
@Component
public class LimitUpService {
    @Autowired
    LimitUpStockRepository limitUpStockRepository;
    @Autowired
    RestTemplate restTemplate;
    public void closeLimitUp(){
        String urlCloseLimitUp = "https://wows-api.wallstreetcn.com/v2/sheet/board_stock?filter=true";
        try {
            Object response =  restTemplate.getForObject(urlCloseLimitUp, String.class);
            JSONArray closeLimitUp = JSONObject.parseObject(response.toString()).getJSONObject("data").getJSONArray("items");
            for(int i=0;i<closeLimitUp.size();i++){
                JSONArray jsonArray =  closeLimitUp.getJSONArray(i);
                XGBStock xgbStock = new XGBStock();
                xgbStock.setCreated(MyUtils.getCurrentDate());
                xgbStock.setName(jsonArray.toArray()[1].toString());
                String code = jsonArray.toArray()[0].toString().substring(0,6);
                if(code.indexOf("6")==0){
                    code = "sh"+code;
                }else {
                    code = "sz"+code;
                }
                xgbStock.setCode(code);
                xgbStock.setOpenCount(Integer.parseInt(jsonArray.toArray()[11].toString()));
                xgbStock.setContinueBoardCount(Integer.parseInt(jsonArray.toArray()[12].toString()));
                xgbStock.setPrice(jsonArray.toArray()[3].toString());
                String down = jsonArray.toArray()[4].toString();
                int downRate= MyUtils.getCentBySinaPriceStr(down);
                xgbStock.setDownRate(downRate);
                JSONArray jsonArrayPlate = jsonArray.getJSONArray(15);
                String plateName ="";
                for(int j=0;j<jsonArrayPlate.size();j++){
                    plateName = plateName+","+jsonArrayPlate.getJSONObject(j).getString("plate_name");
                }
                if(StringUtils.isNotBlank(plateName)){
                    plateName =plateName.substring(1,plateName.length());
                    xgbStock.setPlateName(plateName);
                }else {
                    xgbStock.setPlateName("无");
                }
                // System.out.println(plateName);
                //log.info(xgbStock.getDayFormat()+":当日涨停：" + xgbStock.toString());
                limitUpStockRepository.save(xgbStock);
            }
        }catch (Exception e) {
            e.printStackTrace();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            //log.info("==>重新执行");
            closeLimitUp();
        }
    }
}
