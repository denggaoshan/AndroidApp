/*
package com.example.administrator.androidapp.core;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

*/
/**
 * Created by Administrator on 2015/7/3.
 *//*

class Photo
{
    private String PhotoID;
    private String UserID;
    private String ActivityID;
    private String Address;
    private String Title;
    private String Describe;
    private String Level;
    private String Time;
    private String NickName;
    private String Avatar;

    public Photo(JSONObject jsonObject)
    {
        Field[] fd = this.getClass().getDeclaredFields();
        for(Field f : fd){
            f.setAccessible(true);
            try
            {
                String temp = jsonObject.getString(f.getName());
                f.set(this, temp);
            }
            catch (JSONException e) {
                try
                {
                    f.set(this, null);
                }
                catch (IllegalAccessException eee)
                {

                }
            }
            catch (IllegalAccessException ee)
            {

            }
        }
    }
}
*/
