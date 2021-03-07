package com.wuxianggujun.popupwindow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Context;
import android.widget.Button;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.PopupWindow;
import android.widget.LinearLayout.LayoutParams;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button bt;
    private PopupWindow mPopupWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt = findViewById(R.id.activity_mainButton);
        bt.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    showPopupWindow(v);
                }                
            });
            
    }
    
    private void showPopupWindow(View v) {
        View contentView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_popip,null);         
       
        mPopupWindow = new PopupWindow(contentView);
        mPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);  
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);  
        //设置个个控件的点击事件
        TextView tv1 = contentView.findViewById(R.id.item_popipTextView1);
        TextView tv2 = contentView.findViewById(R.id.item_popipTextView2);
        TextView tv3 = contentView.findViewById(R.id.item_popipTextView3);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
          mPopupWindow.showAsDropDown(v);     
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
          case R.id.item_popipTextView1:
              Toast.makeText(getApplication(), "点击了删除", Toast.LENGTH_SHORT).show();
              mPopupWindow.dismiss();
              break;
           case R.id.item_popipTextView2:
                Toast.makeText(getApplication(), "点击了信息", Toast.LENGTH_SHORT).show();
               mPopupWindow.dismiss();
               break;
            case R.id.item_popipTextView3:
                Toast.makeText(getApplication(), "点击了选择", Toast.LENGTH_SHORT).show();
                mPopupWindow.dismiss();
           break; 
        }
       
    }

    
    
    
    
}
