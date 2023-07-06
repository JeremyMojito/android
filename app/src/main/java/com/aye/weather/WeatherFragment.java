package com.aye.weather;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aye.weather.Bean.Data;
import com.aye.weather.Bean.Hours;
import com.aye.weather.Bean.Index;
import com.aye.weather.Bean.JsonRootBean;
import com.aye.weather.adapter.WeatherAdapter;
import com.google.gson.Gson;

import java.util.List;

public class WeatherFragment extends Fragment implements View.OnClickListener{
    private TextView titleTv;
    private TextView tempTv;
    private TextView mainWeatherTv;
   // private TextView sunriseTv;
    private TextView sunsetTv;
    private TextView windTv;
    private TextView humidityTv;
    private TextView pressureTv;
    private TextView windTv1;
    private TextView humidityTv1;
    private TextView pressureTv1;
    private ImageView todayIconIv;
    private TextView todayTv;
    private TextView todayAirTv;
    private TextView todayTempTv;
    private ImageView tomorrowIconIv;
    private TextView tomorrowTv;
    private TextView tomorrowAirTv;
    private TextView tomorrowTempTv;
    private ImageView afterIconIv;
    private TextView afterTv;
    private TextView afterAirTv;
    private TextView afterTempTv;
    private TextView webTv;
    private RecyclerView hoursRv;
    private ImageView clotheIv;
    private ImageView UVIv;
    private ImageView sportIv;
    private TextView clotheTv;
    private TextView UVTv;
    private TextView sportTv;
    private ImageView carIv;
    private ImageView airPollutionIv;
    private ImageView sickIv;
    private TextView carTv;
    private TextView airPollutionTv;
    private TextView sickTv;

