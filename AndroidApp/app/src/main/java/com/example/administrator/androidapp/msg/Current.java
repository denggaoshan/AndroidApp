package com.example.administrator.androidapp.msg;


/**
 * Created by Administrator on 2015/7/20.
 * 专门用来存放在不同界面时传递的对象
 */
public class Current {

    private static User currentUser;
    private static User otherUser; //存放用户查看别人信息时候的那个人
    private static MyActivity currentActivity;
    private static Inform currentInform;

    private static String picPath;

    public static String getPicturePath(){return picPath;}
    public static String setPicturePath(String picturePath){return picPath=picturePath;}

    public static User getOtherUser() {
        return otherUser;
    }
    public static void setOtherUser(User other){
        otherUser = other;
    }

    public static User getUser(){return currentUser;}
    public static void setUser(User user){currentUser = user;}


    public static MyActivity getActivity(){
        return currentActivity;
    }
    public static void setActivity(MyActivity activity){
        currentActivity = activity;
    }


    public static Inform getInform(){return currentInform;}
    public static void setInform(Inform msg){currentInform=msg;}


}
