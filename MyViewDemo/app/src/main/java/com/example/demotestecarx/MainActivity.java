package com.example.demotestecarx;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.demotestecarx.view.MyHeaderView;

public class MainActivity extends AppCompatActivity {

    private MyHeaderView mHeaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHeaderView = findViewById(R.id.header_view);
        mHeaderView.setmTitle("确实是好标题");
        mHeaderView.setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"点击了左侧返回按钮",Toast.LENGTH_SHORT).show();
            }
        });
        mHeaderView.setRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"点击了右侧返回按钮",Toast.LENGTH_SHORT).show();
            }
        });
    }
}