package com.everlastingseo.organicpandit.productcart.activity;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.everlastingseo.organicpandit.R;
import com.everlastingseo.organicpandit.helper.db.AppEntity;

import java.util.List;

public class CartDataAdapter extends RecyclerView.Adapter<CartDataAdapter.ViewHolder> {

    Context mContext;
    List<AppEntity> modelList;
    ClickeEventHandel clickEvent;

    public CartDataAdapter(Context mContext, List<AppEntity> modelList, ClickeEventHandel clickEvent) {
        this.mContext = mContext;
        this.modelList = modelList;
        this.clickEvent = clickEvent;
    }

    public void updateData(List<AppEntity> all) {
        this.modelList = all;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_data_activity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.TxtPtype.setText( modelList.get(position).getProductType() );
        holder.mTxtProductName.setText("Name : " + modelList.get(position).getproductName());
        holder.mTxtPrice.setText(mContext.getResources().getString(R.string.Rs) + " " + modelList.get(position).getpPrice());
        holder.mTxtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickEvent.getdelete(modelList.get(position));
            }
        });

        holder.mTxtItemAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickEvent.addItem(modelList.get(position));
            }
        });
        holder.mTxtremoveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickEvent.removeItem(modelList.get(position));
            }
        });
        holder.mTxtItemCount.setText(modelList.get(position).getItemcount());
        Glide.with(mContext)
                .load(Uri.parse("http://organicpandit.com/" + modelList.get(position).getProductImg()))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .error(R.mipmap.ic_launcher)
                .into(holder.mProductImage);

    }



    @Override
    public int getItemCount() {
        return modelList.size();
    }

    interface ClickeEventHandel {
        void getdelete(AppEntity appEntity);

        void addItem(AppEntity appEntity);

        void removeItem(AppEntity appEntity);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTxtProductName, TxtPtype, mTxtDelete, mTxtPrice, mTxtremoveItem, mTxtItemCount, mTxtItemAdd;
ImageView mProductImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TxtPtype = (TextView) itemView.findViewById(R.id.TxtPtype);
            mTxtProductName = (TextView) itemView.findViewById(R.id.TxtProductName);
            mTxtDelete = (TextView) itemView.findViewById(R.id.TxtDelete);
            mTxtPrice = (TextView) itemView.findViewById(R.id.TxtPrice);
            mTxtremoveItem = (TextView) itemView.findViewById(R.id.TxtremoveItem);
            mTxtItemCount = (TextView) itemView.findViewById(R.id.TxtItemCount);
            mTxtItemAdd = (TextView) itemView.findViewById(R.id.TxtItemAdd);
            mProductImage=(ImageView)itemView.findViewById(R.id.ProductImage);



//            mTxtProductId=(TextView)itemView.findViewById(R.id.TxtProductId);

        }
    }
}
