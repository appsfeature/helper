package com.helper.model.base;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.helper.R;
import com.helper.callback.Response;
import com.helper.model.HistoryModelResponse;
import com.helper.util.BaseUtil;


public abstract class BaseHistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public Response.OnListUpdateListener<HistoryModelResponse> onUpdateUiListener;
    public Response.OnListClickListener<HistoryModelResponse> onClickOverrideListener;

    public HistoryModelResponse mItem;
    public TextView tvTitle;
    public TextView tvSubTitle;
    public TextView tvTime;
    public View ivDelete;
    public TextView tvStatus;
    public TextView tvType;
    public TextView tvViewCount;
    public boolean isVisibleDelete = true;
    public TextView tvWatched;
    public View llWatched;

    public abstract boolean isEnableViewCount();
    public abstract void initViews(View itemView);
    public abstract void onUpdateUi(HistoryModelResponse mItem);
    public abstract void onItemClicked(View view, int position, HistoryModelResponse item);
    public abstract void onDeleteClicked(View view, int position, HistoryModelResponse item);


    public BaseHistoryViewHolder(View itemView, Response.OnListUpdateListener<HistoryModelResponse> onUpdateUiListener) {
        super(itemView);
        this.onUpdateUiListener = onUpdateUiListener;
        initViewHolder(itemView);
        ivDelete.setVisibility(onUpdateUiListener == null ? View.GONE : View.VISIBLE);
    }

    public BaseHistoryViewHolder(View itemView, Response.OnListClickListener<HistoryModelResponse> onClickOverrideListener) {
        super(itemView);
        this.onClickOverrideListener = onClickOverrideListener;
        initViewHolder(itemView);
    }

    private void initViewHolder(View itemView) {
        tvTitle =  itemView.findViewById(R.id.tvTitle);
        tvSubTitle =  itemView.findViewById(R.id.tvSubTitle);
        tvTime =  itemView.findViewById(R.id.tvTime);
        tvStatus =  itemView.findViewById(R.id.tv_status);
        tvType =  itemView.findViewById(R.id.tv_type);
        tvViewCount =  itemView.findViewById(R.id.tv_view_count);
        ivDelete =  itemView.findViewById(R.id.iv_delete);
        llWatched =  itemView.findViewById(R.id.ll_view_watched);
        tvWatched =  itemView.findViewById(R.id.tv_view_watched);
        itemView.setOnClickListener(this);
        ivDelete.setOnClickListener(this);
        initViews(itemView);
    }

    @Override
    public void onClick(View view) {
        if(mItem != null) {
            if(view.getId() == R.id.iv_delete){
                if(onClickOverrideListener != null) {
                    onClickOverrideListener.onDeleteClicked(view, getAdapterPosition(), mItem);
                }else {
                    onDeleteClicked(view, getAdapterPosition(), mItem);
                }
            }else {
                if(onClickOverrideListener != null) {
                    onClickOverrideListener.onItemClicked(view, mItem);
                }else {
                    onItemClicked(view, getAdapterPosition(), mItem);
                }
            }
        }
    }

    public void setVisibleDelete(boolean visibleDelete) {
        isVisibleDelete = visibleDelete;
    }

    public void setStatus(String status) {
        tvStatus.setText(status);
    }

    public void setType(String type) {
        tvType.setText(type);
    }

    /**
     * @param mItem = List item
     * @param itemType = PDF, Article, Video, MCQ
     * @param viewsCount = getString(R.string.pdf_views, mItem.getViewCountFormatted())
     */
    public void setData(HistoryModelResponse mItem, String itemType, String viewsCount) {
        this.mItem = mItem;
        tvTitle.setText(mItem.getTitle());
        if(!TextUtils.isEmpty(mItem.getSubTitle())){
            tvSubTitle.setText(mItem.getSubTitle());
            tvSubTitle.setVisibility(View.VISIBLE);
        }else {
            tvSubTitle.setVisibility(View.INVISIBLE);
        }
        if(!TextUtils.isEmpty(mItem.getFormattedDate())){
            tvTime.setText(mItem.getFormattedDate());
        }else {
            tvTime.setText(BaseUtil.getTimeInDaysAgoFormat(mItem.getCreatedAt()));
        }
        tvType.setText(itemType);
        if(isEnableViewCount() && !TextUtils.isEmpty(mItem.getViewCountFormatted())) {
            tvViewCount.setText(viewsCount);
            tvViewCount.setVisibility(View.VISIBLE);
        }else {
            tvViewCount.setVisibility(View.GONE);
        }
        ivDelete.setVisibility(isVisibleDelete ? View.VISIBLE : View.GONE);
        if(mItem.getRowCount() > 0) {
            tvWatched.setText("" + mItem.getRowCount());
            llWatched.setVisibility(View.VISIBLE);
        }else {
            llWatched.setVisibility(View.INVISIBLE);
        }
        onUpdateUi(mItem);
    }
}
