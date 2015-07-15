package com.example.administrator.androidapp.page;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.androidapp.R;
import com.example.administrator.androidapp.msg.MyActivity;
import com.example.administrator.androidapp.msg.Message;
import com.example.administrator.androidapp.msg.ToolClass;
import com.example.administrator.androidapp.msg.User;
import com.example.administrator.androidapp.tool.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Page_TotalActivity extends ActionBarActivity implements OnTouchListener{

    private int curPage = 1; //当前页数
    private int activityType = 0;//当前活动类型
    private int applyAble = 1;


    public static final int SNAP_VELOCITY = 100;//滚动显示和隐藏menu时，手指滑动需要达到的速度。
    private int screenWidth;   //屏幕宽度值。
    private int leftEdge;  //menu最多可以滑动到的左边缘。值由menu布局的宽度来定，marginLeft到达此值之后，不能再减少。
    private int rightEdge = 0;//menu最多可以滑动到的右边缘。值恒为0，即marginLeft到达0之后，不能增加。
    private int menuPadding = 500; //menu完全显示时，留给content的宽度值。
    private View content;   //主内容的布局。
    private View menu; //menu的布局。
    private LinearLayout.LayoutParams menuParams;//menu布局的参数，通过此参数来更改leftMargin的值。
    private float xDown;  //记录手指按下时的横坐标。
    private float xMove;//记录手指移动时的横坐标。
    private float xUp;//记录手机抬起时的横坐标。
    private boolean isMenuVisible;// menu当前是显示还是隐藏。只有完全显示或隐藏menu时才会更改此值，滑动过程中此值无效。
    private VelocityTracker mVelocityTracker;  //用于计算手指滑动的速度。
    private boolean ifMenuShow = false;

    //初始化一些关键性数据。包括获取屏幕的宽度，给content布局重新设置宽度，给menu布局重新设置宽度和偏移距离等。
    private void initValues() {
        WindowManager window = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        screenWidth = window.getDefaultDisplay().getWidth();
        content = findViewById(R.id.main_content);
        menu = findViewById(R.id.menu);
        menuParams = (LinearLayout.LayoutParams) menu.getLayoutParams();
        // 将menu的宽度设置为屏幕宽度减去menuPadding
        menuParams.width = screenWidth - menuPadding;
        // 左边缘的值赋值为menu宽度的负数
        leftEdge = -menuParams.width;
        // menu的leftMargin设置为左边缘的值，这样初始化时menu就变为不可见
        menuParams.leftMargin = leftEdge;
        // 将content的宽度设置为屏幕宽度
        content.getLayoutParams().width = screenWidth;
    }

    //点击屏幕事件
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        createVelocityTracker(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 手指按下时，记录按下时的横坐标
                xDown = event.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                // 手指移动时，对比按下时的横坐标，计算出移动的距离，来调整menu的leftMargin值，从而显示和隐藏menu
                xMove = event.getRawX();
                int distanceX = (int) (xMove - xDown);
                if (isMenuVisible) {
                    menuParams.leftMargin = distanceX;
                } else {
                    menuParams.leftMargin = leftEdge + distanceX;
                }
                if (menuParams.leftMargin < leftEdge) {
                    menuParams.leftMargin = leftEdge;
                } else if (menuParams.leftMargin > rightEdge) {
                    menuParams.leftMargin = rightEdge;
                }
                menu.setLayoutParams(menuParams);
                break;
            case MotionEvent.ACTION_UP:
                // 手指抬起时，进行判断当前手势的意图，从而决定是滚动到menu界面，还是滚动到content界面
                xUp = event.getRawX();
                if (wantToShowMenu()) {
                    if (shouldScrollToMenu()) {
                        scrollToMenu();
                    } else {
                        scrollToContent();
                    }
                } else if (wantToShowContent()) {
                    if (shouldScrollToContent()) {
                        scrollToContent();
                    } else {
                        scrollToMenu();
                    }
                }else if(ifMenuShow  && xUp > rightEdge){//只点击Content也可以切换
                    scrollToContent();
                }
                recycleVelocityTracker();
                break;
        }
        return true;
    }



    /**
     * 判断当前手势的意图是不是想显示content。如果手指移动的距离是负数，且当前menu是可见的，则认为当前手势是想要显示content。
     *
     * @return 当前手势想显示content返回true，否则返回false。
     */
    private boolean wantToShowContent() {
        return xUp - xDown < 0 && isMenuVisible;
    }

    /**
     * 判断当前手势的意图是不是想显示menu。如果手指移动的距离是正数，且当前menu是不可见的，则认为当前手势是想要显示menu。
     *
     * @return 当前手势想显示menu返回true，否则返回false。
     */
    private boolean wantToShowMenu() {
        return xUp - xDown > 0 && !isMenuVisible;
    }

    /**
     * 判断是否应该滚动将menu展示出来。如果手指移动距离大于屏幕的1/2，或者手指移动速度大于SNAP_VELOCITY，
     * 就认为应该滚动将menu展示出来。
     *
     * @return 如果应该滚动将menu展示出来返回true，否则返回false。
     */
    private boolean shouldScrollToMenu() {
        return xUp - xDown > screenWidth / 2 || getScrollVelocity() > SNAP_VELOCITY;
    }

    /**
     * 判断是否应该滚动将content展示出来。如果手指移动距离加上menuPadding大于屏幕的1/2，
     * 或者手指移动速度大于SNAP_VELOCITY， 就认为应该滚动将content展示出来。
     *
     * @return 如果应该滚动将content展示出来返回true，否则返回false。
     */
    private boolean shouldScrollToContent() {
        return xDown - xUp + menuPadding > screenWidth / 2 || getScrollVelocity() > SNAP_VELOCITY;
    }

    /**
     * 将屏幕滚动到menu界面，滚动速度设定为30.
     */
    private void scrollToMenu() {
        ifMenuShow = true;
        new ScrollTask().execute(30);
    }

    /**
     * 将屏幕滚动到content界面，滚动速度设定为-30.
     */
    private void scrollToContent() {
        ifMenuShow = false;
        new ScrollTask().execute(-30);
    }

    /**
     * 创建VelocityTracker对象，并将触摸content界面的滑动事件加入到VelocityTracker当中。
     *
     * @param event
     *            content界面的滑动事件
     */
    private void createVelocityTracker(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    /**
     * 获取手指在content界面滑动的速度。
     *
     * @return 滑动速度，以每秒钟移动了多少像素值为单位。
     */
    private int getScrollVelocity() {
        mVelocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) mVelocityTracker.getXVelocity();
        return Math.abs(velocity);
    }

    /**
     * 回收VelocityTracker对象。
     */
    private void recycleVelocityTracker() {
        mVelocityTracker.recycle();
        mVelocityTracker = null;
    }

    class ScrollTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... speed) {
            int leftMargin = menuParams.leftMargin;
            // 根据传入的速度来滚动界面，当滚动到达左边界或右边界时，跳出循环。
            while (true) {
                leftMargin = leftMargin + speed[0];
                if (leftMargin > rightEdge) {
                    leftMargin = rightEdge;
                    break;
                }
                if (leftMargin < leftEdge) {
                    leftMargin = leftEdge;
                    break;
                }
                publishProgress(leftMargin);
                // 为了要有滚动效果产生，每次循环使线程睡眠20毫秒，这样肉眼才能够看到滚动动画。
                sleep(20);
            }
            if (speed[0] > 0) {
                isMenuVisible = true;
            } else {
                isMenuVisible = false;
            }
            return leftMargin;
        }

        @Override
        protected void onProgressUpdate(Integer... leftMargin) {
            menuParams.leftMargin = leftMargin[0];
            menu.setLayoutParams(menuParams);
        }

        @Override
        protected void onPostExecute(Integer leftMargin) {
            menuParams.leftMargin = leftMargin;
            menu.setLayoutParams(menuParams);
        }
    }

    /**
     * 使当前线程睡眠指定的毫秒数。
     *
     * @param millis
     *            指定当前线程睡眠多久，以毫秒为单位
     */
    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_total_activity);
        initValues();
        content.setOnTouchListener(this);

        loadActivity();
        Thread tempThread = new Thread(){
            public void run(){
                new AnotherTask().execute("none");
            }
        };
        tempThread.start();
    }

    private class AnotherTask extends AsyncTask<String, Void, String>{
        @Override
        protected void onPostExecute(String result) {
            //对UI组件的更新操作
            putImgs();
        }
        @Override
        protected String doInBackground(String... params) {
            //耗时的操作
            return params[0];
        }
    }

    void putImgs(){
        Bitmap bm = ToolClass.returnBitMap(User.getCurrentUser().getAvatar());
        ((ImageView) findViewById(R.id.imageView20)).setImageBitmap(ToolClass.resizeBitmap(bm, this, 100, 100));
        ((ImageView) findViewById(R.id.imageView4)).setImageBitmap(ToolClass.resizeBitmap(bm, this, 60, 60));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_total, menu);
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


    private MyActivity[] activities;

    private Map<String,List<MyActivity>> loadAllActivities(){
        Message msg = ToolClass.getActivityList("" + curPage, "" + activityType, "" + applyAble);
        activities = msg.getActivities();

        Map<String,List<MyActivity>> allActivitiesByDayOrder = new HashMap<String,List<MyActivity>>();
        for(MyActivity act : activities){
            String time = act.getStartTime();
            if(allActivitiesByDayOrder.containsKey(time)){
                List<MyActivity> tmp =  allActivitiesByDayOrder.get(time);
                tmp.add(act);
            }else{
                List<MyActivity> tmp = new ArrayList<MyActivity>();
                tmp.add(act);
                allActivitiesByDayOrder.put(time,tmp);
            }
        }
        return allActivitiesByDayOrder;
    }

    class MyAdapter extends BaseAdapter{

        ArrayList<Integer> dateIndex = new ArrayList<Integer>();

        ArrayList<Object> allItem = new ArrayList<Object>();

        public MyAdapter(Map<String,List<MyActivity>> allActivitiesByDayOrder){

            for (Map.Entry<String, List<MyActivity>> entry : allActivitiesByDayOrder.entrySet()) {
                dateIndex.add(allItem.size());//记录日期的位置
                allItem.add(entry.getKey());
                List<MyActivity> tmp = entry.getValue();
                for(MyActivity act : tmp){
                    allItem.add(act);
                }
            }
        }

        @Override
        public int getCount() {
           return allItem.size();
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

            if(dateIndex.contains(position)){
                //显示日期
                String date = (String)allItem.get(position);
                convertView= nflater.inflate(R.layout.content_total_date, null);

                TextView tv = (TextView) convertView.findViewById(R.id.time);
                tv.setText(date);
            }else{
                final MyActivity activity = (MyActivity)allItem.get(position);

                //内容
                convertView= nflater.inflate(R.layout.content_total_activity, null);
                TextView tv = (TextView) convertView.findViewById(R.id.title);
                tv.setText(activity.getTitle());
                tv = (TextView) convertView.findViewById(R.id.time);
                tv.setText(activity.getStartTime());
                tv = (TextView) convertView.findViewById(R.id.position);
                tv.setText(activity.getPlace());
                tv = (TextView) convertView.findViewById(R.id.attending);
                tv.setText(activity.getUserCount());

                //监听事件
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyActivity.setCurrentActivity(activity);
                        Utils.transPage(Page_TotalActivity.this, Page_ActivityInformation.class);
                    }
                });
            }
            return convertView;
        }
    }


    private void loadActivity(){
        ListView vi=(ListView) findViewById(R.id.content);
        MyAdapter myAdapter = new MyAdapter( loadAllActivities());
        vi.setAdapter(myAdapter);
    }



    private PopupWindow popupWindow;

    /*右上菜单栏 */

    //菜单点击事件
    public void func_Click(View v){
        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        View popupWindow_view = getLayoutInflater().inflate(R.layout.menu_total, null,false);
        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        popupWindow = new PopupWindow(popupWindow_view, 300, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // 这里是位置显示方式,在屏幕的左侧
        popupWindow.showAsDropDown(v, 0, 0);
        // 点击其他地方消失
        popupWindow_view.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
                return false;
            }
        });
    }

    //发起活动
    public void add_Click(View v){
        Utils.transPage(this, Page_Organize.class);
    }

    /*左侧菜单栏*/

    //点击左上角的头像
    public void head_Click(View v){
        scrollToMenu();
    }

    //查看消息
    public void  message_Click(View v){
        Utils.transPage(this,Page_Message.class);
    }
    //个人资料
    public void   userInfo_Click(View v){
        Utils.transPage(this,Page_UserInformation.class);
    }
    //活动管理
    public void manage_Click(View v) {
        Utils.transPage(this,Page_UserManager.class);
    }
    //退出登录
    public void logout_Click(View v){
        Utils.clearLogData();
        Utils.transPage(this,Page_Main.class);
    }

}
