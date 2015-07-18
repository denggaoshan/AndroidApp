package com.example.administrator.androidapp.msg;

import android.graphics.Bitmap;

import java.util.HashMap;

/**
 * Created by admin on 2015/7/15.
 * 为了提高速度设置的缓存类
 * 在程序空闲的时候，维护一个优先队列，通过空余的线程不断从服务器下载更新有可能用到的各种资料
 * */

public class Cache {
    private static Bitmap userAvater;

    public static void setUserAvater(Bitmap bm){
        userAvater = bm;
    }

    public static Bitmap getUserAvater(){
        return userAvater;
    }


    //存储图片
    private static HashMap<String, Bitmap> bitmapCache = new HashMap<>();

    //获得图片
    public static Bitmap getBitmap(String url){
        if (bitmapCache.containsKey(url)){
            return bitmapCache.get(url);
        }
        else{
            Bitmap bitmap =  ToolClass.returnBitMap(url);
            if(bitmap!=null){
                setBitmap(url,bitmap);
            }
            return bitmap;
        }
    }

    private static void setBitmap(String url, Bitmap bm){
        bitmapCache.put(url, bm);
    }


    //存储用户信息
    private static HashMap<String,User> userCache = new HashMap<>();


    //根据ID获得用户
    public static User getUserById(String userId){
        if(userCache.containsKey(userId)){
            return userCache.get(userId);
        }else{
            ToolClass.getUserInfo(User.getCurrentUser().getUserID(),userId);
        }
        return null;
    }

    public static void beginDownLoadInformation(){

    }

}
