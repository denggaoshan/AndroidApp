package com.example.administrator.androidapp.msg;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;

import com.example.administrator.androidapp.tool.Utils;

import java.util.ArrayList;
import java.util.HashMap;
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
    private static Map<String, List<MyActivity>> activitiesCache = null;
    private static HashMap<String,User> userCache = new HashMap<>();
    private static HashMap<String,MyActivity> activityCache = new HashMap<>();


    private static HashMap<String,UserAndExplain[]> activityRequests = new HashMap<>();//活动申请

    //先更新缓存再获得
    public static UserAndExplain[] updateLoadRequest(MyActivity currentActivity) {
        activityRequests.remove(currentActivity.getActivityID());
        return getUserAndExplains(currentActivity);
    }

    public static UserAndExplain[] getUserAndExplains(MyActivity currentActivity){
        if(activityRequests.containsKey(currentActivity.getActivityID())){
            return activityRequests.get(currentActivity.getActivityID());
        }else{
            UserAndExplainArray msg = ToolClass.getApplication(Current.getCurrentUser().getUserID(), currentActivity.getActivityID());
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
    private static void updateAllCache() {
        //更新用户的Cache
        for(Map.Entry<String,User> item:userCache.entrySet()){
            String userId = item.getKey();
            MyMessage msg = ToolClass.getUserInfo(Current.getCurrentUser().getUserID(), userId);
            User user = msg.getUser();
            if(user!=null){
                item.setValue(msg.getUser());
            }
        }

        //更新活动的Cache
         activitiesCache=null;
         loadActivitiesMap("A");
    }

    /*开一个线程专门用来在后台更新缓存数据*/
    public static void beginDownLoad(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                //不断更新各种缓存
                while (true){
                    i++;
                    updateAllCache();

                }
            }

        }).start();

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
        MyMessage msg = ToolClass.getParticipation(Current.getCurrentUser().getUserID(), id);
        User[] allUsers = msg.getUsers();
        activityMember.put(id,allUsers);
        return allUsers;
    }

    //活动的成员
    public static User[] loadAllUsers(String id){
        if(activityMember.containsKey(id)){
            return activityMember.get(id);
        }else{
            MyMessage msg = ToolClass.getParticipation(Current.getCurrentUser().getUserID(), id);
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
            ActivityInfo info = ToolClass.getActivityInfo(Current.getCurrentUser().getUserID(), id);
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
            ActivityInfo info = ToolClass.getActivityInfo(Current.getCurrentUser().getUserID(), id);
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
            MyMessage msg = ToolClass.getUserInfo(Current.getCurrentUser().getUserID(), id);
            User user = msg.getUser();
            userCache.put(id,user);
            return user;
        }
    }

    //根据ID获得用户
    public static MyActivity getActivityById(String id){
        if(activityCache.containsKey(id)){
            return activityCache.get(id);
        }else{
            return null;
        }
    }


    private static class AnotherTask extends AsyncTask<String, Void, String> {
        private ActionBarActivity parent;
        private String url;
        private int id;

        public AnotherTask(ActionBarActivity parent, String url, int id) {
            this.parent =  parent;
            this.url =  url;
            this.id =  id;
        }

        @Override
        protected void onPostExecute(String result) {
            //对UI组件的更新操作
            putImgs(parent,id,url);
        }
        @Override
        protected String doInBackground(String... params) {
            //耗时的操作
            return params[0];
        }
    }

    private static void putImgs(ActionBarActivity parent,int id,String url){
        Bitmap bm = Cache.getBitmap(url);
        if (bm != null)
        {
            ((ImageView) parent.findViewById(id)).setImageBitmap(bm);
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
}
