package com.example.as.doctorh;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserRegister {
    private static final String TAG ="UserRegister" ;
    String result = "";
    public boolean userregister(final User1 user) {
        if(user.getSex()=="男")
            user.setSex("f");
        else user.setSex("m");
        new Thread() {
            @Override
            public void run() {

                try {
                    String urlPath = "http://10.0.2.3:8585/add";
                    URL url = new URL(urlPath);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("name", user.getUsername());  //参数put到json串里
                    jsonObject.put("password", user.getPassword());
                    jsonObject.put("phone", user.getTelephone());
                    jsonObject.put("age", user.getAge());
                    jsonObject.put("sex", user.getSex());

                    String content = String.valueOf(jsonObject);  //json串转string类型

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //开启连接
                    conn.setConnectTimeout(5000);

                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("ser-Agent", "Fiddler");
                    conn.setRequestProperty("Content-Type", "application/json");
                    //写输出流，将要转的参数写入流里
                    OutputStream os = conn.getOutputStream();
                    os.write(content.getBytes()); //字符串写进二进流
                    os.close();

                    int code = conn.getResponseCode();
                    if (code == 200) {   //与后台交互成功返回 200
                        //读取返回的json数据
                        Log.e(TAG, "Get方式请求成功，result--->" + conn.getResponseCode());
                        InputStream inStream = conn.getInputStream();
                        BufferedReader rd = new BufferedReader(new InputStreamReader(inStream));
                        String line = "";
                        while ((line = rd.readLine()) != null) {
                            result += line;
                        }
                        Log.i(TAG, result);

                    } else {
                        Log.e(TAG, "Get方式请求失败");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        if(result!=null)
            return true;
        else return false;
    }
    }

