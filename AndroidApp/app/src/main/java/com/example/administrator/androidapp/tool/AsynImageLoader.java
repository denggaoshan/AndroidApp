package com.example.administrator.androidapp.tool;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2015/7/15.
 */
public class AsynImageLoader {
    private static final String TAG = "AsynImageLoader";
    public static String CACHE_DIR = "cache.dat";
    // �������ع���ͼƬ��Map
    private static Map<String, SoftReference<Bitmap>> caches;
    // �������
    private List<Task> taskQueue;
    private boolean isRunning = false;

    public AsynImageLoader(){
        // ��ʼ������
        caches = new HashMap<String, SoftReference<Bitmap>>();
        taskQueue = new ArrayList<Task>();
        // ����ͼƬ�����߳�
        isRunning = true;
        new Thread(runnable).start();
    }

    /**
     *
     * @param imageView ��Ҫ�ӳټ���ͼƬ�Ķ���
     * @param url ͼƬ��URL��ַ
     * @param resId ͼƬ���ع�������ʾ��ͼƬ��Դ
     */
    public void showImageAsyn(ImageView imageView, String url, int resId){
        Bitmap bitmap = loadImageAsyn(url, getImageCallback(imageView, resId));

        if(bitmap == null){
            //imageView.setImageResource(resId);
        }else{
            imageView.setImageBitmap(bitmap);
        }
    }

    public Bitmap loadImageAsyn(String path, ImageCallback callback){
        // �жϻ������Ƿ��Ѿ����ڸ�ͼƬ
        if(caches.containsKey(path)){
            // ȡ��������
            SoftReference<Bitmap> rf = caches.get(path);
            // ͨ�������ã���ȡͼƬ
            Bitmap bitmap = rf.get();
            // �����ͼƬ�Ѿ����ͷţ��򽫸�path��Ӧ�ļ���Map���Ƴ���
            if(bitmap == null){
                caches.remove(path);
            }else{
                // ���ͼƬδ���ͷţ�ֱ�ӷ��ظ�ͼƬ
                Log.i(TAG, "return image in cache" + path);
                return bitmap;
            }
        }else{
            // ��������в����ڸ�ͼƬ���򴴽�ͼƬ��������
            Task task = new Task();
            task.path = path;
            task.callback = callback;
            Log.i(TAG, "new Task ," + path);
            if(!taskQueue.contains(task)){
                taskQueue.add(task);
                // �����������ض���
                synchronized (runnable) {
                    runnable.notify();
                }
            }
        }

        // ������û��ͼƬ�򷵻�null
        return null;
    }

    /**
     *
     * @param imageView
     * @param resId ͼƬ�������ǰ��ʾ��ͼƬ��ԴID
     * @return
     */
    private ImageCallback getImageCallback(final ImageView imageView, final int resId){
        return new ImageCallback() {

            @Override
            public void loadImage(String path, Bitmap bitmap) {
                if(path.equals(imageView.getTag().toString())){
                    imageView.setImageBitmap(bitmap);
                }else{
                    imageView.setImageResource(resId);
                }
            }
        };
    }

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            // ���߳��з��ص�������ɵ�����
            Task task = (Task)msg.obj;
            // ����callback�����loadImage����������ͼƬ·����ͼƬ�ش���adapter
            task.callback.loadImage(task.path, task.bitmap);
        }

    };

    private Runnable runnable = new Runnable() {

        @Override
        public void run() {
            while(isRunning){
                // �������л���δ���������ʱ��ִ����������
                while(taskQueue.size() > 0){
                    // ��ȡ��һ�����񣬲���֮�����������ɾ��
                    Task task = taskQueue.remove(0);
                    // �����ص�ͼƬ��ӵ�����
                    task.bitmap = PicUtil.getbitmap(task.path);
                    caches.put(task.path, new SoftReference<Bitmap>(task.bitmap));
                    if(handler != null){
                        // ������Ϣ���󣬲�����ɵ�������ӵ���Ϣ������
                        Message msg = handler.obtainMessage();
                        msg.obj = task;
                        // ������Ϣ�����߳�
                        handler.sendMessage(msg);
                    }
                }

                //�������Ϊ��,�����̵߳ȴ�
                synchronized (this) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

    //�ص��ӿ�
    public interface ImageCallback{
        void loadImage(String path, Bitmap bitmap);
    }

    class Task{
        // �������������·��
        String path;
        // ���ص�ͼƬ
        Bitmap bitmap;
        // �ص�����
        ImageCallback callback;

        @Override
        public boolean equals(Object o) {
            Task task = (Task)o;
            return task.path.equals(path);
        }
    }
}
