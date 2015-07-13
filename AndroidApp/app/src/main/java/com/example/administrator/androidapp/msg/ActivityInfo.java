package com.example.administrator.androidapp.msg;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 2015/7/6.
 */
public class ActivityInfo {

    private String mess;
    private Comment[] comments;public Comment[] getComments(){return comments;}
    private Photo[] photos;public Photo[] getPhoto(){return photos;}

    public static ActivityInfo createActivityInfo(String jsonString)
    {
        ActivityInfo temp = new ActivityInfo();
        JSONObject allMsg;
        try {
            allMsg = new JSONObject(jsonString);
        }
        catch (JSONException e)
        {
            allMsg = null;
        }

        temp.getMess(allMsg);
        temp.getPhotos(allMsg);
        temp.getComments(allMsg);

        return temp;
    }

    private void getMess(JSONObject jsonObject)
    {
        if (jsonObject != null)
        {
            try {
                mess = jsonObject.getString("mess");
            }
            catch (JSONException e) {
                mess = "";
            }
        }
        else
            mess = "";
    }

    private void getPhotos(JSONObject jsonObject)
    {
        JSONArray jsonArray;
        try {
            jsonArray = jsonObject.getJSONArray("photo");
        }
        catch (JSONException e)
        {
            jsonArray = null;
        }
        if (jsonArray != null && jsonArray.length() != 0)
        {
            photos = new Photo[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++)
            {
                try {
                    photos[i] = Photo.createPhoto(jsonArray.getJSONObject(i));
                }
                catch (JSONException e)
                {
                    photos[i] = null;
                }
            }
        }
        else
            photos = null;
    }

    private void getComments(JSONObject jsonObject)
    {
        JSONArray jsonArray;
        try {
            jsonArray = jsonObject.getJSONArray("comments");
        }
        catch (JSONException e)
        {
            jsonArray = null;
        }
        if (jsonArray != null && jsonArray.length() != 0)
        {
            comments = new Comment[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++)
            {
                try {
                    comments[i] = Comment.createComment(jsonArray.getJSONObject(i));
                }
                catch (JSONException e)
                {
                    comments[i] = null;
                }
            }
        }
        else
            comments = null;
    }
}
