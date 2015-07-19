package com.example.administrator.androidapp.msg;

import android.widget.TextView;

import com.example.administrator.androidapp.msg.ToolClass;
import com.example.administrator.androidapp.tool.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

/**
 * Created by admin on 2015/7/3.
 */
public class User {

    private String UserID;
    private String Account;
    private String Avatar; public String getAvatar() {return Avatar; }
    private String NickName;public String getNickName(){return NickName;}
    private String Sex;public String getSex(){return Sex;}
    private String Age;public String getAge(){return Age;}
    private String Constellation;
    private String Profession;
    private String LivePlace;
    private String Description;
    private String Phone;
    private String Mailbox;
    private String IsCheckedMailbox;
    private String QQ;
    private String WeiBo;
    private String RoleID;
    private String RegisterTime;

    private String good;
    private String isgood;

    private static User currentUser;
    public static User getCurrentUser(){return currentUser;}
    public static void setCurrentUser(User user){currentUser = user;}


    public String getUserType(){
        if(UserID.equals("00000001")){
            return "游客";
        }else if(IsCheckedMailbox.equals("0")){
            return "普通用户";
        }else {
            return "认证用户";
        }
    }

    public boolean isLauncher(User[] allUsers){
        if(allUsers!=null){
            if(UserID.equals(allUsers[0].getUserID())){
                return true;
            }
        }
        return false;
    }

    //给别人点赞
    public String createGood(String toid){
        if(isgood=="0"){
            String ret = ToolClass.addOrDeleteGood(this.getUserID(), toid, "1");
            isgood="1";
            return ret;
        }else{
            return "已经赞过了";
        }
    }

    public static User createUser(JSONObject jsonObject)
    {
        try {
            String id = jsonObject.getString("UserID");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        User temp = new User();
        String[] tmp = {"UserID","Account","Avatar","NickName","Sex","Age",
                "Constellation","Profession","LivePlace","Description","Phone",
                "Mailbox","IsCheckedMailbox","QQ","WeiBo","RoleID","RegisterTime"};

        if (jsonObject != null) {
            for(String val :tmp){
                temp.setProperty(val, jsonObject);
            }
        }

        return temp;
    }

    public String getUserID()
    {
        return UserID;
    }

    public String getAccount()
    {
        return Account;
    }


    //获得用户属性fieldName的值
    public String getFieldContent(String fieldName) {
        String ret = null;
        try {
            Field  fs= this.getClass().getDeclaredField(fieldName);
            fs.setAccessible(true);
            ret = (String)fs.get(this);
            if(ret != null && !ret.equals("null")){
                //转换性别
                if(fieldName.equals("Sex")){
                    ret = Utils.changeSex(ret);
                }
            }else{
                ret = "";
            }

        } catch (NoSuchFieldException e) {
            return "";
        }  catch (IllegalAccessException e) {
            return  "";
        }

        return ret;
    }

    public void setGood(JSONObject jsonObject)
    {
        try
        {
            good = jsonObject.getString("good");
        }
        catch (JSONException e) {
            good = "";
        }
    }

    public void setIsgood(JSONObject jsonObject)
    {
        try
        {
            isgood = jsonObject.getString("isgood");
        }
        catch (JSONException e) {
            isgood = "";
        }
    }


    private void setProperty(String data,JSONObject userMsg){
        Field fs = null;
        try
        {
            fs= this.getClass().getDeclaredField(data);
            fs.setAccessible(true);
            String value = userMsg.getString(data);
            fs.set(this,value);
        }
        catch (NoSuchFieldException e)//没找到对应属性
        {
            return;
        }
        catch (JSONException e)//没找到JSON
        {
            try {
                fs.set(this,"");
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
        }
        catch (IllegalAccessException e)//不允许设置属性
        {
            e.printStackTrace();
        }
    }

    private static User otherUser; //存放用户查看别人信息时候的那个人
    public static User getOtherUser() {
        return otherUser;
    }
    public static void setOtherUser(User other){
        otherUser = other;
    }
}
