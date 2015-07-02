package com.example.administrator.androidapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class Page_Registered extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
    }


    public void back_Click(View v) {
        Intent intent = new Intent();
        intent.setClass(Page_Registered.this, Page_Main.class);
        Page_Registered.this.startActivity(intent);
        Page_Registered.this.finish();
    }


    public void register_Click(View v){

        if("OK".equals(getInput())){
          User user = ToolClass.register(account,password,sex,phone,mailbox,avatar);

            if(user.ifLoading()){
                //ע��ɹ���ת����½����
                Toast.makeText(this, "ע��ɹ�", Toast.LENGTH_LONG).show();
            }else{
                //ע��ʧ��
                Toast.makeText(this, "ע��ʧ��", Toast.LENGTH_LONG).show();
                return;
            }

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


    private String getInput() {

        account = ((EditText) findViewById(R.id.account_RegText)).getText().toString();
        if(!"OK".equals(PatternValid.validUsername(account))){
            //�û�����֤ʧ��
            Toast.makeText(this, "�û�����ʽ����ȷ", Toast.LENGTH_LONG).show();
            return "ERROR";
        }

        password =((EditText) findViewById(R.id.password_RegTxt)).getText().toString();
        if(!"OK".equals(PatternValid.validPassword(password))){
            Toast.makeText(this, "�����ʽ����ȷ", Toast.LENGTH_LONG).show();
        }
        String repassword = ((EditText) findViewById(R.id.repassword_RegTxt)).getText().toString();
       if(!repassword.equals(password)){
           //���벻һ��
           Toast.makeText(this, "������������벻һ��", Toast.LENGTH_LONG).show();
           return "ERROR";
       }


        phone = ((EditText) findViewById(R.id.phone_RegTxt)).getText().toString();
        if(!"OK".equals(PatternValid.validPhone(account))){
            //��֤�ֻ���ʽʧ��
            Toast.makeText(this, "�ֻ������ʽ����ȷ", Toast.LENGTH_LONG).show();
            return "ERROR";
        }

        if(findViewById(R.id.girl).isSelected()){
            sex = "1";
        }else{
            sex = "0";
        }

        //ͷ�� TO DO
        avatar = "";

        mailbox = ((EditText) findViewById(R.id.email_RegTxt)).getText().toString();
        if(!"OK".equals(PatternValid.validPhone(mailbox))){
            //��֤�����ʽʧ��
            Toast.makeText(this, "�����ʽ����ȷ", Toast.LENGTH_LONG).show();
            return "ERROR";
        }
        return "OK";
    }
}
