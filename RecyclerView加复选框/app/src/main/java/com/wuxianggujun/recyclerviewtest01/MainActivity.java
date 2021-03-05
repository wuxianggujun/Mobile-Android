package com.wuxianggujun.recyclerviewtest01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.List;
import java.util.ArrayList;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
	private RecyclerView mRecyclerView;
    private List<VideoModel> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		initDatas();
		mRecyclerView = findViewById(R.id.activity_main_RecyclerView);
		LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

		final RvCheckBoxAdapter mRvCheckBoxAdapter = new  RvCheckBoxAdapter(mDatas);
		mRecyclerView.setAdapter(mRvCheckBoxAdapter);
		mRvCheckBoxAdapter.setOnItemLongClickListener(new RvCheckBoxAdapter.OnItemLongClickListener(){

				@Override
				public void onItemLongClickListener(View view, int position) {
					Toast.makeText(getApplication(), "长按事件" + position, Toast.LENGTH_SHORT).show();
					if (Config.isActionMode) {
						Config.isActionMode = false;
						mRvCheckBoxAdapter.unSelectAll();
	
					} else {
						Config.isActionMode = true;
						mRvCheckBoxAdapter.unSelectAll();
					}
					mRvCheckBoxAdapter.notifyDataSetChanged();				
				}
			});
	    mRvCheckBoxAdapter.setOnItemClickListener(new RvCheckBoxAdapter.OnItemClickListener(){

				@Override
				public void onItemClick(View view, int position) {
					Toast.makeText(getApplication(), "点击事件" + position, Toast.LENGTH_SHORT).show();
					mRvCheckBoxAdapter.deletingData();
				}
			});

	}

	private void initDatas() {
		mDatas = new ArrayList<VideoModel>();
		for (int i = 0; i < 500; i++) {
			VideoModel vm = new VideoModel();
			vm.setVideoName("无相孤君" + i);
			mDatas.add(vm);
		}

	}


}
