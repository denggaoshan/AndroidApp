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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.androidapp.R;
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

        loadMessage("0");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_page__message, menu);

        return true;
    }

    User currentUser ;
    InformArray informsArr;
    Inform[] informs;

    /*加载系统消息*/
    private void loadMessage(String type) {

        currentUser = User.getCurrentUser();

        if(currentUser!=null){
            if(informsArr==null){
                informsArr = ToolClass.getInform(currentUser.getUserID());
            }
            if(informsArr!=null){
                informs = informsArr.getInforms();

                //筛选类型为type的
                ArrayList<Inform> tmps = new ArrayList<Inform>();
                for(Inform info:informs){
                    if(info.getType().equals(type)){
                        tmps.add(info);
                    }
                }
                informs = new Inform[tmps.size()];
                for(int i=0;i<informs.length;i++){
                    informs[i] = tmps.get(i);
                }

                //加载活动适配器
                ListView vi = (ListView)findViewById(R.id.content);
                if(vi!=null){
                    MyAdapter myAdapter = new MyAdapter(informs);
                    vi.setAdapter(myAdapter);
                }else{
                    Utils.debugMessage(Page_Message.this,"没有content");
                }
            }else{
                Utils.debugMessage(Page_Message.this,"当前用户没有消息");
            }
        }else{
            Utils.debugMessage(Page_Message.this,"当前的用户为空");
        }

    }


    private class MyAdapter extends BaseAdapter{
        Inform[] informs;

        public MyAdapter(Inform[] infs){
            informs = infs;
        }
        @Override
        public int getCount() {
            return informs.length;
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
            Inform inform = informs[position];

            convertView= nflater.inflate(R.layout.content_message, null);
            if(convertView!=null){
                try{
                    TextView tv = (TextView) convertView.findViewById(R.id.name);
                    String tmp = ToolClass.getUserInfo(currentUser.getUserID(), inform.getForm()).getUser().getNickName();
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

    /* 点击事件 */

    //点击系统消息
    public void system_Click(View v){
        cleanContent();
        loadMessage("0");
    }

    //点击私信
    public void private_Click(View v){
        cleanContent();
        loadMessage("1");
    }

    //点击活动消息
    public void activity_Click(View v){
        cleanContent();
        loadMessage("2");
    }

    //返回上级
    public void back_Click(View v) {
        Utils.transPage(this, Page_TotalActivity.class);
    }
}
