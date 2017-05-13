package cn.bmob.imdemo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import cn.bmob.imdemo.BmobIMApplication;
import cn.bmob.imdemo.R;
import cn.bmob.imdemo.bean.Needs;

/**
 * Created by Administrator on 2017/1/3.
 */

public class DiscoverFragmentListAdapter extends android.widget.BaseAdapter {


    private Context context;
    private List<Needs> needs;
    private LayoutInflater inflater;
    private String[] addss;
    //4全部(更多)需求
    private int len = 0;
    private int id[];
    private String classificationText[];


    public void setDatas(List<Needs> datas) {
        this.needs = datas;
    }


    public DiscoverFragmentListAdapter(Context context, List<Needs> needs) {
        len = 1;
        this.context = context;
        this.needs = needs;
        inflater = LayoutInflater.from(context);
    }

    public DiscoverFragmentListAdapter(Context context, List<Needs> needs, int len) {
        this.len = len;
        this.context = context;
        this.needs = needs;
        inflater = LayoutInflater.from(context);
    }

    public DiscoverFragmentListAdapter(Context context, String[] addss) {
        len = 2;
        this.context = context;
        this.addss = addss;
        inflater = LayoutInflater.from(context);
    }

    public DiscoverFragmentListAdapter(Context context, int[] id, String[] classificationText) {
        len = 3;
        this.context = context;
        this.id = id;
        this.classificationText = classificationText;
        inflater = LayoutInflater.from(context);
    }

    //设置数据
    public void SetNeeds(List<Needs> needs) {
        this.needs = needs;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        int size = 0;
        if (len == 1) {
            if (needs.size() < 4) {
                size = needs.size();
            } else {
                size = 4;
            }
        } else if (len == 2) {
            size = addss.length;
        } else if (len == 3) {
            size = id.length;
        } else if (len == 4) {
            size = needs.size();
        }
        return size;
    }

    @Override
    public Object getItem(int position) {
        Object object = null;
        if (len == 1) {
            object = needs.get(position);
        } else if (len == 2) {
            object = addss[position];
        } else if (len == 3) {
            object = id[position];
        } else if (len == 4) {
            object = needs.get(position);
        }
        return object;
    }

    @Override
    public long getItemId(int position) {
        if (len == 1) {
            position = needs.size();
        } else if (len == 2) {
            position = addss.length;
        } else if (len == 3) {
            position = id.length;
        } else if (len == 4) {
            position = needs.size();
        }
        return position;
    }

