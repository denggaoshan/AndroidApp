package com.example.administrator.androidapp.page;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.androidapp.tool.PatternValid;
import com.example.administrator.androidapp.R;
import com.example.administrator.androidapp.tool.ToolClass;
import com.example.administrator.androidapp.core.User;
import com.example.administrator.androidapp.tool.Utils;


public class Page_Registered extends ActionBarActivity {

    private String account;
    private String password;
    private String sex;
    private String avatar;
    private String phone;
    private String mailbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_registered);
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


    public void back_Click(View v) {
        Utils.transPage(this, Page_Main.class);
    }

    public void register_Click(View v){
        if("OK".equals(getInput())){
            User user = ToolClass.register(account, password, sex, phone, mailbox, avatar);

            if(user.ifLoading()){
                Toast.makeText(this, "注册成功", Toast.LENGTH_LONG).show();
                User.setUser(user);
                //登陆
                Utils.transPage(this, Page_Total.class);
            }else{
                Toast.makeText(this, "注册失败", Toast.LENGTH_LONG).show();
                return;
            }
        }
    }

    private String getInput() {
        account = ((EditText) findViewById(R.id.account_RegText)).getText().toString();
        if(!"OK".equals(PatternValid.validUsername(account))){
            Toast.makeText(this, "用户名格式不正确", Toast.LENGTH_LONG).show();
            return "NO";
        }
        password =((EditText) findViewById(R.id.password_RegTxt)).getText().toString();
        if(!"OK".equals(PatternValid.validPassword(password))){
            Toast.makeText(this, "密码格式不正确", Toast.LENGTH_LONG).show();
        }
        String repassword = ((EditText) findViewById(R.id.repassword_RegTxt)).getText().toString();
       if(!repassword.equals(password)){
           Toast.makeText(this, "两次密码输入不一致", Toast.LENGTH_LONG).show();
           return "NO";
       }
        phone = ((EditText) findViewById(R.id.phone_RegTxt)).getText().toString();
        if(!"OK".equals(PatternValid.validPhone(account))){
            Toast.makeText(this, "手机号码格式不正确", Toast.LENGTH_LONG).show();
            return "NO";
        }

        if(findViewById(R.id.girl).isSelected()){
            sex = "1";
        }else{
            sex = "0";
        }

        avatar = "";

        mailbox = ((EditText) findViewById(R.id.email_RegTxt)).getText().toString();
        if(!"OK".equals(PatternValid.validPhone(mailbox))){
            Toast.makeText(this, "邮箱格式不正确", Toast.LENGTH_LONG).show();
            return "NO";
        }
        return "OK";
    }
}
