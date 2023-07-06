package com.aye.weather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aye.weather.Bean.Hours;
import com.aye.weather.R;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {
    private Context context;
    private List<Hours>  timeBean;

    public WeatherAdapter(Context context, List<Hours> timeBean) {
        this.context = context;
        this.timeBean = timeBean;
    }
    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.fragment_recylerview_item,parent,false);
        WeatherViewHolder weatherViewHolder=new WeatherViewHolder(view);
        return weatherViewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        Hours hoursBean = timeBean.get(position);
        holder.timeTv.setText(hoursBean.getHours());
        holder.timeWeatherTv.setText(hoursBean.getWea());
        holder.timeTempTv.setText(hoursBean.getTem()+"°C");
        holder.timeWindTv.setText(hoursBean.getWin()+" "+hoursBean.getWin_speed());
    }

    @Override
    public int getItemCount() {
        if (timeBean==null){
            return 0;
        }
        return timeBean.size();
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder {
        TextView timeTv;
        TextView timeTempTv;
        TextView timeWeatherTv;
        TextView timeWindTv;
        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            timeTv = itemView.findViewById(R.id.timeTv);
            timeTempTv = itemView.findViewById(R.id.timeTempTv);
            timeWeatherTv = itemView.findViewById(R.id.timeWeatherTv);
            timeWindTv = itemView.findViewById(R.id.timeWindTv);
        }
    }
}
