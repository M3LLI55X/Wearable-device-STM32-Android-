package com.example.as.doctorh;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class f1 extends AppCompatActivity {
    private DBOpenHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        Intent intent = getIntent();
        final String phone = intent.getStringExtra("username");
        setContentView( R.layout.activity_f1 );
        dbHelper=new DBOpenHelper( getApplicationContext(),"members.db",null,1 );
        Button bt=(Button)findViewById( R.id.summit );
        bt.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et1 = (EditText) findViewById(R.id.telephone);
                EditText et2 = (EditText) findViewById(R.id.name);
                final String telephone = et1.getText().toString();
                final String name = et2.getText().toString();

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("name", name);
                values.put("telelphone", telephone);
                values.put("master",phone);
                db.insert("familyMember", null, values);
                values.clear();
                    String flag;
                    try {
                        flag = new UrgentConnect().addConnector(phone, telephone);
                        if (flag.equals("existed")){
                            Toast.makeText(getApplicationContext(), "添加的电话已经存在", Toast.LENGTH_LONG).show();
                        }
                        else if (flag.equals("fail")){
                            Toast.makeText(getApplicationContext(), "添加失败", Toast.LENGTH_LONG).show();
                            }
                        else if (flag.equals("success")) {
                            Toast.makeText(getApplicationContext(), "添加成功", Toast.LENGTH_LONG).show();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                Intent intent = getIntent();
                setResult(0x12, intent);
                finish();
            }
        });


    }
}
