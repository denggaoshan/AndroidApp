package com.example.administrator.androidapp;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 2015/7/1.
 */
public class Activity {

    private String ActivityID;
    private String UserID;
    private String Title;
    private String Content;
    private String StartTime;
    private String EndTime;
    private String Place;
    private String Type;
    private String UserCount;
    private String IsChecked;
    private String NickName;
    private String Avatar;

    public Activity(JSONObject jsonObject)
    {
        try
        {
            ActivityID = jsonObject.getString("ActivityID");
            UserID = jsonObject.getString("UserID");
            Title = jsonObject.getString("Title");
            Content = jsonObject.getString("Content");
            StartTime = jsonObject.getString("StartTime");
            EndTime = jsonObject.getString("EndTime");
            Place = jsonObject.getString("Place");
            Type = jsonObject.getString("Type");
            UserCount = jsonObject.getString("UserCount");
            IsChecked = jsonObject.getString("IsChecked");
            NickName = jsonObject.getString("NickName");
            Avatar = jsonObject.getString("Avatar");
        }
        catch (JSONException E)
        {

        }
    }
}
