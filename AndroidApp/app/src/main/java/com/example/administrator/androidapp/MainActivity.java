package com.example.administrator.androidapp;

import android.content.Intent;
import android.os.Handler;
import android.os.StrictMode;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.regex.Pattern;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        Toast.makeText(MainActivity.this, "登录中", Toast.LENGTH_LONG).show();
        User user =  ToolClass.load(username,password);
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
        getInput();

        if(!"OK".equals(PatternValid.validUsername(username))){
            Toast.makeText(MainActivity.this, "�û�����ʽ����ȷ", Toast.LENGTH_LONG).show();
        }else if(!"OK".equals(PatternValid.validPassword(password))){
            Toast.makeText(MainActivity.this, "�����ʽ����ȷ", Toast.LENGTH_LONG).show();
        }else {
            if( "OK".equals(checkUser(username, password))){
                //��½�ɹ�
                Toast.makeText(MainActivity.this, "", Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, TotalActivity.class);
                MainActivity.this.startActivity(intent);
                MainActivity.this.finish();
            }else{
                Toast.makeText(MainActivity.this, "用户名或者密码错误", Toast.LENGTH_LONG).show();
            }
        }

    }

    //切换到注册界�?
    public void registered_Click(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, RegisteredActivity.class);
                MainActivity.this.startActivity(intent);
                MainActivity.this.finish();
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
