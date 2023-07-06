package com.musicneo.db;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 *  ContactInjfoDao  数据库操作类  dao后缀的都是数据库操作类
 *
 *  我们这里的每一个 增删改查 的方法都通过getWritableDatabase()去实例化了一个数据库,这里必须这么做
 *  不客气抽取 成为一个成员变量, 否则报错,若是觉得麻烦可以通过定义方法来置为null和重新赋值
 *
 *  —— 其实dao类在这里做得事情特别简单：
 *  1、定义一个构造方法，利用这个方法去实例化一个  数据库帮助类
 *  2、编写dao类的对应的 增删改查 方法。
 *
 */
public class MusicDao {

    private MusicDBHelper mMyDBHelper;
    private String tableName ="musicInfo";

    /**
     * dao类需要实例化数据库Help类,只有得到帮助类的对象我们才可以实例化 SQLiteDatabase
     * @param context
     */
    public MusicDao(Context context) {
        mMyDBHelper=new MusicDBHelper(context);
        SQLiteDatabase db = mMyDBHelper.getWritableDatabase();
    }



    public void insert(MusicBean bean){
        SQLiteDatabase db = mMyDBHelper.getWritableDatabase();
        db.execSQL("insert into "+tableName+"(name,path,url) values(?,?,?)", new Object[]{ bean.getName(), bean.getPath(),bean.getUrl()});
        db.close();
    }




    /**
     * 根据名字更新对应的路径
     * @param name
     * @param path
     * @return
     */
    public int updateData(String name,String path){
        SQLiteDatabase sqLiteDatabase = mMyDBHelper.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        contentValues.put("path", path);
        int updateResult = sqLiteDatabase.update(tableName, contentValues, "name=?", new String[]{name});
        sqLiteDatabase.close();
        return updateResult;
    }



    /**
     * 查询全部数据
     */
    public List<MusicBean> queryAllData(){
        SQLiteDatabase sqLiteDatabase =  mMyDBHelper.getReadableDatabase();
        //查询全部数据
        Cursor cursor =  sqLiteDatabase.query(tableName,null,null,null,null,null,null,null);
        List<MusicBean> list = new ArrayList<>();
        if(cursor.getCount() > 0) {
            //移动到首位
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String url = cursor.getString(cursor.getColumnIndex("url"));
                String path = cursor.getString(cursor.getColumnIndex("path"));
                MusicBean model = new MusicBean(id,name,url,path);
                list.add(model);
                //移动到下一位
                cursor.moveToNext();
            }
        }

        cursor.close();
        sqLiteDatabase.close();

        return list;
    }


}
