package com.example.as.doctorh;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
//import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


//@SuppressLint("ValidFragment")
public class MessageSettingFragment extends AppCompatActivity {

    private TextView mSwitchText;
    private String phone;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private Context context = this;
//    @SuppressLint("ValidFragment")
//    public MessageSettingFragment(String phone){
//        this.phone = phone;
//    }
    public void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_message_setting);
        Switch mSwitch;
        final Data data=new Data();
        sp = context.getSharedPreferences("switch",Context.MODE_PRIVATE);
        editor=sp.edit();
        mSwitch=(Switch)findViewById(R.id.switch1);
        mSwitchText=(TextView)findViewById(R.id.REsetting);
        Intent intent = getIntent();
        final String phone = intent.getStringExtra("phone");
        boolean check = sp.getBoolean("checked",false);
        if(check==true) {
            mSwitch.setChecked(check);
            mSwitchText.setText("持续更新已开启");
        }
        else {
            mSwitch.setChecked(check);
            mSwitchText.setText("持续更新未开启");
        }
        System.out.println("qian");
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println("check:"+phone);
                    if (isChecked) {
                        editor.putBoolean("checked",isChecked);
                        editor.commit();
                        data.setContSet(1);
                        mSwitchText.setText("持续更新已开启");
                    } else {
                        editor.putBoolean("checked",isChecked);
                        editor.commit();
                        data.setContSet(0);
                        mSwitchText.setText("持续更新未开启");
                    }

                new DataRefresh().update_continuous(phone,data.getContSet());
            }
        });
        System.out.println("hou");
    }
    public void onResume() {
        super.onResume();

    }

}
