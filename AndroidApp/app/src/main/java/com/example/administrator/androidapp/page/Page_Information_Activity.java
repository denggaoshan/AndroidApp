package com.example.administrator.androidapp.page;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.androidapp.R;
import com.example.administrator.androidapp.msg.*;
import com.example.administrator.androidapp.tool.Utils;

public class Page_Information_Activity extends BasePage {

    private MyActivity currentActivity;

    private User currentUser;
    private User[] allUsers;
    private Comment[]  allComments;
    private Photo[] allPhotos;


    private boolean ifLoadMembers=false;
    private boolean ifLoadPhotos=false;
    private boolean ifLoadComments=false;

    private  boolean isLauncher = false;  //当前用户是不是活动发起人

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_activity_information);

        //加载当前的活动信息
        loadCurrentActivity();

        //加载活动成员
        allUsers = Cache.loadAllUsers(currentActivity.getActivityID());
        isLauncher= Current.getCurrentUser().isLauncher(allUsers);//是否为活动发起人

        showActivityDetail();
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
            convertView = new LinearLayout(parent.getContext());
            if(position == 0){
                if(convertView!=null){
                    Context ctx = convertView.getContext();
                    LayoutInflater nflater = LayoutInflater.from(ctx);
                    convertView = nflater.inflate(R.layout.content_activity_detail, null);

                    int[] ids = {R.id.title,
                            R.id.startTime,
                            R.id.endTime,
                            R.id.position,
                            R.id.attending,
                            R.id.type,
                            R.id.description};
                    String[] vals = {currentActivity.getTitle(),
                            currentActivity.getStartTime(),
                            currentActivity.getEndTime(),
                            currentActivity.getPlace(),
                            currentActivity.getUserCount(),
                            currentActivity.getType(),
                            currentActivity.getContent()};

                    try{
                        for(int i=0;i<ids.length;i++){
                            TextView tv = (TextView)convertView.findViewById(ids[i]);
                            if(ids[i] != R.id.type){
                                tv.setText(vals[i]);
                            }else {
                                tv.setText(Utils.changeType(vals[i]));
                            }
                        }
                    }catch (Exception e){
                        Utils.debugMessage(Page_Information_Activity.this,"debug 0010 ");
                    }


                }
            }else{

                if(isLauncher){
                    //如果是活动管理员，显示群发消息按钮
                    //显示最后的按钮
                    convertView = new LinearLayout(parent.getContext());
                    if (convertView != null) {
                        Context ctx = convertView.getContext();
                        LayoutInflater nflater = LayoutInflater.from(ctx);
                        convertView = nflater.inflate(R.layout.content_button, null);
                        Button btn = (Button) convertView.findViewById(R.id.btn);
                        btn.setText("群发消息");
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sendActivityMessage();
                            }
                        });
                    }

                }else if(currentUser.getUserType().equals("游客")){
                    //显示最后的按钮
                    convertView = new LinearLayout(parent.getContext());
                    if (convertView != null) {
                        Context ctx = convertView.getContext();
                        LayoutInflater nflater = LayoutInflater.from(ctx);
                        convertView = nflater.inflate(R.layout.content_button, null);
                        Button btn = (Button) convertView.findViewById(R.id.btn);
                        btn.setText("赶快注册！");
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Utils.transPage(Page_Information_Activity.this,Page_Registered.class);
                            }
                        });
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
                                //活动报名
                                Utils.showMessage(Page_Information_Activity.this,"报名中");
                                joinActivity();
                            }
                        });
                    }
                }

            }
            return convertView;
        }
    }
    /*  活动成员适配器 */
    private class AllUsersAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            if(Current.getCurrentUser().getUserType().equals("游客")){
                return 1;
            }
            if(allUsers!=null){
                return allUsers.length+1;
            }else {
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
            if(position < getCount()-1){
                //如果是正常内容

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

                    //成员头像
                    Cache.loadImg(Page_Information_Activity.this,user.getAvatar(),R.id.image );

                    //添加点击事件
                    convertView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Current.setOtherUser(user);
                            Utils.transPage(Page_Information_Activity.this,Page_Information_Others.class);
                        }
                    });

                }else{
                    Utils.debugMessage(Page_Information_Activity.this,"某个用户为空");
                }
            }else{
                if( isLauncher ){
                    //如果是获得发起人，则可以看到成员管理按钮
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
                } else if(Current.getCurrentUser().getUserType().equals("游客")){
                    //如果是游客，显示提示消息
                    Context ctx = convertView.getContext();
                    LayoutInflater nflater = LayoutInflater.from(ctx);
                    convertView = nflater.inflate(R.layout.content_tips, null);
                    TextView tv = (TextView) convertView.findViewById(R.id.content);
                    tv.setText("对不起，您无权查看这里的内容");
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
            if(position<getCount()-1){
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

    //装载当前的活动信息
    private void loadCurrentActivity() {
        //当前活动
        currentActivity = Current.getCurrentActivity();
        //当前用户
        currentUser = Current.getCurrentUser();
    }

    //显示活动详情
    private void showActivityDetail(){
        //如果没有装载的话
        if(currentActivity==null){
            loadCurrentActivity();
        }
        try {
            ListView vi=(ListView) findViewById(R.id.content);
            if(currentActivity!=null){
                DetailAdapter adapter = new DetailAdapter(currentActivity);
                vi.setAdapter(adapter);
            }else{
                Utils.debugMessage(this,"当前活动为空");
            }
        }catch (Exception e){
            Utils.debugMessage(Page_Information_Activity.this,"加载适配器遇到空指针");
        }
    }

    //显示活动成员
    private void showActivityMember() {
        if(!Current.getCurrentUser().getUserType().equals("游客")) {
            if (!ifLoadMembers) {
                allUsers = Cache.loadAllUsers(currentActivity.getActivityID());
                isLauncher= Current.getCurrentUser().isLauncher(allUsers);
            }
        }
        try{
            ListView vi=(ListView) findViewById(R.id.content);
            AllUsersAdapter adapter = new AllUsersAdapter();
            vi.setAdapter(adapter);
        }catch (Exception e){
            Utils.debugMessage(Page_Information_Activity.this,"加载适配器遇到空指针");
        }
    }

    //显示活动评论
    private void showActivityComment() {

        if(!Current.getCurrentUser().getUserType().equals("游客")){
            if(!ifLoadComments){
                allComments = Cache.loadAllComments(currentActivity.getActivityID());
            }
        }else{
            allComments = null;
        }

        try{
            ListView vi=(ListView) findViewById(R.id.content);
            CommentsAdapter adapter = new CommentsAdapter();
            vi.setAdapter(adapter);
        }catch (Exception e){
            Utils.debugMessage(Page_Information_Activity.this,"加载适配器遇到空指针");
        }
    }

    //显示活动相册
    private void showActivityAlbum(){

        if(!Current.getCurrentUser().getUserType().equals("游客")) {
            if (!ifLoadPhotos) {
                allPhotos = Cache.loadAllPhotos(currentActivity.getActivityID());
            }
        }

        try{
            ListView vi=(ListView) findViewById(R.id.content);
            PhotosAdapter  adapter = new PhotosAdapter();
            vi.setAdapter(adapter);
        }catch (Exception e){
            Utils.debugMessage(Page_Information_Activity.this,"加载适配器遇到空指针");
        }

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

    private void changeFocus(int id){
        int[] ids = {R.id.details,R.id.member,R.id.album,R.id.comment};
        for(int i:ids){
            LinearLayout layout=(LinearLayout) findViewById(i);
            layout.setBackgroundColor(Color.WHITE);
        }
        LinearLayout rn=(LinearLayout) findViewById(id);
        rn.setBackgroundColor(0xFF50d2c2);
    }

    //群发消息接口
    private void sendActivityMessage(){
    }

    //活动报名
    private void joinActivity() {
        String ret = ToolClass.applyParticipation(currentUser.getUserID(), currentActivity.getActivityID(), "我想参加");
        if(ret!=null){
            if(ret.equals("OK")){
                Utils.showMessage(this, "报名成功，审核中");
            }else{
                Utils.showMessage(this, "已经报名过了");
            }
        }
    }


    //点击导航栏
    public void navi_Click(View v) {
        int id = v.getId();
        String userType = Current.getCurrentUser().getUserType();

        changeFocus(id);//切换焦点到此标签

        switch (v.getId()){ //显示相应内容
            case R.id.details:showActivityDetail();;break;
            case R.id.member:showActivityMember(); ;break;
            case R.id.album:showActivityAlbum();break;
            case R.id.comment:showActivityComment();break;
        }
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
                String ret = ToolClass.addCommment(Current.getCurrentUser().getUserID(), Current.getCurrentActivity().getActivityID(),str);
                Utils.showMessage(this,ret);
                //////////提示Cache更新评论
                allComments = Cache.updateAndLoadComments(currentActivity.getActivityID());
                showActivityComment();
            }
        }else{
            Utils.debugMessage(this, "没找到comment");
        }
    }
}
