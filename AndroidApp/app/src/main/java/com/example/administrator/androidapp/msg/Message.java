package com.example.administrator.androidapp.msg;

import com.example.administrator.androidapp.msg.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 2015/7/3.
 */
public class Message {

    private static String mess;
    private static User user;
    private static User[] users;
    private static Activity activity;
    private static Activity[] activities;
    private static Message currentMessage;
    private static String tempJsonString;
    public String getJsonString()
    {
        return tempJsonString;
    }

    public static void setCurrentMessage(Message msg)
    {
        currentMessage = msg;
    }

    public static Message getCurrentMessage()
    {
        return currentMessage;
    }
    public static Message createMessage(String jsonString, int userCount, int activityCount)
    {
        tempJsonString = jsonString;
        Message temp = new Message();

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
            activity = Activity.createActivity(jsonObject.getJSONObject("activity"));
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
            activities = new Activity[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++)
            {
                try
                {
                    activities[i] = Activity.createActivity(jsonArray.getJSONObject(i));
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
    public Activity getActivity() { return activity; }
    public Activity[] getActivities() { return activities; }
    public User getUser() { return user; }
    public User[] getUsers() { return users; }

}
