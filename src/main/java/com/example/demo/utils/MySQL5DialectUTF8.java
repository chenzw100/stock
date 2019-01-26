package com.example.demo.utils;

import org.hibernate.dialect.MySQL5InnoDBDialect;

/**
 * Created by chenzhiwei on 2018/11/5.
 */
public class MySQL5DialectUTF8 extends MySQL5InnoDBDialect {
    @Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}
