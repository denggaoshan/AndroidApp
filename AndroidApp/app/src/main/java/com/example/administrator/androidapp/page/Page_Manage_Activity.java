package com.example.administrator.androidapp.page;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.androidapp.R;
import com.example.administrator.androidapp.msg.Cache;
import com.example.administrator.androidapp.msg.DateFactory;
import com.example.administrator.androidapp.msg.Message;
import com.example.administrator.androidapp.msg.MyActivity;
import com.example.administrator.androidapp.msg.ToolClass;
import com.example.administrator.androidapp.msg.User;
import com.example.administrator.androidapp.tool.AsynImageLoader;
import com.example.administrator.androidapp.tool.Utils;

import java.util.Calendar;
import java.util.Date;

public class Page_Manage_Activity extends ActionBarActivity {

    private MyActivity[] allLauncheActivities;
    private MyActivity[] allParticipatedActivities;
    private MyActivity[] allApplyingActivities;

    private boolean ifLoadLauncheActivities =false;
    private boolean ifLoadPartactivityActivities =false;
    private boolean ifLoadAppliactivityActivities =false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_manager);
        loadUserInformation();
        shoLauncheActivities();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_manager, menu);
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

    /**********   装载信息   *************/
    //装载用户名和头像
    private void loadUserInformation(){
        Message msg = Message.getCurrentMessage();
        TextView account = (TextView)findViewById(R.id.Account);
        account.setText(msg.getUser().getAccount());
        try {
            ImageView iv = (ImageView)findViewById(R.id.imageView8);
            if (Cache.getUserAvater() != null)
                iv.setImageBitmap(ToolClass.resizeBitmap(Cache.getUserAvater(), this, iv.getWidth(), iv.getHeight()));
            else{
                AsynImageLoader asynImageLoader = new AsynImageLoader();
                asynImageLoader.showImageAsyn(((ImageView)findViewById(R.id.imageView8)), User.getCurrentUser().getAvatar(), 0);
            }
        }catch (Exception e){
            Utils.debugMessage(this,"头像这里有BUG啊");
        }
    }

    //重新装载发起活动
    private void loadLauncheActivities(){
        Message msg = ToolClass.getLaunchedActivity(User.getCurrentUser().getUserID());
        allLauncheActivities = msg.getActivities();
        ifLoadLauncheActivities = true;
    }

    //重新装载参与活动
    private void loadParticipatedActivities(){
        Message msg = ToolClass.getParticipatedActivity(User.getCurrentUser().getUserID());
        allParticipatedActivities = msg.getActivities();
        ifLoadPartactivityActivities = true;
    }

    //重新装载申请中
    private void loadApplicatedActivities(){
        Message msg = ToolClass.getApplicatedActivity(User.getCurrentUser().getUserID());
        allApplyingActivities = msg.getActivities();
        ifLoadAppliactivityActivities = true;
    }



    private void changeFocus(int i){
        int[] ids = {R.id.running,R.id.history,R.id.applying};

        for(int id:ids){
            LinearLayout layout=(LinearLayout) findViewById(id);
            layout.setBackgroundColor(Color.WHITE);
        }

        LinearLayout rn=(LinearLayout) findViewById(ids[i]);
        rn.setBackgroundColor(0xFF50d2c2);
    }

    /***************** 所有适配器类  ****************/
    private class MyAdapter extends BaseAdapter{

        MyActivity[] allData;

        public MyAdapter(MyActivity[] activities){
            allData=activities;
        }

        @Override
        public int getCount() {
            if(allData !=null){
                return allData.length;
            }
            return 0;
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

            Context ctx = convertView.getContext();
            LayoutInflater nflater = LayoutInflater.from(ctx);

            final MyActivity activity = allData[position];

            convertView = nflater.inflate(R.layout.content_manager_activity, null);

            int[] ids = {R.id.title,R.id.startTime,R.id.endTime,R.id.position,R.id.attending};
            String[] vals ={activity.getTitle(),activity.getStartTime(),activity.getEndTime(),activity.getPlace(),activity.getUserCount()+" 人"};

            for(int i=0;i<ids.length;i++){
                TextView tv = (TextView)convertView.findViewById(ids[i]);
                tv.setText(vals[i]);
            }

            String status = activity.getIsChecked();
            if(status!=null){
                TextView tv = (TextView)convertView.findViewById(R.id.status);

                if(tv!=null){
                    if(status.equals("0")){
                        tv.setText("审核中");
                        tv.setBackgroundColor(0xFFc8c8a9);
                    }else if(status.equals("1")){

                        String startTimeString = activity.getStartTime();
                        Date startTime = DateFactory.createDateByString(startTimeString);
                        String endTimeString = activity.getEndTime();
                        Date endTime = DateFactory.createDateByString(endTimeString);
                        Date now =  DateFactory.getCurrentTime();

                        if(now.compareTo(startTime)<0){
                            tv.setText("尚未开始");
                            tv.setBackgroundColor(0xff83af9b);

                        }else if(now.compareTo(startTime)>0 && now.compareTo(endTime)< 0){
                            tv.setText("正在进行中");
                            tv.setBackgroundColor(0xfffc9d9a);
                        }else if(now.compareTo(endTime)>0){
                            tv.setText("已经过期");
                            tv.setBackgroundColor(0xffeae3e3);
                        }
                    }
                }else{
                    Utils.debugMessage(Page_Manage_Activity.this,"status为null");
                }
            }
            //绑定点击事件
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   if(activity!=null){
                       MyActivity.setCurrentActivity(activity);
                       Utils.transPage(Page_Manage_Activity.this,Page_Information_Activity.class);
                   }
                }
            });
            return convertView;
        }
    }


    /*******************   显示内容   ********************/
    //显示我发起的活动
    private void shoLauncheActivities(){
        if(ifLoadLauncheActivities ==false){
            loadLauncheActivities();
        }
        ListView vi=(ListView) findViewById(R.id.content);
        vi.removeAllViewsInLayout();
        MyAdapter adapter = new MyAdapter(allLauncheActivities);
        vi.setAdapter(adapter);
    }

    //显示我参与的活动
    private void showPartactivityActivities(){
        if(ifLoadPartactivityActivities ==false){
            loadParticipatedActivities();
        }
        ListView vi=(ListView) findViewById(R.id.content);
        vi.removeAllViewsInLayout();
        MyAdapter adapter = new MyAdapter(allParticipatedActivities);
        vi.setAdapter(adapter);
    }

    //显示我申请的活动
    private void shoAppliactivityActivities(){
        if(ifLoadAppliactivityActivities ==false){
            loadApplicatedActivities();
        }
        ListView vi=(ListView) findViewById(R.id.content);
        vi.removeAllViewsInLayout();
        MyAdapter adapter = new MyAdapter(allApplyingActivities);
        vi.setAdapter(adapter);
    }


    private String DEFAULTAVATER = "http://chenranzhen.xyz/Upload/Avatar/Default.png";

    /***************  点击事件 **************/
    public void back_Click(View v) {
        Utils.backPage(this);
    }

    public void launched_Click(View v) {
        changeFocus(0);
        shoLauncheActivities();
    }

    public void participated_Click(View v) {
       changeFocus(1);
        showPartactivityActivities();
    }


    public void applyed_Click(View v) {
         changeFocus(2);
        shoAppliactivityActivities();
    }



}
