package com.example.as.doctorh;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.annotation.Target;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Send_yanzheng {
    private static String TAG = "Yanzhengma";
    private String network = " ";
    public String send_yanzheng(final User1 user) throws InterruptedException {
        new Thread()
        {
            @Override
            public void run()
            {
                try {
//                    JSONObject userJSON = new JSONObject();
//                    userJSON.put("name",user.getUsername());
//                    userJSON.put("password",user.getPassword());
//                    userJSON.put("email","13456");


//                    JSONObject object = new JSONObject();
//                    object.put("user",userJSON);
//
//                    String content = String.valueOf(userJSON);

                    //HttpURLConnection connection  =
                    /**
                     * 请求地址
                     */
                    String phone = user.getTelephone();
                    String urlPath="http://10.0.2.3:8585/yanzhengma?phone="+phone;
                    URL url = new URL(urlPath);
                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    //HttpURLConnection connection = (HttpURLConnection) nURL.openConnection();
                    connection.setConnectTimeout(5000);
                    connection.setRequestMethod("GET");
                    connection.setDoOutput(true);
                    connection.setRequestProperty("Charset", "UTF-8");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.connect();
                    // 获取返回的数据
                    //String result = streamToString(connection.getInputStream());
//                    OutputStream os = connection.getOutputStream();
//                    os.write(content.getBytes());
//                    os.close();
                    int code = connection.getResponseCode();
                    if(code==200) {
                        Log.e(TAG, "Get方式请求成功，result--->" + connection.getResponseCode());
                        InputStream inStream = connection.getInputStream();
                        BufferedReader rd = new BufferedReader(new InputStreamReader(inStream));
                        String line;
                        String result = "";
                        while((line = rd.readLine())!=null){
                            result += line;
                        }
                        Log.i(TAG,result);
                        change_value(result);
                    }
                    else {
                        Log.e(TAG, "Get方式请求失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        Thread.sleep(1000);
        System.out.println("this network is:"+this.network);
        if(!this.network.equals(null))
            return this.network;
        else return "error";

    }

    private void change_value(String result) {
        System.out.println("change result is:"+result);
        this.network = result;
    }
}
