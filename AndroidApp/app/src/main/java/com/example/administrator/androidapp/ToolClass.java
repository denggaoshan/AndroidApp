package com.example.administrator.androidapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import Test.MD5Deal;

/**
 * Created by admin on 2015/7/1.
 */
public class ToolClass {

    public static String IMGSERVERURL = "http://chenranzhen.xyz/UpLoadFile.php";
    public static String MSGSERVERURL = "http://chenranzhen.xyz/privateinterface.php";

    /**
     * 图片上传方法
     * @param fileUrl 图片本地地址
     * @param serverUrl 图片上传服务器地址 默认IMGSERVERURL
     * @return 成功时返回图片在网络上存储地址，可用于后续显示图片  失败时返回null
     */
    public static String uploadFile(String fileUrl, String serverUrl)
    {
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "******";
        try
        {
            URL url = new URL(serverUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            // 设置每次传输的流大小，可以有效防止手机因为内存不足崩溃
            // 此方法用于在预先不知道内容长度时启用没有进行内部缓冲的 HTTP 请求正文的流。
            httpURLConnection.setChunkedStreamingMode(128 * 1024);// 128K
            // 允许输入输出流
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            // 使用POST方法
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);

            DataOutputStream dos = new DataOutputStream(
                    httpURLConnection.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + end);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\"; filename=\""
                    + fileUrl.substring(fileUrl.lastIndexOf("/") + 1)
                    + "\""
                    + end);
            dos.writeBytes(end);

            FileInputStream fis = new FileInputStream(fileUrl);
            byte[] buffer = new byte[8192]; // 8k
            int count = 0;
            // 读取文件
            while ((count = fis.read(buffer)) != -1)
            {
                dos.write(buffer, 0, count);
            }
            fis.close();

            dos.writeBytes(end);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
            dos.flush();

            InputStream is = httpURLConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String result = br.readLine();

            dos.close();
            is.close();

            return result;
        }
        catch (Exception w)
        {

        }

