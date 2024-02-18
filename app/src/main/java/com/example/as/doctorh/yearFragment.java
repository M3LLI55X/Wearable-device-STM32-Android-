package com.example.as.doctorh;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
//import androidx.fragment.app.Fragment;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;



@SuppressLint("ValidFragment")
public class yearFragment extends Fragment {
    Data data=new Data();
    String telephone ;
    String projectType ;
    String titleType;
    private final String scale="year";
    DataRefresh dataRefresh = new DataRefresh();
    private LineChartView lineChart;

    String[] time = {"00：00","06：00","12：00","18：00","00：00"};//X轴的标注
    ArrayList<Integer> rates = new ArrayList<Integer>();
    ArrayList<Integer> walks = new ArrayList<Integer>();
    ArrayList<Double> temps = new ArrayList<Double>();
    ArrayList<Integer> lpress = new ArrayList<Integer>(); //高压
    ArrayList<Integer> hpress = new ArrayList<Integer>(); //低压
    Map<String,ArrayList<Integer>> map;
    // int[] rate= {50,42,90,33,10};//图表的数据点
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ValidFragment")
    public yearFragment(String phone , String type)throws InterruptedException{
        this.telephone=phone;
        this.projectType = type;
        if(projectType.equals("rate")) {
            rates = dataRefresh.rate_refresh(telephone, this.scale, projectType);
            if(dataRefresh.flag.equals("success")) {
                //先判断flag是不是为success，如果为success，再执行画图的工作
                System.out.println("dayfragment:" + rates.size());
                for (int i = 0; i < rates.size(); i++)
                    System.out.println(rates.get(i));
            }
            else
                Toast.makeText(getContext(),"获取数据失败",Toast.LENGTH_LONG).show();
        }
        else if(projectType.equals("temp")) {
            temps = dataRefresh.temp_refresh(telephone, this.scale, projectType);
            if(dataRefresh.flag.equals("success")) {
                System.out.println("dayfragment:" + temps.size());
                for (int i = 0; i < temps.size(); i++)
                    System.out.println(temps.get(i));
            }
            else
                Toast.makeText(getContext(),"获取数据失败",Toast.LENGTH_LONG).show();
        }
        else if(projectType.equals("walk")) {
            walks = dataRefresh.walk_refresh(telephone, this.scale, projectType);
            if(dataRefresh.flag.equals("success")) {
                System.out.println("dayfragment:" + walks.size());
                for (int i = 0; i < walks.size(); i++)
                    System.out.println(walks.get(i));
            }
            else
                Toast.makeText(getContext(),"获取数据失败",Toast.LENGTH_LONG).show();
        }
        else if(projectType.equals("press")) {
            map = dataRefresh.press_refresh(telephone, this.scale, projectType);
            if(dataRefresh.flag.equals("success")) {
                lpress = map.get("lpress");
                hpress = map.get("hpress");
                System.out.println("lpress size:"+lpress.size());
                System.out.println("hpress size:"+hpress.size());
                System.out.println("lpress:");
                for (int i = 0; i < lpress.size(); i++)
                    System.out.println(lpress.get(i));
                System.out.println("hpress:");
                for (int i = 0; i < hpress.size(); i++)
                    System.out.println(hpress.get(i));
            }
            else
                Toast.makeText(getContext(),"获取数据失败",Toast.LENGTH_LONG).show();
        }
    }
    /**
     * 判断是否是初始化Fragment
     */
    private boolean hasStarted = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //setContentView(R.layout.activity_main);
        @SuppressLint("InflateParams")
        View view=inflater.inflate(R.layout.fragment_year,null);
        lineChart = (LineChartView)view.findViewById(R.id.line_chartYear);
        View view2=inflater.inflate(R.layout.title_layout,null);
        TextView titleText = (TextView) view2.findViewById(R.id.title_text_tv);
        typeJudge();//生理指标模式判断
        titleText.setText(titleType);
        //getAxisXLables();//获取x轴的标注
        //getAxisPoints();//获取坐标点
        // initLineChart();//初始化
        return inflater.inflate(R.layout.fragment_year, container, false);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onResume() {
        super.onResume();
        if(projectType.equals("rate")) {
            try {
                rates = dataRefresh.rate_refresh(telephone, this.scale, projectType);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(dataRefresh.flag.equals("success")) {
                //先判断flag是不是为success，如果为success，再执行画图的工作
                System.out.println("dayfragment:" + rates.size());
                for (int i = 0; i < rates.size(); i++)
                    System.out.println(rates.get(i));
            }
            else
                Toast.makeText(getContext(),"获取数据失败",Toast.LENGTH_LONG).show();
        }
        else if(projectType.equals("temp")) {
            try {
                temps = dataRefresh.temp_refresh(telephone, this.scale, projectType);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(dataRefresh.flag.equals("success")) {
                System.out.println("dayfragment:" + temps.size());
                for (int i = 0; i < temps.size(); i++)
                    System.out.println(temps.get(i));
            }
            else
                Toast.makeText(getContext(),"获取数据失败",Toast.LENGTH_LONG).show();
        }
        else if(projectType.equals("walk")) {
            try {
                walks = dataRefresh.walk_refresh(telephone, this.scale, projectType);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(dataRefresh.flag.equals("success")) {
                System.out.println("dayfragment:" + walks.size());
                for (int i = 0; i < walks.size(); i++)
                    System.out.println(walks.get(i));
            }
            else
                Toast.makeText(getContext(),"获取数据失败",Toast.LENGTH_LONG).show();
        }
        else if(projectType.equals("press")) {
            try {
                map = dataRefresh.press_refresh(telephone, this.scale, projectType);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(dataRefresh.flag.equals("success")) {
                lpress = map.get("lpress");
                hpress = map.get("hpress");
                System.out.println("lpress size:"+lpress.size());
                System.out.println("hpress size:"+hpress.size());
                System.out.println("lpress:");
                for (int i = 0; i < lpress.size(); i++)
                    System.out.println(lpress.get(i));
                System.out.println("hpress:");
                for (int i = 0; i < hpress.size(); i++)
                    System.out.println(hpress.get(i));
            }
            else
                Toast.makeText(getContext(),"获取数据失败",Toast.LENGTH_LONG).show();
        }
        getAxisXLables();//获取x轴的标注
        getAxisPoints();//获取坐标点
        initLineChart();//初始化

    }
    /**
     * 生理指标模式判断
     */
    private void typeJudge() {
        if (projectType.equals("rate")) {
            titleType = "心率";
        }
        if (projectType.equals("temp")) {
            titleType = "体温";
        }
        if (projectType.equals("walk")) {
            titleType = "步数";
        }
        if (projectType.equals("press")) {
            titleType = "血压";
        }
    }
    /**
     * 设置X 轴的显示
     */
    private void getAxisXLables() {
        for (int i = 0; i < time.length; i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(time[i]));
        }
    }

    /**
     * 图表的每个点的显示
     */
    private void getAxisPoints() {
        for (int i = 0; i < rates.size(); i++) {
            mPointValues.add(new PointValue(i, rates.get(i)));
        }
    }
    private void initLineChart() {
        Line line = new Line(mPointValues).setColor(Color.parseColor("#FFCD41"));  //折线的颜色（橙色）
        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
        line.setCubic(false);//曲线是否平滑，即是曲线还是折线
        line.setFilled(false);//是否填充曲线的面积
        line.setHasLabels(true);//曲线的数据坐标是否加上备注
//      line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);

        //坐标轴
        Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(true);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
        axisX.setTextColor(Color.GRAY);  //设置字体颜色
        //axisX.setName("date");  //表格名称
        axisX.setTextSize(10);//设置字体大小
        axisX.setMaxLabelChars(8); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
        data.setAxisXBottom(axisX); //x 轴在底部
        //data.setAxisXTop(axisX);  //x 轴在顶部
        axisX.setHasLines(true); //x 轴分割线

        // Y轴是根据数据的大小自动设置Y轴上限(在下面我会给出固定Y轴数据个数的解决方案)
        Axis axisY = new Axis();  //Y轴
        axisY.setName(titleType );//y轴标注
        axisY.setTextSize(10);//设置字体大小
        data.setAxisYLeft(axisY);  //Y轴设置在左边
        //data.setAxisYRight(axisY);  //y轴设置在右边


        //设置行为属性，支持缩放、滑动以及平移
        lineChart.setInteractive(true);
        lineChart.setZoomType(ZoomType.HORIZONTAL);
        lineChart.setMaxZoom((float) 2);//最大方法比例
        lineChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        lineChart.setLineChartData(data);
        lineChart.setVisibility(View.VISIBLE);
        /**注：下面的7，10只是代表一个数字去类比而已
         * 当时是为了解决X轴固定数据个数。见（http://forum.xda-developers.com/tools/programming/library-hellocharts-charting-library-t2904456/page2）;
         */
        Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.left = 0;
        v.right = 7;
        lineChart.setCurrentViewport(v);
    }
    private final String init(String telephone){
//  192.168.3.138 这个ip地址是电脑Ipv4 地址 /20170112 是服务端的项目名称  /login/toJsonMain 是@RequestMapping的地址
        String urlPath="http://192.168.3.138:8080/20170112/login/toJsonMain.action";
        //String urlPath="http://192.168.42.207:8080/20170112/login/toJsonMain.action"; 这个是实体机(手机)的端口
        URL url;
        //String yanzheng=" ";
        try {
            url=new URL(urlPath);
            JSONObject jsonObject=new JSONObject();
            jsonObject.put( "telephone", telephone);
            //JSONObject Authorization =new JSONObject();
            //   Authorization.put("po类名",jsonObject 即po的字段)

            String content=String.valueOf(jsonObject);  //json串转string类型\
            HttpURLConnection conn=(HttpURLConnection) url.openConnection(); //开启连接
            conn.setConnectTimeout(5000);

            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("ser-Agent", "Fiddler");
            conn.setRequestProperty("Content-Type","application/json");
            //写输出流，将要转的参数写入流里
            OutputStream os=conn.getOutputStream();
            os.write(content.getBytes()); //字符串写进二进流
            os.close();

            int code=conn.getResponseCode();
            if(code==200){   //与后台交互成功返回 200
                //读取返回的json数据
                InputStream inputStream=conn.getInputStream();
                // 调用自己写的NetUtils() 将流转成string类型
                String json= NetUtils.readString(inputStream);

                Gson gson=new Gson();  //引用谷歌的json包
                User1 user=gson.fromJson(json,User1.class); //谷歌的解析json的方法

                //password= user.getPassword();  //然后user.get你想要的值
                telephone=user.getTelephone();
//                rates=user.getRate();



            }else{
                Toast.makeText (getActivity(),"数据提交失败", Toast.LENGTH_LONG ).show();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return  telephone;
    }
}