    @Override
    public View getView(final int position, View v, ViewGroup parent) {
        ViewHolder vh = null;
        if (len == 1) {
            if (v == null) {
                //填充一个布局
                v = inflater.inflate(R.layout.dis_fra_list_item, null);
                vh = new ViewHolder();
                vh.mList_user_name = (TextView) v.findViewById(R.id.list_user_name);
                vh.mList_user_address = (TextView) v.findViewById(R.id.list_user_address);
                vh.mList_need_name = (TextView) v.findViewById(R.id.list_need_name);
                vh.mList_need = (TextView) v.findViewById(R.id.list_need);
                vh.mDis_img = (SimpleDraweeView) v.findViewById(R.id.dis_img);
                v.setTag(vh);
            } else {
                vh = (ViewHolder) v.getTag();
            }
            if (BmobIMApplication.users.size() > 0) {
                for (int i = 0; i < BmobIMApplication.users.size(); i++) {
                    if (BmobIMApplication.users.get(i).getUsername().equals(needs.get(position).getUsername())) {
                        if (BmobIMApplication.users.get(i).getHand() != null) {
                            Glide.with(context).load(BmobIMApplication.users.get(i).getHand().getUrl()).centerCrop().into(vh.mDis_img);
                        } else {
                            Glide.with(context).load("http://bmob-cdn-9396.b0.upaiyun.com/2017/03/11/1f07fe1b409642f7804a7cd16a113522.png").centerCrop().into(vh.mDis_img);
                        }
                    }
                }
            }
            vh.mList_user_name.setText(needs.get(position).getUsername());
            vh.mList_user_address.setText(needs.get(position).getNeed_address());
            vh.mList_need_name.setText(needs.get(position).getNeed_title());
            vh.mList_need.setText(needs.get(position).getNeed_details());


        } else if (len == 2) {
            if (v == null) {
                //填充一个布局
                v = inflater.inflate(R.layout.address_item, null);
                vh = new ViewHolder();

                vh.mAddress_item_city_name = (TextView) v.findViewById(R.id.address_item_city_name);
                v.setTag(vh);
            } else {
                vh = (ViewHolder) v.getTag();
            }
            vh.mAddress_item_city_name.setText(addss[position].toString());
            vh.mAddress_item_city_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Activity act = (Activity) context;
                    Intent intent = new Intent();
                    intent.putExtra("add", addss[position].toString());
                    Toast.makeText(act, "点击了:" + addss[position].toString(), Toast.LENGTH_SHORT).show();
                    act.setResult(105, intent);
                    act.finish();
                }
            });

        } else if (len == 3) {
            if (v == null) {
                //填充一个布局
                v = inflater.inflate(R.layout.search_item, null);
                vh = new ViewHolder();
                vh.mSearch_item_text = (TextView) v.findViewById(R.id.search_item_text);
                vh.mSearch_item_img = (ImageView) v.findViewById(R.id.search_item_img);
                v.setTag(vh);
            } else {
                vh = (ViewHolder) v.getTag();
            }
            vh.mSearch_item_img.setImageResource(id[position]);
            vh.mSearch_item_text.setText(classificationText[position].toString());
        } else if (len == 4) {
            if (v == null) {
                //填充一个布局
                v = inflater.inflate(R.layout.dis_fra_list_item, null);
                vh = new ViewHolder();
                vh.mDis_img = (SimpleDraweeView) v.findViewById(R.id.dis_img);
                vh.mList_user_name = (TextView) v.findViewById(R.id.list_user_name);
                vh.mList_user_address = (TextView) v.findViewById(R.id.list_user_address);
                vh.mList_need_name = (TextView) v.findViewById(R.id.list_need_name);
                vh.mList_need = (TextView) v.findViewById(R.id.list_need);
                v.setTag(vh);
            } else {
                vh = (ViewHolder) v.getTag();
            }
//            if (needs.get(position).getUsername().equals(UserModel.getInstance().getCurrentUser().getUsername()) ){
//                if (UserModel.getInstance().getCurrentUser().getHand() != null){
//                    Glide.with(context).load(UserModel.getInstance().getCurrentUser().getHand().getUrl()).centerCrop().into(vh.mDis_img);
//                }else {
//                    Glide.with(context).load("http://bmob-cdn-9396.b0.upaiyun.com/2017/03/11/1f07fe1b409642f7804a7cd16a113522.png").centerCrop().into(vh.mDis_img);
//                }
//            } else {
            if (BmobIMApplication.users.size() > 0) {
                for (int i = 0; i < BmobIMApplication.users.size(); i++) {
                    if (BmobIMApplication.users.get(i).getUsername().equals(needs.get(position).getUsername())) {
                        if (BmobIMApplication.users.get(i).getHand() != null) {
                            Glide.with(context).load(BmobIMApplication.users.get(i).getHand().getUrl()).centerCrop().into(vh.mDis_img);
                        } else {
                            Glide.with(context).load("http://bmob-cdn-9396.b0.upaiyun.com/2017/03/11/1f07fe1b409642f7804a7cd16a113522.png").centerCrop().into(vh.mDis_img);
                        }
                    }
                }
            }
            vh.mList_user_name.setText(needs.get(position).getUsername());
            vh.mList_user_address.setText(needs.get(position).getNeed_address());
            vh.mList_need_name.setText(needs.get(position).getNeed_title());
            vh.mList_need.setText(needs.get(position).getNeed_details());
        }
        return v;
    }

    class ViewHolder {

        TextView mList_user_name, mList_user_address, mList_need_name, mList_need,
                mAddress_item_city_name,
                mSearch_item_text;
        ImageView mSearch_item_img;
        SimpleDraweeView mDis_img;
    }


}
