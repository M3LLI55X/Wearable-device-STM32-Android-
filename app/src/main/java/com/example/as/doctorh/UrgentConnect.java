package com.example.as.doctorh;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UrgentConnect {
    String flag;
    public String addConnector(final String nowphone, final String addphone) throws InterruptedException {
        new Thread() {
            @Override
            public void run() {

                try {
                    String urlPath = "http://10.0.2.3:8585/add_ergent?nowphone="+nowphone+"&addphone="+addphone;
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
                        InputStream inStream = conn.getInputStream();
                        BufferedReader rd = new BufferedReader(new InputStreamReader(inStream));
                        String line = "";
                        String result = "";
                        while ((line = rd.readLine()) != null) {
                            result += line;
                        }
                        System.out.println("insert:"+result);
                        change_value(result);

                    } else {
                        Log.e("UrgentConnect", "Get方式请求失败，result--->" + conn.getResponseCode());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        Thread.sleep(2000);
        System.out.println("login network is:"+this.flag);
        return this.flag;
    }
    public void change_value(String result){
        System.out.println("change_value result:"+result);
        if(result.equals("telephone is existed")){
            this.flag = "existed";
            System.out.println("change network:"+this.flag);
        }
        else if(result.equals("insert fail")){
            this.flag = "fail";
            System.out.println("change network:"+this.flag);
        }
        else if(result.equals("insert success")){
            this.flag = "success";
            System.out.println("change network:"+this.flag);
        }
        else if(result.equals("send fail")){
            this.flag="fail";
            System.out.println("change network:"+this.flag);
        }
        else if(result.equals("send success")){
            this.flag = "success";
            System.out.println("change network:"+this.flag);
        }
    }
    public String sendmessage(final String phone) throws InterruptedException {
        System.out.println(phone);
        new Thread() {
            @Override
            public void run() {

                try {
                    String urlPath = "http://10.0.2.3:8585/ergent?reqphone="+phone;
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
                        InputStream inStream = conn.getInputStream();
                        BufferedReader rd = new BufferedReader(new InputStreamReader(inStream));
                        String line = "";
                        String result = "";
                        while ((line = rd.readLine()) != null) {
                            result += line;
                        }
                        System.out.println("send:"+result);
                        change_value(result);

                    } else {
                        Log.e("UrgentConnect", "Get方式请求失败，result--->" + conn.getResponseCode());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        Thread.sleep(5000);
        System.out.println("login network is:"+this.flag);
        return this.flag;
    }
}
