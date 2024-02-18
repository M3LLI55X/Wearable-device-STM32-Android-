package com.example.as.doctorh;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MainActivityText extends AppCompatActivity {
    private DBOpenHelper dbOpenHelper;   //定义DBOpenHelper
    String name,pwd,yz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main_text );
        TextView  tw=(TextView) findViewById( R.id.maintextBT);
        final CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(tw, 60000, 1000);
        //dbOpenHelper = new DBOpenHelper( MainActivity.this );

        TextView password = (TextView) findViewById( R.id.textwang_mima);   //获取布局文件中的忘记密码
        password.setOnClickListener( new View.OnClickListener() { //为忘记密码创建单击监听事件
            @Override
            public void onClick(View v) {
                //创建Intent对象
                Intent intent = new Intent( MainActivityText.this, password.class );
                startActivity( intent ); //启动Activity
            }
        } );
        TextView alter = (TextView) findViewById( R.id.textalter );//切换登陆方式
        alter.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent( MainActivityText.this, MainActivity.class );
                startActivity( intent ); //启动Activity
            }
        } );

        TextView textView = (TextView) findViewById( R.id.maintextzhu_ce);//获取注册按钮
        textView.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent( MainActivityText.this, register.class );
                startActivity( intent ); //启动Activity
            }
        } );
/*
        Button btn = (Button) findViewById( R.id.zhu_ce );//获取注册按钮
        btn.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent( MainActivity.this, register.class );
                startActivity( intent ); //启动Activity
            }
        } );
*/

        Button butn = (Button) findViewById( R.id.textdeng_lu);//获取登录按钮
        //获取输入的用户信息
        butn.setOnClickListener( new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            public void onClick(View v) {
                //String username = ((EditText) findViewById(R.id.username)).getText().toString();
                //Bundle bundle = new Bundle();
                // bundle.putCharSequence("username", username);//保存姓名
                //Intent intent=new Intent(MainActivity.this, login.class);
                //intent.putExtras(bundle);//将Bundle对象添加到Intent对象中
                //String _username = ((EditText) findViewById(R.id.username)).getText().toString();
                //String _password = ((EditText) findViewById(R.id.password)).getText().toString();


                //User user=new User(MainActivity.this);
                //boolean flag = user.Login(_username, _password);
                //if (flag) {
                //startActivity(intent); //启动Activity
                //    } else {
                //         Toast.makeText(MainActivity.this, "登录失败！",
                //                Toast.LENGTH_SHORT).show();
                //  }

                //final String username = ((EditText) findViewById( R.id.username )).getText().toString();
                //final String password = ((EditText) findViewById( R.id.password )).getText().toString();
                // EditText etpassword = (EditText) findViewById( R.id.mainpassword );
                EditText ettelephone = (EditText) findViewById( R.id.maintexttelephone);
                EditText etyanzheng = (EditText) findViewById( R.id.maintextEditText);
                //final String password = etpassword.getText().toString();
                final String telephone = ettelephone.getText().toString();
                final String yanzheng = etyanzheng.getText().toString();
                SharedPreferences.Editor editor=getSharedPreferences("telephone",MODE_PRIVATE ).edit();
                editor.putString( "telephone",telephone );
                editor.apply();
                Data data=new Data();
                data.setTelephone(telephone);
                String flag = null;
                User1 user = new User1();
                user.setTelephone(telephone);
                try {
                    flag = new login().login_byyanzheng(user);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Log.i("MainActivityTextfunction",new login().login_byyanzheng(user));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.e("MainActivityText",flag+" ");
                System.out.println("yanzhengma is:"+yanzheng);
                System.out.println("input yanzhengma is:"+yz);
                if(flag=="success") {
                    if (yz.equals(yanzheng)) {   //判断id是否大于1，>1就是查询到有数据 ，可以进入主界面
                        Toast.makeText(getApplicationContext(), "验证成功", Toast.LENGTH_LONG).show();
                        Intent it=new Intent(MainActivityText.this,loginsuccess.class);
                        it.putExtra("username",telephone);
                        startActivity(it);
                    } else {
                        Toast.makeText(getApplicationContext(), "验证失败", Toast.LENGTH_LONG).show();
                    }
                }
                else if(flag=="error") Toast.makeText(getApplicationContext(), "账号不存在", Toast.LENGTH_LONG).show();
                        /*if(num>0){   //判断id是否大于1，>1就是查询到有数据 ，可以进入主界面
                            Intent it=new Intent(MainActivity.this,login.class);
                            it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(it);
                        }else {
                            Toast.makeText (getApplicationContext(),"用户或密码错误", Toast.LENGTH_LONG ).show();
                        }*/
                    }
        } );

        tw.setOnClickListener( new View.OnClickListener(){//获取验证码
            public void onClick(View v) {
                mCountDownTimerUtils.start();
                EditText ettelephone = (EditText) findViewById( R.id.maintexttelephone);
                String telephone = ettelephone.getText().toString();

                User1 user1 = new User1();
                user1.setTelephone( telephone );
                try {
                    yz = new Send_yanzheng().send_yanzheng(user1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }} );


    }

    //json格式与服务端交互
    private final String init(String phone) throws IOException {
//  192.168.3.138 这个ip地址是电脑Ipv4 地址 /20170112 是服务端的项目名称  /login/toJsonMain 是@RequestMapping的地址
        boolean flag = true;
        String result = null;
        try {
           String urlPath = "http://10.0.2.3:8585/login_byyanzheng?phone=" + phone;
           URL url = new URL(urlPath);
           HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //开启连接
           conn.setConnectTimeout(5000);
           conn.setDoOutput(true);
           conn.setRequestMethod("POST");
           conn.setRequestProperty("ser-Agent", "Fiddler");
           conn.setRequestProperty("Content-Type", "application/json");

           int code = conn.getResponseCode();
           if (code == 200) {   //与后台交互成功返回 200
               //读取返回的json数据
               Log.e("MainActivityText", "Get方式请求成功，result--->" + conn.getResponseCode());
               InputStream inStream = conn.getInputStream();
               BufferedReader rd = new BufferedReader(new InputStreamReader(inStream));
               String line = "";
               while ((line = rd.readLine()) != null) {
                   result += line;
               }
               System.out.println(result);
           } else {
               Log.e("MainActivityText", "Get方式请求失败");
           }
       }
     catch (Exception e) {
        e.printStackTrace();
    }
        System.out.println("login result:"+result);
        if(result == "success")
            return "success";
        else return "error";
    }





}



