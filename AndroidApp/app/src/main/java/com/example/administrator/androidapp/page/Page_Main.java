package com.example.administrator.androidapp.page;

import android.content.Intent;
import android.os.StrictMode;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.androidapp.tool.PatternValid;
import com.example.administrator.androidapp.R;
import com.example.administrator.androidapp.tool.ToolClass;
import com.example.administrator.androidapp.core.User;


public class Page_Main extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_main);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
    }

    private String username;
    private String password;

    private void getInput(){
        EditText us=(EditText) findViewById(R.id.username);
        EditText pw=(EditText) findViewById(R.id.password);
        username=us.getText().toString();
        password=pw.getText().toString();
    }

    private String checkUser(String username,String password){
        User user =  ToolClass.load(username, password);
        if(user==null){
            return "NO";
        }
        if(user.ifLoading()){
            User.setUser(user);
            return "OK";
        }
        return "NO";
    }

    public void landing_Click(View v) {
        //获得输入框文本
        getInput();
        Toast.makeText(Page_Main.this, "登录中", Toast.LENGTH_LONG).show();

        if(!"OK".equals(PatternValid.validUsername(username))){
            Toast.makeText(Page_Main.this, "用户名格式不正确", Toast.LENGTH_LONG).show();
        }else if(!"OK".equals(PatternValid.validPassword(password))){
            Toast.makeText(Page_Main.this, "密码格式不正确", Toast.LENGTH_LONG).show();
        }else {
            if( "OK".equals(checkUser(username, password))){
                //登陆成功
                Toast.makeText(Page_Main.this, "登陆成功", Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.setClass(Page_Main.this, Page_TotalActivity.class);
                Page_Main.this.startActivity(intent);
                Page_Main.this.finish();
            }else{
                Toast.makeText(Page_Main.this, "用户名或者密码错误", Toast.LENGTH_LONG).show();
            }
        }
    }

    //切换到注册界面
    public void registered_Click(View v) {
                Intent intent = new Intent();
                intent.setClass(Page_Main.this, Page_Registered.class);
                Page_Main.this.startActivity(intent);
                Page_Main.this.finish();
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
