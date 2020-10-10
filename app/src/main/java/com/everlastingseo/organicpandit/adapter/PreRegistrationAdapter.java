package com.everlastingseo.organicpandit.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.everlastingseo.organicpandit.R;
import com.everlastingseo.organicpandit.activity.RegistrationActvity;
import com.everlastingseo.organicpandit.model.DashboardServiceModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PreRegistrationAdapter extends RecyclerView.Adapter<PreRegistrationAdapter.ViewHolder> {

    List<DashboardServiceModel> modelList;
    Context mContext;

    public PreRegistrationAdapter(Context mContext, List<DashboardServiceModel> serviceModels) {
        this.mContext = mContext;
        this.modelList = serviceModels;


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
                    Intent intent=new Intent(mContext, RegistrationActvity.class);
                    intent.putExtra("TitleName",modelList.get(getAdapterPosition()).getServicename());
                    intent.putExtra("USERID",modelList.get(getAdapterPosition()).getUserID());
                    mContext.startActivity(intent);
                }
            });

        }
    }
}
