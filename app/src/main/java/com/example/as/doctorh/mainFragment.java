package com.example.as.doctorh;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.View;


public class mainFragment extends AppCompatActivity {
    String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);
        ImageView imageView1 = (ImageView) findViewById(R.id.image1);//获取布局文件的第一个导航图片
        ImageView imageView2 = (ImageView) findViewById(R.id.image2);//获取布局文件的第二个导航图片
        ImageView imageView3 = (ImageView) findViewById(R.id.image3);//获取布局文件的第三个导航图片
        imageView1.setOnClickListener(l);//为第一个导航图片添加单机事件
        imageView2.setOnClickListener(l);//为第二个导航图片添加单机事件
        imageView3.setOnClickListener(l);//为第三个导航图片添加单机事件
        FragmentManager fm = getFragmentManager();   // 获取Fragment
        FragmentTransaction ft = fm.beginTransaction(); // 开启一个事务
        Intent intent = getIntent();
        phone = intent.getStringExtra("username");
        Fragment f = new message_fragment(phone); //为Fra
        ft.replace(R.id.fragment, f); //替换Fragment
        ft.commit(); //提交事务
    }

    View.OnClickListener l = new View.OnClickListener() {

        public void onClick(View v) {
            FragmentManager fm = getFragmentManager();   // 获取Fragment
            FragmentTransaction ft = fm.beginTransaction(); // 开启一个事务
            Fragment f = null; //为Fragment初始化
            switch (v.getId()) {    //通过获取点击的id判断点击了哪个张图片
                case R.id.image1:
                    f = new tips_fragment(); //创建第一个Fragment
                    break;
                case R.id.image2:
                    f = new message_fragment(phone);//创建第二个Fragment
                    break;
                case R.id.image3:
                    f = new me_fragment(phone);//创建第三个Fragment
                    break;
                default:
                    break;
            }
            ft.replace(R.id.fragment, f); //替换Fragment
            ft.commit(); //提交事务
        }};}





