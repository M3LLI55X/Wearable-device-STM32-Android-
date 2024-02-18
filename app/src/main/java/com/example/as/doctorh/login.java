package com.example.as.doctorh;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class login{
    private static final String TAG ="Login" ;
    private String network = "";
    private String temp;
    public String login_byyanzheng(final User1 user) throws InterruptedException {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            String phone = user.getTelephone();
                            String urlPath = "http://10.0.2.3:8585/login_byyanzheng?phone="+phone;
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
                                Log.e(TAG, "Get方式请求成功，result--->" + conn.getResponseCode());
                                InputStream inStream = conn.getInputStream();
                                BufferedReader rd = new BufferedReader(new InputStreamReader(inStream));
                                String line = "";
                                String result = "";
                                while ((line = rd.readLine()) != null) {
                                    result += line;
                                }
                                System.out.println("network qingqiu:"+result);
                                change_value(result);
                            } else {
                                Log.e(TAG, "Get方式请求失败");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            Thread.sleep(2000);
            System.out.println("login network is:"+this.network);
            if(this.network == "success")
                return "success";
            else return "error";
        }

    public void change_value(String result){
        System.out.println("change_value result:"+result);
        if(result.equals("success")){
            this.network = "success";
            System.out.println("change network:"+this.network);
        }
        else{
            this.network = "error";
        }
    }

    public String login_bypwd(final User1 user) throws InterruptedException {
        if(user.getSex()=="男")
            user.setSex("f");
        else user.setSex("m");
        new Thread() {
            @Override
            public void run() {

                try {
                    String phone = user.getTelephone();
                    String password = user.getPassword();
                    String urlPath = "http://10.0.2.3:8585/login_bypassword?phone="+phone+"&password="+password;
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
                        Log.e(TAG, "Get方式请求成功，result--->" + conn.getResponseCode());
                        InputStream inStream = conn.getInputStream();
                        BufferedReader rd = new BufferedReader(new InputStreamReader(inStream));
                        String line = "";
                        String result = "";
                        while ((line = rd.readLine()) != null) {
                            result += line;
                        }
                        System.out.println("network qingqiu:"+result);
                        change_value(result);

                    } else {
                        Log.e(TAG, "Get方式请求失败，result--->" + conn.getResponseCode());
                        Log.e(TAG, "Get方式请求失败");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        Thread.sleep(2000);
        System.out.println("login network is:"+this.network);
        if(this.network == "success")
            return "success";
        else return "error";
    }
}

