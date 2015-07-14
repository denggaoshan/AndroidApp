package com.example.administrator.androidapp.page;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import com.example.administrator.androidapp.msg.Message;
import com.example.administrator.androidapp.msg.Photo;
import com.example.administrator.androidapp.tool.Utils;

import java.util.ArrayList;
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


    public void running_Click(View v) {
       changeFocus(0);

    }

    private void loadRunningActivity(){
        ListView vi=(ListView) findViewById(R.id.content);
        vi.removeAllViewsInLayout();
        SimpleAdapter adapter = new SimpleAdapter(this, getRunningData(), R.layout.content_activity_album,
                new String[] { "avater", "nickname", "time", "img"},
                new int[] { R.id.avater_album, R.id.name_album, R.id.time_album, R.id.img_album});
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
    private List<? extends Map<String, ?>> getRunningData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if (allPhotos != null){
            for (Photo photo : allPhotos){
                list.add(getOnePhoto(photo));
            }
        }
        return list;
    }

    public void history_Click(View v) {
       changeFocus(1);
    }

    public void applying_Click(View v) {
         changeFocus(2);
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
