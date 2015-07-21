package com.example.administrator.androidapp.msg;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 2015/7/14.
 */
public class UserAndExplain {

    private User user;public User getUser(){return user;}
    private String expain; public String getExpain(){ return expain; }
    private String time; public String getTime(){ return time; }

    public static UserAndExplain createUserAndExplain(JSONObject jsonObject){
        try {
            UserAndExplain temp = new UserAndExplain();
            temp.setUser(jsonObject);
            temp.expain = jsonObject.getString("explain");
            temp.time = jsonObject.getString("time");
            return temp;
        } catch (JSONException e) {
            return null;
        }
    }

    private void setUser(JSONObject jsonObject){
        try {
            user = (User) User.create(User.class, jsonObject.getJSONObject("user"));
        } catch (JSONException e){
            user = null;
        }
    }

}
