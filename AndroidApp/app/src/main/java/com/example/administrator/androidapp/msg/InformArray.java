package com.example.administrator.androidapp.msg;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 2015/7/6.
 */
public class InformArray {

    private String mess;
    private Inform[] informs;

    public static InformArray createInformArray(String jsonString)
    {
        InformArray tmp = new InformArray();
        JSONObject allMsg = tmp.getAllMsg(jsonString);
        tmp.setMess(allMsg);
        tmp.setInforms(allMsg);

        return tmp;
    }

    private void setInforms(JSONObject jsonObject)
    {
        JSONArray jsonArray;
        try
        {
            jsonArray = jsonObject.getJSONArray("informs");
        }
        catch (JSONException e)
        {
            jsonArray = null;
        }

        if (jsonArray != null)
        {
            informs = new Inform[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++)
            {
                try {
                    informs[i] = Inform.createInform(jsonArray.getJSONObject(i));
                }
                catch (JSONException e)
                {
                    informs[i] = null;
                }
            }
        }
        else
        {
            informs = null;
        }
    }
    private JSONObject getAllMsg(String jsonString)
    {
        JSONObject allMsg;
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

}
