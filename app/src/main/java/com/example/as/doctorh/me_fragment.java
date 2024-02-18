package com.example.as.doctorh;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageView;

import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/2/18.
 */
@SuppressLint("ValidFragment")
public class me_fragment extends Fragment{
    private DBOpenHelper dbHelper;
    private String phone;
    @SuppressLint("ValidFragment")
    public me_fragment(String phone){
        this.phone = phone;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.me_fragment, null);
        final TextView name1 = view.findViewById(R.id.name1);
        final TextView name2 = view.findViewById(R.id.name2);
        final TextView tele1 = view.findViewById(R.id.tele1);
        final TextView tele2 = view.findViewById(R.id.tele2);
        final String names[] = new String[2];
        final String teles[] = new String[2];
        dbHelper=new DBOpenHelper( getActivity(),"members.db",null,1 );
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("familymember", null, null, null, null, null, "id desc ", "2");
            if (cursor.moveToFirst()) {
                int i = 0;
                do {
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String telephone = cursor.getString(cursor.getColumnIndex("telelphone"));
                    String master = cursor.getString(cursor.getColumnIndex("master"));
                    if(master.equals(phone)) {
                        names[i] = name;
                        teles[i] = telephone;
                        i++;
                    }
                } while (cursor.moveToNext());

            }
            if(names[0]!=null) {
                name1.setText(names[0]);
                tele1.setText(teles[0]);
            }
            if(names[1]!=null){
                name2.setText(names[1]);
                tele2.setText(teles[1]);
            }
        Button button = view.findViewById(R.id.btn);//获取选择头像按钮
        button.setOnClickListener(new View.OnClickListener() { //为按钮创建单机事件
            @Override
            public void onClick(View v) {
                //创建Intent对象
                Intent intent = new Intent(getActivity(), touxiang.class);
                startActivityForResult(intent, 0x11);//启动intent对应的Activity
                //Toast.LENGTH_SHORT).show(data);
            }
        });

        Button butn1=view.findViewById( R.id.bt1 );
        butn1.setOnClickListener(new View.OnClickListener() { //为按钮创建单机事件
            @Override
            public void onClick(View v) {
                //创建Intent对象
                Intent intent = new Intent(getActivity(), f1.class);
                intent.putExtra("username",phone);
                startActivityForResult(intent, 0x11);//启动intent对应的Activity
            }
        });
        Button butn2=view.findViewById( R.id.bt2 );
        butn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建Intent对象
                final String names[] = new String[2];
                final String teles[] = new String[2];
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor cursor = db.query("familymember", null, null, null, null, null, "id desc ", "2");
                if (cursor.moveToFirst()) {
                    int i = 0;
                    do {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String telephone = cursor.getString(cursor.getColumnIndex("telelphone"));

                        names[i] = name;
                        teles[i] = telephone;
                        i++;
                    } while (cursor.moveToNext());

                }
                if(names[0]!=null) {
                    name1.setText(names[0]);
                    tele1.setText(teles[0]);
                }
                if(names[1]!=null){
                    name2.setText(names[1]);
                    tele2.setText(teles[1]);
                }
            }
        });

        ImageView imge = view.findViewById( R.id.call );

        final String[] _name = new String[2];
        final String[] _tele = new String[2];
        imge.setOnClickListener( new View.OnClickListener() { //为按钮创建单机事件
                                     @Override
                                     public void onClick(View v) {
                                         String flag;
                                         _tele[0] = (String) tele1.getText();
                                         _tele[1] = (String) tele2.getText();
                                         if (_tele[0].equals("电话号码")) {
                                             Toast.makeText( getActivity().getApplicationContext(), "联系人信息为空", Toast.LENGTH_LONG ).show();
                                         } else {
                                             try {
                                                 flag = new UrgentConnect().sendmessage(phone);
                                                 if(flag.equals("success"))
                                                     Toast.makeText( getActivity().getApplicationContext(), "发送成功", Toast.LENGTH_LONG ).show();
                                                 else if(flag.equals("fail"))
                                                     Toast.makeText( getActivity().getApplicationContext(), "发送失败", Toast.LENGTH_LONG ).show();
                                             } catch (InterruptedException e) {
                                                 e.printStackTrace();
                                             }
                                         }

                                     }
                                 }
        );


        return view;
    }


    @Override

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==0x11 && resultCode==0x11){	//判断是否为待处理的结果
            Bundle bundle=data.getExtras();		//获取传递的数据包
            int imageId=bundle.getInt("imageId");	//获取选择的头像ID
            ImageView iv=(ImageView)getView().findViewById(R.id.imageView);	//获取布局文件中添加的ImageView组件
            iv.setImageResource(imageId);	//显示选择的头像
        }
    }

    private void init(String _tele0,String _tele1){
        String urlPath="http://192.168.3.138:8080/20170112/login/toJsonMain.action";
        URL url;
        int id=0;
        try {
            url=new URL(urlPath);
            JSONObject jsonObject=new JSONObject();
            jsonObject.put( "telephone0", _tele0 );
            jsonObject.put( "telephone1", _tele1);
            String content=String.valueOf(jsonObject);  //json串转string类型
            HttpURLConnection conn=(HttpURLConnection) url.openConnection(); //开启连接
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("ser-Agent", "Fiddler");
            conn.setRequestProperty("Content-Type","application/json");
            //写输出流，将要转的参数写入流里
            OutputStream os=conn.getOutputStream();
            os.write(content.getBytes()); //字符串写进二进流
            os.close();
            int code=conn.getResponseCode();
            if(code==200){
                Toast.makeText (getActivity().getApplicationContext(),"紧急信息发送成功", Toast.LENGTH_LONG ).show();
            }else{
                Toast.makeText (getActivity().getApplicationContext(),"紧急信息发送失败", Toast.LENGTH_LONG ).show();
            }



        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

