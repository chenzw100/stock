package com.example.demo.enums;

/**
 * Created by chenzw on 2018/10/22.
 */
public class NumberEnum {
    public enum TemperatureType{
        OPEN(1,"开盘"),
        CLOSE(2,"收盘"),
        NORMAL(3,"正常");

        private TemperatureType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }
        private int code;
        private String desc;
        public int getCode() {
            return code;
        }
        public void setCode(int code) {
            this.code = code;
        }
        public String getDesc() {
            return desc;
        }
        public void setDesc(String desc) {
            this.desc = desc;
        }

        public static String getTemperatureType(int code){
            for(TemperatureType d : TemperatureType.values()){
                if(d.getCode()==code){
                    return d.getDesc();
                }
            }
            return "";
        }
    }
    public enum StockType{
        COMMON(1,"两者"),
        DAY(2,"一天"),
        CURRENT(3,"实时"),
        BID(4,"竞价"),
        STRONG(11,"强势"),
        OPEN(12,"炸板"),
        DOWN(13,"低吸"),
        PRE(20,"共同"),
        WORKDAY(21,"交易"),
        HOLIDAY(22,"休息"),
        ;

        private StockType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }
        private int code;
        private String desc;
        public int getCode() {
            return code;
        }
        public void setCode(int code) {
            this.code = code;
        }
        public String getDesc() {
            return desc;
        }
        public void setDesc(String desc) {
            this.desc = desc;
        }

        public static String getStockType(int code){
            for(StockType d : StockType.values()){
                if(d.getCode()==code){
                    return d.getDesc();
                }
            }
            return "";
        }
    }
    public enum StrongOpenType{
        STRONG(1,"强势"),
        OPEN(2,"开板"),
        ;

        private StrongOpenType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }
        private int code;
        private String desc;
        public int getCode() {
            return code;
        }
        public void setCode(int code) {
            this.code = code;
        }
        public String getDesc() {
            return desc;
        }
        public void setDesc(String desc) {
            this.desc = desc;
        }

        public static String getStrongOpenType(int code){
            for(StrongOpenType d : StrongOpenType.values()){
                if(d.getCode()==code){
                    return d.getDesc();
                }
            }
            return "";
        }
    }
}
