package com.example.administrator.androidapp.core;

import com.example.administrator.androidapp.core.Comment;
import com.example.administrator.androidapp.core.Photo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 2015/7/2.
 */
public class ActivityInfo {

    private String mess;
    private Comment[] comments;
    private Photo[] photos;

    public ActivityInfo(String jsonString)
    {
        JSONObject allMsg;
        try
        {
            allMsg = new JSONObject(jsonString);
        }
        catch (JSONException e)
        {
            allMsg = null;
            return;
        }
        try
        {
            mess = allMsg.getString("mess");
        }
        catch (JSONException e)
        {
            mess = null;
            return ;
        }

        JSONArray jsonArray_comments;
        try
        {
            jsonArray_comments = allMsg.getJSONArray("comment");
            if (jsonArray_comments.length() != 0)
                comments = new Comment[jsonArray_comments.length()];
            else
                comments = null;
        }
        catch (JSONException E) {
            jsonArray_comments = null;
            comments = null;
        }
        if (comments != null)
        {
            for (int i = 0; i < jsonArray_comments.length(); i++)
            {
                try
                {
                    comments[i] = new Comment(jsonArray_comments.getJSONObject(i));
                }
                catch (JSONException e)
                {
                    comments[i] = null;
                }
            }
        }

        JSONArray jsonArray_photo;
        try {
            jsonArray_photo = allMsg.getJSONArray("photo");
            if (jsonArray_photo.length() != 0)
            {
                photos = new Photo[jsonArray_photo.length()];
            }
            else
                photos = null;
        }
        catch (JSONException E)
        {
            jsonArray_photo = null;
            photos = null;
        }
        if (photos != null)
        {
            for (int i = 0; i < jsonArray_photo.length(); i++)
            {
                try
                {
                    photos[i] = new Photo(jsonArray_photo.getJSONObject(i));
                }
                catch (JSONException e)
                {
                    photos[i] = null;
                }
            }
        }

    }
}

