package com.example.administrator.androidapp.msg;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2015/7/3.
 */
public class Comment
{
    private String CommentID;
    private String UserID;
    private String ActivityID;
    private String Content;public String getContent(){return Content;}
    private String Time;public String getTime(){return Time;}
    private String NickName;public String getNickName(){return NickName;}
    private String Avatar;

    public static Comment createComment(JSONObject jsonObject)
    {
        Comment temp = new Comment();
        String[] tmp = {"CommentID", "UserID", "ActivityID", "Content",
                "Time", "NickName", "Avatar"};

        if (jsonObject != null) {
            for(String val :tmp){
                temp.setProperty(val, jsonObject);
            }
        }

        return temp;
    }

    private void setProperty(String data,JSONObject userMsg){
        Field fs = null;
        try
        {
            fs= this.getClass().getDeclaredField(data);
            fs.setAccessible(true);
            String value = userMsg.getString(data);
            fs.set(this,value);
        }
        catch (NoSuchFieldException e)//没找到对应属性
        {
            return;
        }
        catch (JSONException e)//没找到JSON
        {
            try {
                fs.set(this,"");
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
        }
        catch (IllegalAccessException e)//不允许设置属性
        {
            e.printStackTrace();
        }
    }
}