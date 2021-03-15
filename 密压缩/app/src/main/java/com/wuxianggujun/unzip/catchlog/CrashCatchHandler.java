package com.wuxianggujun.unzip.catchlog;
import android.content.Context;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import com.wuxianggujun.unzip.R;
import android.os.Looper;
import android.content.Intent;

public class CrashCatchHandler implements Thread.UncaughtExceptionHandler {

    private Context context;
    private static  CrashCatchHandler INSTANCE = null;

    private Thread.UncaughtExceptionHandler defaultCaughtHandler;//系统默认的UncaughtExceptionHandler


    private CrashCatchHandler() {      
    }

    public static CrashCatchHandler getInstance() {        
        if (INSTANCE == null) {
            INSTANCE = new CrashCatchHandler();
        }
        return INSTANCE;
    }

    public void init(Context context) {
        this.context = context;
        defaultCaughtHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);//设置当前UncaughtExceptionHandler为系统处理器

    }


    @Override
    public void uncaughtException(Thread thread, final Throwable ex) {
      /*
        Intent intent = new Intent(context,SLogActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 请勿修改，否则无法打开页面
        context.startActivity(intent);
        */
      
        new Thread(){
            @Override
            public void run() {
                super.run();
                Looper.prepare();
                StackTraceElement[] stackTraceElement = ex.getStackTrace();
                for (int i = 0; i < stackTraceElement.length; i++) {
                    String method = stackTraceElement[i].getClassName();
                    Toast.makeText(context,method, Toast.LENGTH_SHORT).show();                  
                }
              Looper.loop();
            }       

        }.start();
        
      /*  ex.printStackTrace();
        
        if(defaultCaughtHandler != null){
           defaultCaughtHandler.uncaughtException(thread,ex);
       }else{       
           android.os.Process.killProcess(android.os.Process.myPid());
           System.exit(1);
       }
       */       
        
        
        
    }

private String getBug(Throwable ex){
   StackTraceElement[] stackTraceElement = ex.getStackTrace();
   String name = stackTraceElement[0].getClassName();
   return name;
}


}
