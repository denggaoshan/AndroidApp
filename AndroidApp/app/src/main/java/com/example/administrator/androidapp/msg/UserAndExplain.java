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
        UserAndExplain temp = new UserAndExplain();
        if (jsonObject != null){
            temp.setUser(jsonObject);
            try {
                temp.expain = jsonObject.getString("explain");
            } catch (JSONException e){
                temp.expain = null;
            }
            try {
                temp.time = jsonObject.getString("time");
            } catch (JSONException e){
                temp.time = null;
            }
        }
        return temp;
    }

    private void setUser(JSONObject jsonObject){
        try {
            user = User.createUser(jsonObject.getJSONObject("user"));
        } catch (JSONException e){
            user = null;
        }
    }

}
