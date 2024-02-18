package com.example.as.doctorh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static final String TAG ="MainActivity" ;
    private DBOpenHelper dbOpenHelper;   //定义DBOpenHelper
    String name, pwd;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private CheckBox rememberPass;
    private Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TextView  tw=(TextView) findViewById( R.id.mainBT);
        //final CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(tw, 60000, 1000);
        //dbOpenHelper = new DBOpenHelper( MainActivity.this );

        TextView password = (TextView) findViewById(R.id.wang_mima);   //获取布局文件中的忘记密码
        password.setOnClickListener(new View.OnClickListener() { //为忘记密码创建单击监听事件
            @Override
            public void onClick(View v) {
                //创建Intent对象
                Intent intent = new Intent(MainActivity.this, password.class);
                startActivity(intent); //启动Activity
            }
        });
        TextView alter = (TextView) findViewById(R.id.mainalter);//切换登陆方式
        alter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivityText.class);
                startActivity(intent); //启动Activity
            }
        });
        TextView textView = (TextView) findViewById(R.id.mainzhu_ce);//获取注册按钮
        textView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, register.class);
                startActivity(intent); //启动Activity
            }
        });
/*
        Button btn = (Button) findViewById( R.id.zhu_ce );//获取注册按钮
        btn.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent( MainActivity.this, register.class );
                startActivity( intent ); //启动Activity
            }
        } );
*/
        EditText etpassword = (EditText) findViewById( R.id.mainpassword );
        EditText ettelephone = (EditText) findViewById( R.id.maintelephone);
//        pref= PreferenceManager.getDefaultSharedPreferences( this) ;
        pref = context.getSharedPreferences("data",Context.MODE_PRIVATE);
        rememberPass=(CheckBox)findViewById( R.id.remember_pass ) ;
        boolean isRemember=pref.getBoolean( "remember_password",false );

        if(isRemember){
            String telephone=pref.getString( "username","");
            String pwd=pref.getString( "password","" );
            ettelephone.setText(telephone);
            etpassword.setText(pwd);
            rememberPass.setChecked( true );

        }

        Button butn = (Button) findViewById(R.id.deng_lu);//获取登录按钮
        //获取输入的用户信息
        butn.setOnClickListener(new View.OnClickListener() {
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
                EditText etpassword = (EditText) findViewById(R.id.mainpassword);
                EditText ettelephone = (EditText) findViewById(R.id.maintelephone);
                //EditText etyanzheng = (EditText) findViewById( R.id.mainEditText );
                final String password = etpassword.getText().toString();
                final String telephone = ettelephone.getText().toString();
                //final String yanzheng = etyanzheng.getText().toString();
                String flag = "";
                User1 user = new User1();
                user.setPassword(password);
                user.setTelephone(telephone);
                try {
                    flag = new login().login_bypwd(user);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("flag is:"+flag);
                if (flag.equals("success")) {   //判断id是否大于1，>1就是查询到有数据 ，可以进入主界面
                    pref = context.getSharedPreferences("data",Context.MODE_PRIVATE);
                    editor=pref.edit();
                    if(rememberPass.isChecked()){
                        editor.putBoolean( "remember_password" ,true);
                        editor.putString("password",password);
                        editor.putString("username",telephone);
                    }else{
                        editor.clear();
                    }
                    editor.apply();
                    Log.i(TAG, flag + " ");
                    Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_LONG).show();
                    Intent it=new Intent(MainActivity.this,loginsuccess.class);
                    it.putExtra("username",telephone);
                    startActivity(it);
                } else {
                    Log.i(TAG, flag + " ");
                    Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_LONG).show();
                }
                        /*if(num>0){   //判断id是否大于1，>1就是查询到有数据 ，可以进入主界面
                            Intent it=new Intent(MainActivity.this,login.class);
                            it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(it);
                        }else {
                            Toast.makeText (getApplicationContext(),"用户或密码错误", Toast.LENGTH_LONG ).show();
                        }*/
            }

        });

       /* tw.setOnClickListener( new View.OnClickListener(){//获取验证码
            public void onClick(View v) {
                mCountDownTimerUtils.start();
                EditText ettelephone = (EditText) findViewById( R.id.maintelephone);
                String telephone = ettelephone.getText().toString();

                User1 user1 = new User1();
                user1.setTelephone( telephone );
                UserRegister1.userRegister1(user1);

            }} );*/


    }

//    //json格式与服务端交互
//    private final String init(String pwd, String tele) {
////  192.168.3.138 这个ip地址是电脑Ipv4 地址 /20170112 是服务端的项目名称  /login/toJsonMain 是@RequestMapping的地址
//        String urlPath = "http://192.168.3.138:8080/20170112/login/toJsonMain.action";
//        //    String urlPath="http://192.168.42.207:8080/20170112/login/toJsonMain.action"; 这个是实体机(手机)的端口
//        URL url;
//        //String yanzheng=" ";
//        String password = " ";
//        try {
//            url = new URL(urlPath);
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("password", pwd);
//            jsonObject.put("telephone", tele);
//            //  jsonObject.put( "yanzheng", yz);
//
//            //JSONObject Authorization =new JSONObject();
//            //   Authorization.put("po类名",jsonObject 即po的字段)
//
//            String content = String.valueOf(jsonObject);  //json串转string类型
//
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //开启连接
//            conn.setConnectTimeout(5000);
//
//            conn.setDoOutput(true);
//            conn.setRequestMethod("POST");
//            conn.setRequestProperty("ser-Agent", "Fiddler");
//            conn.setRequestProperty("Content-Type", "application/json");
//            //写输出流，将要转的参数写入流里
//            OutputStream os = conn.getOutputStream();
//            os.write(content.getBytes()); //字符串写进二进流
//            os.close();
//
//            int code = conn.getResponseCode();
//            if (code == 200) {   //与后台交互成功返回 200
//                //读取返回的json数据
//                InputStream inputStream = conn.getInputStream();
//                // 调用自己写的NetUtils() 将流转成string类型
//                String json = NetUtils.readString(inputStream);
//
//                Gson gson = new Gson();  //引用谷歌的json包
//                User1 user = gson.fromJson(json, User1.class); //谷歌的解析json的方法
//
//                password = user.getPassword();  //然后user.get你想要的值
//                String telephone = user.getTelephone();
//
//
//            } else {
//                Toast.makeText(getApplicationContext(), "数据提交失败", Toast.LENGTH_LONG).show();
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return password;
//    }


}



