package com.yl.campus.app.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yl.campus.R;
import com.yl.campus.app.model.CardBean;
import com.yl.campus.common.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BalanceActivity extends BaseActivity {

    @BindView(R.id.rv_card)
    RecyclerView rvCard;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_banlance;
    }

    @Override
    protected String getDefaultTitle() {
        return "余额查询";
    }

    @Override
    protected void initView() {
        rvCard.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initData() {
        // 模拟数据
        CardBean eat = new CardBean();
        eat.bgRes = R.drawable.bg_card_green;
        eat.cardType = "食堂饭卡";
        eat.cardNo = "246523526";
        eat.cardState = "未欠费";
        eat.cardBalance = "150.50";
        CardBean net = new CardBean();
        net.bgRes = R.drawable.bg_card_bule;
        net.cardType = "宿舍水卡";
        net.cardNo = "98765588";
        net.cardState = "未欠费";
        net.cardBalance = "23.90";
        CardBean water = new CardBean();
        water.bgRes = R.drawable.bg_card_yellow;
        water.cardType = "宽带网卡";
        water.cardNo = "32680986";
        water.cardState = "未欠费";
        water.cardBalance = "70.80";
        List<CardBean> list = new ArrayList<>();
        list.add(eat);
        list.add(net);
        list.add(water);
        rvCard.setAdapter(new CardAdapter(list));
    }

    class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

        private List<CardBean> beans;

        CardAdapter(List<CardBean> beans) {
            this.beans = beans;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_card, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            CardBean bean = beans.get(position);
            holder.rlCard.setBackgroundResource(bean.bgRes);
            holder.tvType.setText(bean.cardType);
            holder.tvNo.setText(getString(R.string.card_no, bean.cardNo));
            holder.tvState.setText(getString(R.string.card_state, bean.cardState));
            holder.tvBalance.setText(getString(R.string.current_balance, bean.cardBalance));
            holder.btnCharge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(BalanceActivity.this, PayModeActivity.class));
                }
            });
        }

        @Override
        public int getItemCount() {
            return beans.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.tv_type)
            TextView tvType;
            @BindView(R.id.tv_no)
            TextView tvNo;
            @BindView(R.id.tv_state)
            TextView tvState;
            @BindView(R.id.tv_balance)
            TextView tvBalance;
            @BindView(R.id.rl_card)
            RelativeLayout rlCard;
            @BindView(R.id.btn_charge)
            Button btnCharge;

            ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
