package com.sz.leo.androidnote.chapter04;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.PixelCopy;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sz.leo.androidnote.R;

import java.util.List;

/**
 * @author：leo
 * @date：2019/5/24
 * @email：lei.lu@e-at.com
 */
public class MyAdapter extends BaseAdapter {
    private Context context;
    private List<String> data;

    public MyAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String str = data.get(position);
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_data, null);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvContent.setText(str);
        holder.line.setVisibility(position != data.size() - 1 ? View.VISIBLE : View.GONE);
        return convertView;
    }

    class ViewHolder {
        TextView tvContent;
        View line;

        public ViewHolder(View itemView) {
            tvContent = itemView.findViewById(R.id.tv_content);
            line = itemView.findViewById(R.id.divider);
        }
    }
}
