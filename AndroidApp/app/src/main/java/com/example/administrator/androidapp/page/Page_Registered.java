package com.example.administrator.androidapp.page;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.androidapp.tool.PatternValid;
import com.example.administrator.androidapp.R;
import com.example.administrator.androidapp.msg.ToolClass;
import com.example.administrator.androidapp.msg.Message;
import com.example.administrator.androidapp.tool.Utils;


public class Page_Registered extends ActionBarActivity {

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

    private String picPath = null;
    public void choose_Click(View v)
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            /**
             * 当选择的图片不为空的话，在获取到图片的途径
             */
            Uri uri = data.getData();
            //Log.e(TAG, "uri = " + uri);
            try {
                String[] pojo = { MediaStore.Images.Media.DATA };

                Cursor cursor = managedQuery(uri, pojo, null, null, null);
                if (cursor != null) {
                    ContentResolver cr = this.getContentResolver();
                    int colunm_index = cursor
                            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    String path = cursor.getString(colunm_index);
                    /***
                     * 这里加这样一个判断主要是为了第三方的软件选择，比如：使用第三方的文件管理器的话，你选择的文件就不一定是图片了，
                     * 这样的话，我们判断文件的后缀名 如果是图片格式的话，那么才可以
                     */
                    if (path.endsWith("jpg") || path.endsWith("png")) {
                        picPath = path;
                        Bitmap bitmap = BitmapFactory.decodeStream(cr
                                .openInputStream(uri));
                        ((ImageView) findViewById(R.id.imageView2)).setImageBitmap(bitmap);
                    } else {
                        alert();
                    }
                } else {
                    alert();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    private void alert() {
        Dialog dialog = new AlertDialog.Builder(this).setTitle("提示")
                .setMessage("您选择的不是有效的图片")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        picPath = null;
                    }
                }).create();
        dialog.show();
    }

    public void back_Click(View v) {
        Utils.transPage(this, Page_Main.class);
    }

    public void register_Click(View v){
        if("OK".equals(getInput())){
            if (picPath == null)
                avatar = DEFAULTIMG;
            else
                avatar = ToolClass.uploadFile(picPath);
             Message msg = ToolClass.register(account, password, sex, phone, mailbox, avatar);

            if(checkMess(msg.getMess())){
                Toast.makeText(this, "注册成功", Toast.LENGTH_LONG).show();
                Message.setCurrentMessage(msg);
                //登陆
                Toast.makeText(Page_Registered.this, "", Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.setClass(Page_Registered.this, Page_TotalActivity.class);
                Page_Registered.this.startActivity(intent);
                Page_Registered.this.finish();
            }else{
                Toast.makeText(this, "注册失败", Toast.LENGTH_LONG).show();
                return;
            }
        }
    }

    private boolean checkMess(String mess)
    {
        if (mess == null || mess.equals("register") || mess.equals(""))
            return false;
        else
            return true;
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
