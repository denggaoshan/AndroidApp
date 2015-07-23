package com.example.administrator.androidapp.msg;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.androidapp.tool.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

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

    private static HashMap<String, ArrayList<Inform>> systemInformsCache = new HashMap<>();
    private static HashMap<String, ArrayList<Inform>> activityInformsCache = new HashMap<>();
    private static HashMap<String, ArrayList<Inform>> privatedInformsCache = new HashMap<>();
    private static HashMap<String,User[]> activityMember = new HashMap<>();
    private static HashMap<String,Comment[]> activityComments = new HashMap<>();
    private static HashMap<String,Photo[]> activityPhotos = new HashMap<>();
    private static Map<String, List<MyActivity>> activitiesCache = new HashMap<String, List<MyActivity>>();
    private static HashMap<String,User> userCache = new HashMap<>();
    private static HashMap<String,UserAndExplain[]> activityRequests = new HashMap<>();//活动申请
    private static HashMap<String,MyActivity[]> launchedActivity = new HashMap<>();//用户发起的互动
    private static HashMap<String,MyActivity[]> participatedActivity = new HashMap<>();//用户参与的活动
    private static HashMap<String,MyActivity[]> applicatedActivity = new HashMap<>();//


    public static void updateAllActivity(){
        launchedActivity=new HashMap<>();
        participatedActivity = new HashMap<>();
        applicatedActivity = new HashMap<>();
    }

    public static MyActivity[] getLaunchedActivity(String id) {
        if(launchedActivity.containsKey(id)){
            return launchedActivity.get(id);
        }else{
            MyMessage msg = ToolClass.getLaunchedActivity(id);
            MyActivity[] ret = msg.getActivities();
            if(ret!=null){
                launchedActivity.put(id,ret);
                return ret;
            }else{
                launchedActivity.put(id,new MyActivity[0]);
            }
        }
        return null;
    }

    public static MyActivity[] getParticipatedActivity(String id) {
        if(participatedActivity.containsKey(id)){
            return participatedActivity.get(id);
        }else{
            MyMessage msg = ToolClass.getParticipatedActivity(id);
            MyActivity[] ret = msg.getActivities();
            if(ret!=null){
                participatedActivity.put(id,ret);
                return ret;
            }else{
                participatedActivity.put(id,null);
            }
        }
        return null;
    }

    public static MyActivity[] getApplicatedActivity(String id) {
        if(applicatedActivity.containsKey(id)){
            return applicatedActivity.get(id);
        }else{
            MyMessage msg = ToolClass.getApplicatedActivity(id);
            MyActivity[] ret = msg.getActivities();
            if(ret!=null){
                applicatedActivity.put(id,ret);
                return ret;
            }else{
                applicatedActivity.put(id,null);
            }
        }
        return null;

    }



    //先更新缓存再获得
    public static UserAndExplain[] updateLoadRequest(MyActivity currentActivity) {
        activityRequests.remove(currentActivity.getActivityID());
        return getUserAndExplains(currentActivity);
    }

    public static UserAndExplain[] getUserAndExplains(MyActivity currentActivity){
        if(activityRequests.containsKey(currentActivity.getActivityID())){
            return activityRequests.get(currentActivity.getActivityID());
        }else{
            MyMessage msg = ToolClass.getApplication(Current.getUser().getUserID(), currentActivity.getActivityID());
            if(msg!=null){
                UserAndExplain[] ret = msg.getUserAndExplains();
                if(ret!=null){
                    activityRequests.put(currentActivity.getActivityID(),ret);
                    return ret;
                }
            }else{
                return null;
            }
        }
        return null;
    }

    //更新所有的Cache
    public static void updateAllCache() {
       systemInformsCache = new HashMap<>();
       activityInformsCache = new HashMap<>();
        privatedInformsCache = new HashMap<>();
       activityMember = new HashMap<>();
       activityComments = new HashMap<>();
      activityPhotos = new HashMap<>();
      activitiesCache = new HashMap<>();
      userCache = new HashMap<>();
        activityRequests = new HashMap<>();//活动申请
       launchedActivity = new HashMap<>();//用户发起的互动
        participatedActivity = new HashMap<>();//用户参与的活动
        applicatedActivity = new HashMap<>();//
    }



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


    //更新
    public static void updateAllMessages(String id) {
        loadAllMessages(id);
    }

    //重新加载用户为id的所有消息
    private static void loadAllMessages(String id) {

        User user = Cache.getUserById(id);

        if (user != null) {
            MyMessage msg = ToolClass.getInform(user.getUserID());

            if (msg != null) {

                Inform[] allInforms = msg.getInforms();


                ArrayList<Inform> systemInforms = new ArrayList<>();
                ArrayList<Inform> activityInforms = new ArrayList<>();
                ArrayList<Inform> privatedInforms = new ArrayList<>();

                if(allInforms!=null){
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


    //主页面的活动缓存
    public static Map<String, List<MyActivity>> loadActivitiesMap(String type) {

        Map<String, List<MyActivity>> allActivitiesByDayOrder = new HashMap<String, List<MyActivity>>();

        if (activitiesCache != null && activitiesCache.size()!=0) {
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
            MyMessage msg = ToolClass.getActivityList("" + 1, "" + -1, "" + 1);
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


    public static User[] updateLoadAllUsers(String id){
        MyMessage msg = ToolClass.getParticipation(Current.getUser().getUserID(), id);
        User[] allUsers = msg.getUsers();
        activityMember.put(id,allUsers);
        return allUsers;
    }


    public static void updateMembers(String id) {
        MyMessage msg = ToolClass.getParticipation(Current.getUser().getUserID(), id);
        User[] allUsers = msg.getUsers();
        activityMember.put(id,allUsers);
    }


    //活动的成员
    public static User[] loadAllUsers(String id){
        if(activityMember.containsKey(id)){
            return activityMember.get(id);
        }else{
            MyMessage msg = ToolClass.getParticipation(Current.getUser().getUserID(), id);
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
            MyMessage info = ToolClass.getActivityInfo(Current.getUser().getUserID(), id);

            Comment[] allComments = info.getComments();
            activityComments.put(id,allComments);
            return allComments;
        }
    }


    public static void updateAllPhotos(String id){
        MyMessage info = ToolClass.getActivityInfo(Current.getUser().getUserID(), id);
        Photo[] allPhotos = info.getPhoto();
        activityPhotos.put(id,allPhotos);
    }

    //活动相册
    public static Photo[] loadAllPhotos(String id){
        if(activityPhotos.containsKey(id)){
            return activityPhotos.get(id);
        }else{
            MyMessage info = ToolClass.getActivityInfo(Current.getUser().getUserID(), id);
            Photo[] allPhotos = info.getPhoto();
            activityPhotos.put(id,allPhotos);
            return allPhotos;
        }
    }


    //根据ID获得用户
    public static User getUserById(String id){
        if(userCache.containsKey(id)){
            return userCache.get(id);
        }else{
            MyMessage msg = ToolClass.getUserInfo(Current.getUser().getUserID(), id);
            User user = msg.getUser();
            userCache.put(id,user);
            return user;
        }
    }

    public static MyActivity updateActivityDetail(String userId,String activityId) {
        MyMessage msg = ToolClass.getActivityInfo(userId,activityId);
        if(msg!=null){
            MyActivity ret = msg.getActivity();
            Current.setActivity(ret);
            return ret;
        }
        return null;
    }


    private static class AnotherTask extends AsyncTask<String, Void, String> {
        private ActionBarActivity parent;
        private String url;
        private int id;
        private View parentView;

        public AnotherTask(ActionBarActivity parent, String url, int id) {
            this.parent =  parent;
            this.url =  url;
            this.id =  id;
        }

        public AnotherTask(View parent, String url, int id) {
            this.parentView =  parent;
            this.url =  url;
            this.id =  id;
        }

        @Override
        protected void onPostExecute(String result) {
            //对UI组件的更新操作
            if(parent!=null){
                putImgs(parent,id,url);
            }else{
                putImgs(parentView,id,url);
            }

        }
        @Override
        protected String doInBackground(String... params) {
            //耗时的操作
            return params[0];
        }
    }

    private static void putImgs(ActionBarActivity parent,int id,String url){
        try{
            Bitmap bm = Cache.getBitmap(url);
            if (bm != null)
            {
                if(parent != null){
                    ImageView imageView = ((ImageView) parent.findViewById(id));
                    if(imageView!=null){
                        imageView.setImageBitmap(bm);
                    }else {
                        Utils.debugMessage(parent,"imageView没找到");
                    }
                }
            }
        }catch (Exception e){
            Utils.debugMessage(parent,"Img bug " +e.getCause());
        }
    }

    private static void putImgs(View parent,int id,String url){
        try{
            Bitmap bm = Cache.getBitmap(url);
            if (bm != null)
            {
                if(parent != null){
                    ImageView imageView = ((ImageView) parent.findViewById(id));
                    if(imageView!=null){
                        imageView.setImageBitmap(bm);
                    }else {
                    }
                }
            }
        }catch (Exception e){
        }
    }

    /**
     * 往parent的id中装载图片 url
     * @param parent
     * @param url
     * @param id
     */
    public static void loadImg(final ActionBarActivity parent, final String url, final int id) {
        if(url==null || url.equals("null")){
            return;
        }
        try {
            new Thread(){
                public void run(){
                    new AnotherTask(parent,url,id).execute("none");
                }
            }.start();
        }catch (Exception e){
            Utils.debugMessage(parent,"头像BUG url="+url);
        }
    }

    public static void loadImg(final View parent, final String url, final int id) {
        if(url==null || url.equals("null")){
            return;
        }
        try {
            new Thread(){
                public void run(){
                    new AnotherTask(parent,url,id).execute("none");
                }
            }.start();
        }catch (Exception e){
        }
    }
}
