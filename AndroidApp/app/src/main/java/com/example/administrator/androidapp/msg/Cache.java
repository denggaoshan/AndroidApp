package com.example.administrator.androidapp.msg;

import android.graphics.Bitmap;

import com.example.administrator.androidapp.tool.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Created by admin on 2015/7/15.
 * 为了提高速度设置的缓存类
 * 在程序空闲的时候，维护一个优先队列，通过空余的线程不断从服务器下载更新有可能用到的各种资料
 * */

public class Cache {
    private static Bitmap userAvater;

    public static void setUserAvater(Bitmap bm) {
        userAvater = bm;
    }

    public static Bitmap getUserAvater() {
        return userAvater;
    }


    /**
     * ***********************   存储消息的缓存  ************************
     */
    private static HashMap<String, ArrayList<Inform>> systemInformsCache = new HashMap<>();
    private static HashMap<String, ArrayList<Inform>> activityInformsCache = new HashMap<>();
    private static HashMap<String, ArrayList<Inform>> privatedInformsCache = new HashMap<>();


    /*获得当前用户的系统消息*/
    public static ArrayList<Inform> getSystemInforms(String id) {
        if (systemInformsCache.containsKey(id)) {
            return systemInformsCache.get(id);
        } else {
            loadAllMessages(id);
            return systemInformsCache.get(id);
        }
    }

    /*获得当前用户的活动消息*/
    public static ArrayList<Inform> getActivityInforms(String id) {
        if (activityInformsCache.containsKey(id)) {
            return activityInformsCache.get(id);
        } else {
            loadAllMessages(id);
            return activityInformsCache.get(id);
        }
    }

    /*获得当前用户的私信*/
    public static ArrayList<Inform> getPrivatedInforms(String id) {
        if (privatedInformsCache.containsKey(id)) {
            return privatedInformsCache.get(id);
        } else {
            loadAllMessages(id);
            return privatedInformsCache.get(id);
        }
    }

    //重新加载用户为id的所有消息
    private static void loadAllMessages(String id) {

        User user = Cache.getUserById(id);

        if (user != null) {
            InformArray informsArr = ToolClass.getInform(user.getUserID());
            ;

            if (informsArr != null) {

                Inform[] allInforms = informsArr.getInforms();

                ArrayList<Inform> systemInforms = new ArrayList<>();
                ArrayList<Inform> activityInforms = new ArrayList<>();
                ArrayList<Inform> privatedInforms = new ArrayList<>();

                for (Inform inform : allInforms) {
                    if (inform.getType().equals("0")) {
                        systemInforms.add(inform);
                    } else if (inform.getType().equals("1")) {
                        activityInforms.add(inform);
                    } else if (inform.getType().equals("2")) {
                        privatedInforms.add(inform);
                    } else {
                    }
                }

                /*存到缓存里*/
                systemInformsCache.put(id, systemInforms);
                activityInformsCache.put(id, activityInforms);
                privatedInformsCache.put(id, privatedInforms);
            }
        }
    }


    //存储图片
    private static HashMap<String, Bitmap> bitmapCache = new HashMap<>();

    //获得图片
    public static Bitmap getBitmap(String url) {
        if (bitmapCache.containsKey(url)) {
            return bitmapCache.get(url);
        } else {
            Bitmap bitmap = ToolClass.returnBitMap(url);
            if (bitmap != null) {
                setBitmap(url, bitmap);
            }
            return bitmap;
        }
    }

    private static void setBitmap(String url, Bitmap bm) {
        bitmapCache.put(url, bm);
    }



    /************** 活动的缓存 ***************/
    private static Map<String, List<MyActivity>> activitiesCache = null;

