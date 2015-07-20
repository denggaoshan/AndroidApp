package com.example.administrator.androidapp.msg;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by admin on 2015/7/3.
 */
public class MyMessage {

    /**
     * 解释 allData存储消息中的信息以及对象
     */
    HashMap<String,Object> allData= new HashMap<>();

    private static String tempJsonString;public String getJsonString()
    {
        return tempJsonString;
    }

    public String getMess() {
        if(allData.containsKey("mess")){
            return (String)allData.get("mess");
        }else{
            return null;
        }
    }

    public MyActivity getActivity() {
        if(allData.containsKey("activity")){
            return (MyActivity)allData.get("activity");
        }else{
            return null;
        }
    }

    public MyActivity[] getActivities() {
        if(allData.containsKey("activities")){
            return (MyActivity[])allData.get("activities");
        }else{
            return null;
        }
    }

    public User getUser() {
        if(allData.containsKey("user")){
            return (User)allData.get("user");
        }else{
            return null;
        }
    }

    public User[] getUsers() {
        if(allData.containsKey("users")){
            return (User[])allData.get("users");
        }else{
            return null;
        }}


    public static MyMessage createMessage(String jsonString)
    {
        tempJsonString = jsonString;
        MyMessage temp = new MyMessage();
        JSONObject allMsg = temp.createJSONObjectByString(jsonString);
        if (allMsg != null) {
            temp.setMess(allMsg);
            temp.setUser(allMsg);
            temp.setUsers(allMsg);
            temp.setActivity(allMsg);
            temp.setActivities(allMsg);
        }
        return temp;
    }

    private JSONObject createJSONObjectByString(String jsonString)
    {
        JSONObject allMsg;
        if (jsonString == null)
            return null;
        try {
            allMsg = new JSONObject(jsonString);
        }
        catch (JSONException e)
        {
            allMsg = null;
        }
        return allMsg;
    }

    private void setMess(JSONObject jsonObject)
    {
        try {
            String mess = (String)jsonObject.getString("mess");
            allData.put("mess",mess);
        } catch (JSONException e) {

        }
    }

    private void setUser(JSONObject jsonObject)
    {
        try {
            User user = (User) User.create(User.class, jsonObject.getJSONObject("user"));
            allData.put("user",user);
        } catch (JSONException e) {
        }
    }

    private void setUsers(JSONObject jsonObject) {
        try{
            JSONArray jsonArray = jsonObject.getJSONArray("users");
            if (jsonArray != null && jsonArray.length() != 0)
            {
                User[] users = new User[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    try
                    {
                        users[i] = (User) User.create(User.class, jsonArray.getJSONObject(i));
                    }
                    catch (JSONException E)
                    {
                        users[i] = null;
                    }
                }
                allData.put("users",users);
            }
        }catch (JSONException e) {
        }
    }

    private void setActivity(JSONObject jsonObject) {
        try {
            MyActivity activity = (MyActivity) BaseType.create(MyActivity.class,jsonObject.getJSONObject("activity"));
            allData.put("activity",activity);
        }
        catch (JSONException e)
        {
        }
    }

    private void setActivities(JSONObject jsonObject) {
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("activities");
            if (jsonArray != null && jsonArray.length() != 0)
            {
                MyActivity[] activities = new MyActivity[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++)
                {
                   try {
                    activities[i] = (MyActivity) MyActivity.create(MyActivity.class,jsonArray.getJSONObject(i));
                   } catch (JSONException E) {
                    activities[i] = null;
                   }
                }
                allData.put("activities",activities);
            }
        } catch (JSONException E){
        }
    }




}
