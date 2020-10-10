package com.everlastingseo.organicpandit.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.everlastingseo.organicpandit.R;
import com.everlastingseo.organicpandit.activity.BidProductActivity;
import com.everlastingseo.organicpandit.activity.OrganicInputProductActivity;
import com.everlastingseo.organicpandit.activity.SearchUserProductDetailsActivity;
import com.everlastingseo.organicpandit.activity.ShopDataActivity;
import com.everlastingseo.organicpandit.dialog.ProductInquiryDialog;
import com.everlastingseo.organicpandit.helper.ApplicationConstatnt;
import com.everlastingseo.organicpandit.pagination.PaginationAdapterCallback;
import com.everlastingseo.organicpandit.pojo.postrequirment.PostRequirmentData;
import com.everlastingseo.organicpandit.pojo.searchuser_wise.SearchData;
import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchUserwisePrductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;


    private List<SearchData> data;
    private Context context;

    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;

    private PaginationAdapterCallback mCallback;

    private String errorMsg;



    public OnClick OnClick;

   public interface OnClick{
       public void getViewData(SearchData searchData);
   }

    public SearchUserwisePrductAdapter(Context mContext, OnClick onClick) {
        this.context = mContext;
        this.mCallback = (PaginationAdapterCallback) context;
        data = new ArrayList<>();
        this.OnClick = onClick;
    }
    public List<SearchData> getMovies() {
        return data;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.activity_userproduct_adapter, parent, false);
                viewHolder = new ViewHolder(viewItem);
                break;
            case LOADING:
                View viewLoading = inflater.inflate(R.layout.item_loading, parent, false);
                viewHolder = new LoadingVH(viewLoading);
                break;
        }
        return viewHolder;
    }




    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holdert, final int position) {

        switch (getItemViewType(position)) {
            case ITEM:
                final ViewHolder holder = (ViewHolder) holdert;
                holder.mTxtUserFullName.setText("Name : " + data.get(position).getFullname());
                holder.mTxtaddress.setText("Address : " + data.get(position).getAddress());

                if (data.get(position).getUserTypeId().equals("7") || data.get(position).getUserTypeId().equals("12")) {
                    holder.mTxtShop.setVisibility(View.VISIBLE);
                } else {
                    holder.mTxtShop.setVisibility(View.GONE);
                }
                Glide.with(context)
                        .load("http://organicpandit.com//assets//images//gallery//" + Uri.parse(data.get(position).getProfileImage()))
                        .error(R.drawable.userprofile)
                        .into(holder.mImageUserProfile);

                holder.mTxtShop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (data.get(position).getUserTypeId().equals("12")) {
                            Intent intent = new Intent(context, ShopDataActivity.class);
                            intent.putExtra("USERID", data.get(position).getUserId());
                            intent.putExtra("NAME", data.get(position).getFullname());
                            intent.putExtra("TIITLE", data.get(position).getUsername());
                            context.startActivity(intent);
                        } else if (data.get(position).getUserTypeId().equals("7")) {
                            Intent intent = new Intent(context, OrganicInputProductActivity.class);
                            intent.putExtra("USERID", data.get(position).getUserId());
                            intent.putExtra("TIITLE", data.get(position).getUsername());
                            intent.putExtra("NAME", data.get(position).getFullname());
                            context.startActivity(intent);
                        } else {
                            ApplicationConstatnt.getDialog(context, "Response", "Not Allowed");
                        }

                    }
                });
                holder.mTxtView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, SearchUserProductDetailsActivity.class);
                        intent.putExtra("TITLE", data.get(position).getFullname());
                        intent.putExtra("ID", data.get(position).getUserId());
                        context.startActivity(intent);
                    }
                });
                holder.mTxtEnqiry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        ProductInquiryDialog productInquiryDialog = new ProductInquiryDialog(context,data.get(position).getUserTypeId(),data.get(position).getUserId());
                        productInquiryDialog.show();
                    }
                });
                break;

            case LOADING:
                LoadingVH loadingVH = (LoadingVH) holdert;
                if (retryPageLoad) {
                    loadingVH.mErrorLayout.setVisibility(View.VISIBLE);
                    loadingVH.mProgressBar.setVisibility(View.GONE);

                    loadingVH.mErrorTxt.setText(
                            errorMsg != null ?
                                    errorMsg :
                                    context.getString(R.string.error_msg_unknown));

                } else {
                    loadingVH.mErrorLayout.setVisibility(View.GONE);
                    loadingVH.mProgressBar.setVisibility(View.VISIBLE);
                }
                break;
        }


    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == data.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public void add(SearchData r) {
        data.add(r);
        notifyItemInserted(data.size() - 1);
    }

    public void addAll(List<SearchData> moveResults) {
        for (SearchData result : moveResults) {
            add(result);
        }
    }

    public void remove(SearchData r) {
        int position = data.indexOf(r);
        if (position > -1) {
            data.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }


    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new SearchData());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = data.size() - 1;
        SearchData result = getItem(position);

        if (result != null) {
            data.remove(position);
            notifyItemRemoved(position);
        }
    }
public void removeprogress(){
    isLoadingAdded = false;
    notifyDataSetChanged();

}
    public SearchData getItem(int position) {
        return data.get(position);
    }

    public void showRetry(boolean show, @Nullable String errorMsg) {
        retryPageLoad = show;
        notifyItemChanged(data.size() - 1);

        if (errorMsg != null) this.errorMsg = errorMsg;
    }


    protected class LoadingVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ProgressBar mProgressBar;
        private ImageButton mRetryBtn;
        private TextView mErrorTxt;
        private LinearLayout mErrorLayout;

        public LoadingVH(View itemView) {
            super(itemView);

            mProgressBar = itemView.findViewById(R.id.loadmore_progress);
            mRetryBtn = itemView.findViewById(R.id.loadmore_retry);
            mErrorTxt = itemView.findViewById(R.id.loadmore_errortxt);
            mErrorLayout = itemView.findViewById(R.id.loadmore_errorlayout);

            mRetryBtn.setOnClickListener(this);
            mErrorLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.loadmore_retry:
                case R.id.loadmore_errorlayout:

                    showRetry(false, null);
                    mCallback.retryPageLoad();

                    break;
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageUserProfile;
        private RelativeLayout mLin;
        private TextView mTxtUserFullName;
        private TextView mTxtaddress;
        private TextView mTxtShop, mTxtView, mTxtEnqiry;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageUserProfile = (ImageView) itemView.findViewById(R.id.ImageUserProfile);
            mLin = (RelativeLayout) itemView.findViewById(R.id.Lin);
            mTxtUserFullName = (TextView) itemView.findViewById(R.id.TxtUserFullName);
            mTxtaddress = (TextView) itemView.findViewById(R.id.Txtaddress);
            mTxtShop = (TextView) itemView.findViewById(R.id.TxtShop);
            mTxtView = (TextView) itemView.findViewById(R.id.TxtView);
            mTxtEnqiry = (TextView) itemView.findViewById(R.id.TxtEnqiry);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OnClick.getViewData(data.get(getAdapterPosition()));
                }
            });
        }
    }

}
