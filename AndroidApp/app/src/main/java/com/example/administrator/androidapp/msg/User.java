package com.example.administrator.androidapp.msg;

import com.example.administrator.androidapp.tool.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

/**
 * Created by admin on 2015/7/3.
 */
public class User extends BaseType{

    private String UserID; public String getUserID()
    {
        return UserID;
    }
    private String Account; public String getAccount()
    {
        return Account;
    }
    private String Avatar;public String getAvatar() {return Avatar; }
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
    private String Good;
    private String IsGood;


    public static User createUser(JSONObject jsonObject){
        User temp = new User();
        temp.loadAllPropertyFromJSON(jsonObject);
        return temp;
    }

    //获得用户认证级别
    public String getUserType(){
        if(UserID.equals("00000001")){
            return "游客";
        }else if(IsCheckedMailbox.equals("0")){
            return "普通用户";
        }else {
            return "认证用户";
        }
    }

    //是否是活动的发起人
    public boolean isLauncher(User[] allUsers){
        if(allUsers!=null){
            if(UserID.equals(allUsers[0].getUserID())){
                return true;
            }
        }
        return false;
    }
    //给别人点赞
    public String createGood(User touser){
        if(touser.IsGood.equals("0")){
            String ret = ToolClass.addOrDeleteGood(this.getUserID(), touser.getUserID(), "1");
            touser.IsGood ="1";
            if(ret.equals("ok")){
                return ret;
            }else{
                return "失败";
            }
        }else{
            return "已经赞过了";
        }
    }


}
