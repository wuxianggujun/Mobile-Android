package com.wuxianggujun.unzip;

import androidx.fragment.app.DialogFragment;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.wuxianggujun.unzip.adapter.FileRvAdapter;
import java.util.List;
import java.util.ArrayList;
import android.view.animation.Animation;
import android.view.Window;
import android.graphics.drawable.ColorDrawable;
import android.graphics.Color;
import android.view.WindowManager;
import java.io.File;
import java.util.Collections;
import android.os.Environment;
import com.wuxianggujun.unzip.adapter.bean.FileInfo;
import java.util.Arrays;
import com.wenming.library.save.imp.LogWriter;

public class FileRecyclerView extends DialogFragment {
    
    private FileRvAdapter fileRvAdapter;
    private List<FileInfo> data = new ArrayList<FileInfo>();
    String path = Environment.getExternalStorageDirectory().toString();
    
    
    private ClickCallBack mClickCallBack;
    
    public interface  ClickCallBack{
        void onitemClick();
    }
       
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        data.clear();
        return super.onCreateDialog(savedInstanceState);    
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_dialog,null);
        RecyclerView recyclerView = view.findViewById(R.id.fragment_main_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(layoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        
        fileRvAdapter = new FileRvAdapter(data);
        
        
        File dir=new File(path);
        File[] subFiles=dir.listFiles();
        LogWriter.writeLog("file",dir.getAbsolutePath());
        if(subFiles != null){
            for(File f : subFiles){
                FileInfo ff = new FileInfo();
                ff.setName(f.getName());
                data.add(ff);
            }
        }
        fileRvAdapter.notifyDataSetChanged();
        
        recyclerView.setAdapter(fileRvAdapter);
        
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onActivityCreated(savedInstanceState);
        //设置对话框内容为透明
        //getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //将对话框外的内容设置为透明
        Window window= getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window != null ? window.getAttributes() : null;
        windowParams.dimAmount = 0.0f;
        window.setAttributes(windowParams);
        
    }

    @Override
    public void onResume() {
        super.onResume();
        //动态设置宽高
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return super.onCreateAnimation(transit, enter, nextAnim);
    }                   


    
    
    
}
