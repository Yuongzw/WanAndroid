package com.example.admin.mydailystudy.mvp.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.admin.mydailystudy.R;
import com.example.admin.mydailystudy.bean.SkinColor;
import com.example.admin.mydailystudy.mvp.view.widget.CircleImageView;

import java.util.List;

public class MyRvDialogAdapter extends RecyclerView.Adapter {

    private List<SkinColor> skinColors;
    private OnItemClickListener onItemClickListener;
    private Context context;

    public MyRvDialogAdapter(Context context, List<SkinColor> skinColors) {
        this.context = context;
        this.skinColors = skinColors;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dialog_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.setPosition(position);
        SkinColor skinColor = skinColors.get(position);
        holder.circleImageView.setImageResource(skinColor.getColor());
        if (skinColor.isChoosed()) {
            holder.imageView.setVisibility(View.VISIBLE);
        } else {
            holder.imageView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return skinColors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView circleImageView;
        private ImageView imageView;
        private int position;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.circleImageView);
            imageView = itemView.findViewById(R.id.imageView);
            circleImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onClick(position);
                    }
                }
            });
        }

        public void setPosition(int position) {

            this.position = position;
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {

        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }
}
