package com.example.demo.domain;

/**
 * Created by laikui on 2019/1/10.
 */
public class MyTotalStockImpl {
    private MyTotalStock myTotalStock;
    public MyTotalStockImpl(MyTotalStock myTotalStock) {
        this.myTotalStock = myTotalStock;
    }

    @Override
    public String toString() {
        return myTotalStock.getCode()+myTotalStock.getName()+":"+myTotalStock.getTotalCount()+",hot:"+myTotalStock.getHotValue()+",Seven:"+myTotalStock.getHotSeven()+"<br>";
    }
}
