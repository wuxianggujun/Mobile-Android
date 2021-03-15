package com.wuxianggujun.unzip;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Context;
import com.wenming.library.save.imp.LogWriter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FileRecyclerView frv = new FileRecyclerView();
        frv.show(getSupportFragmentManager(),"dialog");
       // int i=1/0;
       LogWriter.writeLog("我是无相孤君","打印log测试");
    }
}
