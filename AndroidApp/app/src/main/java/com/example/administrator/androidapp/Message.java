package com.example.administrator.androidapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

/**
 * Created by admin on 2015/7/2.
 */
public class Message {

    private String mess;
    private Msg[] informs;

    public Message(String jsonString)
    {
        try {
            JSONObject allMsg = new JSONObject(jsonString);
            mess = allMsg.getString("mess");
            JSONArray jsonArray = allMsg.getJSONArray("informs");
            informs = new Msg[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++)
            {
                informs[i]  = new Msg(jsonArray.getJSONObject(i));
            }
        }
        catch (JSONException e) {

        }
    }

}

class Msg
{

    private String InformID;
    private String TargetID;
    private String Form;
    private String Time;
    private String Type;
    private String Title;
    private String Content;
    private String IsRead;

    public Msg(JSONObject jsonObject)
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