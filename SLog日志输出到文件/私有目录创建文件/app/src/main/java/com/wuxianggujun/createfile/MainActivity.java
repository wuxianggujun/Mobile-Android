package com.wuxianggujun.createfile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Context;
import java.io.File;
import android.widget.Toast;
import java.io.IOException;
import android.os.Environment;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SLog.a("我是小白，啪啪啪啪啪啪啪");
        SLog.d("完了，我喜欢你啊😫");
        SLog.e("我是小白");      
        SLog.i(12);
        SLog.w(15000);
    }
    
    
    public void createFile(){
        File  path= MainActivity.this.getExternalFilesDir("log");
        String folder = path.getAbsolutePath();
        
        if (!path.exists()) {        
            path.mkdirs();          
            }  
            
        File file = new File(folder, "test.log");    
        try {       
            FileOutputStream stream = new FileOutputStream(file);        
            for (int i = 0; i < 66; i++) {            
                stream.write("Hello Android!\n".getBytes());       
            }        
            stream.flush();        
            stream.close();        
            Toast.makeText(this, "写入成功！", Toast.LENGTH_SHORT).show();    
        } catch (Exception e) {       
            e.printStackTrace();    
        }


    }
    
    }
        
       
