package com.example.as.doctorh;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.util.Log;

public class DBOpenHelper extends SQLiteOpenHelper {

    public DBOpenHelper(Context context,String name,SQLiteDatabase.CursorFactory factory ,int version) {
        super(context,name,factory,version); //重写构造方法并设置工厂为null
    }

    String CREATE_TABLE_SQL =
            "create table familymember(id integer primary key autoincrement,name string ,telelphone string,master string)";

    public void create(SQLiteDatabase db){
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
 //创建用户信息表
        db.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    // 重写基类的onUpgrade()方法，以便数据库版本更新
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //提示版本更新并输出旧版本信息与新版本信息
        System.out.println("---版本更新-----" + oldVersion + "--->" + newVersion);
    }
}