    private String UVTitle,UVLevel,UVDesc;
    private String clotheTitle,clotheLevel,clotheDesc;
    private String sportTitle,sportLevel,sportDesc;
    private String carTitle,carLevel,carDesc;
    private String airTitle,airLevel,airDesc;
    private String sickTitle,sickLevel,sickDesc;
    private WeatherAdapter weatherAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_main, container, false);
        initView(view);
        //通过activity传值获取到当前fragment加载的是哪个地方的天气情况
        Bundle arguments = getArguments();
        String city=arguments.getString("city");
        titleTv.setText(city);
        getWeatherCity(city);
        return view;
    }
    private void getWeatherCity(String selectCity) {
        //开启子线程，请求网络
        new Thread(new Runnable() {
            @Override
            public void run() {
                //请求网络
                String weatherOfCity=NetUtil.getWeatherOfCity(selectCity);
                //使用handler将数据传递给主线程
                Message message=Message.obtain();
                message.what=0;
                message.obj=weatherOfCity;
                handler.sendMessage(message);
            }
        }).start();
    }
    private Handler handler=new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what==0){
                //主线程收到的天气数据
                String weather= (String) msg.obj;
                Log.i("Aye","主线程收到的天气数据："+weather);
                //使用gson解析
                Gson gson=new Gson();
                JsonRootBean jsonRootBean=gson.fromJson(weather, JsonRootBean.class);
                updateWeather(jsonRootBean);
            }
        }
    };
    //数据显示
    private void updateWeather(JsonRootBean jsonRootBean) {
        if (jsonRootBean!=null){
            List<Data> dayWeather = jsonRootBean.getData(); //获取每一天的数据
            Data todayWeather = dayWeather.get(0);//获取今天的数据
            if (todayWeather!=null){
                tempTv.setText(todayWeather.getTem1());
                mainWeatherTv.setText(todayWeather.getWea());;
                todayTv.setText("今天："+todayWeather.getWea());
                todayAirTv.setText("空气："+todayWeather.getAir_level());
                todayTempTv.setText(todayWeather.getTem()+"~"+todayWeather.getTem2());
                todayIconIv.setImageResource(getImg(todayWeather.getWea_img()));
                windTv.setText(todayWeather.getWin_speed());
                humidityTv.setText(todayWeather.getHumidity());
                pressureTv.setText(todayWeather.getPressure()+"hPa");
                windTv1.setText(todayWeather.getWin().get(0));
//                sunriseTv.setText("日出："+todayWeather.getSunrise());
//                sunsetTv.setText("日落："+todayWeather.getSunset());
            }
            //获取明天的数据
            Data tomorrowWeather = dayWeather.get(1);
            if (tomorrowWeather!=null){
                tomorrowTv.setText("明天："+tomorrowWeather.getWea());
                tomorrowAirTv.setText("空气："+tomorrowWeather.getAir_level());
                tomorrowTempTv.setText(tomorrowWeather.getTem()+"~"+tomorrowWeather.getTem2());
                tomorrowIconIv.setImageResource(getImg(tomorrowWeather.getWea_img()));
            }
            //获取后天的数据
            Data afterWeather = dayWeather.get(2);
            if (afterWeather!=null){
                afterTv.setText("后天："+afterWeather.getWea());
                afterAirTv.setText("空气："+afterWeather.getAir_level());
                afterTempTv.setText(afterWeather.getTem()+"~"+afterWeather.getTem2());
                afterIconIv.setImageResource(getImg(afterWeather.getWea_img()));
            }
            //获取指数信息
            List<Index> index = todayWeather.getIndex();
            if (index!=null) {
                //紫外线指数
                UVTitle = index.get(0).getTitle();
                UVLevel = index.get(0).getLevel();
                UVDesc = index.get(0).getDesc();
                //穿衣指数
                clotheTitle = index.get(3).getTitle();
                clotheLevel = index.get(3).getLevel();
                clotheDesc = index.get(3).getDesc();
                //运动指数
                sportTitle=index.get(1).getTitle();
                sportLevel=index.get(1).getLevel();
                sportDesc=index.get(1).getDesc();
                //洗车指数
                carTitle=index.get(4).getTitle();
                carLevel=index.get(4).getLevel();
                carDesc=index.get(4).getDesc();
                //血糖指数
                sickTitle=index.get(2).getTitle();
                sickLevel=index.get(2).getLevel();
                sickDesc=index.get(2).getDesc();
                //空气污染指数
                airTitle=index.get(5).getTitle();
                airLevel=index.get(5).getLevel();
                airDesc=index.get(5).getDesc();
            }
            //获取逐小时天气情况，传递给Adapter用于显示
            List<Hours> timeBean = todayWeather.getHours();
            weatherAdapter=new WeatherAdapter(getActivity(),timeBean);
            LinearLayoutManager manager=new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false);
            hoursRv.setAdapter(weatherAdapter);
            hoursRv.setLayoutManager(manager);
        }
    }
    private int getImg(String wea_img) {
        int result = 0;
        switch (wea_img) {
            case "qing":
                result=R.mipmap.sun;
                break;
            case "yin":
                result=R.mipmap.yin;
                break;
            case "yu":
                result=R.mipmap.yu;
                break;
            case "yun":
                result=R.mipmap.yun;
                break;
            case "bingbao":
                result=R.mipmap.bingbao;
                break;
            case "wu":
                result=R.mipmap.wu;
                break;
            case "shachen":
                result=R.mipmap.shachen;
                break;
            case "lei":
                result=R.mipmap.lei;
                break;
            case "xue":
                result=R.mipmap.xue;
                break;
            default:
                result=R.mipmap.sun;
                break;
        }
        return result;
    }

    private void initView(View view) {
        titleTv = view.findViewById(R.id.titleTv);
        tempTv = view.findViewById(R.id.tempTv);
        mainWeatherTv = view.findViewById(R.id.mainWeatherTv);
        //sunriseTv = view.findViewById(R.id.sunriseTv);
        sunsetTv = view.findViewById(R.id.sunsetTv);
        windTv = view.findViewById(R.id.windTv);
        humidityTv = view.findViewById(R.id.humidityTv);
        pressureTv = view.findViewById(R.id.pressureTv);
        windTv1 = view.findViewById(R.id.windTv1);
        humidityTv1 = view.findViewById(R.id.humidityTv1);
        pressureTv1 = view.findViewById(R.id.pressureTv1);
        todayIconIv = view.findViewById(R.id.todayIconIv);
        todayTv = view.findViewById(R.id.todayTv);
        todayAirTv = view.findViewById(R.id.todayAirTv);
        todayTempTv = view.findViewById(R.id.todayTempTv);
        tomorrowIconIv = view.findViewById(R.id.tomorrowIconIv);
        tomorrowTv = view.findViewById(R.id.tomorrowTv);
        tomorrowAirTv = view.findViewById(R.id.tomorrowAirTv);
        tomorrowTempTv = view.findViewById(R.id.tomorrowTempTv);
        afterIconIv = view.findViewById(R.id.afterIconIv);
        afterTv = view.findViewById(R.id.afterTv);
        afterAirTv = view.findViewById(R.id.afterAirTv);
        afterTempTv = view.findViewById(R.id.afterTempTv);
        webTv = view.findViewById(R.id.webTv);
        hoursRv = view.findViewById(R.id.hoursRv);
        clotheIv = view.findViewById(R.id.clotheIv);
        UVIv = view.findViewById(R.id.UVIv);
        sportIv = view.findViewById(R.id.sportIv);
        clotheTv = view.findViewById(R.id.clotheTv);
        UVTv = view.findViewById(R.id.UVTv);
        sportTv = view.findViewById(R.id.sportTv);
        carIv = view.findViewById(R.id.carIv);
        airPollutionIv = view.findViewById(R.id.airPollutionIv);
        sickIv = view.findViewById(R.id.sickIv);
        carTv = view.findViewById(R.id.carTv);
        airPollutionTv = view.findViewById(R.id.airPollutionTv);
        sickTv = view.findViewById(R.id.sickTv);

        clotheIv.setOnClickListener(this);
        UVIv.setOnClickListener(this);
        sickIv.setOnClickListener(this);
        carIv.setOnClickListener(this);
        airPollutionIv.setOnClickListener(this);
        sportIv.setOnClickListener(this);
        webTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.addIv:

                    break;

                case R.id.moreIv:

                    break;
                case R.id.webTv:
                    Uri uri=Uri.parse("https://tianqi.qq.com/index.htm");
                    Intent intent=new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    intent.setData(uri);
                    startActivity(intent);
                    break;
                case R.id.clotheIv:
                    showAlertDialog(clotheTitle,clotheLevel,clotheDesc);
                    break;
                case R.id.UVIv:
                    showAlertDialog(UVTitle,UVLevel,UVDesc);
                    break;
                case R.id.sportIv:
                    showAlertDialog(sportTitle,sportLevel,sportDesc);
                    break;
                case R.id.carIv:
                    showAlertDialog(carTitle,carLevel,carDesc);
                    break;
                case R.id.airPollutionIv:
                    showAlertDialog(airTitle,airLevel,airDesc);
                    break;
                case R.id.sickIv:
                    showAlertDialog(sickTitle,sickLevel,sickDesc);
                    break;
            }
    }
    private void showAlertDialog(String title, String level, String desc) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        builder.setTitle(title).setMessage("\n"+level+"\n\n"+desc).create().show();
    }
}