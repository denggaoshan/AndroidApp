package com.example.administrator.androidapp.page;

import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;

import com.example.administrator.androidapp.tool.Utils;

/**
 * Created by dengaoshan on 2015/7/20.
 * 基础页面类
 * 包含所有页面公共的方法
 */
public abstract class BasePage extends ActionBarActivity {

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
}
