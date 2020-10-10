package com.everlastingseo.organicpandit.adapter.fiveuseradapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.everlastingseo.organicpandit.R;
import com.everlastingseo.organicpandit.pojo.fetchuserdata_details.UserCropList;

import java.util.List;

public class FiveUserCropInspectionDetailsAdapter extends RecyclerView.Adapter<FiveUserCropInspectionDetailsAdapter.ViewHolder> {
    Context mContext;
    List<UserCropList> userCropList;

    public FiveUserCropInspectionDetailsAdapter(Context mContext, List<UserCropList> userCropList) {
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
        holder.mTxtCropName.setText("Name : " + userCropList.get(position).getCropName());
        holder.mTxt_Area__inacre.setText("Area (in acre's) : " + userCropList.get(position).getArea());
        holder.mTxtDateOFDown.setText("Sown  : " + userCropList.get(position).getDateSown());
        holder.mTxtDateOfAverest.setText("Harvest : " + userCropList.get(position).getDateHarvest());
        holder.mTxtCropCondiation.setText("Condiation : " + userCropList.get(position).getCropCondition());
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
