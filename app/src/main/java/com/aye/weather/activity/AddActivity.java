package com.aye.weather.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.aye.weather.CityDBHelper;
import com.aye.weather.R;
import com.aye.weather.adapter.AddAdapter;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {
    private AddAdapter addAdapter;
    private CityDBHelper cityDBHelper;
    private EditText findEt;
    private ImageView findTv;
    private RecyclerView cityRv;

    private List<String> cityList;
    private String[] cityStrings=new String[]{"北京","天津","哈尔滨","沈阳","石家庄", "兰州", "西安", "郑州", "太原", "长沙", "南京", "贵阳",   "杭州", "广州", "台北",
            "上海" , "重庆", "长春", "呼和浩特", "乌鲁木齐", "西宁", "银川", "济南", "合肥", "武汉", "成都", "拉萨", "昆明", "南昌", "福州", "海口", "澳门","淮安","徐州","苏州","宿迁"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        findEt = findViewById(R.id.findEt);
        findTv = findViewById(R.id.findTv);
        cityRv = findViewById(R.id.cityRv);

        cityDBHelper=new CityDBHelper(this);

        cityList=new ArrayList<>();
        for (int i=0;i<cityStrings.length;i++){
            cityList.add(cityStrings[i]);
        }

        addAdapter=new AddAdapter(this,cityList,cityDBHelper);
        cityRv.setLayoutManager(new GridLayoutManager(this,3));
        cityRv.setAdapter(addAdapter);


        findTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String findCity=findEt.getText().toString();
                if (findCity!=null){
                    if(cityDBHelper.findCity(findCity)){
                        if (cityDBHelper.addCity(findCity)) {
                            Toast.makeText(AddActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent("UPDATE");
                            sendBroadcast(intent);
                        }else {
                            Toast.makeText(AddActivity.this,"添加失败",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(AddActivity.this,"已存在该城市",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(AddActivity.this,"输入城市为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}