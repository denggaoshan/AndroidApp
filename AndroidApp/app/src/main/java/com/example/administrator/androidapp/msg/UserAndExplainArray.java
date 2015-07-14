package com.example.administrator.androidapp.msg;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 2015/7/14.
 */
public class UserAndExplainArray {

    private String mess; public String getMess(){ return mess; }
    public UserAndExplain[] userAndExplains;

    public static UserAndExplainArray createUserAndExplainArray(String jsonString){
        UserAndExplainArray temp = new UserAndExplainArray();
        JSONObject allMsg;

        if (jsonString != null && !jsonString.equals("")){
            try{
                allMsg = new JSONObject(jsonString);
            }
            catch (JSONException e){
                allMsg = null;
            }
            if (allMsg != null){
                temp.setMess(allMsg);
                JSONArray jsonArray;
                try{
                    jsonArray = allMsg.getJSONArray("users");
                    temp.userAndExplains = new UserAndExplain[jsonArray.length()];
                    for (int i = 0; i < jsonArray.length(); i++){
                        temp.userAndExplains[i] = UserAndExplain.createUserAndExplain(jsonArray.getJSONObject(i));
                    }
                } catch (JSONException e){
                    temp.userAndExplains = null;
                }
            }
        }
        return temp;
    }

    private void setMess(JSONObject jsonObject){
        try {
            mess = jsonObject.getString("mess");
        } catch (JSONException e){
            mess = "";
        }
    }
}
