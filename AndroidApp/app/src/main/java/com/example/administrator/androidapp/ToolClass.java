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
     * ͼƬ�ϴ�����
     * @param fileUrl ͼƬ���ص�ַ
     * @param serverUrl ͼƬ�ϴ���������ַ Ĭ��IMGSERVERURL
     * @return �ɹ�ʱ����ͼƬ�������ϴ洢��ַ�������ں�����ʾͼƬ  ʧ��ʱ����null
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
            // ����ÿ�δ��������С��������Ч��ֹ�ֻ���Ϊ�ڴ治�����
            // �˷���������Ԥ�Ȳ�֪�����ݳ���ʱ����û�н����ڲ������ HTTP �������ĵ�����
            httpURLConnection.setChunkedStreamingMode(128 * 1024);// 128K
            // �������������
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            // ʹ��POST����
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
            // ��ȡ�ļ�
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
     * ����ͼƬ����
     * @param imgUrl ͼƬ�������ϵĵ�ַ
     * @return �ɹ�ʱ���������ص�ͼƬ ʧ��ʱ����null
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
     * MD5���ֽ�����ת��Ϊ��д�ַ�������
     * @param byteArray �ֽ�����
     * @return ת����Ĵ�д�ַ���
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
     * ���ַ�����MD5���ܷ����������������
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
     * JSON ARRAY ת���� Array<List> ����
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
     * ��½����
     * @param account �û��˺�
     * @param password �û�����
     * @return �����û���Ϣmap ʧ��ʱmap�С�mess��->"loginfail" �˺����벻��ȷ
     */
    public static User load(String account, String password)
    {
        String pass_MD = convetToMD5(password);
        String getUrl = MSGSERVERURL + "?" + "oper=login"
                + "&account=" + account + "&password=" + pass_MD;

        return new User(httpGet(getUrl));
    }

    /**
     * ע�᷽��
     * @param account �û��˺�
     * @param password �û�����
     * @param sex �û��Ա� 0�� 1Ů
     * @param phone �û��ֻ�����
     * @param mailBox �û�����
     * @param avatar �û�ͷ��ͼƬ�����ַ
     * @return �����û���Ϣmap��ʧ��ʱmap��"mess"->"registerfail" �˺��Ѵ���
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
     * �޸��û���Ϣ����
     * @param userid �û�id
     * @param sex �û��Ա� 0�� 1Ů
     * @param age �û�����
     * @param constellation �û�����
     * @param profession �û�ְҵ
     * @param liveplace �û���ַ
     * @param description �û�����˵��
     * @param phone �û��ֻ�����
     * @param mailBox �û�����
     * @return �����û���Ϣmap��ʧ��ʱmap��"mess"->"useriderror"  userid����
     */
    public static User updateuserbaseinfo(String userid, String sex, String age, String constellation,
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
     * �޸��û����뷽��
     * @param userid �û�Id
     * @param oldpassword �û�ԭ����
     * @param newpassword �û�������
     * @return �����û���Ϣmap�� �ɹ�ʱmap��"mess"->"updateerror" userid������������
     */
    public static User updateuserpassword(String userid, String oldpassword, String newpassword)
    {
        String oldPassword_MD = convetToMD5(oldpassword);
        String newPassword_MD = convetToMD5(newpassword);
        String getUrl = MSGSERVERURL + "?" + "oper=updateuserpassword"
                + "&userid=" + userid + "&oldpassword=" + oldPassword_MD
                + "&newpassword=" + newPassword_MD;

        return new User(httpGet(getUrl));
    }


    /**
     * ��ȡ�û�����Ļ����
     * @param userid �û�Id
     * @return �����û���Ϣmap�� �ɹ�ʱmap��"mess"->"empty" userid�����δ����
     */
    public static User getLaunchedActivity(String userid)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=getlaunchedactivitybyuserid"
                + "&userid=" + userid;

        return new User(httpGet(getUrl));
    }

    /**
     * ��ȡ�û�����Ļ����
     * @param userid �û�Id
     * @return �����û���Ϣmap�� �ɹ�ʱmap��"mess"->"empty" userid�����δ����
     */
    public static User getParticipatedActivity(String userid)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=getpartactivitybyuserid"
                + "&userid=" + userid;

        return new User(httpGet(getUrl));
    }


    /**
     * ��ȡ�û�����Ļ����
     * @param userid �û�Id
     * @return �����û���Ϣmap�� �ɹ�ʱmap��"mess"->"empty" userid�����δ����
     */
    public static User getApplicatedActivity(String userid)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=getappliactivitybyuserid"
                + "&userid=" + userid;

        return new User(httpGet(getUrl));
    }


    /**
     * ��������
     * @param userid
     * @param title
     * @param content
     * @param starttime
     * @param endtime
     * @param place
     * @param type
     * @return ����map, ��map�е�"mess"->"error" �쳣
     */
    public static User launchActivity(String userid, String title, String content,
                                                     String starttime, String endtime, String place, String type)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=launchactivity"
                + "&userid=" + userid + "&title=" + title
                + "&content=" + content + "&starttime=" + starttime
                + "&endtime=" + endtime + "&place=" + place + "&type=" + type;

        return new User(httpGet(getUrl));
    }

    /**
     * ��ȡ��б���
     * @param page
     * @param type
     * @param applyable
     * @param keyword
     * @return ����User�����н���activity��
     */
    public static User getActivityList(String page, String type, String applyable, String keyword)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=getactivitylist"
                + "&page=" + page + "&type=" + type
                + "applyable=" + applyable + "&keyword=" + keyword;

        return new User(httpGet(getUrl));
    }

    /**
     * ��ȡ�������Ա����
     * @param userid
     * @param activityid
     * @return Ĭ�ϵ�0��Ϊ������
     */
    public static User[] getParticipation(String userid, String activityid)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=getparticipation"
                + "&userid=" + userid + "&activityid=" + activityid;

        User temp = new User();
        return temp.getUsers(httpGet(getUrl));
    }

    /**
     * ���ĳ�û���Ϣ
     * @param userid
     * @return
     */
    public static Message getInform(String userid)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=getinformbyuserid"
                + "&userid=" + userid;

        return new Message(httpGet(getUrl));
    }

    /**
     * �û�����μӻ
     * @param userid
     * @param activityid
     * @param explain
     * @return
     */
    public static User applyParticipation(String userid, String activityid, String explain)
    {
        String getUrl = MSGSERVERURL + "?" + "oper=applyparticipation"
                + "&userid=" + userid + "&activityid=" + activityid
                + "&explain=" + explain;

        return new User(httpGet(getUrl));
    }


}
