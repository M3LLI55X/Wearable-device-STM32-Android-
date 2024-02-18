//package com.example.as.doctorh;
//
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//public class User extends client {
//    private DBOpenHelper dbHelper;
//    public User(Context context) {
//        dbHelper = new DBOpenHelper(context);
//    }
//
//
//    public boolean Login(String username, String password) {
//        SQLiteDatabase sqLiteDatabase =dbHelper .getReadableDatabase();
//        String sql = "select * from user where username = ? and password = ? ";
//        Cursor rawQuery = sqLiteDatabase.rawQuery(sql, new String[] { username,
//                password });
//        if (rawQuery.moveToFirst() == true) {
//            rawQuery.close();
//            return true;
//        }
//        return false;
//    }
//
//}
