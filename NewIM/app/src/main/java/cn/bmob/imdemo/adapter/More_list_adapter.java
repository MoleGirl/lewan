package cn.bmob.imdemo.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//import com.facebook.drawee.view.SimpleDraweeView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import cn.bmob.imdemo.R;
import cn.bmob.imdemo.bean.Product;


/**
 * Created by Administrator on 2016/12/28.
 */

public class More_list_adapter extends android.widget.BaseAdapter{


    private Context context;
    private LayoutInflater inflater;
    private List<Product> datas;

    public More_list_adapter(Context context, List<Product> datas) {
        this.context = context;
        this.datas = datas;
        inflater= LayoutInflater.from(context);
    }

    public void  setDatas(List<Product> datas){
        this.datas = datas;
    }


    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        ViewHolder vh=null;
        if (v==null) {
            //填充一个布局
            v=inflater.inflate(R.layout.more_item, null);
            vh=new ViewHolder();
            vh.mMore_item_name=(TextView) v.findViewById(R.id.more_item_name);
            vh.mMore_item_money=(TextView) v.findViewById(R.id.more_item_money);
            vh.more_item_img=(SimpleDraweeView) v.findViewById(R.id.more_item_img);
            v.setTag(vh);
        }else{
            vh=(ViewHolder) v.getTag();
        }
        vh.mMore_item_name.setText(datas.get(position).getName());
        vh.mMore_item_money.setText(datas.get(position).getPrice());
        vh.more_item_img.setImageURI(datas.get(position).getImgs().get(0).getUrl());
        return v;
    }
    class  ViewHolder{

        TextView mMore_item_name,mMore_item_money;
        SimpleDraweeView more_item_img;

    }

}
