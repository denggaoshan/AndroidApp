package com.example.administrator.androidapp.msg;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v7.app.ActionBarActivity;
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

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
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
    private static final String TAG = "uploadFile";
    private static final int TIME_OUT = 10 * 1000; // 超时时间
    private static final String CHARSET = "utf-8"; // 设置编码


    public static String getCurLocation(){
        String getUrl = IPAPI + "?" + "ak=" + AK;
        String jsonString = httpGet(getUrl);
        String result = "";
        try {
            JSONObject jsonObject = new JSONObject(jsonString).getJSONObject("content");
            result = jsonObject.getString("address");
        } catch (JSONException e) {
            e.printStackTrace();
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

    /*      返回MyMessage的接口 */
    //登陆
    public static MyMessage load(String account, String password)
    {
        String pass_MD = convetToMD5(password);
        String getUrl = MSGSERVERURL + "?" + "oper=login"
                + "&account=" + account + "&password=" + pass_MD;

        MyMessage ret =  MyMessage.createMessage(httpGet(getUrl));

        return ret;
    }

    //注册
    public static MyMessage register(String account, String password, String
            sex, String phone, String mailBox, String avatar)
    {
        String pass_MD = convetToMD5(password);
        String getUrl = MSGSERVERURL + "?" + "oper=register"
                + "&account=" + account + "&password=" + pass_MD
                + "&sex=" + sex + "&phone=" + phone + "&mailbox=" + mailBox
                + "&avatar=" + avatar;

        return MyMessage.createMessage(httpGet(getUrl));
    }


    //更新用户的信息
    public static MyMessage updateuserbaseinfo(String userid,String nickname,String sex, String age, String constellation,
                                          String profession, String liveplace, String description,
                                          String phone)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=updateuserbaseinfo"
                + "&userid=" + userid +"&sex=" + sex + "&age=" + age
                + "&constellation=" + constellation + "&profession=" + profession
                + "&liveplace=" + liveplace + "&description=" + description
                + "&phone=" + phone +"&nickname="+nickname;
        return MyMessage.createMessage(httpGet(getUrl));
    }

    //修改密码
    public static MyMessage updateuserpassword(String userid, String oldpassword, String newpassword)
    {
        String oldPassword_MD = convetToMD5(oldpassword);
        String newPassword_MD = convetToMD5(newpassword);
        String getUrl = MSGSERVERURL + "?" + "oper=updateuserpassword"
                + "&userid=" + userid + "&oldpassword=" + oldPassword_MD
                + "&newpassword=" + newPassword_MD;

        return MyMessage.createMessage(httpGet(getUrl));
    }


    //活动用户发起的活动
    public static MyMessage getLaunchedActivity(String userid)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=getlaunchedactivitybyuserid"
                + "&userid=" + userid;

        return MyMessage.createMessage(httpGet(getUrl));
    }

    public static MyMessage getParticipatedActivity(String userid)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=getpartactivitybyuserid"
                + "&userid=" + userid;

        return MyMessage.createMessage(httpGet(getUrl));
    }

    public static MyMessage getApplicatedActivity(String userid)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=getappliactivitybyuserid"
                + "&userid=" + userid;

        return MyMessage.createMessage(httpGet(getUrl));
    }

    public static MyMessage launchActivity(String userid, String title, String content,
                                      String starttime, String endtime, String place, String type)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=launchactivity"
                + "&userid=" + userid + "&title=" + title
                + "&content=" + content + "&starttime=" + starttime
                + "&endtime=" + endtime + "&place=" + place + "&type=" + type;

        return MyMessage.createMessage(httpGet(getUrl));
    }

    public static MyMessage getActivityList(String page, String type, String applyable)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=getactivitylist"
                + "&page=" + page + "&type=" + type
                + "&applyable=" + applyable;
        return MyMessage.createMessage(httpGet(getUrl));
    }
    public static MyMessage getActivityList(String page, String type, String applyable, String keyword)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=getactivitylist"
                + "&page=" + page + "&type=" + type
                + "applyable=" + applyable + "&keyword=" + keyword;

        return MyMessage.createMessage(httpGet(getUrl));
    }


    public static MyMessage getParticipation(String userid, String activityid)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=getparticipation"
                + "&userid=" + userid + "&activityid=" + activityid;

        MyMessage msg;

        String ret = httpGet(getUrl);
        if(ret!=null){
            msg =  MyMessage.createMessage(ret);
            if(msg!=null){
                return msg;
            }
        }
        return null;
    }

    public static MyMessage getInform(String userid)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=getinformbyuserid"
                + "&userid=" + userid;

        return MyMessage.createMessage(httpGet(getUrl));
    }

    public static MyMessage applyParticipation(String userid, String activityid, String explain)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=applyparticipation"
                + "&userid=" + userid + "&activityid=" + activityid
                + "&explain=" + explain;

        return MyMessage.createMessage(httpGet(getUrl));
    }

    public static MyMessage getApplication(String userid, String activityid)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=getapplication"
                + "&userid=" + userid + "&activityid=" + activityid;

        return MyMessage.createMessage(httpGet(getUrl));
    }


    public static MyMessage updateActivityInfo(String activityid, String title, String content,
                                          String starttime, String endtime, String place, String type)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=updateactivityinfo"
                + "&activityid=" + activityid + "&title=" + title
                + "&content=" + content + "&starttime=" + starttime
                + "&endtime=" + endtime + "&place=" + place + "&type=" + type;

        return MyMessage.createMessage(httpGet(getUrl));
    }

    public static MyMessage getUserInfo(String userid, String searchid)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=getuserinfobyid"
                + "&userid=" + userid + "&searchid=" + searchid;

        return MyMessage.createMessage(httpGet(getUrl));
    }

    public static MyMessage getActivityInfo(String userid, String activityid)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=getactivityinfo"
                + "&userid=" + userid + "&activityid=" + activityid;

        return MyMessage.createMessage(httpGet(getUrl));
    }


    /*只返回String的接口*/

    public static MyMessage addOrDeleteGood(String userid, String toid, String good)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=addordeletegood"
                + "&userid=" + userid + "&toid=" + toid + "&good=" + good;

        return MyMessage.createMessage(httpGet(getUrl));
    }

    public static MyMessage kick(String userid, String activityid, String kickid)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=kick"
                + "&userid=" + userid + "&activityid=" + activityid + "&kickid=" + kickid;

        return MyMessage.createMessage(httpGet(getUrl));
    }

    public static MyMessage addCommment(String userid, String activityid, String content)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=addcomment"
                + "&userid=" + userid + "&activityid=" + activityid + "&content=" + content;

        return MyMessage.createMessage(httpGet(getUrl));
    }

    public static MyMessage addPhoto(String userid, String activityid, String address,
                                String title, String describe, String level)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=addphoto"
                + "&userid=" + userid + "&activityid=" + activityid + "&address=" + address
                + "&title=" + title + "&describe=" + describe + "&level=" + level;

        return MyMessage.createMessage(httpGet(getUrl));
    }

    public static MyMessage handleApplication(String userid, String activityid, String applyid, String isallow)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=handleapplication"
                + "&userid=" + userid + "&activityid="+activityid+"&applyid=" + applyid
                + "&isallow=" + isallow;

        return MyMessage.createMessage(httpGet(getUrl));
    }

    public static MyMessage fsendMess(String userid, String activityid, String title, String content)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=fsendmess"
                + "&userid=" + userid + "&activityid=" + activityid
                + "&title=" + title + "&content=" + content;

        return MyMessage.createMessage(httpGet(getUrl));
    }

    public static MyMessage affirminform(String informid)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=affirminform"
                + "&informid=" + informid;

        return MyMessage.createMessage(httpGet(getUrl));
    }

    public static MyMessage sendPrivateMess(String userid, String toid, String title, String content)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=sendprivatemess"
                + "&userid=" + userid + "&toid=" + toid
                + "&title=" + title + "&content=" + content;

        return MyMessage.createMessage(httpGet(getUrl));
    }


    public static MyMessage verify(String userid, String mailbox)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=verify"
                + "&userid=" + userid + "&mailbox=" + mailbox;
        return MyMessage.createMessage(httpGet(getUrl));
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

        try{
            HttpResponse response = httpClient.execute(getMethod);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK
                    || response.getStatusLine().getStatusCode() == 200
                    || response.getStatusLine().getStatusCode() == 302){
                resultStr = EntityUtils.toString(response.getEntity(), "utf-8");
            }
        } catch (Exception e){

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


    //只返回Mess的内容
    private static String onlyGetMess(String jsonString)
    {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            String mess = jsonObject.getString("mess");
            return mess;
        } catch (JSONException e) {
            return null;
        }
    }

    //根据url获得图片
    public static Bitmap returnBitMap(String url)
    {
        try {
            if(url!=null && url!="" && url!="null"){
                URL myFileUrl = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();
                InputStream is = conn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                is.close();
                return bitmap;
            }else{
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }



    //上传文件
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

    public static Bitmap resizeBitmap(Bitmap bm, ActionBarActivity ac, int srcWidth, int srcHeight){
        int width = bm.getWidth();
        int height = bm.getHeight();
        float density = ac.getResources().getDisplayMetrics().density;
        float newWidth = srcWidth * density;
        float newHeight = srcHeight * density;
        float scaleWidth = ((float)newWidth) / width;
        float scaleHeight = ((float)newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizeBitmap = Bitmap.createBitmap(bm, 0, 0, width,
                height, matrix, true);

        return resizeBitmap;
    }

}
