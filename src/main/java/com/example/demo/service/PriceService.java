package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by laikui on 2019/1/28.
 */
@Component
public class PriceService {
    @Autowired
    RestTemplate restTemplate;
    String currentPrice(String code) {
        String url ="http://qt.gtimg.cn/q=s_"+code;
        Object response =  restTemplate.getForObject(url,String.class);
        String str = response.toString();
        String[] stockObj = str.split("~");
        if(stockObj.length<3){
            return null;
        }
        return stockObj[3];
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }
}
