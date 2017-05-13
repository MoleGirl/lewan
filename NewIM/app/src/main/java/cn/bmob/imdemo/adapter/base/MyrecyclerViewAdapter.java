package cn.bmob.imdemo.adapter.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.List;

import cn.bmob.imdemo.R;
import cn.bmob.imdemo.bean.Product;
import cn.bmob.imdemo.ui.activity.DetailsActivity;
import cn.bmob.imdemo.util.OnItemClickListener;


/**
 * Created by Administrator on 2016/12/27.
 */

public class MyrecyclerViewAdapter extends RecyclerView.Adapter<MyViewHolder> {


    private Context context;
    private LayoutInflater inflater;
    private List<Product> datas;

    private String[] letters;//热门城市
    private MyViewHolder holder;
    int len = 0;

    //用来注册观察者的位置
    private OnItemClickListener listener;
    public void setOnItemCickListener( OnItemClickListener listener){
        this.listener = listener;
    }

    public MyrecyclerViewAdapter(Context context, List<Product> datas) {
        len = 1;
        this.context = context;
        this.datas = datas;
        inflater = LayoutInflater.from(context);
    }

    public MyrecyclerViewAdapter(Context context, String[] letters) {
        len = 2;
        this.context = context;
        this.letters = letters;
        inflater = LayoutInflater.from(context);
    }

    //设置数据
    public void SetDatas(List<Product> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        if (len == 1) {
            /**
             * 布局
             * 根节点
             */
            View view = inflater.inflate(R.layout.recycler_item, viewGroup, false);
            holder = new MyViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Product product = datas.get(i);
                    Intent intent = new Intent(context, DetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Product", product);
                    intent.putExtras(bundle);
                    context.startActivity(intent);

                }
            });
        } else if (len == 2) {
            View view = inflater.inflate(R.layout.recycler_address_item, viewGroup, false);
            holder = new MyViewHolder(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, final int i) {
        ViewGroup.LayoutParams para = viewHolder.itemView.getLayoutParams();
        if (len == 1) {
            /**
             * 获取绑定对象
             */

            para.width = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth() / 2 - 20;
            //para.height=((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight()/3;
            viewHolder.itemView.setLayoutParams(para);
            viewHolder.name.setText(datas.get(i).getName());
            viewHolder.price.setText(datas.get(i).getPrice());
        } else if (len == 2) {
            viewHolder.city.setText(letters[i]);
            viewHolder.city.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Activity activity = (Activity)context;
                    Intent intent = new Intent();
                    intent.putExtra("add", letters[i]);
                    activity.setResult(105, intent);
                    activity.finish();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (len == 1) {
            size = 4;
        } else if (len == 2) {
            size = letters.length;
        }
        return size;
    }


}

/**
 * 继承系统提供的ViewHolder 用来给RecyclerView进行布局的优化与重用
 */
class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public TextView price;


    public TextView city;

    public MyViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.re_name);
        price = (TextView) itemView.findViewById(R.id.re_price);
        city = (TextView) itemView.findViewById(R.id.city);
    }

}