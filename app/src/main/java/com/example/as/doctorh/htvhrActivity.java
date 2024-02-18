package com.example.as.doctorh;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentActivity; // 注意这里我们导入的V4的包，不要导成app的包了
//import androidx.fragment.app.FragmentTransaction;
//import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.os.Bundle;

public class htvhrActivity extends AppCompatActivity implements View.OnClickListener {
// 初始化顶部栏显示
private ImageView titleLeftImv;
private TextView titleTv;
// 定义4个Fragment对象
private dayFragment fg1;
//Fragment fg = new dayFragment();
private weekFragment fg2;
private monthFragment fg3;
private yearFragment fg4;
// 帧布局对象，用来存放Fragment对象
private FrameLayout frameLayout;
// 定义每个选项中的相关控件
private RelativeLayout firstLayout;
private RelativeLayout secondLayout;
private RelativeLayout thirdLayout;
private RelativeLayout fourthLayout;
private ImageView firstImage;
private ImageView secondImage;
private ImageView thirdImage;
private ImageView fourthImage;
private TextView firstText;
private TextView secondText;
private TextView thirdText;
private TextView fourthText;
// 定义几个颜色
private int whirt = 0xFFFFFFFF;
private int gray = 0xFF7597B3;
private int dark = 0xff000000;
// 定义FragmentManager对象管理器
private FragmentManager fragmentManager;
private String type;
String phone;   //上个界面传来的用户的电话
@RequiresApi(api = Build.VERSION_CODES.M)
@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_htvhr);
        fragmentManager = getFragmentManager();
        Intent intent = getIntent();
        phone = intent.getStringExtra("username");
        type = intent.getStringExtra("type");
        initView(); // 初始化界面控件
        try {
                setChioceItem(0); // 初始化页面加载时显示第一个选项卡
        } catch (InterruptedException e) {
                e.printStackTrace();
        }
}
/**
 * 初始化页面
 */
//修改标题！！！！！！！！！！！！
private void initView() {
// 初始化页面标题栏
        titleLeftImv = (ImageView) findViewById(R.id.title_imv);
        titleLeftImv.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        startActivity(new Intent(htvhrActivity.this, mainFragment.class));
        }
        });
        titleTv = (TextView) findViewById(R.id.title_text_tv);
        titleTv.setText("心 率");
// 初始化底部导航栏的控件
        //firstImage = (ImageView) findViewById(R.id.first_image);
        // secondImage = (ImageView) findViewById(R.id.second_image);
        //thirdImage = (ImageView) findViewById(R.id.third_image);
        //fourthImage = (ImageView) findViewById(R.id.fourth_image);
        firstText = (TextView) findViewById(R.id.first_text);
        secondText = (TextView) findViewById(R.id.second_text);
        thirdText = (TextView) findViewById(R.id.third_text);
        fourthText = (TextView) findViewById(R.id.fourth_text);
        firstLayout = (RelativeLayout) findViewById(R.id.first_layout);
        secondLayout = (RelativeLayout) findViewById(R.id.second_layout);
        thirdLayout = (RelativeLayout) findViewById(R.id.third_layout);
        fourthLayout = (RelativeLayout) findViewById(R.id.fourth_layout);
        firstLayout.setOnClickListener(htvhrActivity.this);
        secondLayout.setOnClickListener(htvhrActivity.this);
        thirdLayout.setOnClickListener(htvhrActivity.this);
        fourthLayout.setOnClickListener(htvhrActivity.this);
        }
@RequiresApi(api = Build.VERSION_CODES.M)
@Override
public void onClick(View v) {
        switch (v.getId()) {
        case R.id.first_layout:
                try {
                        setChioceItem(0);
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }
                break;
        case R.id.second_layout:
                try {
                        setChioceItem(1);
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }
                break;
        case R.id.third_layout:
                try {
                        setChioceItem(2);
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }
                break;
        case R.id.fourth_layout:
                try {
                        setChioceItem(3);
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }
                break;
default:
        break;
        }
        }
/**
 * 设置点击选项卡的事件处理
 *
 * @param index 选项卡的标号：0, 1, 2, 3
 */
@RequiresApi(api = Build.VERSION_CODES.M)
private void setChioceItem(int index) throws InterruptedException {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        clearChioce(); // 清空, 重置选项, 隐藏所有Fragment
        hideFragments(fragmentTransaction);
        Fragment fg = null;
        switch (index) {
        case 0:
// firstImage.setImageResource(R.drawable.XXXX); 需要的话自行修改
        firstText.setTextColor(dark);
        firstLayout.setBackgroundColor(gray);
// 如果fg1为空，则创建一个并添加到界面上
        if (fg1 == null) {
        fg1 = new dayFragment(phone,type);
        fragmentTransaction.replace(R.id.content, fg1);

        } else {
// 如果不为空，则直接将它显示出来
        fragmentTransaction.show(fg1);
        }
        break;
                case 1:
                        secondText.setTextColor(dark);
                        secondLayout.setBackgroundColor(gray);
                        fg = new weekFragment(phone,type);
                        fragmentTransaction.replace(R.id.content, fg);
                        break;

                case 2:
// thirdImage.setImageResource(R.drawable.XXXX);
                        thirdText.setTextColor(dark);
                        thirdLayout.setBackgroundColor(gray);
                        fg = new monthFragment(phone,type);
                        fragmentTransaction.replace(R.id.content, fg);
                        break;
        case 3:
// fourthImage.setImageResource(R.drawable.XXXX);
                fourthText.setTextColor(dark);
                fourthLayout.setBackgroundColor(gray);
                fg = new yearFragment(phone,type);
                fragmentTransaction.replace(R.id.content, fg);

                break;
        }
        fragmentTransaction.commit(); // 提交
        }
/**
 * 当选中其中一个选项卡时，其他选项卡重置为默认
 */
private void clearChioce() {
// firstImage.setImageResource(R.drawable.XXX);
        firstText.setTextColor(gray);
        firstLayout.setBackgroundColor(whirt);
// secondImage.setImageResource(R.drawable.XXX);
        secondText.setTextColor(gray);
        secondLayout.setBackgroundColor(whirt);
// thirdImage.setImageResource(R.drawable.XXX);
        thirdText.setTextColor(gray);
        thirdLayout.setBackgroundColor(whirt);
// fourthImage.setImageResource(R.drawable.XXX);
        fourthText.setTextColor(gray);
        fourthLayout.setBackgroundColor(whirt);
        }
/**
 * 隐藏Fragment
 *
 * @param fragmentTransaction
 */
private void hideFragments(FragmentTransaction fragmentTransaction) {
        if (fg1 != null) {
        fragmentTransaction.hide(fg1);
        }
        if (fg2 != null) {
        fragmentTransaction.hide(fg2);
        }
        if (fg3 != null) {
        fragmentTransaction.hide(fg3);
        }
        if (fg4 != null) {
        fragmentTransaction.hide(fg4);
        }
        }

        }
