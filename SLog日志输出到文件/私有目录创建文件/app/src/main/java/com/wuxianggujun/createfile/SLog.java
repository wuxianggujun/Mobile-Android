package com.wuxianggujun.createfile;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import androidx.annotation.NonNull;
import java.util.Arrays;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;

public final class SLog {

    //判断日志是否写入文件
    private static boolean isWriter = true;

    //判断是否打印日志，默认打印
    private static boolean isDebug = true;
    //上下文
    private static Context mContext;

    //设置TAG
    private static String TAG = null;

    //目录文件路径
    private static String PATH_LOGCAT;

    //时间
    private static final int TIMINGDAY = 1;

    private static SimpleDateFormat dateFormatter;

    private static final char[] LEVEL= new char[]{'V', 'D', 'I', 'W', 'E', 'A'};


    private static final int STACK_INDEX = 4;

    //日志等级
    private static final int A =Log.ASSERT;

    private static final int D =Log.DEBUG;

    private static final int E =Log.ERROR;

    private static final int I =Log.INFO;

    private static final int V =Log.VERBOSE;

    private static final int W =Log.WARN;

    private volatile static SLog instance = null;

    private SLog(Context context) {
        this.mContext = context;
        //这里创建应用项目下的文件夹
        File  pathFile= context.getExternalFilesDir("log");
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
        PATH_LOGCAT = pathFile.getAbsolutePath();
        if (TAG == null) {
            TAG = context.getClass().getSimpleName();
        }

        //文件最后修改的时间大于今天超过24小时，就删除
        removeFileByTime(pathFile);
    }

    public static SLog getInstance(Context context) {
        if (instance == null) {        
            synchronized (SLog.class) {
                if (instance == null) {
                    instance = new SLog(context);

                }
            }           
        }
        return instance;   
    }

    public static void a(Object... contents) {
        log(getPrefix(A), contents);
    }

    public static void d(Object... contents) {
        log(getPrefix(D), contents);
    }

    public static void e(Object... contents) {
        log(getPrefix(E), contents);
    }

    public static void i(Object... contents) {
        log(getPrefix(I), contents);
    }

    public static void v(Object... contents) {
        log(getPrefix(V), contents);
    }

    public static void w(Object... contents) {
        log(getPrefix(W), contents);
    }

    public SLog setTag(String tag) {
        this.TAG = tag;
        return this;
    }

    public SLog isWriter(boolean isWriter) {
        this.isWriter = isWriter;
        return this;
    }

    public SLog isPrint(boolean isDebug) {
        this.isDebug = isDebug;
        return this;
    }

    private static void log(@NonNull String tag, Object... contents) {
        //判断日志是否写入文件
        if (!isWriter || tag == null) {
            return;
        }
        if (!isDebug) {
            return; 
        }

        StringBuilder sb = new StringBuilder()
            .append(setPrintTimeState())
            .append(tag)
            .append(":\t");

        if (dateFormatter == null) {
            dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        }
        String dayTime = dateFormatter.format(System.currentTimeMillis());
        File file = new File(PATH_LOGCAT, dayTime + ".log");    

        try { 

            PrintWriter pw = new PrintWriter(new FileWriter(file, true));                     
            for (int i=0;i < contents.length;i++) {

                if (contents[i] instanceof String) {
                    pw.println(sb.toString() + (String)contents[i]);                  
                }
                if (contents[i] instanceof Integer) {
                    pw.println(sb.toString() + (int) contents[i]);
                }
                if (contents[i] instanceof Double) {
                    pw.println(sb.toString() + (double) contents[i]);
                }
                if (contents[i] instanceof Float) {
                    pw.println(sb.toString() + (float) contents[i]);
                }
                if (contents[i] instanceof Long) {
                    pw.println(sb.toString() + (long) contents[i]);
                }
                if (contents[i] instanceof Boolean) {
                    pw.println(sb.toString() + (boolean) contents[i]);
                }

            }

            pw.close();
        } catch (IOException e) {       
            e.printStackTrace();    
        }

    }

    //打印抛出的日志错误
    public static String toString(Exception e) {
        StringBuilder sb = new StringBuilder();
        StackTraceElement[] stackTraceElements = e.getStackTrace();
        sb.append(e.toString() + "\n");
        for (StackTraceElement stackTraceElemen : stackTraceElements) {
            sb.append(stackTraceElemen.toString() + "\n");
        }
        return sb.toString();
	}

    //通过StackTraceElement获取方法位置
    private static String getPrefix(int type) {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[STACK_INDEX];
        String className = stackTraceElement.getClassName();
        int classNameStartIndex = className.lastIndexOf(".") + 1;
        className = className.substring(classNameStartIndex);
        String methodName = stackTraceElement.getMethodName();
        int methodLine = stackTraceElement.getLineNumber();
        char level = LEVEL[type - 2];
        String format = "<%s>%s-%s(%s:%d)";
        return String.format(Locale.CHINESE, format, TAG, className, methodName, level, methodLine);
    }


    public static String showAllElementsInfo() {
        String print = "";
        int count = 0;
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            count++;
            print += String.format("ClassName:%s " +
                                   "\nMethodName:%s " +
                                   "\nMethodLine:%d " +
                                   "\n当前是第%d个 " +
                                   "\n---------------------------- " +
                                   "\n ",
                                   stackTraceElement.getClassName(),
                                   stackTraceElement.getMethodName(),
                                   stackTraceElement.getLineNumber(),
                                   count);
        }
        return print;
    }

    //设置打印时间
    private static  String setPrintTimeState() {
        SimpleDateFormat format=new SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault());    //james.test
        return format.format(System.currentTimeMillis());      
    }

    //移除文件，获取文件时间与当前时间对比，我这时间定位5天，删除五天前的文件
    public static void removeFileByTime(File dirPath) {
        //获取目录下所有文件
        List<File> allFile = getDirAllFile(dirPath);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        //获取当前时间
        Date end = new Date(System.currentTimeMillis());
        try {
            end = dateFormat.parse(dateFormat.format(new Date(System.currentTimeMillis())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (File file : allFile) {//ComDef
            try {
                //文件时间减去当前时间
                Date start = dateFormat.parse(dateFormat.format(new Date(file.lastModified())));
                long diff = end.getTime() - start.getTime();//这样得到的差值是微秒级别
                long days = diff / (1000 * 60 * 60 * 24);
                if (TIMINGDAY <= days) {
                    deleteFile(file);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    //对文件进行时间排序
    private static void fileSortByTime(List<File> fileList) {
        Collections.sort(fileList, new Comparator<File>() {
                public int compare(File p1, File p2) {
                    if (p1.lastModified() < p2.lastModified()) {
                        return -1;
                    }
                    return 1;
                }
            });
    }

    //删除文件夹及文件夹下所有的文件
    private static void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f:files) {
                deleteFile(f);
            }
            file.delete();
        } else if (file.exists()) {
            file.delete();
        }

    }

    //获取指定目录下一级文件
    private static List<File>getDirAllFile(File file) {
        List<File> fileList = new ArrayList<File>();
        File[] files = file.listFiles();
        if (files == null) {
            return fileList;
        }
        for (File f : files) {
            fileList.add(f);
        }
        fileSortByTime(fileList);
        return fileList;
    } 

}
