package com.example.administrator.androidapp.msg;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

/**
 * Created by admin on 2015/7/3.
 */
public class MyActivity extends BaseType{

    private String ActivityID;public String getActivityID(){return ActivityID;}
    private String UserID;
    private String Title;public String getTitle(){return Title;}
    private String Content;public String getContent(){return Content;}
    private String StartTime;public String getStartTime(){return StartTime;}
    private String EndTime;public String getEndTime(){return EndTime;}
    private String Place;public String getPlace(){return Place;}
    private String Type;public  String getType(){return Type;}
    private String UserCount;public String getUserCount(){return UserCount;}
    private String IsChecked;public String getIsChecked(){return IsChecked;}
    private String NickName;
    private String Avatar;public String getAvatar(){return Avatar;}

}
