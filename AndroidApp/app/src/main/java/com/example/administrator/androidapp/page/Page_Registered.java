package com.example.administrator.androidapp.page;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.androidapp.msg.Current;
import com.example.administrator.androidapp.msg.User;
import com.example.administrator.androidapp.tool.PatternValid;
import com.example.administrator.androidapp.R;
import com.example.administrator.androidapp.msg.ToolClass;
import com.example.administrator.androidapp.msg.MyMessage;
import com.example.administrator.androidapp.tool.Utils;

import java.io.IOException;


public class Page_Registered extends BasePage {

    private String account;
    private String password;
    private String sex;
    private String avatar;
    private String phone;
    private String mailbox;
    private String DEFAULTIMG = "http://chenranzhen.xyz/Upload/Avatar/Default.png";

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

    private int TAKE_PICTURE = 1234;
    private String picPath = null;
    public void choose_Click(View v)
    {
        try{
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, TAKE_PICTURE);
        }catch (Exception e){
            Utils.showMessage(this,"上传头像失败"+e.toString());
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK){
            switch (requestCode) {
                case 1234:
                    Bitmap bitmap;
                    Uri originalUri = data.getData();
                    ContentResolver resolver = getContentResolver();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                    } catch (IOException e){
                        bitmap = null;
                    }
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    float density = getResources().getDisplayMetrics().density;
                    float newWidth = 130 * density;
                    float newHeight = 130 * density;
                    float scaleWidth = ((float)newWidth) / width;
                    float scaleHeight = ((float)newHeight) / height;
                    Matrix matrix = new Matrix();
                    matrix.postScale(scaleWidth, scaleHeight);
                    Bitmap resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, width,
                            height, matrix, true);
                    ((ImageView) findViewById(R.id.imageView2)).setImageBitmap(resizeBitmap);

                    String[] proj = {MediaStore.Images.Media.DATA};
                    Cursor cursor = managedQuery(originalUri, proj, null, null, null);
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    String path = cursor.getString(column_index);
                    picPath = path;
                    break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }




    public void register_Click(View v){
        if("OK".equals(getInput())){
            if (picPath == null)
                avatar = DEFAULTIMG;
            else
                avatar = ToolClass.uploadFile(picPath);
             MyMessage msg = ToolClass.register(account, password, sex, phone, mailbox, avatar);
            if(checkMess(msg.getMess())){
                Utils.showMessage(this, "注册成功");
                Current.setCurrentMyMessage(msg);
                User user = msg.getUser();
                if(user!=null){
                    Current.setCurrentUser(msg.getUser());
                    //登陆
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
