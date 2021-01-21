package com.example.demotestecarx.view;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.demotestecarx.R;

import java.util.ArrayList;

/**
 * 瀑布流所用的adapter
 *
 * 加载不同高度的item实现瀑布流效果
 * */
public class MDStaggeredRvAdapter extends RecyclerView.Adapter<MDStaggeredRvAdapter.ViewHolder> {
    // 展示数据
    private ArrayList<String> mData;
    public MDStaggeredRvAdapter(ArrayList<String> data) {
        this.mData = data;
    }
    public void updateData(ArrayList<String> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        // 瀑布流样式外部设置spanCount为2，在这列设置两个不同的item type，以区分不同的布局
        return position % 2;
    }

    @Override
    public MDStaggeredRvAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v;
        if(viewType == 1) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_staggered_item_one, parent, false);
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_staggered_item_two, parent, false);
        }
        // 实例化viewholder
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MDStaggeredRvAdapter.ViewHolder holder, int position) {
        // 绑定数据
        holder.mTv.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTv;

        public ViewHolder(View itemView) {
            super(itemView);
            mTv = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
