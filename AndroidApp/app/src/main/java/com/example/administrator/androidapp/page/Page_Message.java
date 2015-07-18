package com.example.administrator.androidapp.page;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.androidapp.R;
import com.example.administrator.androidapp.msg.Cache;
import com.example.administrator.androidapp.msg.Inform;
import com.example.administrator.androidapp.msg.InformArray;
import com.example.administrator.androidapp.msg.ToolClass;
import com.example.administrator.androidapp.msg.User;
import com.example.administrator.androidapp.tool.Utils;

import java.util.ArrayList;

public class Page_Message extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_message);

        currentUser = User.getCurrentUser();

        showSystemMessage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_page__message, menu);
        return true;
    }

    User currentUser ;




    boolean ifLoadInforms = false;




    private class MyAdapter extends BaseAdapter{
        ArrayList<Inform> informs;

        public MyAdapter(ArrayList<Inform> infs){
            informs = infs;
        }

        @Override
        public int getCount() {
            if(informs!=null){
                return informs.size();
            }else{
                //没有消息
                return 1;
            }

        }
        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = new LinearLayout(parent.getContext());

            Context ctx = convertView.getContext() ;
            LayoutInflater nflater = LayoutInflater.from(ctx);

            if(informs!=null ){
                Inform inform = informs.get(position);

                convertView= nflater.inflate(R.layout.content_message, null);
                if(convertView!=null){
                    try{
                        TextView tv = (TextView) convertView.findViewById(R.id.name);

                        String tmp = inform.getUserSource().getNickName();
                        tv.setText(tmp);

                        tv = (TextView)convertView.findViewById(R.id.time);
                        tmp=inform.getTime();
                        tv.setText(tmp);

                        tv = (TextView)convertView.findViewById(R.id.content);
                        tmp=inform.getContent() ;
                        tv.setText(tmp);
                    }catch (NullPointerException e){
                        Utils.debugMessage(Page_Message.this,"界面上存在空指针");
                    }
                }
            }else{
                //空的消息
                //没活动的话
                convertView = nflater.inflate(R.layout.content_tips, null);
                TextView tv = (TextView) convertView.findViewById(R.id.content);
                tv.setText("没有任何消息");
            }


            return convertView;
        }
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

    private void cleanContent(){
        ListView lv = (ListView)findViewById(R.id.content);
        if(lv!=null){
            lv.removeAllViewsInLayout();
        }else{
            Utils.debugMessage(Page_Message.this,"没有content");
        }
    }

    //显示系统消息
    private void showSystemMessage(){
        ListView vi=(ListView) findViewById(R.id.content);
        vi.removeAllViewsInLayout();
        MyAdapter adapter = new MyAdapter(Cache.getSystemInforms(User.getCurrentUser().getUserID()));
        vi.setAdapter(adapter);
    }

    //显示活动消息
    private void showActivityMessage(){
        ListView vi=(ListView) findViewById(R.id.content);
        vi.removeAllViewsInLayout();
        MyAdapter adapter = new MyAdapter(Cache.getActivityInforms(User.getCurrentUser().getUserID()));
        vi.setAdapter(adapter);
    }

    //显示私人消息
    private void showPrivateMessage(){
        ListView vi=(ListView) findViewById(R.id.content);
        vi.removeAllViewsInLayout();
        MyAdapter adapter = new MyAdapter(Cache.getPrivatedInforms(User.getCurrentUser().getUserID()));
        vi.setAdapter(adapter);
    }


    /* 点击事件 */
    //点击系统消息
    public void system_Click(View v){
        cleanContent();
        showSystemMessage();
    }

    //点击私信
    public void private_Click(View v){
        cleanContent();
        showPrivateMessage();
    }

    //点击活动消息
    public void activity_Click(View v){
        cleanContent();
        showActivityMessage();
    }

    //返回上级
    public void back_Click(View v) {
        Utils.backPage(this);
    }

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
