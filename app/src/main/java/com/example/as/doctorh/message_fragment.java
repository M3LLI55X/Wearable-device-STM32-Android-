package com.example.as.doctorh;
import com.github.mikephil.charting.charts.LineChart;
import com.google.gson.Gson;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;



/**
 * Created by Administrator on 2016/2/18.
 */

@SuppressLint("ValidFragment")
public class message_fragment extends Fragment {
    LineChart chart1 ;
    TextView HRTV;
    TextView TEMTV;
    TextView WIGTV;
    TextView HPTV;
    String HRtext;
    String HBPtext;
    String LBPtext;
    String TEMtext;
    String WIGtext;
    Data data=new Data();
    int continueSet=data.getContSet();
    String telephone;
    private TextView settext;
    @SuppressLint("ValidFragment")
    public message_fragment(String phone){
        this.telephone = phone;
    }
    public String getPhone(){
        return this.telephone;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.message_fragment,null);
        View view2 = LayoutInflater.from(getActivity()).inflate( R.layout.fragment_message_setting, null);
        settext = (TextView) view2.findViewById(R.id.REsetting);

      /*  Button btn =  view.findViewById(R.id.xx1);//获取注册按钮
       btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), message1.class);
                startActivityForResult(intent, 0x11);//启动intent对应的Activity
            }});*/

        HRTV =(TextView)view.findViewById(R.id.HTVHRView);
        TEMTV =(TextView)view.findViewById(R.id.HTVTView);
        WIGTV =(TextView)view.findViewById(R.id.HTVWView);
        HPTV =(TextView)view.findViewById(R.id.HTVHPView);
        ImageView HR;
        ImageView TEM;
        ImageView WIG;
        ImageView HP;
        //View getView = inflater.inflate(R.layout.message_fragment, container, false);
        HR =(ImageView)view.findViewById(R.id.HIVHR);
        TEM=(ImageView)view.findViewById(R.id.HIVT);
        WIG=(ImageView)view.findViewById(R.id.HIVW);
        HP =(ImageView)view.findViewById(R.id.HIVHP);

        ImageView ContinueSetting;
        ContinueSetting =(ImageView)view.findViewById(R.id.MessageSet);
        ContinueSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MessageSettingFragment.class);
                intent.putExtra("phone",telephone);
                startActivity(intent);
            }
        });

        HR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), htvhrActivity.class);
                intent.putExtra("type","rate");
                intent.putExtra("username",telephone);
                startActivity(intent);
            }
        });

        TEM.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), htvhrActivity.class);
                    intent.putExtra("type","temp");
                    intent.putExtra("username",telephone);
                    startActivity(intent);

            }
        });
        WIG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), htvhrActivity.class);
                intent.putExtra("type","walk");
                intent.putExtra("username",telephone);
                startActivity(intent);}
        });
        HP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), htvhrActivity.class);
                intent.putExtra("type","press");
                intent.putExtra("username",telephone);
                startActivity(intent);
            }
        });

        Button RE;
        RE=(Button)view.findViewById(R.id.messagebutton);//更新数据
        RE.setOnClickListener(new View.OnClickListener(){
            @SuppressLint("SetTextI18n")
            public void onClick(View v) {
                System.out.println("phone is:"+telephone);
                User1 user = new User1();
                DataRefresh dataRefresh = new DataRefresh();
                try {

                    dataRefresh.refresh(telephone,user);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("store rate:"+user.getHR());
                System.out.println("store temp:"+user.getTEM());
                System.out.println("store hpress:"+user.getHBP());
                System.out.println("store lpress:"+user.getLBP());
                System.out.println("store walk:"+user.getWIG());
//                String tp=init(telephone);
                if (dataRefresh.flag.equals("success")) {
                 HRtext = Integer.toString(user.getHR());
                 TEMtext = Double.toString(user.getTEM());
                 WIGtext = Integer.toString(user.getWIG());
                 HBPtext = Integer.toString(user.getHBP());
                 LBPtext = Integer.toString(user.getLBP());
                    HRTV.setText(HRtext);
                    TEMTV.setText(TEMtext);
                    WIGTV.setText(WIGtext);
                    HPTV.setText(HBPtext + "/" + LBPtext);
              }
            }});


        return view;
    }
    public  void onResume() {

        super.onResume();
        if (continueSet==1){
            while(true){
                System.out.println("phone is:"+telephone);
                User1 user = new User1();
                try {
                    new DataRefresh().refresh(telephone,user);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("store rate:"+user.getHR());
                System.out.println("store temp:"+user.getTEM());
                System.out.println("store hpress:"+user.getHBP());
                System.out.println("store lpress:"+user.getLBP());
                System.out.println("store walk:"+user.getWIG());
//                String tp=init(telephone);
////                if (tp==telephone) {
                HRtext = Integer.toString(user.getHR());
                TEMtext = Double.toString(user.getTEM());
                WIGtext = Integer.toString(user.getWIG());
                HBPtext = Integer.toString(user.getHBP());
                LBPtext = Integer.toString(user.getLBP());
                HRTV.setText(HRtext);
                TEMTV.setText(TEMtext);
                WIGTV.setText(WIGtext);
                HPTV.setText(HBPtext + "/" + LBPtext);
            }
        }
    }




//    private final String init(String telephone){
////  192.168.3.138 这个ip地址是电脑Ipv4 地址 /20170112 是服务端的项目名称  /login/toJsonMain 是@RequestMapping的地址
//
//    }




}



