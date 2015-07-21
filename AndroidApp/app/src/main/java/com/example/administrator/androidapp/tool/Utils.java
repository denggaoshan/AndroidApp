package com.example.administrator.androidapp.tool;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.androidapp.R;
import com.example.administrator.androidapp.msg.User;
import com.example.administrator.androidapp.page.*;

//import org.apache.commons.codec.binary.Base64;
import android.util.Base64;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Stack;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * Created by denggaoshan on 2015/7/3.
 * 存储一些常用的工具函数
 */
public class Utils {

    public static final String KEY_ALGORITHM = "DES";
    public static final String CIPHER_ALGORITHM = "DES/ECB/PKCS5Padding";
    public static final String SCRETEKEY = "A1B553D4E5F60758";
    private static String LOGADRESS = "userLog.dat";



    //将当前的用户信息存到本地
    public static void storeLogData(String jsonString)
    {
        try {
            clearLogData();//清楚当前的数据
            File extDir = Environment.getExternalStorageDirectory();
            DataOutputStream out=new DataOutputStream(new FileOutputStream(new File(extDir, LOGADRESS)));
            String encryptData = "";
            encryptData = encrypt(jsonString, SCRETEKEY);
            out.writeUTF(encryptData);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //清除登陆缓存
    public static void clearLogData()
    {
        try {
            File extDir = Environment.getExternalStorageDirectory();
            DataOutputStream out=new DataOutputStream(new FileOutputStream(new File(extDir, LOGADRESS)));
            out.writeUTF("");
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //获取本地用户信息
    public static String getLogData()
    {
        try{
            File extDir = Environment.getExternalStorageDirectory();
            String result = "";
            DataInputStream in=new DataInputStream(new BufferedInputStream(new FileInputStream(new File(extDir, LOGADRESS))));
            result = in.readUTF();
            in.close();
            String decryptData = "";
            decryptData = decrypt(result, SCRETEKEY);
            return decryptData;
        }  catch (Exception e) {
            return "";
        }
    }


    /* 页面有关的 */

    private static Stack<Class> historyPages = new Stack<Class>(){};

    //切换页面
    public static void transPage(ActionBarActivity before,Class after){
        historyPages.push(before.getClass());
        Intent intent = new Intent();
           intent.setClass(before,after);
           before.startActivity(intent);
           before.finish();
    }

    //返回上一个页面
    public static void backPage(ActionBarActivity now){
        Class after = historyPages.pop();
        if(after!=null){
            transPage(now,after);
        }else{
            Utils.debugMessage(now,"返回上个页面的页面为空");
        }
    }

    //弹出提示窗口
    public static void showMessage(final ActionBarActivity parent, final String message){
        Toast.makeText(parent,message, Toast.LENGTH_LONG).show();
    }

    //弹出Debug窗口
    public static void debugMessage(ActionBarActivity parent,String message){
        Toast.makeText(parent,message, Toast.LENGTH_LONG).show();
    }

    //返回复选框的内容
    public static long getSpinnerById(ActionBarActivity parent,int id){
        return ((Spinner) parent.findViewById(id)).getSelectedItemId();
    }


    //返回文本框的内容 level
    public static String getValueOfEditText(ActionBarActivity parent, int id){
       return ((EditText) parent.findViewById(id)).getText().toString();
    }


    //性别
    public static String changeSex(String val){
        if(val.equals("0")){
            return "男";
        }
        if(val.equals("1")){
            return "女";
        }
        return "出错";
    }
    //活动类型
    public static String changeType(String val){
        //0户外 1运动 2玩乐 3旅行 4音乐 5其他
        String[] data = {"户外","运动","玩乐","旅行","音乐","其他"};
        int index = Integer.parseInt(val);
        return data[index];
    }



    private static SecretKey keyGenerator(String keyStr) throws Exception {
        byte input[] = HexString2Bytes(keyStr);
        DESKeySpec desKey = new DESKeySpec(input);
        //创建一个密匙工厂，然后用它把DESKeySpec转换成
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(desKey);
        return securekey;
    }

    private static int parse(char c) {
        if (c >= 'a') return (c - 'a' + 10) & 0x0f;
        if (c >= 'A') return (c - 'A' + 10) & 0x0f;
        return (c - '0') & 0x0f;
    }
    // 从十六进制字符串到字节数组转换
    private static byte[] HexString2Bytes(String hexstr) {
        byte[] b = new byte[hexstr.length() / 2];
        int j = 0;
        for (int i = 0; i < b.length; i++) {
            char c0 = hexstr.charAt(j++);
            char c1 = hexstr.charAt(j++);
            b[i] = (byte) ((parse(c0) << 4) | parse(c1));
        }
        return b;
    }
    private static String encrypt(String data, String key) throws Exception {
        Key deskey = keyGenerator(key);
        // 实例化Cipher对象，它用于完成实际的加密操作
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        SecureRandom random = new SecureRandom();
        // 初始化Cipher对象，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, deskey, random);
        byte[] results = cipher.doFinal(data.getBytes());
        // 该部分是为了与加解密在线测试网站（http://tripledes.online-domain-tools.com/）的十六进制结果进行核对
        for (int i = 0; i < results.length; i++) {
            System.out.print(results[i] + " ");
        }
        System.out.println();
        // 执行加密操作。加密后的结果通常都会用Base64编码进行传输
        //return Base64.encodeBase64String(results);
        return Base64.encodeToString(results, Base64.DEFAULT);
    }
    private static String decrypt(String data, String key) throws Exception {
        Key deskey = keyGenerator(key);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        //初始化Cipher对象，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, deskey);
        // 执行解密操作
        //return new String(cipher.doFinal(Base64.decodeBase64(data)));
        return new String(cipher.doFinal(Base64.decode(data, Base64.DEFAULT)));
    }



    public static void setTextView(ActionBarActivity parent, int id, String value) {
        TextView tv = (TextView)parent.findViewById(id);
        if(tv!=null){
            tv.setText(value);
        }else{
            Utils.debugMessage(parent,"没找到textview "+value);
        }
    }

    public static void setTextView(View parent, int id, String value) {
        TextView tv = (TextView)parent.findViewById(id);
        if(tv!=null){
            tv.setText(value);
        }
    }


    /**
     * 装载用户信息到parent中相应的TextView中去
     * @param parent 含有很多TextView的页面
     * @param user 用户信息类
     * @param ids   TextView的id数组
     * @param attributes 跟数组对应的相应的属性
     */
    public static void loadUserInformation(ActionBarActivity parent,User user,int[] ids,String[] attributes){

        if(parent!=null && user!=null &&ids!=null && attributes!=null){
            for(int i=0;i<ids.length;i++){
                String ret = user.getFieldContent(attributes[i]);
                if(ret!=null){
                    Utils.setTextView(parent, ids[i],ret);
                }else{
                    Utils.debugMessage(parent,"找不到属性"+attributes[i]);
                }
            }
        }else{
            Utils.debugMessage(parent,"Utils.loadUserInformation输入的数据有错误");
        }

    }

}
