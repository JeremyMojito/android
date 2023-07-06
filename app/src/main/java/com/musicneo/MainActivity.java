package com.musicneo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadTask;
import com.musicneo.db.MusicBean;
import com.musicneo.db.MusicDao;
import com.musicneo.db.SharedPreferencesUtils;
import com.musicneo.dialog.DownloadDialog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView listView;
    private String curName; //当前的下载名
    private String curPath; //当前的下载保存路径
    private Context mContext;
    private MusicDao mDao;
    public static List<MusicBean> list = new ArrayList();
    public static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private DownloadDialog mm;
    private int currposition;

    class MyBaseAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i).getName();
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            View view = View.inflate(mContext, R.layout.item_layout, null);
            TextView tv_name = view.findViewById(R.id.item_name);
            ImageView iv = view.findViewById(R.id.iv);
            iv.setImageResource(R.drawable.mm);
            tv_name.setText(list.get(i).getName());
            return view;
        }
    }

    /**
     * 验证读取sd卡的权限
     *
     * @param activity
     */
    public boolean verifyStoragePermissions(Activity activity) {
        /*******below android 6.0*******/
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        }
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            return false;
        } else {
            return true;
        }
    }

    /**
     * 初始化导入数据，导出歌曲名称和下载地址，提供后续下载
     */
    public void initImport() {
        mDao = new MusicDao(MainActivity.this);
        verifyStoragePermissions(this);
        SharedPreferencesUtils sharedPrefrencesHelper = new SharedPreferencesUtils(getApplicationContext());
        boolean isInit = sharedPrefrencesHelper.getBoolean("init");
        if (!isInit) {
            MusicBean bean = new MusicBean("Benjamin Lee - On a calm seas", "http://47.99.58.88/1.mp3", "");
            mDao.insert(bean);
            MusicBean bean1 = new MusicBean("Kathrin Klimek - In Love With Trees", "http://47.99.58.88/2.mp3", "");
            mDao.insert(bean1);
            MusicBean bean2 = new MusicBean("Kathrin Klimek - Friendship Forever", "http://47.99.58.88/3.mp3", "");
            mDao.insert(bean2);
            MusicBean bean3 = new MusicBean("Kathrin Klimek - United For Joy", "http://47.99.58.88/4.mp3", "");
            mDao.insert(bean3);
            MusicBean bean4 = new MusicBean("Kathrin Klimek - French Ride", "http://47.99.58.88/5.mp3", "");
            mDao.insert(bean4);
            MusicBean bean5 = new MusicBean("Kathrin Klimek - Say Hello My Joy", "http://47.99.58.88/6.mp3", "");
            mDao.insert(bean5);
            sharedPrefrencesHelper.setBoolean("init", true);
        }
    }

    public void initData() {
        list = mDao.queryAllData();
    }


    /**
     * 格式化日期时间
     *
     * @param date   date
     * @param format format
     * @return String
     */
    public static String format(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINESE);
        return sdf.format(date);
    }

    public static String getMicronetSDPathNew(Context mContext){
        String path ="";
        path = mContext.getExternalFilesDir(null).getAbsolutePath() ;
        path =  path+ File.separator+"APDU"+ File.separator;
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        return path;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Aria.download(this).register();
        setContentView(R.layout.music_list);
        initImport();
        initData();
        this.mContext = this;
        listView = findViewById(R.id.lv);
        MyBaseAdapter adapter = new MyBaseAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MusicBean temp = list.get(position);
                if(TextUtils.isEmpty(temp.getPath())){
                    String fileName = format(new Date(), "yyyy-MM-ddHHmmssSSS") + ".mp3";
                    curPath = getMicronetSDPathNew(mContext) + fileName;
                    Aria.download(mContext)
                            .load(temp.getUrl())     //读取下载地址
                            .setFilePath(curPath) //设置文件保存的完整路径
                            .start();
                    mm = new DownloadDialog(mContext, 100);
                    mm.show();
                    curName = temp.getName();
                    currposition= position;
                }else{
                    toRun(position);
                }
            }
        });
    }

    public void toRun(int position){
        //点击跳转到播放页面播放歌曲
        Intent intent = new Intent(mContext, Music_Activity.class);
        intent.putExtra("index", position);
        startActivity(intent);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }


    /**
     * 下载失败
     * @param task
     */
    @Download.onTaskFail
    protected void onTaskFail(DownloadTask task) {
        Toast.makeText(mContext,"网络错误",Toast.LENGTH_LONG).show();
    }

    //在这里处理任务执行中的状态，如进度进度条的刷新
    @Download.onTaskRunning
    protected void running(DownloadTask task) {
        int p = task.getPercent();  //任务进度百分比
        String speed = task.getConvertSpeed();  //转换单位后的下载速度，单位转换需要在配置文件中打开
        if(mm!=null){
            mm.updateLoading(p);
        }
    }

    @Download.onTaskComplete
    void taskComplete(DownloadTask task) {
        //在这里处理任务完成的状态
        if(mm!=null){
            Toast.makeText(mContext,"下载完成",Toast.LENGTH_LONG).show();
            mm.updateLoading(100);
            mDao.updateData(curName,curPath);
            mm.dismiss();
            //更新完刷新列表
            list = mDao.queryAllData();
            toRun(currposition);

        }
    }

}
