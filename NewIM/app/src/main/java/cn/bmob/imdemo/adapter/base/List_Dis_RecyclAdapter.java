package cn.bmob.imdemo.adapter.base;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.imdemo.R;

/**
 * Created by Administrator on 2016/12/29.
 */

public class List_Dis_RecyclAdapter extends RecyclerView.Adapter<MyHolder_rec> {

    private Context context;
    private LayoutInflater inflater;
    private String name_datas[];
    private List<Boolean> isClicks;



    public List_Dis_RecyclAdapter(Context context, String name_datas[]) {
        this.context = context;
        this.name_datas = name_datas;
        inflater = LayoutInflater.from(context);
        isClicks = new ArrayList<>();
        for(int i = 0;i<name_datas.length;i++){
            isClicks.add(false);
        }
    }

    @Override
    public MyHolder_rec onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_dis_rec_item, parent, false);
        MyHolder_rec holder = new MyHolder_rec(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyHolder_rec holder, final int position) {
        if (position==0){
            holder.name.setTextColor(Color.RED);
            holder.name.setTextSize(20);
        }
        if (isClicks.get(position)){
            holder.name.setTextColor(Color.RED);
            holder.name.setTextSize(20);
        }

        holder.name.setText(name_datas[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i <isClicks.size();i++){
                    isClicks.set(i,false);
                }
                isClicks.set(position,true);
                notifyDataSetChanged();
                Toast.makeText(context, "点击了" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });


    }
    @Override
    public int getItemCount() {
        return name_datas.length;
    }
}

/**
 * 继承系统提供的ViewHolder 用来给RecyclerView进行布局的优化与重用
 */
class MyHolder_rec extends RecyclerView.ViewHolder{
    public TextView name;

    public MyHolder_rec(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.list_item_rec_name);
    }

}
