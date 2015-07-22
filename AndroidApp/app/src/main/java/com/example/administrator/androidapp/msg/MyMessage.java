package com.example.administrator.androidapp.msg;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2015/7/3.
 */
public class MyMessage {

    /**
     * 解释 allData存储消息中的信息以及对象
     */
    protected HashMap<String,Object> allData= new HashMap<>();


    public static MyMessage createMessage(String jsonString)
    {
        tempJsonString = jsonString;
        MyMessage temp = new MyMessage();
        JSONObject allMsg = temp.createJSONObjectByString(jsonString);

        try{
            Iterator<String> val = allMsg.keys();
            while(val.hasNext()){
                String str = val.next();
                String value = allMsg.getString(str);
                temp.addToMessage(str, value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return temp;
    }


    private static HashMap allType = new HashMap (){
        {
            put("user", User.class);
            put("activity", MyActivity.class);
        }
    };

    private static HashMap allArrayType = new HashMap (){
        {
            put("users", User.class);
            put("activities", MyActivity.class);
            put("comments", Comment.class);
            put("photos", Photo.class);
            put("informs", Inform.class);
            put("usersandexplain", UserAndExplain.class);
        }
    };


    //根据key值添加相应的Message
    private void addToMessage(String str, String JsonString) {
        if(str.equals("mess")){
            setMess(JsonString);
        }else if(allType.containsKey(str)){
            setValue(str, (Class) allType.get(str),JsonString);
        }else if(allArrayType.containsKey(str)){
            Class type = (Class) allArrayType.get(str);
            setValues(str, type,JsonString);
        }else{
            //有BUG
        }
    }

    private void setMess(String  data)
    {
        allData.put("mess",data);
    }


    private void setValue(String key,Class type,String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            Object instance = BaseType.create(type, jsonObject);
            allData.put(key,instance);
        } catch (Exception e) {
        }
    }


    //塞入一个数组
    private void setValues(String str, Class type, String jsonString) {
        try{
            JSONArray jsonArray = new JSONArray(jsonString);
            if (jsonArray != null && jsonArray.length() != 0)
            {
                List<Object> data = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++)
                {
                    Object instance = BaseType.create(type,(JSONObject) jsonArray.get(i));
                    if(instance!=null){
                        data.add(instance);
                    }else if(type==UserAndExplain.class){
                        Object explain =  UserAndExplain.createUserAndExplain((JSONObject) jsonArray.get(i));
                        data.add(explain);
                    }

                }
                allData.put(str,data);
            }
        }catch (JSONException e) {

        }
    }


    private static String tempJsonString;public String getJsonString()
    {
        return tempJsonString;
    }

    public String getMess() {
        if(allData!=null){
            return (String)allData.get("mess");
        }else{
            return null;
        }
    }

    public MyActivity getActivity() {
        return (MyActivity)allData.get("activity");
    }


    public User getUser() {
        return (User)allData.get("user");
    }


    public MyActivity[] getActivities() {
        List<Object> data = (List<Object>) allData.get("activities");
        if(data!=null){
            MyActivity[] ret = new MyActivity[data.size()];
            for(int i=0;i<data.size();i++){
                ret[i] = (MyActivity)data.get(i);
            }
            return ret;
        }else{
            return null;
        }
    }

    public User[] getUsers() {
        List<Object> data = (List<Object>) allData.get("users");
        if(data!=null){
            User[] ret = new User[data.size()];
            for(int i=0;i<data.size();i++){
                ret[i] = (User)data.get(i);
            }
            return ret;
        }else {
            return null;
        }
    }

    public Comment[] getComments() {
        List<Object> data = (List<Object>) allData.get("comments");
        if(data!=null){
            Comment[] ret = new Comment[data.size()];
            for(int i=0;i<data.size();i++){
                ret[i] = (Comment)data.get(i);
            }
            return ret;
        }else{
            return null;
        }
    }

    public Photo[] getPhoto() {
        List<Object> data = (List<Object>) allData.get("photos");
        if(data!=null){
            Photo[] ret = new Photo[data.size()];
            for(int i=0;i<data.size();i++){
                ret[i] = (Photo)data.get(i);
            }
            return ret;
        }else{
            return null;
        }
    }

    public Inform[] getInforms() {
        List<Object> data = (List<Object>) allData.get("informs");
        if(data!=null){
            Inform[] ret = new Inform[data.size()];
            for(int i=0;i<data.size();i++){
                ret[i] = (Inform)data.get(i);
            }
            return ret;
        }else {
            return null;
        }
    }

    public UserAndExplain[] getUserAndExplains() {
        List<Object> data = (List<Object>) allData.get("usersandexplain");
        if(data!=null){
            UserAndExplain[] ret = new UserAndExplain[data.size()];
            for(int i=0;i<data.size();i++){
                ret[i] = (UserAndExplain)data.get(i);
            }
            return ret;
        }else{
            return null;
        }
    }


    private JSONObject createJSONObjectByString(String jsonString)
    {
        JSONObject allMsg;
        if (jsonString == null)
            return null;
        try {
            allMsg = new JSONObject(jsonString);
            return allMsg;
        }
        catch (JSONException e)
        {
            allMsg = null;
            return allMsg;
        }

    }

}
