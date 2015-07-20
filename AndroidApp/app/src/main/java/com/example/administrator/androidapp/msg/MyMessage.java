package com.example.administrator.androidapp.msg;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 2015/7/3.
 */
public class MyMessage {

    private String mess;
    private User user;
    private User[] users;
    private MyActivity activity;
    private MyActivity[] activities;
    private static MyMessage currentMyMessage;
    private static String tempJsonString;
    public String getJsonString()
    {
        return tempJsonString;
    }

    public static void setCurrentMyMessage(MyMessage msg)
    {
        currentMyMessage = msg;
    }

    public static MyMessage getCurrentMyMessage()
    {
        return currentMyMessage;
    }


    public static MyMessage createMessage(String jsonString, int userCount, int activityCount)
    {
        tempJsonString = jsonString;
        MyMessage temp = new MyMessage();

        JSONObject allMsg = temp.getAllMsg(jsonString);
        if (allMsg != null)
            temp.setMess(allMsg);

        if (allMsg != null && userCount > 0)
        {
            if (userCount > 1)
                temp.setUsers(allMsg);
            else
                temp.setUser(allMsg);
        }
        if (allMsg != null && activityCount > 0)
        {
            if (activityCount > 1)
                temp.setActivities(allMsg);
            else
                temp.setActivity(allMsg);
        }

        return temp;
    }

    private JSONObject getAllMsg(String jsonString)
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
        try
        {
            mess = jsonObject.getString("mess");
        }
        catch (JSONException e)
        {
            mess = "";
        }
    }




    private void setUser(JSONObject jsonObject)
    {
        try {
            user = User.createUser(jsonObject.getJSONObject("user"));
            user.setGood(jsonObject);
            user.setIsgood(jsonObject);
        }
        catch (JSONException e)
        {
            user = null;
        }
    }

    private void setUsers(JSONObject jsonObject) {
        JSONArray jsonArray;
        try {
            jsonArray = jsonObject.getJSONArray("users");
        }
        catch (JSONException E)
        {
            jsonArray = null;
        }

        if (jsonArray != null && jsonArray.length() != 0)
        {
            users = new User[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++)
            {
                try
                {
                    users[i] = User.createUser(jsonArray.getJSONObject(i));
                    users[i].setGood(jsonObject);
                    users[i].setIsgood(jsonObject);
                }
                catch (JSONException E)
                {
                    users[i] = null;
                }

            }
        }
        else
        {
            users = null;
        }
    }

    private void setActivity(JSONObject jsonObject) {
        try {
            activity = MyActivity.createActivity(jsonObject.getJSONObject("activity"));
        }
        catch (JSONException e)
        {
            activity = null;
        }
    }

    private void setActivities(JSONObject jsonObject) {
        JSONArray jsonArray;
        try {
            jsonArray = jsonObject.getJSONArray("activities");
        }
        catch (JSONException E)
        {
            jsonArray = null;
        }

        if (jsonArray != null && jsonArray.length() != 0)
        {
            activities = new MyActivity[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++)
            {
                try
                {
                    activities[i] = MyActivity.createActivity(jsonArray.getJSONObject(i));
                }
                catch (JSONException E)
                {
                    activities[i] = null;
                }

            }
        }
        else
        {
            activities = null;
        }
    }


    public String getMess() { return mess; }
    public MyActivity getActivity() { return activity; }
    public MyActivity[] getActivities() { return activities; }

    public User getUser() {
        return user;
    }

    public User[] getUsers() { return users; }


}
