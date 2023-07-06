package com.aye.weather;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CityDBHelper extends SQLiteOpenHelper {
    private static SQLiteDatabase db;
    ContentValues values=new ContentValues();
    private long flag=0;

    public CityDBHelper(@Nullable Context context) {
        super(context, "city.db", null, 1);
        db=this.getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表
        String sql = "create table city(id integer primary key autoincrement,city varchar(20) unique not null)";
        db.execSQL(sql);
    }
    public boolean addCity(String city){
        values.put("city",city);
        flag=db.insert("city",null,values);
        return flag>0?true:false;
    }
    public boolean deleteCity(String city){
        flag=db.delete("city","city=?",new String[]{city});
        return flag>0?true:false;
    }
    public List<String> queryCity(){
        List<String> cityList=new ArrayList<>();
        Cursor cursor=db.query("city",null,null,null,null,null,null);
        if (cursor!=null){
            while (cursor.moveToNext()){
                String city=cursor.getString(1);
                cityList.add(city);
            }
        }
        return cityList;
    }
    public boolean findCity(String city) {
        Cursor cursor = db.query("city", null, "city=?", new String[]{city}, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String findCity = cursor.getString(1);
                Log.i("Aye",findCity+"findCity");
                if (findCity != null) {
                    return false;
                } else {
                    return true;
                }
            }
        }
        return true;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
