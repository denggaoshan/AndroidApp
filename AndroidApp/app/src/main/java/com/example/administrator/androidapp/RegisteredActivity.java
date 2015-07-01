package com.example.administrator.androidapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class RegisteredActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
    }

    public void back_Click(View v) {
        Intent intent = new Intent();
        intent.setClass(RegisteredActivity.this, MainActivity.class);
        RegisteredActivity.this.startActivity(intent);
        RegisteredActivity.this.finish();
    }


    public void register_Click(View v){

        if("OK".equals(getInput())){
            
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registered, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private String account;
    private String password;
    private String sex;
    private String avatar;
    private String phone;
    private String mailbox;

    public String getInput() {

        account = ((EditText) findViewById(R.id.account_RegText)).getText().toString();
        if(!"OK".equals(PatternValid.validUsername(account))){
            //用户名验证失败
            return "ERROR";
        }

        password =((EditText) findViewById(R.id.password_RegTxt)).getText().toString();
        String repassword = ((EditText) findViewById(R.id.repassword_RegTxt)).getText().toString();
       if(!repassword.equals(password)){
           //密码不一致
           return "ERROR";
       }

        phone = ((EditText) findViewById(R.id.phone_RegTxt)).getText().toString();
        if(!"OK".equals(PatternValid.validPhone(account))){
            //验证手机格式失败
            return "ERROR";
        }

        //头像 TO DO
        avatar = "";

        mailbox = ((EditText) findViewById(R.id.email_RegTxt)).getText().toString();
        if(!"OK".equals(PatternValid.validPhone(mailbox))){
            //验证邮箱格式失败
            return "ERROR";
        }
        return "OK";
    }
}
