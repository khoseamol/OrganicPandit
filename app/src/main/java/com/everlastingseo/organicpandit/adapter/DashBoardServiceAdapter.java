package com.everlastingseo.organicpandit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.everlastingseo.organicpandit.R;
import com.everlastingseo.organicpandit.model.DashboardServiceModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DashBoardServiceAdapter extends RecyclerView.Adapter<DashBoardServiceAdapter.ViewHolder> {

    List<DashboardServiceModel> modelList;
    Context mContext;
    public OnClick onClick;

    public DashBoardServiceAdapter(Context mContext, List<DashboardServiceModel> serviceModels, OnClick OnClick) {
        this.mContext = mContext;
        this.modelList = serviceModels;
        this.onClick = OnClick;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.serviceadapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mTextViewName.setText(modelList.get(position).getServicename());
        holder.mTextViewName.setSelected(true);
        Picasso.with(mContext).load(modelList.get(position).getServiceImage()).into(holder.mImageView);


        holder.mImageView.setImageResource(modelList.get(position).getServiceImage());

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

   public interface OnClick {
        public void gettitle(DashboardServiceModel dashboardServiceModel);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView mTextViewName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.imageView);
            mTextViewName = (TextView) itemView.findViewById(R.id.textViewName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClick.gettitle(modelList.get(getAdapterPosition()));
                }
            });

        }
    }
}
