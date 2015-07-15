package com.example.administrator.androidapp.page;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.administrator.androidapp.R;
import com.example.administrator.androidapp.msg.*;
import com.example.administrator.androidapp.tool.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Page_ActivityInformation extends ActionBarActivity {

    private int currentSelect = 0 ; //当前所选择的标签 0详情 1成员 2相册 3评论
    private MyActivity currentActivity;

    private User currentUser;
    private User[] allUsers;
    private Comment[]  allComments;
    private Photo[] allPhotos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_activity_information);
        loadCurrentActivity();
        loadActivityDetail();

        EditText comment = (EditText)findViewById(R.id.input_comment);
        comment.setVisibility(View.GONE);

    }

    //装载当前的活动信息
    private void loadCurrentActivity() {
        currentActivity = MyActivity.getCurrentActivity();
        currentUser = User.getCurrentUser();
        Message msg = ToolClass.getParticipation(currentUser.getUserID(), currentActivity.getActivityID());
        allUsers = msg.getUsers();

        ActivityInfo info = ToolClass.getActivityInfo(currentUser.getUserID(), currentActivity.getActivityID());
        allComments = info.getComments();
        if(allComments==null){
            allComments = new Comment[0];
        }
        allPhotos = info.getPhoto();
        if (allPhotos == null){
            allPhotos = new Photo[0];
        }
    }

    //装载活动详情
    private void loadActivityDetail(){
        ListView vi=(ListView) findViewById(R.id.content);
        SimpleAdapter adapter = new SimpleAdapter(this, getDetailData(), R.layout.content_activity_detail,
                new String[] { "title",  "time","position","attending","image","description"},
                new int[] { R.id.title, R.id.time,R.id.position,R.id.attending,R.id.image,R.id.description});

        adapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data,
                                        String textRepresentation) {
                if ((view instanceof ImageView) & (data instanceof Bitmap)) {
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

    private void loadActivityComment() {
        ListView vi=(ListView) findViewById(R.id.content);
        vi.removeAllViewsInLayout();
        SimpleAdapter adapter = new SimpleAdapter(this, getCommentData(), R.layout.content_activity_comment,
                new String[] { "name",  "time","comment"},
                new int[] { R.id.name, R.id.time,R.id.comment});
        vi.setAdapter(adapter);
    }

    private void loadActivityAlbum(){
        ListView vi=(ListView) findViewById(R.id.content);
        vi.removeAllViewsInLayout();
        SimpleAdapter adapter = new SimpleAdapter(this, getAlbumData(), R.layout.content_activity_album,
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

    private void loadActivityMember() {
        ListView vi=(ListView) findViewById(R.id.content);
        SimpleAdapter adapter = new SimpleAdapter(this, getMemberData(), R.layout.content_activity_member,
                new String[] { "name",  "age","time"},
                new int[] { R.id.name, R.id.age,R.id.time});
        vi.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_page__activity_information, menu);
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

    private void changeFocus(int i){
        currentSelect = i ;
        int[] ids = {R.id.details,R.id.member,R.id.album,R.id.comment};
        for(int id:ids){
            LinearLayout layout=(LinearLayout) findViewById(id);
            layout.setBackgroundColor(Color.WHITE);
        }
        LinearLayout rn=(LinearLayout) findViewById(ids[i]);
        rn.setBackgroundColor(0xFF50d2c2);
    }

    private void addComment() {
        EditText test = (EditText)findViewById(R.id.input_comment);
        String content = test.getText().toString();
        String ret =ToolClass.addCommment(currentUser.getUserID(),currentActivity.getActivityID(),content);
        Utils.showMessage(this, ret);
    }

    private void addPhoto() {

    }

    private void joinActivity() {
        String ret = ToolClass.applyParticipation(currentUser.getUserID(),currentActivity.getActivityID(),"我想参加");
        Utils.showMessage(this, ret);
    }

    /*  点击事件  */

    public void btn_Click(View v) {
        switch (currentSelect){
            case 0:joinActivity();break;
            case 1:memberMana();break;
            case 2:addPhoto();break;
            case 3:addComment();break;
        }
    }

    private void memberMana() {
        Utils.transPage(this,Page_MemberManager.class);
    }

    public void comment_Click(View v) {
        changeFocus(3);
        ListView vi=(ListView) findViewById(R.id.content);
        vi.removeAllViewsInLayout();

        EditText comment = (EditText)findViewById(R.id.input_comment);
        comment.setVisibility(View.VISIBLE);

        loadActivityComment();
    }

    public void album_Click(View v) {
        changeFocus(2);
        ListView vi=(ListView) findViewById(R.id.content);
        vi.removeAllViewsInLayout();

        Button btn = (Button)findViewById(R.id.btn);
        btn.setText("添加照片");

        EditText comment = (EditText)findViewById(R.id.input_comment);
        comment.setVisibility(View.GONE);
        loadActivityAlbum();
    }


    public void details_Click(View v) {
        changeFocus(0);
        ListView vi=(ListView) findViewById(R.id.content);
        vi.removeAllViewsInLayout();

        loadActivityDetail();

        Button btn = (Button)findViewById(R.id.btn);
        btn.setText("报名参加");
    }

    //获取活动成员
    public void member_Click(View v) {
        changeFocus(1);
        ListView vi=(ListView) findViewById(R.id.content);
        vi.removeAllViewsInLayout();

        loadActivityMember();

        Button btn = (Button)findViewById(R.id.btn);
        btn.setText("成员管理");
    }

    private List<? extends Map<String, ?>> getAlbumData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if (allPhotos != null){
            for (Photo photo : allPhotos){
                list.add(getOnePhoto(photo));
            }
        }
        return list;
    }

    private String DEFAULTAVATER = "http://chenranzhen.xyz/Upload/Avatar/Default.png";
    public Map<String, Object> getOnePhoto(Photo photo){
        Map<String, Object> ret = new HashMap<String, Object>();
        String ava;
        if (photo.getAvatar() == null || photo.getAvatar().equals(""))
            ava = DEFAULTAVATER;
        else
            ava = photo.getAvatar();
        ret.put("avater", ToolClass.returnBitMap(ava));
        ret.put("nickname", photo.getNickName());
        ret.put("time", photo.getTime());
        ret.put("img", ToolClass.returnBitMap(photo.getAddress()));
        return ret;
    }

    private List<? extends Map<String, ?>> getMemberData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if(allUsers !=null){
            for(User user:allUsers){
                list.add(getOneMember(user));
            }
        }
        return list;
    }

    private List<? extends Map<String, ?>> getCommentData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if(allComments!=null){
            for(Comment comment:allComments){
                list.add(getOneComment(comment));
            }
        }
        return list;
    }

    private List<Map<String, Object>> getDetailData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("title", currentActivity.getTitle());
        ret.put("time", currentActivity.getStartTime());
        ret.put("position", currentActivity.getPlace());
        ret.put("attending", currentActivity.getUserCount());
        ret.put("description", currentActivity.getContent());
        list.add(ret);
        return list;
    }

    private  Map<String, Object> getOneMember(User user){
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("name", user.getNickName());
        ret.put("age", user.getAge());
        ret.put("time", "07月02日 10:00");
        return ret;
    }

    private Map<String, Object> getOneComment(Comment comment) {
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("name", comment.getNickName());
        ret.put("time", comment.getTime());
        ret.put("comment",comment.getContent() );
        return ret;
    }

    public void close_Click(View v) {
        Utils.transPage(this, Page_TotalActivity.class);
    }
}
