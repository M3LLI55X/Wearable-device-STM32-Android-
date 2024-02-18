package com.example.as.doctorh;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.SyncFailedException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class DataRefresh {
    User1 user;
    String flag = "success";
    ArrayList<Integer> rates;
    ArrayList<Double> temps;
    ArrayList<Integer> walks;
    ArrayList<Integer> lpre;
    ArrayList<Integer> hpre;
    Map<String, ArrayList<Integer>> map;

    public void refresh(final String telephone, User1 user1) throws InterruptedException {
        user = user1;
        //String urlPath="http://192.168.42.207:8080/20170112/login/toJsonMain.action"; 这个是实体机(手机)的端口
        //String yanzheng=" ";
        new Thread() {
            @Override
            public void run() {
                try {
                    String urlPath = "http://10.0.2.3:8585/refresh/?phone=" + telephone;
                    URL url = new URL(urlPath);
                    //JSONObject Authorization =new JSONObject();
                    //   Authorization.put("po类名",jsonObject 即po的字段)
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //开启连接
                    conn.setConnectTimeout(5000);
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("ser-Agent", "Fiddler");
                    conn.setRequestProperty("Content-Type", "application/json");

                    int code = conn.getResponseCode();
                    if (code == 200) {   //与后台交互成功返回 200
                        //读取返回的json数据
                        InputStream inputStream = conn.getInputStream();
                        // 调用自己写的NetUtils() 将流转成string类型
                        String json = NetUtils.readString(inputStream);
                        if (json.equals("fail")) changeflag(json);
                        else {
//            {"temp":36.2,"rate":92,"hpress":110,"lpress":73,"walk":19392}
                            JSONObject jsonObject = new JSONObject(json);
                            int rate = jsonObject.getInt("rate");
                            double temp = jsonObject.getDouble("temp");
                            int hpress = jsonObject.getInt("hpress");
                            int lpress = jsonObject.getInt("lpress");
                            int walk = jsonObject.getInt("walk");
                            System.out.println("rate:" + rate);
                            System.out.println("temp:" + temp);
                            System.out.println("hpress:" + hpress);
                            System.out.println("lpress:" + lpress);
                            System.out.println("walk:" + walk);
                            data_store(rate, temp, lpress, hpress, walk);
                        }
                    } else {
                        Log.e("DataRefresh", "访问失败，代码code：" + code);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        Thread.sleep(2000);
    }

    private void changeflag(String json) {
        if (json.equals("fail"))
            this.flag = "fail";
    }

    public void data_store(int rate, double temp, int lpress, int hpress, int walk) {
        user.setHR(rate);
        user.setTEM(temp);
        user.setHBP(hpress);
        user.setLBP(lpress);
        user.setWIG(walk);
    }

    public ArrayList<Integer> rate_refresh(final String telephone, final String scale, final String type) throws InterruptedException {
        switch (scale) {
            case "hours": {
                new Thread() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void run() {
                        try {
                            System.out.println("phone:" + telephone + "type:" + type);
                            String urlPath = "http://10.0.2.3:8585/hours/?phone=" + telephone + "&type=" + type;
                            URL url = new URL(urlPath);
                            //JSONObject Authorization =new JSONObject();
                            //   Authorization.put("po类名",jsonObject 即po的字段)
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //开启连接
                            conn.setConnectTimeout(5000);
                            conn.setDoOutput(true);
                            conn.setRequestMethod("POST");
                            conn.setRequestProperty("ser-Agent", "Fiddler");
                            conn.setRequestProperty("Content-Type", "application/json");

                            int code = conn.getResponseCode();
                            if (code == 200) {   //与后台交互成功返回 200
                                //读取返回的json数据
                                InputStream inputStream = conn.getInputStream();
                                // 调用自己写的NetUtils() 将流转成string类型
                                String json = NetUtils.readString(inputStream);
                                if (json.equals("fail")) changeflag(json);
//            {"temp":36.2,"rate":92,"hpress":110,"lpress":73,"walk":19392}
                                else {
                                    ArrayList<Integer> tempory = new ArrayList<Integer>();
                                    JSONObject jsonObject = new JSONObject(json);
                                    JSONArray jsonArray = jsonObject.optJSONArray("rate");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        tempory.add(jsonArray.getInt(i));
                                    }
                                    System.out.println("rate_refresh:" + tempory.size());
                                    for (int j = 0; j < tempory.size(); j++) {
                                        System.out.println(tempory.get(j));
                                    }
                                    rate_store(tempory);
                                }


                            } else {
                                Log.e("DataRefresh", "访问失败，代码code：" + code);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
            }
            case "weeks": {
                new Thread() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void run() {
                        try {
                            System.out.println("phone:" + telephone + "type:" + type);
                            String urlPath = "http://10.0.2.3:8585/weeks/?phone=" + telephone + "&type=" + type;
                            URL url = new URL(urlPath);
                            //JSONObject Authorization =new JSONObject();
                            //   Authorization.put("po类名",jsonObject 即po的字段)
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //开启连接
                            conn.setConnectTimeout(5000);
                            conn.setDoOutput(true);
                            conn.setRequestMethod("POST");
                            conn.setRequestProperty("ser-Agent", "Fiddler");
                            conn.setRequestProperty("Content-Type", "application/json");

                            int code = conn.getResponseCode();
                            if (code == 200) {   //与后台交互成功返回 200
                                //读取返回的json数据
                                InputStream inputStream = conn.getInputStream();
                                // 调用自己写的NetUtils() 将流转成string类型
                                String json = NetUtils.readString(inputStream);
                                if (json.equals("fail")) changeflag(json);
//            {"temp":36.2,"rate":92,"hpress":110,"lpress":73,"walk":19392}
                                else {
                                    ArrayList<Integer> tempory = new ArrayList<Integer>();
                                    JSONObject jsonObject = new JSONObject(json);
                                    JSONArray jsonArray = jsonObject.optJSONArray("rate");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        tempory.add(jsonArray.getInt(i));
                                    }
                                    System.out.println("rate_refresh:" + tempory.size());
                                    for (int j = 0; j < tempory.size(); j++) {
                                        System.out.println(tempory.get(j));
                                    }
                                    rate_store(tempory);
                                }


                            } else {
                                Log.e("DataRefresh", "访问失败，代码code：" + code);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
            }
            case "month": {
                new Thread() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void run() {
                        try {
                            System.out.println("phone:" + telephone + "type:" + type);
                            String urlPath = "http://10.0.2.3:8585/month/?phone=" + telephone + "&type=" + type;
                            URL url = new URL(urlPath);
                            //JSONObject Authorization =new JSONObject();
                            //   Authorization.put("po类名",jsonObject 即po的字段)
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //开启连接
                            conn.setConnectTimeout(5000);
                            conn.setDoOutput(true);
                            conn.setRequestMethod("POST");
                            conn.setRequestProperty("ser-Agent", "Fiddler");
                            conn.setRequestProperty("Content-Type", "application/json");

                            int code = conn.getResponseCode();
                            if (code == 200) {   //与后台交互成功返回 200
                                //读取返回的json数据
                                InputStream inputStream = conn.getInputStream();
                                // 调用自己写的NetUtils() 将流转成string类型
                                String json = NetUtils.readString(inputStream);
                                if (json.equals("fail")) changeflag(json);
//            {"temp":36.2,"rate":92,"hpress":110,"lpress":73,"walk":19392}
                                else {
                                    ArrayList<Integer> tempory = new ArrayList<Integer>();
                                    JSONObject jsonObject = new JSONObject(json);
                                    JSONArray jsonArray = jsonObject.optJSONArray("rate");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        tempory.add(jsonArray.getInt(i));
                                    }
                                    System.out.println("rate_refresh:" + tempory.size());
                                    for (int j = 0; j < tempory.size(); j++) {
                                        System.out.println(tempory.get(j));
                                    }
                                    rate_store(tempory);
                                }


                            } else {
                                Log.e("DataRefresh", "访问失败，代码code：" + code);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
            }
            case "year": {
                new Thread() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void run() {
                        try {
                            System.out.println("phone:" + telephone + "type:" + type);
                            String urlPath = "http://10.0.2.3:8585/years/?phone=" + telephone + "&type=" + type;
                            URL url = new URL(urlPath);
                            //JSONObject Authorization =new JSONObject();
                            //   Authorization.put("po类名",jsonObject 即po的字段)
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //开启连接
                            conn.setConnectTimeout(5000);
                            conn.setDoOutput(true);
                            conn.setRequestMethod("POST");
                            conn.setRequestProperty("ser-Agent", "Fiddler");
                            conn.setRequestProperty("Content-Type", "application/json");

                            int code = conn.getResponseCode();
                            if (code == 200) {   //与后台交互成功返回 200
                                //读取返回的json数据
                                InputStream inputStream = conn.getInputStream();
                                // 调用自己写的NetUtils() 将流转成string类型
                                String json = NetUtils.readString(inputStream);
                                if (json.equals("fail")) changeflag(json);
//            {"temp":36.2,"rate":92,"hpress":110,"lpress":73,"walk":19392}
                                else {
                                    ArrayList<Integer> tempory = new ArrayList<Integer>();
                                    JSONObject jsonObject = new JSONObject(json);
                                    JSONArray jsonArray = jsonObject.optJSONArray("rate");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        tempory.add(jsonArray.getInt(i));
                                    }
                                    System.out.println("rate_refresh:" + tempory.size());
                                    for (int j = 0; j < tempory.size(); j++) {
                                        System.out.println(tempory.get(j));
                                    }
                                    rate_store(tempory);
                                }


                            } else {
                                Log.e("DataRefresh", "访问失败，代码code：" + code);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
            }
        }
        Thread.sleep(2000);
        return this.rates;
    }

    private void rate_store(ArrayList<Integer> tempory) {
        this.rates = tempory;
        System.out.println("data refresh:");
        for (int i = 0; i < rates.size(); i++) {
            System.out.println(rates.get(i));
        }
    }

    public ArrayList<Double> temp_refresh(final String telephone, String scale, final String type) throws InterruptedException {
        switch (scale) {
            case "hours": {
                new Thread() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void run() {
                        try {
                            System.out.println("phone:" + telephone + "type:" + type);
                            String urlPath = "http://10.0.2.3:8585/hours/?phone=" + telephone + "&type=" + type;
                            URL url = new URL(urlPath);
                            //JSONObject Authorization =new JSONObject();
                            //   Authorization.put("po类名",jsonObject 即po的字段)
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //开启连接
                            conn.setConnectTimeout(5000);
                            conn.setDoOutput(true);
                            conn.setRequestMethod("POST");
                            conn.setRequestProperty("ser-Agent", "Fiddler");
                            conn.setRequestProperty("Content-Type", "application/json");

                            int code = conn.getResponseCode();
                            if (code == 200) {   //与后台交互成功返回 200
                                //读取返回的json数据
                                InputStream inputStream = conn.getInputStream();
                                // 调用自己写的NetUtils() 将流转成string类型
                                String json = NetUtils.readString(inputStream);
                                if (json.equals("fail")) changeflag(json);
//            {"temp":36.2,"rate":92,"hpress":110,"lpress":73,"walk":19392}
                                else {
                                    ArrayList<Double> tempory = new ArrayList<Double>();
                                    JSONObject jsonObject = new JSONObject(json);
                                    JSONArray jsonArray = jsonObject.optJSONArray("temp");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        tempory.add(jsonArray.getDouble(i));
                                    }
                                    System.out.println("temp_refresh:" + tempory.size());
                                    for (int j = 0; j < tempory.size(); j++) {
                                        System.out.println(tempory.get(j));
                                    }
                                    temp_store(tempory);
                                }


                            } else {
                                Log.e("DataRefresh", "访问失败，代码code：" + code);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
            }
            case "weeks": {
                new Thread() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void run() {
                        try {
                            System.out.println("phone:" + telephone + "type:" + type);
                            String urlPath = "http://10.0.2.3:8585/weeks/?phone=" + telephone + "&type=" + type;
                            URL url = new URL(urlPath);
                            //JSONObject Authorization =new JSONObject();
                            //   Authorization.put("po类名",jsonObject 即po的字段)
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //开启连接
                            conn.setConnectTimeout(5000);
                            conn.setDoOutput(true);
                            conn.setRequestMethod("POST");
                            conn.setRequestProperty("ser-Agent", "Fiddler");
                            conn.setRequestProperty("Content-Type", "application/json");

                            int code = conn.getResponseCode();
                            if (code == 200) {   //与后台交互成功返回 200
                                //读取返回的json数据
                                InputStream inputStream = conn.getInputStream();
                                // 调用自己写的NetUtils() 将流转成string类型
                                String json = NetUtils.readString(inputStream);
                                if (json.equals("fail")) changeflag(json);
//            {"temp":36.2,"rate":92,"hpress":110,"lpress":73,"walk":19392}
                                ArrayList<Double> tempory = new ArrayList<Double>();
                                JSONObject jsonObject = new JSONObject(json);
                                JSONArray jsonArray = jsonObject.optJSONArray("temp");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    tempory.add(jsonArray.getDouble(i));
                                }
                                System.out.println("temp_refresh:" + tempory.size());
                                for (int j = 0; j < tempory.size(); j++) {
                                    System.out.println(tempory.get(j));
                                }
                                temp_store(tempory);


                            } else {
                                Log.e("DataRefresh", "访问失败，代码code：" + code);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
            }
            case "month": {
                new Thread() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void run() {
                        try {
                            System.out.println("phone:" + telephone + "type:" + type);
                            String urlPath = "http://10.0.2.3:8585/month/?phone=" + telephone + "&type=" + type;
                            URL url = new URL(urlPath);
                            //JSONObject Authorization =new JSONObject();
                            //   Authorization.put("po类名",jsonObject 即po的字段)
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //开启连接
                            conn.setConnectTimeout(5000);
                            conn.setDoOutput(true);
                            conn.setRequestMethod("POST");
                            conn.setRequestProperty("ser-Agent", "Fiddler");
                            conn.setRequestProperty("Content-Type", "application/json");

                            int code = conn.getResponseCode();
                            if (code == 200) {   //与后台交互成功返回 200
                                //读取返回的json数据
                                InputStream inputStream = conn.getInputStream();
                                // 调用自己写的NetUtils() 将流转成string类型
                                String json = NetUtils.readString(inputStream);
                                if (json.equals("fail")) changeflag(json);
//            {"temp":36.2,"rate":92,"hpress":110,"lpress":73,"walk":19392}
                                ArrayList<Double> tempory = new ArrayList<Double>();
                                JSONObject jsonObject = new JSONObject(json);
                                JSONArray jsonArray = jsonObject.optJSONArray("temp");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    tempory.add(jsonArray.getDouble(i));
                                }
                                System.out.println("temp_refresh:" + tempory.size());
                                for (int j = 0; j < tempory.size(); j++) {
                                    System.out.println(tempory.get(j));
                                }
                                temp_store(tempory);


                            } else {
                                Log.e("DataRefresh", "访问失败，代码code：" + code);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
            }
            case "years": {
                new Thread() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void run() {
                        try {
                            System.out.println("phone:" + telephone + "type:" + type);
                            String urlPath = "http://10.0.2.3:8585/years/?phone=" + telephone + "&type=" + type;
                            URL url = new URL(urlPath);
                            //JSONObject Authorization =new JSONObject();
                            //   Authorization.put("po类名",jsonObject 即po的字段)
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //开启连接
                            conn.setConnectTimeout(5000);
                            conn.setDoOutput(true);
                            conn.setRequestMethod("POST");
                            conn.setRequestProperty("ser-Agent", "Fiddler");
                            conn.setRequestProperty("Content-Type", "application/json");

                            int code = conn.getResponseCode();
                            if (code == 200) {   //与后台交互成功返回 200
                                //读取返回的json数据
                                InputStream inputStream = conn.getInputStream();
                                // 调用自己写的NetUtils() 将流转成string类型
                                String json = NetUtils.readString(inputStream);
                                if (json.equals("fail")) changeflag(json);
//            {"temp":36.2,"rate":92,"hpress":110,"lpress":73,"walk":19392}
                                ArrayList<Double> tempory = new ArrayList<Double>();
                                JSONObject jsonObject = new JSONObject(json);
                                JSONArray jsonArray = jsonObject.optJSONArray("temp");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    tempory.add(jsonArray.getDouble(i));
                                }
                                System.out.println("temp_refresh:" + tempory.size());
                                for (int j = 0; j < tempory.size(); j++) {
                                    System.out.println(tempory.get(j));
                                }
                                temp_store(tempory);
                            } else {
                                Log.e("DataRefresh", "访问失败，代码code：" + code);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
            }
        }
        Thread.sleep(2000);
        return this.temps;
    }

    private void temp_store(ArrayList<Double> tempory) {
        this.temps = tempory;
        System.out.println("data refresh:");
        for (int i = 0; i < temps.size(); i++) {
            System.out.println(temps.get(i));
        }
    }

    public ArrayList<Integer> walk_refresh(final String telephone, String scale, final String type) throws InterruptedException {
        switch (scale) {
            case "hours": {
                new Thread() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void run() {
                        try {
                            System.out.println("phone:" + telephone + "type:" + type);
                            String urlPath = "http://10.0.2.3:8585/hours/?phone=" + telephone + "&type=" + type;
                            URL url = new URL(urlPath);
                            //JSONObject Authorization =new JSONObject();
                            //   Authorization.put("po类名",jsonObject 即po的字段)
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //开启连接
                            conn.setConnectTimeout(5000);
                            conn.setDoOutput(true);
                            conn.setRequestMethod("POST");
                            conn.setRequestProperty("ser-Agent", "Fiddler");
                            conn.setRequestProperty("Content-Type", "application/json");

                            int code = conn.getResponseCode();
                            if (code == 200) {   //与后台交互成功返回 200
                                //读取返回的json数据
                                InputStream inputStream = conn.getInputStream();
                                // 调用自己写的NetUtils() 将流转成string类型
                                String json = NetUtils.readString(inputStream);
                                if (json.equals("fail")) changeflag(json);
//            {"temp":36.2,"rate":92,"hpress":110,"lpress":73,"walk":19392}
                                else {
                                    ArrayList<Integer> tempory = new ArrayList<Integer>();
                                    JSONObject jsonObject = new JSONObject(json);
                                    JSONArray jsonArray = jsonObject.optJSONArray("walk");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        tempory.add(jsonArray.getInt(i));
                                    }
                                    System.out.println("walk_refresh:" + tempory.size());
                                    for (int j = 0; j < tempory.size(); j++) {
                                        System.out.println(tempory.get(j));
                                    }
                                    walk_store(tempory);
                                }


                            } else {
                                Log.e("DataRefresh", "访问失败，代码code：" + code);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
            }
            case "weeks": {
                new Thread() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void run() {
                        try {
                            System.out.println("phone:" + telephone + "type:" + type);
                            String urlPath = "http://10.0.2.3:8585/weeks/?phone=" + telephone + "&type=" + type;
                            URL url = new URL(urlPath);
                            //JSONObject Authorization =new JSONObject();
                            //   Authorization.put("po类名",jsonObject 即po的字段)
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //开启连接
                            conn.setConnectTimeout(5000);
                            conn.setDoOutput(true);
                            conn.setRequestMethod("POST");
                            conn.setRequestProperty("ser-Agent", "Fiddler");
                            conn.setRequestProperty("Content-Type", "application/json");

                            int code = conn.getResponseCode();
                            if (code == 200) {   //与后台交互成功返回 200
                                //读取返回的json数据
                                InputStream inputStream = conn.getInputStream();
                                // 调用自己写的NetUtils() 将流转成string类型
                                String json = NetUtils.readString(inputStream);
                                if (json.equals("fail")) changeflag(json);
//            {"temp":36.2,"rate":92,"hpress":110,"lpress":73,"walk":19392}
                                else {
                                    ArrayList<Integer> tempory = new ArrayList<Integer>();
                                    JSONObject jsonObject = new JSONObject(json);
                                    JSONArray jsonArray = jsonObject.optJSONArray("walk");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        tempory.add(jsonArray.getInt(i));
                                    }
                                    System.out.println("rate_refresh:" + tempory.size());
                                    for (int j = 0; j < tempory.size(); j++) {
                                        System.out.println(tempory.get(j));
                                    }
                                    walk_store(tempory);
                                }


                            } else {
                                Log.e("DataRefresh", "访问失败，代码code：" + code);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
            }
            case "month": {
                new Thread() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void run() {
                        try {
                            System.out.println("phone:" + telephone + "type:" + type);
                            String urlPath = "http://10.0.2.3:8585/month/?phone=" + telephone + "&type=" + type;
                            URL url = new URL(urlPath);
                            //JSONObject Authorization =new JSONObject();
                            //   Authorization.put("po类名",jsonObject 即po的字段)
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //开启连接
                            conn.setConnectTimeout(5000);
                            conn.setDoOutput(true);
                            conn.setRequestMethod("POST");
                            conn.setRequestProperty("ser-Agent", "Fiddler");
                            conn.setRequestProperty("Content-Type", "application/json");

                            int code = conn.getResponseCode();
                            if (code == 200) {   //与后台交互成功返回 200
                                //读取返回的json数据
                                InputStream inputStream = conn.getInputStream();
                                // 调用自己写的NetUtils() 将流转成string类型
                                String json = NetUtils.readString(inputStream);
                                if (json.equals("fail")) changeflag(json);
//            {"temp":36.2,"rate":92,"hpress":110,"lpress":73,"walk":19392}
                                else {
                                    ArrayList<Integer> tempory = new ArrayList<Integer>();
                                    JSONObject jsonObject = new JSONObject(json);
                                    JSONArray jsonArray = jsonObject.optJSONArray("walk");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        tempory.add(jsonArray.getInt(i));
                                    }
                                    System.out.println("walk_refresh:" + tempory.size());
                                    for (int j = 0; j < tempory.size(); j++) {
                                        System.out.println(tempory.get(j));
                                    }
                                    walk_store(tempory);
                                }


                            } else {
                                Log.e("DataRefresh", "访问失败，代码code：" + code);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
            }
            case "year": {
                new Thread() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void run() {
                        try {
                            System.out.println("phone:" + telephone + "type:" + type);
                            String urlPath = "http://10.0.2.3:8585/years/?phone=" + telephone + "&type=" + type;
                            URL url = new URL(urlPath);
                            //JSONObject Authorization =new JSONObject();
                            //   Authorization.put("po类名",jsonObject 即po的字段)
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //开启连接
                            conn.setConnectTimeout(5000);
                            conn.setDoOutput(true);
                            conn.setRequestMethod("POST");
                            conn.setRequestProperty("ser-Agent", "Fiddler");
                            conn.setRequestProperty("Content-Type", "application/json");

                            int code = conn.getResponseCode();
                            if (code == 200) {   //与后台交互成功返回 200
                                //读取返回的json数据
                                InputStream inputStream = conn.getInputStream();
                                // 调用自己写的NetUtils() 将流转成string类型
                                String json = NetUtils.readString(inputStream);
                                if (json.equals("fail")) changeflag(json);
//            {"temp":36.2,"rate":92,"hpress":110,"lpress":73,"walk":19392}
                                else {
                                    ArrayList<Integer> tempory = new ArrayList<Integer>();
                                    JSONObject jsonObject = new JSONObject(json);
                                    JSONArray jsonArray = jsonObject.optJSONArray("walk");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        tempory.add(jsonArray.getInt(i));
                                    }
                                    System.out.println("walk_refresh:" + tempory.size());
                                    for (int j = 0; j < tempory.size(); j++) {
                                        System.out.println(tempory.get(j));
                                    }
                                    walk_store(tempory);
                                }


                            } else {
                                Log.e("DataRefresh", "访问失败，代码code：" + code);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
            }
        }
        Thread.sleep(2000);
        return this.walks;
    }

    private void walk_store(ArrayList<Integer> tempory) {
        this.walks = tempory;
        System.out.println("data refresh:");
        for (int i = 0; i < walks.size(); i++) {
            System.out.println(walks.get(i));
        }
    }

    public Map<String, ArrayList<Integer>> press_refresh(final String telephone, String scale, final String type) throws InterruptedException {
        switch (scale) {
            case "hours": {
                new Thread() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void run() {
                        try {
                            System.out.println("phone:" + telephone + "type:" + type);
                            String urlPath = "http://10.0.2.3:8585/hours/?phone=" + telephone + "&type=" + type;
                            URL url = new URL(urlPath);
                            //JSONObject Authorization =new JSONObject();
                            //   Authorization.put("po类名",jsonObject 即po的字段)
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //开启连接
                            conn.setConnectTimeout(5000);
                            conn.setDoOutput(true);
                            conn.setRequestMethod("POST");
                            conn.setRequestProperty("ser-Agent", "Fiddler");
                            conn.setRequestProperty("Content-Type", "application/json");

                            int code = conn.getResponseCode();
                            if (code == 200) {   //与后台交互成功返回 200
                                //读取返回的json数据
                                InputStream inputStream = conn.getInputStream();
                                // 调用自己写的NetUtils() 将流转成string类型
                                String json = NetUtils.readString(inputStream);
                                if (json.equals("fail")) changeflag(json);
//            {"temp":36.2,"rate":92,"hpress":110,"lpress":73,"walk":19392}
                                else {
                                    ArrayList<Integer> tempory1 = new ArrayList<Integer>();
                                    ArrayList<Integer> tempory2 = new ArrayList<Integer>();
                                    JSONObject jsonObject = new JSONObject(json);
                                    JSONArray jsonArray1 = jsonObject.optJSONArray("lpress");
                                    JSONArray jsonArray2 = jsonObject.optJSONArray("hpress");
                                    for (int i = 0; i < jsonArray1.length(); i++) {
                                        tempory1.add(jsonArray1.getInt(i));
                                    }
                                    for (int i = 0; i < jsonArray2.length(); i++) {
                                        tempory2.add(jsonArray2.getInt(i));
                                    }
                                    System.out.println("lpress_refresh:" + tempory1.size());
                                    System.out.println("hpress_refresh:" + tempory2.size());
                                    System.out.println("lpress:");
                                    for (int j = 0; j < tempory1.size(); j++) {
                                        System.out.println(tempory1.get(j));
                                    }
                                    System.out.println("hpress:");
                                    for (int j = 0; j < tempory2.size(); j++) {
                                        System.out.println(tempory2.get(j));
                                    }
                                    press_store(tempory1, tempory2);
                                }


                            } else {
                                Log.e("DataRefresh", "访问失败，代码code：" + code);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
            }
            case "weeks": {
                new Thread() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void run() {
                        try {
                            System.out.println("phone:" + telephone + "type:" + type);
                            String urlPath = "http://10.0.2.3:8585/weeks/?phone=" + telephone + "&type=" + type;
                            URL url = new URL(urlPath);
                            //JSONObject Authorization =new JSONObject();
                            //   Authorization.put("po类名",jsonObject 即po的字段)
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //开启连接
                            conn.setConnectTimeout(5000);
                            conn.setDoOutput(true);
                            conn.setRequestMethod("POST");
                            conn.setRequestProperty("ser-Agent", "Fiddler");
                            conn.setRequestProperty("Content-Type", "application/json");

                            int code = conn.getResponseCode();
                            if (code == 200) {   //与后台交互成功返回 200
                                //读取返回的json数据
                                InputStream inputStream = conn.getInputStream();
                                // 调用自己写的NetUtils() 将流转成string类型
                                String json = NetUtils.readString(inputStream);
                                if (json.equals("fail")) changeflag(json);
//            {"temp":36.2,"rate":92,"hpress":110,"lpress":73,"walk":19392}
                                else {
                                    ArrayList<Integer> tempory1 = new ArrayList<Integer>();
                                    ArrayList<Integer> tempory2 = new ArrayList<Integer>();
                                    JSONObject jsonObject = new JSONObject(json);
                                    JSONArray jsonArray1 = jsonObject.optJSONArray("lpress");
                                    JSONArray jsonArray2 = jsonObject.optJSONArray("hpress");
                                    for (int i = 0; i < jsonArray1.length(); i++) {
                                        tempory1.add(jsonArray1.getInt(i));
                                    }
                                    for (int i = 0; i < jsonArray2.length(); i++) {
                                        tempory2.add(jsonArray2.getInt(i));
                                    }
                                    System.out.println("lpress_refresh:" + tempory1.size());
                                    System.out.println("hpress_refresh:" + tempory2.size());
                                    System.out.println("lpress:");
                                    for (int j = 0; j < tempory1.size(); j++) {
                                        System.out.println(tempory1.get(j));
                                    }
                                    System.out.println("hpress:");
                                    for (int j = 0; j < tempory2.size(); j++) {
                                        System.out.println(tempory2.get(j));
                                    }
                                    press_store(tempory1, tempory2);
                                }


                            } else {
                                Log.e("DataRefresh", "访问失败，代码code：" + code);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
            }
            case "month": {
                new Thread() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void run() {
                        try {
                            System.out.println("phone:" + telephone + "type:" + type);
                            String urlPath = "http://10.0.2.3:8585/month/?phone=" + telephone + "&type=" + type;
                            URL url = new URL(urlPath);
                            //JSONObject Authorization =new JSONObject();
                            //   Authorization.put("po类名",jsonObject 即po的字段)
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //开启连接
                            conn.setConnectTimeout(5000);
                            conn.setDoOutput(true);
                            conn.setRequestMethod("POST");
                            conn.setRequestProperty("ser-Agent", "Fiddler");
                            conn.setRequestProperty("Content-Type", "application/json");

                            int code = conn.getResponseCode();
                            if (code == 200) {   //与后台交互成功返回 200
                                //读取返回的json数据
                                InputStream inputStream = conn.getInputStream();
                                // 调用自己写的NetUtils() 将流转成string类型
                                String json = NetUtils.readString(inputStream);
                                if (json.equals("fail")) changeflag(json);
//            {"temp":36.2,"rate":92,"hpress":110,"lpress":73,"walk":19392}
                                else {
                                    ArrayList<Integer> tempory1 = new ArrayList<Integer>();
                                    ArrayList<Integer> tempory2 = new ArrayList<Integer>();
                                    JSONObject jsonObject = new JSONObject(json);
                                    JSONArray jsonArray1 = jsonObject.optJSONArray("lpress");
                                    JSONArray jsonArray2 = jsonObject.optJSONArray("hpress");
                                    for (int i = 0; i < jsonArray1.length(); i++) {
                                        tempory1.add(jsonArray1.getInt(i));
                                    }
                                    for (int i = 0; i < jsonArray2.length(); i++) {
                                        tempory2.add(jsonArray2.getInt(i));
                                    }
                                    System.out.println("lpress_refresh:" + tempory1.size());
                                    System.out.println("hpress_refresh:" + tempory2.size());
                                    System.out.println("lpress:");
                                    for (int j = 0; j < tempory1.size(); j++) {
                                        System.out.println(tempory1.get(j));
                                    }
                                    System.out.println("hpress:");
                                    for (int j = 0; j < tempory2.size(); j++) {
                                        System.out.println(tempory2.get(j));
                                    }
                                    press_store(tempory1, tempory2);
                                }


                            } else {
                                Log.e("DataRefresh", "访问失败，代码code：" + code);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
            }
            case "year": {
                new Thread() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void run() {
                        try {
                            System.out.println("phone:" + telephone + "type:" + type);
                            String urlPath = "http://10.0.2.3:8585/years/?phone=" + telephone + "&type=" + type;
                            URL url = new URL(urlPath);
                            //JSONObject Authorization =new JSONObject();
                            //   Authorization.put("po类名",jsonObject 即po的字段)
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //开启连接
                            conn.setConnectTimeout(5000);
                            conn.setDoOutput(true);
                            conn.setRequestMethod("POST");
                            conn.setRequestProperty("ser-Agent", "Fiddler");
                            conn.setRequestProperty("Content-Type", "application/json");

                            int code = conn.getResponseCode();
                            if (code == 200) {   //与后台交互成功返回 200
                                //读取返回的json数据
                                InputStream inputStream = conn.getInputStream();
                                // 调用自己写的NetUtils() 将流转成string类型
                                String json = NetUtils.readString(inputStream);
                                if (json.equals("fail")) changeflag(json);
//            {"temp":36.2,"rate":92,"hpress":110,"lpress":73,"walk":19392}
                                else {
                                    ArrayList<Integer> tempory1 = new ArrayList<Integer>();
                                    ArrayList<Integer> tempory2 = new ArrayList<Integer>();
                                    JSONObject jsonObject = new JSONObject(json);
                                    JSONArray jsonArray1 = jsonObject.optJSONArray("lpress");
                                    JSONArray jsonArray2 = jsonObject.optJSONArray("hpress");
                                    for (int i = 0; i < jsonArray1.length(); i++) {
                                        tempory1.add(jsonArray1.getInt(i));
                                    }
                                    for (int i = 0; i < jsonArray2.length(); i++) {
                                        tempory2.add(jsonArray2.getInt(i));
                                    }
                                    System.out.println("lpress_refresh:" + tempory1.size());
                                    System.out.println("hpress_refresh:" + tempory2.size());
                                    System.out.println("lpress:");
                                    for (int j = 0; j < tempory1.size(); j++) {
                                        System.out.println(tempory1.get(j));
                                    }
                                    System.out.println("hpress:");
                                    for (int j = 0; j < tempory2.size(); j++) {
                                        System.out.println(tempory2.get(j));
                                    }
                                    press_store(tempory1, tempory2);
                                }


                            } else {
                                Log.e("DataRefresh", "访问失败，代码code：" + code);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
            }
        }
        Thread.sleep(2000);
        return this.map;
    }

    private void press_store(ArrayList<Integer> tempory1, ArrayList<Integer> tempory2) {
        this.lpre = tempory1;
        this.hpre = tempory2;
        System.out.println("lpre store:");
        for (int i = 0; i < lpre.size(); i++) {
            System.out.println(lpre.get(i));
        }
        System.out.println("hpre store:");
        for (int i = 0; i < hpre.size(); i++) {
            System.out.println(hpre.get(i));
        }
        this.map.put("hpress", this.hpre);
        this.map.put("lpress", this.lpre);
    }

    public void update_continuous(final String phone, final int k) {
        System.out.println(k);
        new Thread() {
            @Override
            public void run() {
                try {
                    String urlPath = "http://10.0.2.3:8585/gengxin/?phone=" + phone + "&k=" + k;
                    URL url = new URL(urlPath);
                    //JSONObject Authorization =new JSONObject();
                    //   Authorization.put("po类名",jsonObject 即po的字段)
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //开启连接
                    conn.setConnectTimeout(5000);
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("ser-Agent", "Fiddler");
                    conn.setRequestProperty("Content-Type", "application/json");
                    int code = conn.getResponseCode();
                    if (code == 200) {
                        System.out.println("请求成功");
                    }
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