        return null;
    }

    /**
     * 下载图片方法
     * @param imgUrl 图片在网络上的地址
     * @return 成功时返回所下载的图片 失败时返回null
     */
    public static Bitmap downBitmap(String imgUrl)
    {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(imgUrl);
        Bitmap result = null;
        try
        {
            HttpResponse resp = httpClient.execute(httpGet);
            if (HttpStatus.SC_OK == resp.getStatusLine().getStatusCode())
            {
                HttpEntity entity = resp.getEntity();
                InputStream in = entity.getContent();
                result = BitmapFactory.decodeStream(in);
            }
        }
        catch(Exception e)
        {

        }
        finally
        {
            httpClient.getConnectionManager().shutdown();
        }

        return result;
    }

    /**
     * MD5中字节数组转换为大写字符串方法
     * @param byteArray 字节数组
     * @return 转换后的大写字符串
     */
    private static String byteArrayToHex(byte[] byteArray) {
        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F' };
        char[] resultCharArray = new char[byteArray.length * 2];
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }
        return new String(resultCharArray);
    }

    /**
     * 将字符串用MD5加密方法，用于密码加密
     * @param str
     * @return
     */
    private static String convetToMD5(String str)
    {
        String result = "";
        try
        {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] inputByteArray = str.getBytes();
            messageDigest.update(inputByteArray);
            byte[] resultByteArray = messageDigest.digest();

            result = byteArrayToHex(resultByteArray);
        }
        catch (NoSuchAlgorithmException e)
        {

        }

        return result;
    }

    /**
     * JSON ARRAY 转换成 Array<List> 方法
     * @param array
     * @return
     */
    private static ArrayList<String> jsonArrayToStringArray(JSONArray array)
    {
        ArrayList<String> result = new ArrayList<String>();

        try
        {
            for (int i = 0; i < array.length(); i++) {
                result.add(array.getString(i));
            }
        }
        catch (JSONException e)
        {

        }

        return  result;
    }


    private static String httpGet(String url)
    {
        String resultStr = null;

        HttpGet getMethod = new HttpGet(url);
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

        return resultStr;
    }

    /**
     * 登陆方法
     * @param account 用户账号
     * @param password 用户密码
     * @return 返回用户信息map 失败时map中“mess”->"loginfail" 账号密码不正确
     */
    public static User load(String account, String password)
    {
        String pass_MD = convetToMD5(password);
        String getUrl = MSGSERVERURL + "?" + "oper=login"
                + "&account=" + account + "&password=" + pass_MD;

        return new User(httpGet(getUrl));
    }

    /**
     * 注册方法
     * @param account 用户账号
     * @param password 用户密码
     * @param sex 用户性别 0男 1女
     * @param phone 用户手机号码
     * @param mailBox 用户邮箱
     * @param avatar 用户头像图片网络地址
     * @return 返回用户信息map，失败时map中"mess"->"registerfail" 账号已存在
     */
    public static User register(String account, String password, String sex, String phone, String mailBox, String avatar)
    {
        String pass_MD = MD5Deal.convetToMD5(password);
        String getUrl = MSGSERVERURL + "?" + "oper=register"
                + "&account=" + account + "&password=" + pass_MD
                + "&sex=" + sex + "&phone=" + phone + "&mailbox=" + mailBox + "&avatar=" + avatar;

        return new User(httpGet(getUrl));
    }


    /**
     * 修改用户信息方法
     * @param userid 用户id
     * @param sex 用户性别 0男 1女
     * @param age 用户年龄
     * @param constellation 用户星座
     * @param profession 用户职业
     * @param liveplace 用户地址
     * @param description 用户个人说明
     * @param phone 用户手机号码
     * @param mailBox 用户邮箱
     * @return 返回用户信息map，失败时map中"mess"->"useriderror"  userid错误
     */
    public User updateuserbaseinfo(String userid, String sex, String age, String constellation,
                                                         String profession, String liveplace, String description,
                                                         String phone, String mailBox)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=updateuserbaseinfo"
                + "&userid=" + userid + "&sex=" + sex + "&age=" + age
                + "&constellation=" + constellation + "&profession=" + profession
                + "&liveplace=" + liveplace + "&description=" + description
                + "&phone=" + phone + "&mailBox=" + mailBox;

        return new User(httpGet(getUrl));
    }


    /**
     * 修改用户密码方法
     * @param userid 用户Id
     * @param oldpassword 用户原密码
     * @param newpassword 用户新密码
     * @return 返回用户信息map， 成功时map中"mess"->"updateerror" userid错误或密码错误
     */
    public User updateuserpassword(String userid, String oldpassword, String newpassword)
    {
        String oldPassword_MD = convetToMD5(oldpassword);
        String newPassword_MD = convetToMD5(newpassword);
        String getUrl = MSGSERVERURL + "?" + "oper=updateuserpassword"
                + "&userid=" + userid + "&oldpassword=" + oldPassword_MD
                + "&newpassword=" + newPassword_MD;

        return new User(httpGet(getUrl));
    }


    /**
     * 获取用户发起的活动方法
     * @param userid 用户Id
     * @return 返回用户信息map， 成功时map中"mess"->"empty" userid错误或未发起活动
     */
    public User getLaunchedActivity(String userid)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=getlaunchedactivitybyuserid"
                + "&userid=" + userid;

        return new User(httpGet(getUrl));
    }

    /**
     * 获取用户参与的活动方法
     * @param userid 用户Id
     * @return 返回用户信息map， 成功时map中"mess"->"empty" userid错误或未发起活动
     */
    public static Map<String, Object> getParticipatedActivity(String userid)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=getpartactivitybyuserid"
                + "&userid=" + userid;

        return parseJSONString_participatedActivity(httpGet(getUrl));
    }

    /**
     * 获取用户参与的活动 信息中json解析
     * @param JSONString
     * @return
     */
    private static Map<String, Object> parseJSONString_participatedActivity(String JSONString)
    {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try
        {
            JSONObject msg = new JSONObject(JSONString);
            resultMap.put("mess", msg.getString("mess"));
            resultMap.put("user", jsonArrayToStringArray(msg.getJSONArray("user")));
        }
        catch (JSONException e)
        {

        }

        return resultMap;
    }

    /**
     * 获取用户申请的活动方法
     * @param userid 用户Id
     * @return 返回用户信息map， 成功时map中"mess"->"empty" userid错误或未发起活动
     */
    public static Map<String, Object> getApplicatedActivity(String userid)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=getappliactivitybyuserid"
                + "&userid=" + userid;

        return parseJSONString_applicatedActivity(httpGet(getUrl));
    }

    /**
     * 获取用户参与的活动 信息中json解析
     * @param JSONString
     * @return
     */
    private static Map<String, Object> parseJSONString_applicatedActivity(String JSONString)
    {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try
        {
            JSONObject msg = new JSONObject(JSONString);
            resultMap.put("mess", msg.getString("mess"));
            resultMap.put("user", jsonArrayToStringArray(msg.getJSONArray("user")));
        }
        catch (JSONException e)
        {

        }

        return resultMap;
    }

    /**
     * 发起活动方法
     * @param userid
     * @param title
     * @param content
     * @param starttime
     * @param endtime
     * @param place
     * @param type
     * @return 返回map, 当map中的"mess"->"error" 异常
     */
    public static Map<String, Object> launchActivity(String userid, String title, String content,
                                                     String starttime, String endtime, String place, String type)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=getappliactivitybyuserid"
                + "&userid=" + userid;

        return parseJSONString_applicatedActivity(httpGet(getUrl));
    }

    private static Map<String, Object> parseJSONString_launchActivity(String JSONString)
    {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try
        {
            JSONObject msg = new JSONObject(JSONString);
            resultMap.put("mess", msg.getString("mess"));
            resultMap.put("user", jsonArrayToStringArray(msg.getJSONArray("user")));
        }
        catch (JSONException e)
        {

        }

        return resultMap;
    }

}
