package com.example.as.doctorh;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/2/18.
 */
public class tips_fragment extends Fragment {

    final int FLAG_MSG = 0x001;    //定义要发送的消息代码
    private ViewFlipper flipper;   //定义ViewFlipper
    private Message message;        //声明消息对象
    //定义图片数组
    private int[] images = new int[]{R.mipmap.img6, R.mipmap.img7, R.mipmap.img8, R.mipmap.img11, R.mipmap.img12};
    private Animation[] animation = new Animation[2];  //定义动画数组，为ViewFlipper指定切换动画
    String A="打篮球需要注意些什么，doctor.h告诉你",B="如何提高心肺功能？4种方法在家就能做",C="建树有方，绿动生活",D="来，学习运动健康小知识";
    private String[] data={ A,B,C,D};
    private List<Tips> tipsList=new ArrayList<>();



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.tips_fragment,null);
        flipper =view.findViewById(R.id.viewFlipper);  //获取ViewFlipper
        for (int i = 0; i < images.length; i++) {      //遍历图片数组中的图片
            ImageView imageView = new ImageView(getActivity());  //创建ImageView对象
            imageView.setImageResource(images[i]);  //将遍历的图片保存在ImageView中
            flipper.addView(imageView);             //加载图片
        }
        //初始化动画数组
        animation[0] = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_right); //右侧平移进入动画
        animation[1] = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_left); //左侧平移退出动画
        flipper.setInAnimation(animation[0]);   //为flipper设置图片进入动画效果
        flipper.setOutAnimation(animation[1]);  //为flipper设置图片退出动画效果

        message=Message.obtain();       //获得消息对象
        message.what=FLAG_MSG;  //设置消息代码
        handler.sendMessage(message); //发送消息


        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,data);
        ListView listView=view.findViewById(R.id.list_view);
        listView.setAdapter( adapter );



        initTips();
        TipsAdapter adapter1=new TipsAdapter(getActivity(),R.layout.item1,tipsList);
        ListView listView1=view.findViewById(R.id.list_view);
        listView1.setAdapter( adapter1 );
        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tips tip=tipsList.get(position);
                if(position==0){
                    Uri uri = Uri.parse("http://www.baidu.com");    //设置跳转的网站
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent); }
                else if(position==1){
                    Uri uri = Uri.parse("http://www.baidu.com");    //设置跳转的网站
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);}
                else if(position==2){
                    Uri uri = Uri.parse("http://www.baidu.com");    //设置跳转的网站
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent); }
                else if(position==3){
                    Uri uri = Uri.parse("http://www.baidu.com");    //设置跳转的网站
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);}
            }
        } );
        return view;

    }
    Handler handler = new Handler() {  //创建android.os.Handler对象
        @Override


        public void handleMessage(Message msg) {
            if (msg.what == FLAG_MSG) {  //如果接收到的是发送的标记消息
                flipper.showPrevious();                  //示下一张图片
            }
            Log.e("AAA","shwimage::");
            message=handler.obtainMessage(FLAG_MSG);   //获取要发送的消息
            handler.sendMessageDelayed(message, 3000);  //延迟3秒发送消息
        }
    };
    private void initTips(){
        for (int i=0;i<1;i++){
            Tips tip1=new Tips(A,R.mipmap.image1);
            tipsList.add(tip1);
            Tips tip2=new Tips(B,R.mipmap.image2);
            tipsList.add(tip2);
            Tips tip3=new Tips(C,R.mipmap.image3);
            tipsList.add(tip3);
            Tips tip4=new Tips(D,R.mipmap.image4);
            tipsList.add(tip4);

        }

    }
}





