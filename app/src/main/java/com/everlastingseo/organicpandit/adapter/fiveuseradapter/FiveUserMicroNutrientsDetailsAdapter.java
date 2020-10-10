package com.everlastingseo.organicpandit.adapter.fiveuseradapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.everlastingseo.organicpandit.R;
import com.everlastingseo.organicpandit.pojo.fetchuserdata_details.UserMicroList;

import java.util.List;

public class FiveUserMicroNutrientsDetailsAdapter extends RecyclerView.Adapter<FiveUserMicroNutrientsDetailsAdapter.ViewHolder> {
    Context mContext;
    List<UserMicroList> userCropList;

    public FiveUserMicroNutrientsDetailsAdapter(Context mContext, List<UserMicroList> userCropList) {
        this.mContext = mContext;
        this.userCropList = userCropList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchfiveuser_crop_details_adapter, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mTxtCropName.setText("Element : " + userCropList.get(position).getElementName());
        holder.mTxt_Area__inacre.setVisibility(View.GONE);
        holder.mTxtDateOFDown.setVisibility(View.GONE);
        holder.mTxtDateOfAverest.setVisibility(View.GONE);
        holder.mTxtCropCondiation.setText("Percentage : " + userCropList.get(position).getPercentageName());
    }

    @Override
    public int getItemCount() {
        return userCropList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTxtCropName;
        private TextView mTxt_Area__inacre;
        private TextView mTxtDateOFDown;
        private TextView mTxtDateOfAverest;
        private TextView mTxtCropCondiation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTxtCropName = (TextView) itemView.findViewById(R.id.TxtCropName);
            mTxt_Area__inacre = (TextView) itemView.findViewById(R.id.Txt_Area__inacre);
            mTxtDateOFDown = (TextView) itemView.findViewById(R.id.TxtDateOFDown);
            mTxtDateOfAverest = (TextView) itemView.findViewById(R.id.TxtDateOfAverest);
            mTxtCropCondiation = (TextView) itemView.findViewById(R.id.TxtCropCondiation);

        }
    }
}
