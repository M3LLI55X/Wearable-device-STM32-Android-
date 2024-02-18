package com.example.as.doctorh;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class register extends AppCompatActivity {
    private static final String TAG ="RegisterResult" ;
    private DBOpenHelper dbOpenHelper;  //定义DBOpenHelper,用于与数据库连接
    RadioGroup rg;
    String sex;
    int age;
    String yz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        TextView tw = (TextView) findViewById(R.id.mTextView);
        final CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(tw, 60000, 1000);

        ImageButton close = (ImageButton) findViewById(R.id.close); //获取布局文件中的关闭按钮
        close.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish(); //关闭当前Activity
            }
        });


        rg = (RadioGroup) findViewById(R.id.sex);//读取性别
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton r = (RadioButton) findViewById(checkedId);
                Toast.makeText(register.this, "性别：" + r.getText(), Toast.LENGTH_SHORT).show();
            }
        });


        Spinner spinner = (Spinner) findViewById(R.id.spinner1);   //获取下拉列表
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String select_year = parent.getItemAtPosition(position).toString();    //获取选择项的值
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                Date date = new Date();
                int current_year = Integer.parseInt(sdf.format(date));
                age = Integer.parseInt(select_year);
                age = current_year - age;
                Log.i("register", age + " ");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button btn = (Button) findViewById(R.id.zhu_ce);//获取注册按钮
        btn.setOnClickListener(new View.OnClickListener() {  //实现将数据保存在数据库中
            @Override
            public void onClick(View v) {

                for (int i = 0; i < rg.getChildCount(); i++) {
                    RadioButton r = (RadioButton) rg.getChildAt(i);
                    if (r.isChecked()) {
                        sex = r.getText().toString();
                        Log.i("register", sex);
                    }

                }
                EditText etusername = (EditText) findViewById(R.id.username);
                EditText etpassword = (EditText) findViewById(R.id.password);
                EditText ettelephone = (EditText) findViewById(R.id.telephone);
                EditText etyanzheng = (EditText) findViewById(R.id.editText2);
                final String username = etusername.getText().toString();
                final String password = etpassword.getText().toString();
                final String telephone = ettelephone.getText().toString();
                final String yanzheng = etyanzheng.getText().toString();

                boolean flag = false;
                User1 user = new User1();
                user.setAge(age);
                user.setUsername(username);
                user.setPassword(password);
                user.setSex(sex);
                user.setTelephone(telephone);
                flag = new UserRegister().userregister(user);
                if (flag == true && yz == yanzheng) {   //判断id是否大于1，>1就是查询到有数据 ，可以进入主界面
                    Log.i(TAG,flag+" ");
                    Log.i(TAG,yanzheng+" ");
                    Toast.makeText(getApplicationContext(), "验证成功", Toast.LENGTH_LONG).show();

                } else {
                    Log.i(TAG,flag+" ");
                    Log.i(TAG,yanzheng+" ");
                    Toast.makeText(getApplicationContext(), "验证失败", Toast.LENGTH_LONG).show();
                }
            }
        });


        Button btn1 = (Button) findViewById(R.id.mTextView);//获取验证码
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mCountDownTimerUtils.start();
                EditText ettelephone = (EditText) findViewById(R.id.telephone);
                String telephone = ettelephone.getText().toString();
                User1 user1 = new User1();
                user1.setTelephone(telephone);
                try {
                    yz = new Send_yanzheng().send_yanzheng(user1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    //创建insertData()方法实现插入数据
    //private void insertData(SQLiteDatabase readableDatabase, String username, String age, String password) {
    //     ContentValues values = new ContentValues();
    //    values.put( "username", username );       //保存用户名
    //    values.put( "password", password );//保存密码
    //  readableDatabase.insert( "user", null, values );//执行插入操作
    // }

    //protected void onDestroy() {  //实现退出应用时，关闭数据库连接
    //    super.onDestroy();
    //     if (dbOpenHelper != null) {   //如果数据库不为空时
    //         dbOpenHelper.close();     //关闭数据库连接
    //      }
    //  }


//    private final boolean init(String name, String pwd, String tele, String yz, int age, String sex) {
//        String result = "";
//        String yanzheng = " ";
//
}


