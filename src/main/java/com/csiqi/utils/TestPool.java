package com.csiqi.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestPool {
    public static void main(String[] args) {
        //初始化连接池
        RedisUtils.initialPool();
        //启动1000个线程
        RedisUtils.cleanData();
        for (int i = 0; i < 1000; i++) {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            String time = sdf.format(date);
            RedisUtils.setString("key","key"+i, time);
            System.out.println(RedisUtils.getString("key","key"+i));
        }
    }
}