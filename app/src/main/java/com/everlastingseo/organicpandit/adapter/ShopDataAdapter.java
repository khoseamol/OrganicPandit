package com.everlastingseo.organicpandit.adapter;

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
import com.everlastingseo.organicpandit.R;
import com.everlastingseo.organicpandit.helper.ApplicationConstatnt;
import com.everlastingseo.organicpandit.pojo.shopdata.ShopData;

import java.util.List;

public class ShopDataAdapter extends RecyclerView.Adapter<ShopDataAdapter.ViewHolder> {
    Context mContext;
    List<ShopData> data;
  public   getClick getClick;

    public ShopDataAdapter(Context mContext, List<ShopData> data, getClick getClick) {
        this.mContext = mContext;
        this.data = data;

        this.getClick = getClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_shop_data_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.mTxtProductName.setText("Product : " + data.get(position).getProductName());
        holder.mTxtCategory.setText("" + data.get(position).getCategoryName());
        holder.mTxtPrice.setText("Price : " + mContext.getResources().getString(R.string.Rs) + " " + data.get(position).getPrice());
        holder.mTxtFullName.setText("Name : " + data.get(position).getFullname());
        holder.mTxtFinalRemarks.setText("Remark : " + data.get(position).getDescription());
        holder.mTxtStock.setText("Stock : " + data.get(position).getStock());
        holder.mTXtAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getClick.getclickedEvent(data.get(position));
            }
        });

        Glide.with(mContext)
                .load(Uri.parse(data.get(position).getPrimaryImage()))
//                .load("http://organicpandit.com/assets/design/img/slider-2.jpg")
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .skipMemoryCache(true)
                .error(R.mipmap.ic_launcher)
                .into(holder.mImageProduct);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface getClick {
        void getclickedEvent(ShopData data);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageProduct;
        private TextView mTxtPrice;
        private TextView mTXtAddToCart;
        private TextView mTxtProductName;
        private TextView mTxtCategory;
        private TextView mTxtFullName;
        private TextView mTxtStock;
        private TextView mTxtFinalRemarks;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageProduct = (ImageView) itemView.findViewById(R.id.ImageProduct);
            mTxtPrice = (TextView) itemView.findViewById(R.id.TxtPrice);
            mTXtAddToCart = (TextView) itemView.findViewById(R.id.TXtAddToCart);
            mTxtProductName = (TextView) itemView.findViewById(R.id.TxtBrand);
            mTxtCategory = (TextView) itemView.findViewById(R.id.TxtBrand);
            mTxtFullName = (TextView) itemView.findViewById(R.id.TxtSubCategory);
            mTxtStock = (TextView) itemView.findViewById(R.id.TxtStock);
            mTxtFinalRemarks = (TextView) itemView.findViewById(R.id.TxtFinalRemarks);


        }
    }
}
