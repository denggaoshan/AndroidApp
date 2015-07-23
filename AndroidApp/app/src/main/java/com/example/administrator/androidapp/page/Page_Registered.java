package com.example.administrator.androidapp.page;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.administrator.androidapp.msg.Current;
import com.example.administrator.androidapp.msg.User;
import com.example.administrator.androidapp.tool.PatternValid;
import com.example.administrator.androidapp.R;
import com.example.administrator.androidapp.msg.ToolClass;
import com.example.administrator.androidapp.msg.MyMessage;
import com.example.administrator.androidapp.tool.Utils;


public class Page_Registered extends BasePage {

    private String account;
    private String password;
    private String sex;
    private String avatar;
    private String phone;
    private String mailbox;

    private String DEFAULTIMG = "http://chenranzhen.xyz/Upload/Avatar/Default.png";//默认图片路径

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

    public void choose_Click(View v)
    {
       this.showPictureSelect();
    }



    public void register_Click(View v){
        if("OK".equals(getInput())){

            String picturePath =Current.getPicturePath();

            if (picturePath == null){
                avatar = DEFAULTIMG;
            }
            else{
                avatar = ToolClass.uploadFile(picturePath);
            }

             MyMessage msg = ToolClass.register(account, password, sex, phone, mailbox, avatar);

            if(checkMess(msg.getMess())){
                Utils.showMessage(this, "注册成功");
                User user = msg.getUser();
                if(user!=null){
                    Current.setUser(msg.getUser());
                    //登陆
                    Utils.storeLogData(msg.getJsonString());
                    Utils.transPage(this,Page_TotalActivity.class);
                }else{
                    Utils.debugMessage(this,"debug 0001 user为空");
                }
            }else{
                Utils.showMessage(this, "注册失败");
                return;
            }
        }
    }

    private boolean checkMess(String mess)
    {
        if(mess.equals("ok")){
        return true;
        }else{
            return false;
        }
    }

    private String getInput() {
        account = ((EditText) findViewById(R.id.account_RegText)).getText().toString();
        if(!"OK".equals(PatternValid.validUsername(account))){
            Utils.showMessage(this,"用户名格式不正确");
            return "NO";
        }
        password =((EditText) findViewById(R.id.password_RegTxt)).getText().toString();
        if(!"OK".equals(PatternValid.validPassword(password))){
            Utils.showMessage(this,"密码格式不正确");
        }
        String repassword = ((EditText) findViewById(R.id.repassword_RegTxt)).getText().toString();
       if(!repassword.equals(password)){
           Utils.showMessage(this,"两次密码输入不一致");
           return "NO";
       }
        phone = ((EditText) findViewById(R.id.phone_RegTxt)).getText().toString();
        if(!"OK".equals(PatternValid.validPhone(account))){
            Utils.showMessage(this,"手机号码格式不正确");
            return "NO";
        }

        if(findViewById(R.id.girl).isSelected()){
            sex = "1";
        }else{
            sex = "0";
        }

        avatar = "";

        mailbox = ((EditText) findViewById(R.id.email_RegTxt)).getText().toString();
        if(!"OK".equals(PatternValid.validEmail(mailbox))){
            Utils.showMessage(this,"邮箱格式不正确");
            return "NO";
        }
        return "OK";
    }

}
