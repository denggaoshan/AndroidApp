package com.example.administrator.androidapp.msg;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by admin on 2015/7/3.
 */
public class ToolClass {

    public static String IMGSERVERURL = "http://chenranzhen.xyz/UpLoadFile.php";
    public static String MSGSERVERURL = "http://chenranzhen.xyz/privateinterface.php";
    public static String AK = "3Ne7OXKQwUIDLiD9UF6IM90g";
    public static String IPAPI = "http://api.map.baidu.com/location/ip";

    public static String getCurLocation(){
        String getUrl = IPAPI + "?" + "ak=" + AK;
        String jsonString = httpGet(getUrl);
        String result = "";

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonString).getJSONObject("content");
        }
        catch (JSONException e){
            jsonObject = null;
        }
        if (jsonObject != null){
            try {
                result = jsonObject.getString("address");
            }
            catch (JSONException e){
                result = "";
            }
        }
        return result;
    }
    private String convert(String utfString){
        StringBuilder sb = new StringBuilder();
        int i = -1;
        int pos = 0;

        while((i=utfString.indexOf("\\u", pos)) != -1){
            sb.append(utfString.substring(pos, i));
            if(i+5 < utfString.length()){
                pos = i+6;
                sb.append((char)Integer.parseInt(utfString.substring(i+2, i+6), 16));
            }
        }

        return sb.toString();
    }

    public static Message load(String account, String password)
    {
        String pass_MD = convetToMD5(password);
        String getUrl = MSGSERVERURL + "?" + "oper=login"
                + "&account=" + account + "&password=" + pass_MD;

        return Message.createMessage(httpGet(getUrl), 1, 2);
    }

    public static Message register(String account, String password, String
            sex, String phone, String mailBox, String avatar)
    {
        String pass_MD = convetToMD5(password);
        String getUrl = MSGSERVERURL + "?" + "oper=register"
                + "&account=" + account + "&password=" + pass_MD
                + "&sex=" + sex + "&phone=" + phone + "&mailbox=" + mailBox
                + "&avatar=" + avatar;

        return Message.createMessage(httpGet(getUrl), 1, 2);
    }

    public static Message updateuserbaseinfo(String userid, String sex, String age, String constellation,
                                          String profession, String liveplace, String description,
                                          String phone, String mailBox)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=updateuserbaseinfo"
                + "&userid=" + userid + "&sex=" + sex + "&age=" + age
                + "&constellation=" + constellation + "&profession=" + profession
                + "&liveplace=" + liveplace + "&description=" + description
                + "&phone=" + phone + "&mailBox=" + mailBox;

        return Message.createMessage(httpGet(getUrl), 1, 0);
    }

    public static Message updateuserpassword(String userid, String oldpassword, String newpassword)
    {
        String oldPassword_MD = convetToMD5(oldpassword);
        String newPassword_MD = convetToMD5(newpassword);
        String getUrl = MSGSERVERURL + "?" + "oper=updateuserpassword"
                + "&userid=" + userid + "&oldpassword=" + oldPassword_MD
                + "&newpassword=" + newPassword_MD;

        return Message.createMessage(httpGet(getUrl), 1, 0);
    }

    public static Message getLaunchedActivity(String userid)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=getlaunchedactivitybyuserid"
                + "&userid=" + userid;

        return Message.createMessage(httpGet(getUrl), 0, 2);
    }

    public static Message getParticipatedActivity(String userid)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=getpartactivitybyuserid"
                + "&userid=" + userid;

        return Message.createMessage(httpGet(getUrl), 0, 2);
    }

    public static Message getApplicatedActivity(String userid)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=getappliactivitybyuserid"
                + "&userid=" + userid;

        return Message.createMessage(httpGet(getUrl), 0, 2);
    }

    public static Message launchActivity(String userid, String title, String content,
                                      String starttime, String endtime, String place, String type)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=launchactivity"
                + "&userid=" + userid + "&title=" + title
                + "&content=" + content + "&starttime=" + starttime
                + "&endtime=" + endtime + "&place=" + place + "&type=" + type;

        return Message.createMessage(httpGet(getUrl), 0, 1);
    }

    public static Message getActivityList(String page, String type, String applyable)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=getactivitylist"
                + "&page=" + page + "&type=" + type
                + "applyable=" + applyable;

        return Message.createMessage(httpGet(getUrl), 0, 2);
    }
    public static Message getActivityList(String page, String type, String applyable, String keyword)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=getactivitylist"
                + "&page=" + page + "&type=" + type
                + "applyable=" + applyable + "&keyword=" + keyword;

        return Message.createMessage(httpGet(getUrl), 0, 2);
    }

    public static Message getParticipation(String userid, String activityid)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=getparticipation"
                + "&userid=" + userid + "&activityid=" + activityid;

        return Message.createMessage(httpGet(getUrl), 2, 0);
    }

    public static InformArray getInform(String userid)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=getinformbyuserid"
                + "&userid=" + userid;

        return InformArray.createInformArray(httpGet(getUrl));
    }

    public static String applyParticipation(String userid, String activityid, String explain)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=applyparticipation"
                + "&userid=" + userid + "&activityid=" + activityid
                + "&explain=" + explain;

        return onlyGetMess(httpGet(getUrl));
    }

    public static Message getApplication(String userid, String activityid)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=getapplication"
                + "&userid=" + userid + "&activityid=" + activityid;
        Message ret = Message.createMessage(httpGet(getUrl), 2, 0);

        return ret;
    }

    public static String handleApplication(String userid, String applyid, String isallow)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=handleapplication"
                + "&userid=" + userid + "&applyid=" + applyid
                + "&isallow=" + isallow;

        return onlyGetMess(httpGet(getUrl));
    }

    public static String fsendMess(String userid, String activityid, String title, String content)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=fsendmess"
                + "&userid=" + userid + "&activityid=" + activityid
                + "&title=" + title + "&content=" + content;

        return onlyGetMess(httpGet(getUrl));
    }

    public static String affirminform(String informid)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=affirminform"
                + "&informid=" + informid;

        return onlyGetMess(httpGet(getUrl));
    }

    public static String sendPrivateMess(String userid, String toid, String title, String content)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=sendprivatemess"
                + "&userid=" + userid + "&toid=" + toid
                + "&title=" + title + "&content=" + content;

        return onlyGetMess(httpGet(getUrl));
    }

    public static Message updateActivityInfo(String activityid, String title, String content,
                                          String starttime, String endtime, String place, String type)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=updateactivityinfo"
                + "&activityid=" + activityid + "&title=" + title
                + "&content=" + content + "&starttime=" + starttime
                + "&endtime=" + endtime + "&place=" + place + "&type=" + type;

        return Message.createMessage(httpGet(getUrl), 0, 1);
    }

    public static Message getUserInfo(String userid, String searchid)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=getuserinfobyid"
                + "&userid=" + userid + "&searchid=" + searchid;

        return Message.createMessage(httpGet(getUrl), 1, 0);
    }

    public static String addOrDeleteGood(String userid, String toid, String good)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=addordeletegood"
                + "&userid=" + userid + "&toid=" + toid + "&good=" + good;

        return onlyGetMess(httpGet(getUrl));
    }

    public static String kick(String userid, String activityid, String kickid)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=kick"
                + "&userid=" + userid + "&activityid=" + activityid + "&kickid=" + kickid;

        return onlyGetMess(httpGet(getUrl));
    }

    public static String addCommment(String userid, String activityid, String content)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=addcomment"
                + "&userid=" + userid + "&activityid=" + activityid + "&content=" + content;

        return onlyGetMess(httpGet(getUrl));
    }

    public static String addPhoto(String userid, String activityid, String address,
                                String title, String describe, String level)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=addphoto"
                + "&userid=" + userid + "&activityid=" + activityid + "&address=" + address
                + "&title=" + title + "&describe=" + describe + "&level=" + level;

        return onlyGetMess(httpGet(getUrl));
    }

    public static ActivityInfo getActivityInfo(String userid, String activityid)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=getactivityinfo"
                + "&userid=" + userid + "&activityid=" + activityid;

        return ActivityInfo.createActivityInfo(httpGet(getUrl));
    }


    private static String httpGet(String url)
    {
        String resultStr = null;
        url = url.replaceAll(" ", "%20");
        url = url.replaceAll("[{]", "%7B");
        url = url.replaceAll("[}]", "%7D");
        url = url.replaceAll("\\[", "%5B");
        url = url.replaceAll("\\]", "%5D");
        url = url.replaceAll("\"", "%22");

        HttpGet getMethod = new HttpGet(url);
        HttpClient httpClient = new DefaultHttpClient();
        try
        {
            HttpResponse response = httpClient.execute(getMethod);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK
                    || response.getStatusLine().getStatusCode() == 200
                    || response.getStatusLine().getStatusCode() == 302)
                resultStr = EntityUtils.toString(response.getEntity(), "utf-8");
            else
            {
                String temp = EntityUtils.toString(response.getEntity(), "utf-8");//WTF?
                int cc = response.getStatusLine().getStatusCode();
                String ss = temp;
            }
        }
        catch (ClientProtocolException e)
        {
            e.printStackTrace();
        }
        catch (IOException ee)
        {
            ee.printStackTrace();
        }

        return resultStr;
    }

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

    private static String onlyGetMess(String jsonString)
    {
        JSONObject jsonObject;
        String mess;
        try {
            jsonObject = new JSONObject(jsonString);
        }
        catch (JSONException e)
        {
            jsonObject = null;
        }
        if (jsonObject != null)
        {
            try {
                mess = jsonObject.getString("mess");
            }
            catch (JSONException e)
            {
                mess = null;
            }
        }
        else
            mess = null;

        return mess;
    }

    public static Bitmap returnBitMap(String url)
    {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try
        {
            myFileUrl = new URL(url);
        }
        catch (MalformedURLException E)
        {
            E.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private static final String TAG = "uploadFile";
    private static final int TIME_OUT = 10 * 1000; // 超时时间
    private static final String CHARSET = "utf-8"; // 设置编码
    public static String uploadFile(String fileUrl)
    {
        if (fileUrl == null)
            return null;
        int res=0;
        String result = null;
        String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
        String PREFIX = "--", LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data"; // 内容类型

        try {
            URL url = new URL(IMGSERVERURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoInput(true); // 允许输入流
            conn.setDoOutput(true); // 允许输出流
            conn.setUseCaches(false); // 不允许使用缓存
            conn.setRequestMethod("POST"); // 请求方式
            conn.setRequestProperty("Charset", CHARSET); // 设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="+ BOUNDARY);

            File file = new File(fileUrl);
            if (file != null) {
                /**
                 * 当文件不为空时执行上传
                 */
                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                /**
                 * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
                 * filename是文件的名字，包含后缀名
                 */

                sb.append("Content-Disposition: form-data; name=\"file\"; filename=\""
                        + file.getName() + "\"" + LINE_END);
                sb.append("Content-Type: application/octet-stream; charset="
                        + CHARSET + LINE_END);
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());
                InputStream is = new FileInputStream(file);
                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len = is.read(bytes)) != -1) {
                    dos.write(bytes, 0, len);
                }
                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
                        .getBytes();
                dos.write(end_data);
                dos.flush();
                /**
                 * 获取响应码 200=成功 当响应成功，获取响应的流
                 */
                res = conn.getResponseCode();
                Log.e(TAG, "response code:" + res);
                if (res == 200) {
                    Log.e(TAG, "request success");
                    InputStream input = conn.getInputStream();
                    StringBuffer sb1 = new StringBuffer();
                    int ss;
                    while ((ss = input.read()) != -1) {
                        sb1.append((char) ss);
                    }
                    result = sb1.toString().trim();
                    Log.e(TAG, "result : " + result);
                } else {
                    Log.e(TAG, "request error");
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
