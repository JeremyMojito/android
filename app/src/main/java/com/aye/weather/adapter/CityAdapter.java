package com.aye.weather.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aye.weather.R;

import java.util.List;

public class CityAdapter extends BaseAdapter {
    private Context context;
    private List<String> cityList;

    public CityAdapter(Context context, List<String> cityList) {
        this.context = context;
        this.cityList = cityList;
    }

    @Override
    public int getCount() {
        return cityList.size();
    }

    @Override
    public Object getItem(int position) {
        return cityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView=View.inflate(context, R.layout.city_listview_item,null);
            viewHolder.cityNameTv=convertView.findViewById(R.id.cityNameTv);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        viewHolder.cityNameTv.setText(cityList.get(position));
        return convertView;
    }
    class ViewHolder{
        TextView cityNameTv;
    }
}
