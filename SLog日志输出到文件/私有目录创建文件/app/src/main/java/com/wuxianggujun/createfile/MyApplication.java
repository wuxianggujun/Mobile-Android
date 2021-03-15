package com.wuxianggujun.createfile;
import android.app.Application;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SLog.getInstance(this)
        .setTag("我是日志") //设置TAG
        .isPrint(true)    //打印日志
        .isWriter(true); //是否输出到文件
        
        
    }
    
    
    
}
