package Test;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import Test.MD5Deal;

/**
 * Created by admin on 2015/6/30.
 */
public class RegisterDeal {

    public static final String BASE_URL = "";
    public static Map<String, Object> register(String account, String password, String sex, String phone, String mailBox, String avatar)
    {
        String resultStr = null;

        String pass_MD = MD5Deal.convetToMD5(password);
        String getUrl = BASE_URL + "?" + "oper=register"
                + "&account=" + account + "&password=" + pass_MD
                + "&sex=" + sex + "&phone=" + phone + "&mailbox=" + mailBox + "&avatar=" + avatar;

        HttpGet getMethod = new HttpGet(getUrl);
        HttpClient httpClient = new DefaultHttpClient();
        try
        {
            HttpResponse response = httpClient.execute(getMethod);
            if (response.getStatusLine().getStatusCode() == 200)
                resultStr = EntityUtils.toString(response.getEntity(), "utf-8");
        }
        catch (ClientProtocolException e)
        {

        }
        catch (IOException ee)
        {

        }

        return parseJSONString(resultStr);
    }

    private static Map<String, Object> parseJSONString(String JSONString)
    {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try
        {
            JSONObject msg = new JSONObject(JSONString);
            resultMap.put("mess", msg.getString("mess"));
            resultMap.put("user", msg.getJSONArray("user").toString());
            resultMap.put("activities", msg.getJSONArray("activities").toString());
            resultMap.put("good", msg.getString("good"));
        }
        catch (JSONException e)
        {

        }

        return resultMap;
    }

}
