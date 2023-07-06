package com.aye.weather.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aye.weather.CityDBHelper;
import com.aye.weather.adapter.FragmentPagerAdapter;
import com.aye.weather.R;
import com.aye.weather.WeatherFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    private ImageView addIv;
    private TextView titleTv;
    private ImageView moreIv;
    private ViewPager showVp;
    //ViewPager的数据源
    List<Fragment> fragmentList;
    //表示需要显示的城市的集合
    List<String> cityList;
    //ViewPager的页数
    List<ImageView> imgList;

    private CityDBHelper cityDBHelper;
    private MyReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPager();

        addIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CityActivity.class));
            }
        });

        myReceiver=new MyReceiver();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("UPDATE");
        intentFilter.addAction("UPDATE1");
        registerReceiver(myReceiver,intentFilter);
    }

    private void initPager() {
        addIv = findViewById(R.id.addIv);
        moreIv = findViewById(R.id.moreIv);
        showVp = findViewById(R.id.showVp);

        cityDBHelper=new CityDBHelper(this);
        fragmentList=new ArrayList<>();
        cityList=cityDBHelper.queryCity();
        imgList=new ArrayList<>();

        if (cityList.size()==0) {
            cityList.add("北京");
        }
        //创建Fragment对象添加到ViewPager数据源当中
        for (int i=0;i<cityList.size();i++){
            WeatherFragment weatherFragment = new WeatherFragment();
            Bundle bundle = new Bundle();
            bundle.putString("city",cityList.get(i));
            weatherFragment.setArguments(bundle);
            fragmentList.add(weatherFragment);
        }
        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager(),fragmentList);
        showVp.setAdapter(fragmentPagerAdapter);
    }

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            initPager();
        }
    }
}
