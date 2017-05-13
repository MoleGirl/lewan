package cn.bmob.imdemo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
//import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import cn.bmob.imdemo.R;
import cn.bmob.imdemo.bean.Product;


/**
 * Created by Administrator on 2017/2/16.
 */

public class ImageAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private String name_datas[];
    private int isClick;
    private List<Product> product_list;
    private List<String> path;
    //1产品更多导航2首页产品3发布页4定位热门城市5产品更多页
    int len = 0;


    public void setSeclection(int isClick) {
        this.isClick = isClick;
    }

    public ImageAdapter(Context context, String name_datas[], int len) {
        this.len = len;
        this.context = context;
        this.name_datas = name_datas;
        inflater = LayoutInflater.from(context);
    }

    public ImageAdapter(Context context, List<Product> product_list) {
        len = 2;
        this.context = context;
        this.product_list = product_list;
        inflater = LayoutInflater.from(context);
    }


    public ImageAdapter(Context context, List<String> path, int len) {
        this.len = len;
        this.context = context;
        this.path = path;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        int size = 0;
        if (len == 1) {
            size = name_datas.length;
        } else if (len == 2) {
            if (product_list.size() < 4) {
                size = product_list.size();
            } else {
                size = 4;
            }
        } else if (len == 3) {
            size = path.size();
        } else if (len == 4) {
            size = name_datas.length;
        }
        return size;
    }

    @Override
    public Object getItem(int position) {
        Object o = null;
        if (len == 1) {
            o = name_datas[position];
        } else if (len == 2) {
            o = product_list.get(position);
        } else if (len == 3) {
            o = path.get(position);
        } else if (len == 4) {
            o = name_datas[position];
        }
        return o;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if (len == 1) {
            v = inflater.inflate(R.layout.gridview_tv, null);
            TextView mGrid_tv = (TextView) v.findViewById(R.id.grid_tv);
            mGrid_tv.setText(name_datas[position]);
            if (isClick == position) {
                mGrid_tv.setTextColor(Color.RED);
                mGrid_tv.setTextSize(16);
            } else {
                mGrid_tv.setTextColor(Color.BLACK);
                mGrid_tv.setTextSize(12);
            }
        } else if (len == 2) {
            v = inflater.inflate(R.layout.recycler_item, null);
            TextView name = (TextView) v.findViewById(R.id.re_name);
            TextView price = (TextView) v.findViewById(R.id.re_price);
            SimpleDraweeView imageView = (SimpleDraweeView) v.findViewById(R.id.re_imageView);
            //ImageView imageView = (ImageView) v.findViewById(R.id.re_imageView);
            TextView address = (TextView) v.findViewById(R.id.re_address);

            name.setText(product_list.get(position).getName().toString());
            price.setText(product_list.get(position).getPrice().toString());
            address.setText(product_list.get(position).getAddress().toString());
            imageView.setImageURI(product_list.get(position).getImgs().get(0).getUrl());

        } else if (len == 3) {
            v = inflater.inflate(R.layout.image, null);
            ImageView image = (ImageView) v.findViewById(R.id.photo_imageview);
            Glide.with(context)
                    .load(path.get(position))
                    .centerCrop()
                    .into(image);

        } else if (len == 4) {
            v = inflater.inflate(R.layout.recycler_address_item, null);
            TextView city = (TextView) v.findViewById(R.id.city);
            city.setText(name_datas[position]);
        }


        return v;
    }
}
