package com.everlastingseo.organicpandit.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.everlastingseo.organicpandit.R;
import com.everlastingseo.organicpandit.helper.ApplicationConstatnt;
import com.everlastingseo.organicpandit.pojo.organicInput_product.OrganicProductData;

import java.util.List;

public class OrganicInputProductAdapter extends RecyclerView.Adapter<OrganicInputProductAdapter.ViewHolder> {
    Context mContext;
    List<OrganicProductData> data;
    ClickEvent ClickEvent;

    public OrganicInputProductAdapter(Context mContext, List<OrganicProductData> data, ClickEvent ClickEvent) {
        this.mContext = mContext;
        this.data = data;
        this.ClickEvent = ClickEvent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_organicproduct, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.mTxtCateory.setText("Category : " + data.get(position).getCategoryName());
        holder.mTxtSubCategory.setText("Sub Category : " + data.get(position).getSubCategoryName());
        holder.mTxtPrice.setText("Price : " + mContext.getResources().getString(R.string.Rs) + " " + data.get(position).getPrice());
        holder.mTxtBrand.setText("Brand : " + data.get(position).getEcommerceBrandId());
        holder.mTxtFinalRemarks.setText("Remark : " + data.get(position).getFinalRemarks());
        holder.mTxtWeight.setText("Weight : " + data.get(position).getWeight());

        holder.mTxtDosage.setText("Dosage : " + data.get(position).getDosage());
        holder.mTxtSpectrum.setText("Spectrum : " + data.get(position).getSpectrum());
        holder.mTxtApplicableCrops.setText("Applicable Crops : " + data.get(position).getApplicableCrops());

        holder.mTxtCompatibility.setText("Compatibility : " + data.get(position).getCompatibility());
        holder.mTxtDurationof_Effect.setText("Duration of Effect : " + data.get(position).getDurationEffect());
        holder.mTxtFrequencyof_Application.setText("Frequency : " + data.get(position).getFrequencyApplication());


        holder.mTXtAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               ClickEvent.getClickData(data.get(position));
            }
        });
        Glide.with(mContext)
                .load(Uri.parse(data.get(position).getImages()))
//                .load("http://organicpandit.com/assets/design/img/slider-2.jpg")
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .skipMemoryCache(true)
                .error(R.drawable.ic_launcher_background)
                .into(holder.mImageOranicProduct);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface ClickEvent {
        void getClickData(OrganicProductData organicProductData);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageOranicProduct;
        private LinearLayout mLinAdd;
        private TextView mTxtPrice;
        private TextView mTXtAddToCart;
        private LinearLayout mLinn;
        private TextView mTxtCateory;
        private TextView mTxtBrand;
        private TextView mTxtSubCategory;
        private TextView mTxtWeight;
        private TextView mTxtDosage;
        private TextView mTxtSpectrum;
        private TextView mTxtApplicableCrops;
        private TextView mTxtCompatibility;
        private TextView mTxtDurationof_Effect;
        private TextView mTxtFrequencyof_Application;
        private TextView mTxtFinalRemarks;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageOranicProduct = (ImageView) itemView.findViewById(R.id.ImageOranicProduct);
            mLinAdd = (LinearLayout) itemView.findViewById(R.id.LinAdd);
            mTxtPrice = (TextView) itemView.findViewById(R.id.TxtPrice);
            mTXtAddToCart = (TextView) itemView.findViewById(R.id.TXtAddToCart);
            mLinn = (LinearLayout) itemView.findViewById(R.id.linn);
            mTxtCateory = (TextView) itemView.findViewById(R.id.TxtCateory);
            mTxtBrand = (TextView) itemView.findViewById(R.id.TxtBrand);
            mTxtSubCategory = (TextView) itemView.findViewById(R.id.TxtSubCategory);
            mTxtWeight = (TextView) itemView.findViewById(R.id.TxtWeight);
            mTxtDosage = (TextView) itemView.findViewById(R.id.TxtDosage);
            mTxtSpectrum = (TextView) itemView.findViewById(R.id.TxtSpectrum);
            mTxtApplicableCrops = (TextView) itemView.findViewById(R.id.TxtApplicableCrops);
            mTxtCompatibility = (TextView) itemView.findViewById(R.id.TxtCompatibility);
            mTxtDurationof_Effect = (TextView) itemView.findViewById(R.id.TxtDurationof_Effect);
            mTxtFrequencyof_Application = (TextView) itemView.findViewById(R.id.TxtFrequencyof_Application);
            mTxtFinalRemarks = (TextView) itemView.findViewById(R.id.TxtFinalRemarks);


        }
    }
}
