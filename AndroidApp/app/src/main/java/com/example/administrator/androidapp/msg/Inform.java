package com.example.administrator.androidapp.msg;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

/**
 * Created by admin on 2015/7/6.
 */
public class Inform extends BaseType{
    private String InformID;
    private String TargetID;
    private String Form;
    private String Time;public String getTime(){return Time;}
    private String Type;public String getType(){return Type;}
    private String Title;public String getTitle(){return Title;}
    private String Content;public String getContent(){return  Content;}
    private String IsRead;

    public static Inform createInform(JSONObject jsonObject)
    {
        Inform temp = new Inform();
        temp.loadAllPropertyFromJSON(jsonObject);
        return temp;
    }

    public User getUserSource(){
        return Cache.getUserById(Form);
    }

}
