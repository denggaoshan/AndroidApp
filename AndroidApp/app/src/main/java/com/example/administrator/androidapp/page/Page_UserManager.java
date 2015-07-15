package com.example.administrator.androidapp.page;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.administrator.androidapp.R;
import com.example.administrator.androidapp.msg.Cache;
import com.example.administrator.androidapp.msg.Message;
import com.example.administrator.androidapp.msg.MyActivity;
import com.example.administrator.androidapp.msg.ToolClass;
import com.example.administrator.androidapp.msg.User;
import com.example.administrator.androidapp.tool.AsynImageLoader;
import com.example.administrator.androidapp.tool.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Page_UserManager extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_manager);

        Message msg = Message.getCurrentMessage();
        TextView account = (TextView)findViewById(R.id.Account);
        account.setText(msg.getUser().getAccount());
        ListView vi=(ListView) findViewById(R.id.content);
        vi.removeAllViewsInLayout();
        loadLaunchedActivity();
        ImageView iv = (ImageView)findViewById(R.id.imageView8);
        if (Cache.getUserAvater() != null)
            iv.setImageBitmap(ToolClass.resizeBitmap(Cache.getUserAvater(), this, iv.getWidth(), iv.getHeight()));
        else{
            AsynImageLoader asynImageLoader = new AsynImageLoader();
            asynImageLoader.showImageAsyn(((ImageView)findViewById(R.id.imageView8)), User.getCurrentUser().getAvatar(), 0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_manager, menu);
        return true;
    }

    public void close_Click(View v) {
        Utils.transPage(this, Page_TotalActivity.class);
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


    public void launched_Click(View v) {
       changeFocus(0);
        ListView vi=(ListView) findViewById(R.id.content);
        vi.removeAllViewsInLayout();
        loadLaunchedActivity();
    }

    private void loadLaunchedActivity(){
        ListView vi=(ListView) findViewById(R.id.content);
        vi.removeAllViewsInLayout();
        SimpleAdapter adapter = new SimpleAdapter(this, getLaunchedData(), R.layout.content_manager_activity,
                new String[] { "title", "time", "position", "attending", "avater","status"},
                new int[] { R.id.title, R.id.time, R.id.position, R.id.attending, R.id.avater,R.id.status});

        vi.setAdapter(adapter);
    }

    private List<? extends Map<String, ?>> getLaunchedData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Message msg = ToolClass.getLaunchedActivity(User.getCurrentUser().getUserID());
        if (msg.getActivities() != null){
            for (MyActivity ac : msg.getActivities()){
                list.add(getOneLaunched(ac));
            }
        }
        return list;
    }

    private String DEFAULTAVATER = "http://chenranzhen.xyz/Upload/Avatar/Default.png";

    private Map<String, Object> getOneLaunched(MyActivity ac){
        Map<String, Object> ret = new HashMap<String, Object>();
        String ava;
        if (ac.getAvatar() == null || ac.getAvatar().equals(""))
            ava = DEFAULTAVATER;
        else
            ava = ac.getAvatar();
        ret.put("title", ac.getTitle());
        ret.put("time", ac.getStartTime());
        ret.put("position", ac.getPlace());
        ret.put("attending", ac.getUserCount());
        ret.put("avater", ToolClass.returnBitMap(ava));

        if(!ac.getIsChecked().equals("1")){//申请通过的活�?
            String endtime = ac.getEndTime();
            if(Utils.ifTimeEnd(endtime)){
                //已截�?
                ret.put("status","已结�?");
            }else{
                //正在进行�?
                ret.put("status","进行�?");
            }
        }else{//申请未�?�过
            ret.put("status","审核�?");
        }

        return ret;
    }

    /*我参与的活动*/
    public void participated_Click(View v) {
       changeFocus(1);
        ListView vi=(ListView) findViewById(R.id.content);
        vi.removeAllViewsInLayout();
        loadParticipatedActivity();
    }

    private void loadParticipatedActivity(){
        ListView vi=(ListView) findViewById(R.id.content);
        vi.removeAllViewsInLayout();
        SimpleAdapter adapter = new SimpleAdapter(this, getParticipatedData(), R.layout.content_manager_activity,
                new String[] { "title", "time", "position", "attending", "avater"},
                new int[] { R.id.title, R.id.time, R.id.position, R.id.attending, R.id.avater});
        adapter.setViewBinder(new SimpleAdapter.ViewBinder(){
            @Override
            public boolean setViewValue(View view, Object data,
                                        String textRepresentation) {
                if( (view instanceof ImageView) & (data instanceof Bitmap) ) {
                    ImageView iv = (ImageView) view;
                    Bitmap bm = (Bitmap) data;
                    iv.setImageBitmap(bm);
                    return true;
                }
                return false;
            }
        });
        vi.setAdapter(adapter);
    }
    private List<? extends Map<String, ?>> getParticipatedData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Message msg = ToolClass.getParticipatedActivity(User.getCurrentUser().getUserID());


        if (msg.getActivities() != null){
            for (MyActivity ac : msg.getActivities()){
                list.add(getOneParticipated(ac));
            }
        }
        return list;
    }

    private Map<String, Object> getOneParticipated(MyActivity ac){
        Map<String, Object> ret = new HashMap<String, Object>();
        String ava;
        if (ac.getAvatar() == null || ac.getAvatar().equals(""))
            ava = DEFAULTAVATER;
        else
            ava = ac.getAvatar();
        ret.put("title", ac.getTitle());
        ret.put("time", ac.getStartTime());
        ret.put("position", ac.getPlace());
        ret.put("attending", ac.getUserCount());
        ret.put("avater", ToolClass.returnBitMap(ava));
        return ret;
    }


    public void applyed_Click(View v) {
         changeFocus(2);
        ListView vi=(ListView) findViewById(R.id.content);
        vi.removeAllViewsInLayout();
        loadApplyedActivity();
    }
    private void loadApplyedActivity(){
        ListView vi=(ListView) findViewById(R.id.content);
        vi.removeAllViewsInLayout();
        SimpleAdapter adapter = new SimpleAdapter(this, getApplyedData(), R.layout.content_manager_activity,
                new String[] { "title", "time", "position", "attending", "avater"},
                new int[] { R.id.title, R.id.time, R.id.position, R.id.attending, R.id.avater});
        adapter.setViewBinder(new SimpleAdapter.ViewBinder(){
            @Override
            public boolean setViewValue(View view, Object data,
                                        String textRepresentation) {
                if( (view instanceof ImageView) & (data instanceof Bitmap) ) {
                    ImageView iv = (ImageView) view;
                    Bitmap bm = (Bitmap) data;
                    iv.setImageBitmap(bm);
                    return true;
                }
                return false;
            }
        });
        vi.setAdapter(adapter);
    }
    private List<? extends Map<String, ?>> getApplyedData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Message msg = ToolClass.getApplicatedActivity(User.getCurrentUser().getUserID());
        if (msg.getActivities() != null){
            for (MyActivity ac : msg.getActivities()){
                list.add(getOneAppyed(ac));
            }
        }
        return list;
    }

    private Map<String, Object> getOneAppyed(MyActivity ac){
        Map<String, Object> ret = new HashMap<String, Object>();
        String ava;
        if (ac.getAvatar() == null || ac.getAvatar().equals(""))
            ava = DEFAULTAVATER;
        else
            ava = ac.getAvatar();
        ret.put("title", ac.getTitle());
        ret.put("time", ac.getStartTime());
        ret.put("position", ac.getPlace());
        ret.put("attending", ac.getUserCount());
        ret.put("avater", ToolClass.returnBitMap(ava));
        return ret;
    }

    public void back_Click(View v) {
        Utils.transPage(this, Page_UserManager.class);
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
}
