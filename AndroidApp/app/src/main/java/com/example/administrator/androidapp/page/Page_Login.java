package com.example.administrator.androidapp.page;

import android.os.StrictMode;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.administrator.androidapp.msg.Current;
import com.example.administrator.androidapp.tool.PatternValid;
import com.example.administrator.androidapp.R;
import com.example.administrator.androidapp.msg.ToolClass;
import com.example.administrator.androidapp.msg.MyMessage;
import com.example.administrator.androidapp.tool.Utils;



public class Page_Login extends ActionBarActivity {

    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        String jsonMsg = Utils.getLogData();
        if (jsonMsg != null && !jsonMsg.equals(""))
        {
            MyMessage tempMsg = MyMessage.createMessage(jsonMsg);
            Current.setCurrentUser(tempMsg.getUser());
            if (checkMess(tempMsg.getMess())) {
                Current.setCurrentMyMessage(tempMsg);
                Utils.transPage(this, Page_TotalActivity.class);
                return;
            }
        }
        setContentView(R.layout.page_login);
    }

    private void getInput(){
        username= Utils.getValueOfEditText(this,R.id.username);
        password= Utils.getValueOfEditText(this,R.id.password);
    }

    private String checkUser(String username,String password){
        MyMessage msg =  ToolClass.load(username, password);
        if(msg == null){
            return "NO";
        }
        if (checkMess(msg.getMess()))
        {
            Utils.clearLogData();
            Utils.storeLogData(msg.getJsonString());
            Current.setCurrentMyMessage(msg);
            Current.setCurrentUser(msg.getUser());
            return "OK";
        }
        else
            return "NO";
    }

    private boolean checkMess(String mess)
    {
        if (mess == null || mess.equals("loginfail") || mess.equals(""))
            return false;
        else
            return true;
    }

    public void landing_Click(View v) {
        //获得输入框文本
        getInput();
        Utils.showMessage(Page_Login.this, "登录中");

        if(!"OK".equals(PatternValid.validUsername(username))){
            Utils.showMessage(this, "用户名格式不正确");
        }else if(!"OK".equals(PatternValid.validPassword(password))){
            Utils.showMessage(this, "密码格式不正确");
        }else {
            if( "OK".equals(checkUser(username, password))){
                //登陆成功
                Utils.showMessage(this, "登陆成功");
                Utils.transPage(this,Page_TotalActivity.class);
            }else{
                Utils.showMessage(this,"用户名或者密码错误");
            }
        }
    }

    //切换到注册界面
    public void registered_Click(View v) {
        Utils.transPage(this, Page_Registered.class);
    }

    public void tourist_Click(View v){
        username = "Tourist";
        password = "*";

        if( "OK".equals(checkUser(username, password))){
            //登陆成功
            Utils.showMessage(this, "游客登陆成功");
            Utils.transPage(this,Page_TotalActivity.class);
        }else{
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
