package com.wuxianggujun.unzip.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;
import android.view.View;
import java.util.List;
import android.widget.TextView;
import android.view.LayoutInflater;
import com.wuxianggujun.unzip.R;
import com.wuxianggujun.unzip.adapter.bean.FileInfo;

public class FileRvAdapter extends RecyclerView.Adapter<FileRvAdapter.FileRvHolder> {

    private List<FileInfo> data;

    public FileRvAdapter(List<FileInfo> data) {
        this.data = data;
    }
    
    @Override
    public FileRvAdapter.FileRvHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frv_item,parent,false);     
        return new FileRvHolder(view);
    }

    @Override
    public void onBindViewHolder(FileRvAdapter.FileRvHolder holder, int position) {
       holder.fileName.setText(data.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return data !=null ? data.size() : 0;
    }

    
    
    
    
    
    class FileRvHolder extends RecyclerView.ViewHolder{
        public TextView fileName;
        private FileRvHolder(View item_View){
            super(item_View);
            fileName = item_View.findViewById(R.id.frvitem_fileName);
            
        }
        
        
    }
    
    
    
    
    
}
