package com.sz.leo.androidnote.chapter07.sqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sz.leo.androidnote.R;

import java.util.List;

/**
 * @author：leo
 * @date：2019/6/4
 * @email：lei.lu@e-at.com
 */
public class OrderAdapter extends BaseAdapter {
    private Context mContext;
    private List<Order> mOrderList;

    public OrderAdapter(Context context, List<Order> orderList) {
        mContext = context;
        mOrderList = orderList;
    }

    @Override
    public int getCount() {
        return mOrderList == null ? 0 : mOrderList.size();
    }

    @Override
    public Object getItem(int position) {
        return mOrderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Order order = mOrderList.get(position);
        if (order == null) {
            return null;
        }
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_order, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvId.setText(order.getId() + "");
        holder.tvName.setText(order.getCustomName());
        holder.tvPrice.setText(order.getOrderPrice() + "");
        holder.tvCountry.setText(order.getCountry());
        return convertView;
    }

    static class ViewHolder {
        TextView tvId;
        TextView tvName;
        TextView tvPrice;
        TextView tvCountry;

        public ViewHolder(View itemView) {
            tvId = itemView.findViewById(R.id.dateIdTextView);
            tvName = itemView.findViewById(R.id.dateCustomTextView);
            tvPrice = itemView.findViewById(R.id.dateOrderPriceTextView);
            tvCountry = itemView.findViewById(R.id.dateCountoryTextView);
        }
    }
}
