package com.example.administrator.androidapp.page;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.androidapp.R;
import com.example.administrator.androidapp.msg.Current;
import com.example.administrator.androidapp.tool.PicUtil;
import com.example.administrator.androidapp.tool.Utils;

import java.io.IOException;

/**
 * Created by dengaoshan on 2015/7/20.
 * 基础页面类
 * 包含所有页面公共的方法
 */
public abstract class BasePage extends ActionBarActivity {


    private static final int TAKE_PICTURE = 1;

    //返回上级按钮
    public void back_Click(View v) {
        Utils.backPage(this);
    }

    //按后退键盘返回上级
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Utils.backPage(this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //文件管理的回调函数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            try {
                Uri originalUri = data.getData(); //图片数据存在Uri中
                ContentResolver resolver = getContentResolver();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(resolver, originalUri);

                if(bitmap!=null){
                    //设置图片界面
                    ImageView iv =  ((ImageView) findViewById(R.id.img_head));
                    if(iv!=null){
                        iv.setImageBitmap(bitmap);
                    }

                    String path = PicUtil.getRealFilePath(this, originalUri);
                    if(path!=null){
                        Current.setCurrentPicture(path);
                    }

                }else{
                    Utils.debugMessage(this,"没找到imageView");
                }
            }catch (Exception e){
                Utils.showMessage(this,"获取图片失败");
                return;
            }
        }else {
            Utils.showMessage(this, "图片上传失败");
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    //显示图片选择页面,返回所选择的图片的路径
    public String showPictureSelect() {
        try{
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, TAKE_PICTURE);
        }catch (Exception e){

        }
        String path = Current.getCurrentPicture();
        if(path!=null){
            return path;
        }else{
            Utils.debugMessage(this,"path 为 null");
            return null;
        }
    }
}
