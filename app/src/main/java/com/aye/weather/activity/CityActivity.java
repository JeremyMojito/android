package com.aye.weather.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aye.weather.CityDBHelper;
import com.aye.weather.R;
import com.aye.weather.adapter.CityAdapter;

import java.util.List;


public class CityActivity extends AppCompatActivity {
    private CityDBHelper cityDBHelper;
    private boolean flag=false;
    private CityAdapter cityAdapter;
    private List<String> cityList;
    private ListView showLv;
    private ImageView addIv;
    private TextView findTv;
    private MyReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        initView();
        //添加城市
        findTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CityActivity.this,AddActivity.class));
            }
        });
        //删除城市
        showLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String deleteCity=cityList.get(position);
                AlertDialog.Builder builder= new AlertDialog.Builder(CityActivity.this);
                builder.setTitle("提示").setMessage("是否删除该城市天气信息？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                flag=cityDBHelper.deleteCity(deleteCity);
                                if (flag){
                                    Toast.makeText(CityActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                                    initView();
                                    Intent intent=new Intent("UPDATE1");
                                    sendBroadcast(intent);
                                }else {
                                    Toast.makeText(CityActivity.this,"删除失败",Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .create().show();
                cityAdapter.notifyDataSetChanged();
                return true;
            }
        });
        myReceiver=new MyReceiver();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("UPDATE");
        registerReceiver(myReceiver,intentFilter);
    }

    private void initView() {
        showLv=findViewById(R.id.cityLv);
        addIv=findViewById(R.id.addIv);
        findTv=findViewById(R.id.findTv);

        cityDBHelper=new CityDBHelper(this);
        cityList=cityDBHelper.queryCity();
        cityAdapter=new CityAdapter(this,cityList);
        showLv.setAdapter(cityAdapter);
    }

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            initView();
        }
    }
}