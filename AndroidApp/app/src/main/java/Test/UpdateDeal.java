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

/**
 * Created by admin on 2015/6/30.
 */
public class UpdateDeal {

    public static final String BASE_URL = "";
    public static Map<String, Object> updateuserbaseinfo(String userid, String sex, String age, String constellation,
                                                         String profession, String liveplace, String description,
                                                         String phone, String mailBox)
    {
        String resultStr = null;

        String getUrl = BASE_URL + "?" + "oper=updateuserbaseinfo"
                + "&userid=" + userid + "&sex=" + sex + "&age=" + age
                + "&constellation=" + constellation + "&profession=" + profession
                + "&liveplace=" + liveplace + "&description=" + description
                + "&phone=" + phone + "&mailBox=" + mailBox;

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

        return parseJSONString_info_update(resultStr);

    }

    private static Map<String, Object> parseJSONString_info_update(String JSONString)
    {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try
        {
            JSONObject msg = new JSONObject(JSONString);
            resultMap.put("mess", msg.getString("mess"));
            resultMap.put("user", msg.getJSONArray("user").toString());
        }
        catch (JSONException e)
        {

        }

        return resultMap;
    }
}
