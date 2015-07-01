package com.example.administrator.androidapp;

import java.util.Map;

/**
 * Created by Administrator on 2015/7/1.
 */
public class User {

    public User(){
    }

    //用户的个人资料
    private String userId;
    private String account;
    private String avator;
    private String nickName;
    private String sex;
    private String age;
    private String constellation;
    private String profession;
    private String livePlace;
    private String description;
    private String phone;
    private String mailBox;
    private String isCheckedMailbox;
    private String qqNumber;
    private String weiBo;
    private String roleID;
    private String registerTime;


    //用户有关的活动
    private String activityID;

    public User(Map<String, Object> data){
        Map<String,String> result = (Map<String,String> )data.get("user");

    }
}
