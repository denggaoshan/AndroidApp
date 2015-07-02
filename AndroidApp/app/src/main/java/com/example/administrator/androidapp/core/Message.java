package com.example.administrator.androidapp.core;

import com.example.administrator.androidapp.core.Msg;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

