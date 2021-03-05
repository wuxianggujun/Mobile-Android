package com.wuxianggujun.recyclerviewtest01;

import androidx.recyclerview.widget.RecyclerView;
import android.widget.Adapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.CheckBox;
import com.wuxianggujun.recyclerviewtest01.R;
import java.util.List;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import java.util.Map;
import java.util.HashMap;
import android.widget.CompoundButton;
import android.widget.Toast;

public class RvCheckBoxAdapter extends RecyclerView.Adapter<RvCheckBoxAdapter.RvCheckBoxViewHolder> {

	private List<VideoModel> mData;
	// 先给其默认值为false
	private SparseBooleanArray mCheckStates = new SparseBooleanArray();

	public RvCheckBoxAdapter(List<VideoModel> mData) {
		this.mData = mData;
		initCheck(false);

	}



    //更改集合内部存储的状态
    public void initCheck(boolean flag) {
        for (int i = 0; i < mData.size(); i++) {
            //更改指定位置的数据
            mCheckStates.put(i, flag);
        }
    }

    //全选
    public void selectAll() {
        initCheck(true);
        notifyDataSetChanged();
    }

    //全不选
    public void unSelectAll() {
        initCheck(false);
        notifyDataSetChanged();
    }

	public void deletingData(){
		int y = 0;
		for (int i = 0; i < mData.size(); i++) {

			if(mCheckStates.get(i) != false && mCheckStates.get(i)){
				mData.remove(i);
				mCheckStates.delete(i);
				y++;
				i--;			
			}
			
		}
		notifyDataSetChanged();	
		if(y==0){
			//Toast.makeText(getApplication(), "", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	@Override
	public RvCheckBoxAdapter.RvCheckBoxViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_menu, parent, false);
		RvCheckBoxViewHolder mRvCheckBoxViewHolder = new RvCheckBoxViewHolder(view);
		return mRvCheckBoxViewHolder;
	}

	@Override
	public void onBindViewHolder(RvCheckBoxAdapter.RvCheckBoxViewHolder holder, final int position) {
		VideoModel vm=mData.get(position);
		holder.tv.setText(vm.getVideoName());

		//点击事件
		if (mOnItemClickListener != null) {
			holder.tv.setOnClickListener(new View.OnClickListener(){

					@Override
					public void onClick(View p1) {
						mOnItemClickListener.onItemClick(p1, position);
					}		
				});
		}

		//长按事件
		if (mOnItemLongClickListener != null) {
			holder.tv.setOnLongClickListener(new View.OnLongClickListener(){

					@Override
					public boolean onLongClick(View p1) {
						mOnItemLongClickListener.onItemLongClickListener(p1, position);
						return true;
					}
				});
		}

		if (!Config.isActionMode) {
			holder.cb.setVisibility(View.GONE);	
			holder.cb.setChecked(false);		
		} else {
			holder.cb.setVisibility(View.VISIBLE);	
			holder.cb.setOnCheckedChangeListener(null);
			holder.cb.setChecked(mCheckStates.get(position));
			holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

					@Override
					public void onCheckedChanged(CompoundButton p1, boolean p2) {
						mCheckStates.put(position, p2);
					}
				});
		}

		
	}

	@Override
	public int getItemCount() {
		return  mData != null ? mData.size(): 0;
	}


	class RvCheckBoxViewHolder extends RecyclerView.ViewHolder {

		public TextView tv;
		public CheckBox cb;

		private RvCheckBoxViewHolder(View itemView) {
			super(itemView);
			tv = (TextView) itemView.findViewById(R.id.tv_item);
            cb = (CheckBox) itemView.findViewById(R.id.cb_item);	
		}


	}

	private OnItemClickListener mOnItemClickListener;

	private OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.mOnItemClickListener = clickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

	public interface OnItemLongClickListener {
		void onItemLongClickListener(View view, int position);
	}
	public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
		this.mOnItemLongClickListener = mOnItemLongClickListener;
	}



}