    //主页面的活动缓存
    public static Map<String, List<MyActivity>> loadActivitiesMap(String type) {

        Map<String, List<MyActivity>> allActivitiesByDayOrder = new HashMap<String, List<MyActivity>>();
        if (activitiesCache != null) {
            //缓存中有的话
            for (Map.Entry<String, List<MyActivity>> act : activitiesCache.entrySet()) {
                String time = act.getKey();
                String timekey = DateFactory.getFrontDate(time);

                List<MyActivity> allActivity =act.getValue();
                //对每个键值对中的所有活动
                for(MyActivity activity : allActivity){
                    if (type.equals("A") || type.equals(activity.getType())) {
                        //是该类型的项目的话
                        if (allActivitiesByDayOrder.containsKey(timekey)) {
                            List<MyActivity> tmp = allActivitiesByDayOrder.get(timekey);
                            tmp.add(activity);
                        } else {
                            List<MyActivity> tmp = new ArrayList<MyActivity>();
                            tmp.add(activity);
                            allActivitiesByDayOrder.put(timekey, tmp);
                        }
                    }
                }
            }
        } else {
            //下载所有活动
            Message msg = ToolClass.getActivityList("" + 1, "" + -1, "" + 1);
            MyActivity[] activities = msg.getActivities();
            if (activities != null) {
                for (MyActivity act : activities) {
                    String time = act.getStartTime();
                    String timekey = DateFactory.getFrontDate(time);
                    //是该类型的项目的话
                    if (allActivitiesByDayOrder.containsKey(timekey)) {
                        List<MyActivity> tmp = allActivitiesByDayOrder.get(timekey);
                        tmp.add(act);
                    } else {
                        List<MyActivity> tmp = new ArrayList<MyActivity>();
                        tmp.add(act);
                        allActivitiesByDayOrder.put(timekey, tmp);
                    }
                }
            }
            activitiesCache = allActivitiesByDayOrder;
        }
        return allActivitiesByDayOrder;
    }



    private static HashMap<String,User[]> activityMember = new HashMap<>();
    private static HashMap<String,Comment[]> activityComments = new HashMap<>();
    private static HashMap<String,Photo[]> activityPhotos = new HashMap<>();

    //活动的成员
    public static User[] loadAllUsers(String id){
        if(activityMember.containsKey(id)){
            return activityMember.get(id);
        }else{
            Message msg = ToolClass.getParticipation(User.getCurrentUser().getUserID(), id);
            User[] allUsers = msg.getUsers();
            activityMember.put(id,allUsers);
            return allUsers;
        }
    }

    //提示Cache先更新再获得评论
    public static Comment[] updateAndLoadComments(String id){
        activityComments.remove(id);
        return loadAllComments(id);
    }

    //活动的评论
    public static Comment[] loadAllComments(String id){
        if(activityComments.containsKey(id)){
            return activityComments.get(id);
        }else{
            ActivityInfo info = ToolClass.getActivityInfo(User.getCurrentUser().getUserID(), id);
            Comment[] allComments = info.getComments();
            activityComments.put(id,allComments);
            return allComments;
        }
    }

    //活动相册
    public static Photo[] loadAllPhotos(String id){
        if(activityPhotos.containsKey(id)){
            return activityPhotos.get(id);
        }else{
            ActivityInfo info = ToolClass.getActivityInfo(User.getCurrentUser().getUserID(), id);
            Photo[] allPhotos = info.getPhoto();
            activityPhotos.put(id,allPhotos);
            return allPhotos;
        }
    }



    /************** 用户信息缓存 **************/
    //存储用户信息
    private static HashMap<String,User> userCache = new HashMap<>();


    //根据ID获得用户
    public static User getUserById(String id){
        if(userCache.containsKey(id)){
            return userCache.get(id);
        }else{
            Message msg = ToolClass.getUserInfo(User.getCurrentUser().getUserID(), id);
            User user = msg.getUser();
            userCache.put(id,user);
            return user;
        }
    }

    //存储活动信息
    private static HashMap<String,MyActivity> activityCache = new HashMap<>();

    //根据ID获得用户
    public static MyActivity getActivityById(String id){
        if(activityCache.containsKey(id)){
            return activityCache.get(id);
        }else{
            return null;
        }
    }



    public static void beginDownLoad(){

    }

}
