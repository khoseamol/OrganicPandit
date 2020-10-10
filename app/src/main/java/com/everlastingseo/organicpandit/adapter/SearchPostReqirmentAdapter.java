package com.everlastingseo.organicpandit.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.everlastingseo.organicpandit.R;
import com.everlastingseo.organicpandit.activity.BidProductActivity;
import com.everlastingseo.organicpandit.pagination.PaginationAdapterCallback;
import com.everlastingseo.organicpandit.pojo.postrequirment.PostRequirmentData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchPostReqirmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int ITEM = 0;
    private static final int LOADING = 1;


    private List<PostRequirmentData> movieResults;
    private Context context;

    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;

    private PaginationAdapterCallback mCallback;

    private String errorMsg;


    public SearchPostReqirmentAdapter(Context context) {
        this.context = context;
        this.mCallback = (PaginationAdapterCallback) context;
        movieResults = new ArrayList<>();
    }

    public List<PostRequirmentData> getMovies() {
        return movieResults;
    }

    public void setMovies(List<PostRequirmentData> movieResults) {
        this.movieResults = movieResults;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.activity_search_deal_adapter, parent, false);
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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        switch (getItemViewType(position)) {
            case ITEM:
                final ViewHolder viewHolder = (ViewHolder) holder;
                viewHolder.mDelivery_address.setText("D Address : " + movieResults.get(position).getDeliveryAddress());
                viewHolder.mTxtQuntity.setText("Quntity : " + movieResults.get(position).getQuantity());
                viewHolder.mTxtPaymenttrems.setText("Payment Terms  : " + movieResults.get(position).getPaymentTerms());
                viewHolder.mTxtRate.setText("Price : " + context.getResources().getString(R.string.Rs) + " " + movieResults.get(position).getPrice());
                viewHolder.mTxtProductName.setText("Name : " + movieResults.get(position).getProductName());
                viewHolder.mTxtBid.setText("Bid " + "( " + movieResults.get(position).getTotalBids() + ")");
                viewHolder.mTxtSpecification.setText("Specification : " + movieResults.get(position).getQualitySpecification());
                viewHolder.mTxtBid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, BidProductActivity.class);
                        intent.putExtra("bidData", (Serializable) movieResults.get(position));
                        context.startActivity(intent);
                    }
                });
                break;

            case LOADING:
                LoadingVH loadingVH = (LoadingVH) holder;
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
        return movieResults == null ? 0 : movieResults.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == movieResults.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public void add(PostRequirmentData r) {
        movieResults.add(r);
        notifyItemInserted(movieResults.size() - 1);
    }

    public void addAll(List<PostRequirmentData> moveResults) {
        for (PostRequirmentData result : moveResults) {
            add(result);
        }
    }

    public void remove(PostRequirmentData r) {
        int position = movieResults.indexOf(r);
        if (position > -1) {
            movieResults.remove(position);
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
        add(new PostRequirmentData());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = movieResults.size() - 1;
        PostRequirmentData result = getItem(position);

        if (result != null) {
            movieResults.remove(position);
            notifyItemRemoved(position);
        }
    }

   public  void removeprogrss() {
        isLoadingAdded = false;
    }

    public PostRequirmentData getItem(int position) {
        return movieResults.get(position);
    }

    public void showRetry(boolean show, @Nullable String errorMsg) {
        retryPageLoad = show;
        notifyItemChanged(movieResults.size() - 1);

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
        private TextView mTxtProductName;
        private TextView mTxtBid;
        private TextView mTxtRate;
        private TextView mTxtQuntity;
        private TextView mTxtPaymenttrems;
        private TextView mDelivery_address, mTxtSpecification;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTxtSpecification = (TextView) itemView.findViewById(R.id.TxtSpecification);
            mTxtProductName = (TextView) itemView.findViewById(R.id.TxtBrand);
            mTxtBid = (TextView) itemView.findViewById(R.id.TxtBid);
            mTxtRate = (TextView) itemView.findViewById(R.id.TxtSubCategory);
            mTxtQuntity = (TextView) itemView.findViewById(R.id.TxtStock);
            mTxtPaymenttrems = (TextView) itemView.findViewById(R.id.TxtPrice);
            mDelivery_address = (TextView) itemView.findViewById(R.id.TxtFinalRemarks);

        }
    }
}
