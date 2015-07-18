package com.example.administrator.androidapp.page;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.androidapp.R;
import com.example.administrator.androidapp.msg.*;
import com.example.administrator.androidapp.tool.Utils;

public class Page_Information_Activity extends ActionBarActivity {

    private int currentSelect = 0 ; //当前所选择的标签 0详情 1成员 2相册 3评论
    private MyActivity currentActivity;

    private User currentUser;
    private User[] allUsers;
    private Comment[]  allComments;
    private Photo[] allPhotos;


    private boolean ifLoadMembers=false;
    private boolean ifLoadPhotos=false;
    private boolean ifLoadComments=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_activity_information);

        //加载当前的活动信息
        loadCurrentActivity();

        showActivityDetail();

        ImageView iv = ((ImageView) findViewById(R.id.Edit));
        iv.setImageBitmap(ToolClass.resizeBitmap(Cache.getUserAvater(), this, iv.getLayoutParams().width, iv.getLayoutParams().height));

    }

    /* 活动详情适配器 */
    private class DetailAdapter extends BaseAdapter{
        private MyActivity currentActivity;

        public DetailAdapter(MyActivity activity){
            currentActivity = activity;
        }

        @Override
        public int getCount() {
            return 2;
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
            if(position == 0){
                convertView = new LinearLayout(parent.getContext());
                if(convertView!=null){
                    Context ctx = convertView.getContext();
                    LayoutInflater nflater = LayoutInflater.from(ctx);
                    convertView = nflater.inflate(R.layout.content_activity_detail, null);

                    int[] ids = {R.id.title,R.id.startTime,R.id.endTime,R.id.position,R.id.attending,R.id.type,R.id.description};
                    String[] vals = {currentActivity.getTitle(),currentActivity.getStartTime(),currentActivity.getEndTime(),currentActivity.getPlace(),
                            currentActivity.getUserCount(),currentActivity.getType(),currentActivity.getContent()};

                    for(int i=0;i<ids.length;i++){
                        TextView tv = (TextView)convertView.findViewById(ids[i]);
                        if(ids[i] != R.id.type){
                            tv.setText(vals[i]);
                        }else {
                            tv.setText(Utils.changeType(vals[i]));
                        }
                    }
                }
            }else{
                //显示最后的按钮
                convertView = new LinearLayout(parent.getContext());
                if (convertView != null) {
                    Context ctx = convertView.getContext();
                    LayoutInflater nflater = LayoutInflater.from(ctx);
                    convertView = nflater.inflate(R.layout.content_button, null);
                    Button btn = (Button) convertView.findViewById(R.id.btn);
                    btn.setText("报名参加");
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //
                        }
                    });
                }
            }

            return convertView;
        }
    }
    /*  活动成员适配器 */
    private class AllUsersAdapter extends BaseAdapter{

        public AllUsersAdapter(){
        }

        @Override
        public int getCount() {
            if(allUsers!=null){
                return allUsers.length+1;
            }else {
                return 0;
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
            if(position < getCount()-1){
                //如果是正常内容
                convertView = new LinearLayout(parent.getContext());

                final User user = allUsers[position];

                if(convertView!=null && user!= null){

                    Context ctx = convertView.getContext();
                    LayoutInflater nflater = LayoutInflater.from(ctx);
                    convertView = nflater.inflate(R.layout.content_activity_member, null);

                    int[] ids = {R.id.name,R.id.age,R.id.sex};
                    String[] vals = {user.getNickName(),"年龄： "+user.getAge(),user.getSex()};

                    for(int i=0;i<ids.length;i++){
                        TextView tv = (TextView)convertView.findViewById(ids[i]);
                        if(ids[i] != R.id.sex){
                            tv.setText(vals[i]);
                        }else{
                           tv.setText(Utils.changeSex(vals[i]));
                        }
                    }

                    //添加点击事件
                    convertView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            User.setOtherUser(user);
                            Utils.transPage(Page_Information_Activity.this,Page_Information_Others.class);
                        }
                    });

                }else{
                    Utils.debugMessage(Page_Information_Activity.this,"某个用户为空");
                }
            }else{
                convertView = new LinearLayout(parent.getContext());
                if(convertView!=null ) {
                    Context ctx = convertView.getContext();
                    LayoutInflater nflater = LayoutInflater.from(ctx);
                    convertView = nflater.inflate(R.layout.content_button,null);
                    Button btn = (Button)convertView.findViewById(R.id.btn);
                    btn.setText("成员管理");
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Page_Information_Activity.this.memberMana_Click();
                        }
                    });
                }
            }
            return convertView;
        }
    }
    /* 活动评论适配器 */
    private class CommentsAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            if(allComments!=null){
                return allComments.length+1;
            }else {
                Utils.debugMessage(Page_Information_Activity.this,"评论为空");
                return 0;
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

            if(position<getCount()-1){
                convertView = new LinearLayout(parent.getContext());

                Comment comment = allComments[position];

                if(convertView!=null && comment!= null){

                    Context ctx = convertView.getContext();
                    LayoutInflater nflater = LayoutInflater.from(ctx);
                    convertView = nflater.inflate(R.layout.content_activity_comment, null);

                    int[] ids = {R.id.name,R.id.time,R.id.comment};
                    String[] vals = {comment.getNickName(),comment.getTime(),comment.getContent()};

                    try{
                        for(int i=0;i<ids.length;i++){
                            TextView tv = (TextView)convertView.findViewById(ids[i]);
                            tv.setText(vals[i]);
                        }
                    }catch (NullPointerException e){
                        Utils.debugMessage(Page_Information_Activity.this,"存在空的指针"+e);
                    }

                }else{
                    Utils.debugMessage(Page_Information_Activity.this,"某个用户为空");
                }
            }else {
                //显示最后的按钮
                convertView = new LinearLayout(parent.getContext());
                if (convertView != null) {
                    Context ctx = convertView.getContext();
                    LayoutInflater nflater = LayoutInflater.from(ctx);
                    convertView = nflater.inflate(R.layout.content_comment, null);
                    Button btn = (Button) convertView.findViewById(R.id.btn);

                    final EditText ed = (EditText) convertView.findViewById(R.id.comment);

                    if(btn!=null){
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(ed!=null){
                                    addComment_Click(ed);
                                }else{
                                    Utils.debugMessage(Page_Information_Activity.this,"找不到ed");
                                }
                            }
                        });
                    }else{
                        Utils.debugMessage(Page_Information_Activity.this,"btn没找到");
                    }

                }
            }
            return convertView;
        }
    }
    /* 活动相册适配器 */
    private class PhotosAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            if(allPhotos!=null){
                return allPhotos.length+1;
            }else {
                return 0;
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

            Photo photo = allPhotos[position];

            if(convertView!=null && photo!= null){

                Context ctx = convertView.getContext();
                LayoutInflater nflater = LayoutInflater.from(ctx);
                convertView = nflater.inflate(R.layout.content_activity_album, null);

                int[] ids = {R.id.name,R.id.time};
                String[] vals = {photo.getNickName(),photo.getTime()};

                for(int i=0;i<ids.length;i++){
                    TextView tv = (TextView)convertView.findViewById(ids[i]);
                    tv.setText(vals[i]);
                }

            }else{
                Utils.debugMessage(Page_Information_Activity.this,"某个用户为空");
            }
            return convertView;

        }
    }

    //重新加载当前的全部成员
    private void loadAllUsers(){
        Message msg = ToolClass.getParticipation(currentUser.getUserID(), currentActivity.getActivityID());
        allUsers = msg.getUsers();
        ifLoadMembers = true;
    }

    //重新加载当前的全部评论
    private void loadAllComments(){
        ActivityInfo info = ToolClass.getActivityInfo(currentUser.getUserID(), currentActivity.getActivityID());
        allComments = info.getComments();
        ifLoadComments = true;
    }

    //重新加载当前的全部相册
    private void loadAllPhotos(){
        ActivityInfo info = ToolClass.getActivityInfo(currentUser.getUserID(), currentActivity.getActivityID());
        allPhotos = info.getPhoto();
        ifLoadPhotos = true;
    }

    //装载当前的活动信息
    private void loadCurrentActivity() {
        //当前活动
        currentActivity = MyActivity.getCurrentActivity();
        //当前用户
        currentUser = User.getCurrentUser();
    }

    //显示活动详情
    private void showActivityDetail(){
        ListView vi=(ListView) findViewById(R.id.content);
        if(currentActivity!=null){
            DetailAdapter adapter = new DetailAdapter(currentActivity);
            vi.setAdapter(adapter);
        }else{
            Utils.debugMessage(this,"当前活动为空");
        }
    }

    //显示活动成员
    private void showActivityMember() {
        ListView vi=(ListView) findViewById(R.id.content);
        AllUsersAdapter adapter = new AllUsersAdapter();
        vi.setAdapter(adapter);
    }

    //显示活动评论
    private void showActivityComment() {
        ListView vi=(ListView) findViewById(R.id.content);
        CommentsAdapter adapter = new CommentsAdapter();
        vi.setAdapter(adapter);
    }

    //显示活动相册
    private void showActivityAlbum(){
        ListView vi=(ListView) findViewById(R.id.content);
        PhotosAdapter  adapter = new PhotosAdapter();
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

    private void joinActivity() {
        String ret = ToolClass.applyParticipation(currentUser.getUserID(),currentActivity.getActivityID(),"我想参加");
        Utils.showMessage(this, ret);
    }

    /**************       导航栏点击事件           *************/
    //点击评论
    public void comment_Click(View v) {
        changeFocus(3);
        if(!ifLoadComments){
            loadAllComments();
        }
        showActivityComment();
    }
    //点击相册
    public void album_Click(View v) {
        changeFocus(2);
        if(!ifLoadPhotos){
            loadAllPhotos();
        }
        showActivityAlbum();
    }
    //点击活动详情
    public void details_Click(View v) {
        changeFocus(0);
        if(currentActivity==null){
            loadCurrentActivity();
        }
        showActivityDetail();
    }
    //点击活动成员
    public void member_Click(View v) {
        changeFocus(1);
        if(!ifLoadMembers){
            loadAllUsers();
        }
        showActivityMember();
    }

    /**************       底部功能           *************/
    //转到成员管理
    private void memberMana_Click() {
        Utils.transPage(this, Page_Manage_Member.class);
    }
    //添加评论
    public void addComment_Click(EditText et){

        if(et!=null){
            String str = et.getText().toString();
            if(str!=null && !str.replace(" ","").equals("")){
                String ret = ToolClass.addCommment(User.getCurrentUser().getUserID(), MyActivity.getCurrentActivity().getActivityID(),str);
                Utils.showMessage(this,ret);
                loadAllComments();
            }
        }else{
            Utils.debugMessage(this,"没找到comment");
        }
    }

    /****************     返回      ****************/
    public void back_Click(View v) {
        Utils.backPage(this);
    }
}
