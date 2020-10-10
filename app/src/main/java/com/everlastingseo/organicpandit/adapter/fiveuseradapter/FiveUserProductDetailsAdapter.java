package com.everlastingseo.organicpandit.adapter.fiveuseradapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.everlastingseo.organicpandit.R;
import com.everlastingseo.organicpandit.pojo.fetchuserdata_details.UserProductList;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class FiveUserProductDetailsAdapter extends RecyclerView.Adapter<FiveUserProductDetailsAdapter.ViewHolder> {
    Context mContext;
    List<UserProductList> userProductList;

    public FiveUserProductDetailsAdapter(Context mContext, List<UserProductList> userProductList) {
        this.mContext = mContext;
        this.userProductList = userProductList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchfiveuser_productdetails_adapter, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(mContext)
                .load(Uri.parse(userProductList.get(position).getImages()))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .error(R.drawable.ic_launcher_background)
                .into(holder.mImageProduct);
        holder.mTxtDescription.setText("Discription : " + userProductList.get(position).getDescription());
        holder.mTxtProductName.setText("Name : " + userProductList.get(position).getName());
        holder.mTxtProductPrice.setText("Price : " + mContext.getResources().getString(R.string.Rs) +" "+ userProductList.get(position).getPrice());
        holder.mTxtValidity.setText("Validity : " + userProductList.get(position).getFromDate() + " - " + userProductList.get(position).getToDate());
        holder.mTxtQualntity.setText("Qualntity : " + userProductList.get(position).getQuantityName());
        holder.mTxtQuality.setText("Quality : " + userProductList.get(position).getQuality());

    }

    @Override
    public int getItemCount() {
        return userProductList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageProduct;
        private TextView mTxtProductName;
        private TextView mTxtProductPrice;
        private TextView mTxtValidity;
        private TextView mTxtQualntity;
        private TextView mTxtDescription;
        private TextView mTxtQuality;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageProduct = (ImageView) itemView.findViewById(R.id.ImageProduct);
            mTxtProductName = (TextView) itemView.findViewById(R.id.TxtBrand);
            mTxtProductPrice = (TextView) itemView.findViewById(R.id.TxtProductPrice);
            mTxtValidity = (TextView) itemView.findViewById(R.id.TxtValidity);
            mTxtQualntity = (TextView) itemView.findViewById(R.id.TxtQualntity);
            mTxtDescription = (TextView) itemView.findViewById(R.id.TxtDescription);
            mTxtQuality = (TextView) itemView.findViewById(R.id.TxtQuality);

        }
    }
}
