package com.aye.weather.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aye.weather.CityDBHelper;
import com.aye.weather.R;

import java.util.List;

public class AddAdapter extends RecyclerView.Adapter<AddAdapter.ViewHolder> {
    private Context context;
    private List<String> cityList;
    private CityDBHelper cityDBHelper;

    public AddAdapter(Context context, List<String> cityList, CityDBHelper cityDBHelper) {
        this.context = context;
        this.cityList = cityList;
        this.cityDBHelper = cityDBHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 加载布局文件
        View view = View.inflate(context, R.layout.add_recylerview_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // 设置城市名称
        holder.cityTv.setText(cityList.get(position));
        // 设置点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clickCity = cityList.get(position);
                if (clickCity != null) {
                    // 检查城市是否已存在数据库中
                    if (cityDBHelper.findCity(clickCity)) {
                        // 将城市添加到数据库中
                        if (cityDBHelper.addCity(clickCity)) {
                            // 添加成功，显示提示信息，并发送广播更新界面
                            Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent("UPDATE");
                            context.sendBroadcast(intent);
                        } else {
                            // 添加失败，显示提示信息
                            Toast.makeText(context, "添加失败", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // 城市已存在，显示提示信息
                        Toast.makeText(context, "已存在该城市", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    // 视图持有者类
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView cityTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // 获取布局中的城市名称文本视图
            cityTv = itemView.findViewById(R.id.itemCityTv);
        }
    }
}
