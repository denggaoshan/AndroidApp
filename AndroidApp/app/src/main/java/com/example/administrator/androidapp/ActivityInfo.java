package com.example.administrator.androidapp;

import android.app.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

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
                    photos[i] = new Photo(jsonArray_photo.getJSONObject(i))
                }
                catch (JSONException e)
                {
                    photos[i] = null;
                }
            }
        }

    }
}

class Comment
{
    private String CommentID;
    private String UserID;
    private String ActivityID;
    private String Content;
    private String Time;
    private String NickName;
    private String Avatar;

    public Comment(JSONObject jsonObject)
    {
        Field[] fd = this.getClass().getDeclaredFields();
        for(Field f : fd){
            f.setAccessible(true);
            try
            {
                String temp = jsonObject.getString(f.getName());
                f.set(this, temp);
            }
            catch (JSONException e) {
                try
                {
                    f.set(this, null);
                }
                catch (IllegalAccessException eee)
                {

                }
            }
            catch (IllegalAccessException ee)
            {

            }
        }
    }
}

class Photo
{
    private String PhotoID;
    private String UserID;
    private String ActivityID;
    private String Address;
    private String Title;
    private String Describe;
    private String Level;
    private String Time;
    private String NickName;
    private String Avatar;

    public Photo(JSONObject jsonObject)
    {
        Field[] fd = this.getClass().getDeclaredFields();
        for(Field f : fd){
            f.setAccessible(true);
            try
            {
                String temp = jsonObject.getString(f.getName());
                f.set(this, temp);
            }
            catch (JSONException e) {
                try
                {
                    f.set(this, null);
                }
                catch (IllegalAccessException eee)
                {

                }
            }
            catch (IllegalAccessException ee)
            {

            }
        }
    }
}
