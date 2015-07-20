package com.example.administrator.androidapp.msg;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2015/7/3.
 * 评论
 */
public class Comment extends BaseType
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
        temp.loadAllPropertyFromJSON(jsonObject);
        return temp;
    }

}