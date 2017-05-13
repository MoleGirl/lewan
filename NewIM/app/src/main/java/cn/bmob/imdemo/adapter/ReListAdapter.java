package cn.bmob.imdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import cn.bmob.imdemo.R;


/**
 * Created by Administrator on 2016/12/29.
 */

public class ReListAdapter extends BaseAdapter {
    private Context context;
    private String datas[];
    private LayoutInflater inflater;

    public ReListAdapter(Context context, String datas[]) {
        this.context = context;
        this.datas = datas;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.length;
    }

    @Override
    public Object getItem(int position) {
        return datas[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        v = inflater.inflate(R.layout.list_item, null);
        TextView mList_item_name = (TextView) v.findViewById(R.id.list_item_name);
        mList_item_name.setText(datas[position].toString());
        return v;
    }
}
