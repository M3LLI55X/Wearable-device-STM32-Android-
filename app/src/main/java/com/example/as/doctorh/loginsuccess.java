package com.example.as.doctorh;


import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class loginsuccess extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginsuccess);
        Intent intent = getIntent();//获取Intent对象
        Bundle bundle = intent.getExtras();//获取传递的Bundle信息
        TextView username = (TextView) findViewById(R.id.username);//获取显示姓名的TextView组件
        final String name = intent.getStringExtra("username");
        username.setText(name);//获取输入的姓名并显示到TextView组件中
        Data data = new Data();
        TextView  click  =(TextView)findViewById( R.id.click );
        click.setOnClickListener( new View.OnClickListener() { //为忘记密码创建单击监听事件
            @Override
            public void onClick(View v) {
                //创建Intent对象
                ComponentName componentname = new ComponentName(loginsuccess.this,mainFragment.class );
                Intent intent = new Intent();
                intent.putExtra("username",name);
                intent.setComponent(componentname);
                startActivity(intent);
            }
        } );
    }

}
