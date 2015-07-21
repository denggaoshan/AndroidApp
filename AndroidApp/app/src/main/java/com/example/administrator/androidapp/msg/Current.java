package com.example.administrator.androidapp.msg;

import org.json.JSONObject;

/**
 * Created by Administrator on 2015/7/20.
 * 专门用来存放当前的所有东西
 */
public class Current {

    private static User currentUser;
    private static User otherUser; //存放用户查看别人信息时候的那个人
    private static MyActivity currentActivity;
    private static Inform currentInform;
    private static MyMessage currentMyMessage;

    private static String picPath;

    public static String getCurrentPicture(){return picPath;}
    public static String setCurrentPicture(String picturePath){return picPath=picturePath;}

    public static User getOtherUser() {
        return otherUser;
    }
    public static void setOtherUser(User other){
        otherUser = other;
    }

    public static User getCurrentUser(){return currentUser;}
    public static void setCurrentUser(User user){currentUser = user;}


    public static MyActivity getCurrentActivity(){
        return currentActivity;
    }
    public static void setCurrentActivity(MyActivity activity){
        currentActivity = activity;
    }


    public static Inform getCurrentInform(){return currentInform;}
    public static void setCurrentInform(Inform msg){currentInform=msg;}

    public static MyMessage getCurrentMyMessage()
    {
        return currentMyMessage;
    }
    public static void setCurrentMyMessage(MyMessage msg)
    {
        currentMyMessage = msg;
    }

}
