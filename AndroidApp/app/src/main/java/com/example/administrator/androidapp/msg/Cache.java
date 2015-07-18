package com.example.administrator.androidapp.msg;

import android.graphics.Bitmap;

import java.util.HashMap;

/**
 * Created by admin on 2015/7/15.
 */
public class Cache {

    private static Bitmap userAvater;
    public static void setUserAvater(Bitmap bm){
        userAvater = bm;
    }

    public static Bitmap getUserAvater(){
        return userAvater;
    }

    private static HashMap<String, Bitmap> bitmapHashMap = new HashMap<>();

    public static Bitmap getBitmap(String url){
        if (bitmapHashMap.containsKey(url)){
            return bitmapHashMap.get(url);
        }
        else{
            Bitmap bitmap =  ToolClass.returnBitMap(url);
            if(bitmap!=null){
                setBitmap(url,bitmap);
            }
            return bitmap;
        }

    }
    public static void setBitmap(String url, Bitmap bm){
        bitmapHashMap.put(url, bm);
    }

}
