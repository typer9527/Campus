package com.yl.campus.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * RecyclerView.Adapter封装
 * Created by Luke on 2017/10/20.
 */

abstract class SuperAdapter<T> extends RecyclerView.Adapter<SuperAdapter.BaseViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_NORMAL = 1;
    private static final int TYPE_FOOTER = 2;
    private List<T> dataList;
    private int headerLayoutId, footerLayoutId, itemLayoutId;
    private Context context;

    SuperAdapter(List<T> dataList, int itemLayoutId,
                 int headerLayoutId, int footerLayoutId) {
        this.dataList = dataList;
        this.headerLayoutId = headerLayoutId;
        this.footerLayoutId = footerLayoutId;
        this.itemLayoutId = itemLayoutId;
    }

    abstract void bindItemLayout(SuperAdapter.BaseViewHolder holder, T data);

    abstract void bindHeaderLayout(SuperAdapter.BaseViewHolder holder);

    abstract void bindFooterLayout(SuperAdapter.BaseViewHolder holder);

    void onItemClick(int position) {

    }

    T getData(int position) {
        return dataList.get(position);
    }

    Context getContext() {
        return context;
    }

    @Override
    public int getItemViewType(int position) {
        if (headerLayoutId != -1 && position == 0) {
            return TYPE_HEADER;
        }
        if (footerLayoutId != -1 && position == dataList.size() + 1) {
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        context = parent.getContext();
        switch (viewType) {
            case TYPE_HEADER:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(headerLayoutId, parent, false);
                break;
            case TYPE_FOOTER:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(footerLayoutId, parent, false);
                break;
            case TYPE_NORMAL:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(itemLayoutId, parent, false);
                break;
            default:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(itemLayoutId, parent, false);
        }
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SuperAdapter.BaseViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                bindHeaderLayout(holder);
                break;
            case TYPE_FOOTER:
                bindFooterLayout(holder);
                break;
            case TYPE_NORMAL:
                int layoutPos = holder.getLayoutPosition();
                final int realPos = headerLayoutId == -1 ? layoutPos : layoutPos - 1;
                bindItemLayout(holder, dataList.get(realPos));
                holder.getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClick(realPos);
                    }
                });
                break;
            default:
        }
    }

    @Override
    // 重写以适配GridLayoutManager
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager manager = (GridLayoutManager) layoutManager;
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int viewType = getItemViewType(position);
                    return viewType == TYPE_HEADER || viewType ==
                            TYPE_FOOTER ? manager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    // 重写以适配StaggeredGridLayoutManager
    public void onViewAttachedToWindow(SuperAdapter.BaseViewHolder holder) {
        int viewType = getItemViewType(holder.getLayoutPosition());
        if (viewType == TYPE_HEADER || viewType == TYPE_FOOTER) {
            ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
            if (params != null && params instanceof
                    StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams layoutParams =
                        (StaggeredGridLayoutManager.LayoutParams) params;
                layoutParams.setFullSpan(true);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (headerLayoutId != -1 && footerLayoutId != -1) {
            return dataList.size() + 2;
        } else if (headerLayoutId != -1 || footerLayoutId != -1) {
            return dataList.size() + 1;
        } else {
            return dataList.size();
        }
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {
        private View itemView;
        private SparseArray<View> views;

        BaseViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            views = new SparseArray<>();
        }

        View getItemView() {
            return itemView;
        }

        @SuppressWarnings("unchecked")
        <V extends View> V findViewById(int id) {
            View view = views.get(id);
            if (view == null) {
                view = itemView.findViewById(id);
                views.put(id, view);
            }
            return (V) view;
        }
    }
}
