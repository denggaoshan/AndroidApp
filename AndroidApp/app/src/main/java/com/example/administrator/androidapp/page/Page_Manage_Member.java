package com.example.administrator.androidapp.page;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.androidapp.R;
import com.example.administrator.androidapp.msg.MyActivity;
import com.example.administrator.androidapp.msg.ToolClass;
import com.example.administrator.androidapp.msg.User;
import com.example.administrator.androidapp.msg.UserAndExplain;
import com.example.administrator.androidapp.msg.UserAndExplainArray;
import com.example.administrator.androidapp.tool.Utils;

public class Page_Manage_Member extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_member_manager);
        loadActivity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_page__member_manager, menu);
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

    private MyActivity currentActivity;

    //返回上级
    public void back_Click(View v) {
        Utils.backPage(this);
    }


    private UserAndExplain[] allRequests ; //所有请求

    private class MyAdapter extends BaseAdapter {

        UserAndExplain[] allRequests;

        public MyAdapter(UserAndExplain[] allRequests){
            this.allRequests = allRequests;
        }

        @Override
        public int getCount() {
            return allRequests.length;
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

            final UserAndExplain request = allRequests[position];

            Context ctx = convertView.getContext() ;
            LayoutInflater nflater = LayoutInflater.from(ctx);

            //装载请求信息
            convertView= nflater.inflate(R.layout.content_member_request, null);


            TextView tv = (TextView) convertView.findViewById(R.id.NickName);
            if(tv!=null){
                tv.setText(request.getUser().getNickName());
                //查看用户信息
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        User other = request.getUser();
                        if (other != null) {
                            User.setOtherUser(other);
                            Utils.transPage(Page_Manage_Member.this, Page_Information_Others.class);
                        } else {
                            Utils.debugMessage(Page_Manage_Member.this, "requset中User为空");
                        }
                    }
                });
            }else{
                Utils.debugMessage(Page_Manage_Member.this, "没找到名字标签");
            }

            tv = (TextView) convertView.findViewById(R.id.time);
            if(tv!=null){
                tv.setText(request.getTime());
                //查看用户信息
                tv.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        User other = request.getUser();
                        if (other != null) {
                            User.setOtherUser(other);
                            Utils.transPage(Page_Manage_Member.this, Page_Information_Others.class);
                        } else {
                            Utils.debugMessage(Page_Manage_Member.this, "requset中User为空");
                        }
                    }
                });
            }else{
                Utils.debugMessage(Page_Manage_Member.this,"没找到时间标签");
            }

            tv = (TextView) convertView.findViewById(R.id.content);
            if(tv!=null){
                tv.setText(request.getExpain());
            }else{
                Utils.debugMessage(Page_Manage_Member.this,"没有content标签");
            }


            //同意按钮
            Button btn = (Button)convertView.findViewById(R.id.agree);
            if(btn!=null){
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String ret = ToolClass.handleApplication(User.getCurrentUser().getUserID(),MyActivity.getCurrentActivity().getActivityID(), request.getUser().getUserID(), "1");
                        Utils.showMessage(Page_Manage_Member.this, ret);
                    }
                });
            }else{
                Utils.debugMessage(Page_Manage_Member.this,"没有agree按钮");
            }

            //拒绝
            btn = (Button)convertView.findViewById(R.id.refuse);
            if(btn!=null){
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String ret = ToolClass.handleApplication(User.getCurrentUser().getUserID(),MyActivity.getCurrentActivity().getActivityID(), request.getUser().getUserID(), "0");
                        Utils.showMessage(Page_Manage_Member.this, ret);
                    }
                });
            }else{
                Utils.debugMessage(Page_Manage_Member.this,"没有refuse按钮");
            }


            return convertView;
        }
    }

    //装载所有活动
    private void loadActivity(){
        currentActivity  = MyActivity.getCurrentActivity();
        UserAndExplainArray ret = ToolClass.getApplication(User.getCurrentUser().getUserID(), currentActivity.getActivityID());

        allRequests = ret.getUserAndExplains();

        ListView vi=(ListView) findViewById(R.id.requests);
        MyAdapter myAdapter = new MyAdapter(allRequests);
        vi.setAdapter(myAdapter);

    }


}
