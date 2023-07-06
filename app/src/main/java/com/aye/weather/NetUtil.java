package com.aye.weather;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetUtil {
    public static final String URL_WEATHER="https://v0.yiketianqi.com/api?unescape=1&version=v9&appid=21429982&appsecret=nNe80hyA";

    public static String doGet(String urlString){
        String result="";
        String line;
        StringBuilder stringBuilder=null;
        BufferedReader bufferedReader=null;
        //连接网络
        HttpURLConnection connection=null;
        InputStreamReader isr=null;
        try {
            URL url=new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");  //链接方式
            connection.setConnectTimeout(5000);   //超时时间
            //从连接中读取数据(二进制)
            InputStream inputStream=connection.getInputStream();
            //对数据流进行加工
            isr=new InputStreamReader(inputStream);
            //创建缓冲区，将二进制流送入
            bufferedReader=new BufferedReader(isr);
            //从缓冲区一行一行读取字符串
            stringBuilder=new StringBuilder();
            while ((line=bufferedReader.readLine())!=null){
                stringBuilder.append(line); //进行拼接
            }
            result=stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                //关闭流
                connection.disconnect();
                bufferedReader.close();
                isr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    //拼接出来获取天气的url
    public static String getWeatherOfCity(String city){
        String url=URL_WEATHER+"&city="+city;
        Log.i("Aye","URL:"+url);
        Log.i("Aye","URLResult:"+doGet(url));
        return  doGet(url);
    }
}
