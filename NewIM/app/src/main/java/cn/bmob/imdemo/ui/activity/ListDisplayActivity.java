package cn.bmob.imdemo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import cn.bmob.imdemo.BmobIMApplication;
import cn.bmob.imdemo.R;
import cn.bmob.imdemo.adapter.ImageAdapter;
import cn.bmob.imdemo.adapter.More_list_adapter;
import cn.bmob.imdemo.base.ParentWithNaviActivity;
import cn.bmob.imdemo.bean.Product;
import cn.bmob.imdemo.util.FindUtils;

public class ListDisplayActivity extends ParentWithNaviActivity implements View.OnClickListener {

    private List<Product> product_list = new ArrayList<>();
    private String name_datas[] = {"全部", "立柱", "线材", "板材",
            "编织网", "焊接类", "冲拉类", "护栏网", "窗纱网", "装饰网", "非织造",
            "养殖笼具", "丝网机械", "尼龙塑料网", "边坡防护网", "加工/深加工",
            "筛网/筛具/过滤器", "刺/刺网/刺管"};
    //private RefreshListView mMore_list;
    private SwipeRefreshLayout list_display_swrefresh;
    private ListView mMore_list;
    private GridView mGrid_tv;
    private ImageAdapter mGridAdapter;
    private More_list_adapter more_list_adapter;

    @Bind(R.id.display_null)
    TextView display_null;

    int clickPosition = 0;

    private Map<List<Product>, String> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_display);
        initNaviView();//初始化导航条
        initView();
        initData();
        initAdapter();

    }


    protected void initView() {
        //mMore_list = (AutoLoadListView) findViewById(R.id.more_list);
        list_display_swrefresh = (SwipeRefreshLayout) findViewById(R.id.list_display_swrefresh);
        mMore_list = (ListView) findViewById(R.id.more_list);
        mGrid_tv = (GridView) findViewById(R.id.scroll_grid_tv);

        display_null.setOnClickListener(this);

    }

    private void initData() {
        product_list = BmobIMApplication.product_list;
    }

    private void initAdapter() {
        //产品列表
        more_list_adapter = new More_list_adapter(ListDisplayActivity.this, product_list);
        mMore_list.setAdapter(more_list_adapter);
        mMore_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = product_list.get(position);
                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Product", product);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        //导航条
        mGridAdapter = new ImageAdapter(this, name_datas, 1);
        mGrid_tv.setAdapter(mGridAdapter);
        mGrid_tv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                clickPosition = position;
                mGridAdapter.setSeclection(position);//点击变色
                mGridAdapter.notifyDataSetChanged();//刷新
                seclection(position);
            }
        });
        setListener();//刷新监听
    }


    private void setListener() {
        //设置刷新时动画的颜色，可以设置4个
        list_display_swrefresh.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);

        list_display_swrefresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        BmobIMApplication.product_list.clear();
                        new FindUtils().findProduct(getApplicationContext());
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (BmobIMApplication.product_list.size() > 0) {
                                    Toast.makeText(getApplicationContext(), "刷新成功", Toast.LENGTH_LONG).show();
                                    seclection(clickPosition);
                                } else {
                                    Toast.makeText(getApplicationContext(), "没有获取到新的数据", Toast.LENGTH_LONG).show();
                                }
                                list_display_swrefresh.setRefreshing(false);
                            }
                        }, 3000);
                    }
                }
        );
    }

    private void seclection(int position) {
        if (position != 0) {
            BmobIMApplication.product.clear();
            for (int i = 0; i < BmobIMApplication.product_list.size(); i++) {
                Log.e("*****调用了循环", name_datas[position]);
                if (BmobIMApplication.product_list.get(i).getCategory().equals(name_datas[position])) {
                    BmobIMApplication.product.add(BmobIMApplication.product_list.get(i));
                }
            }
            product_list = new ArrayList<>();
            product_list.addAll(BmobIMApplication.product);
            more_list_adapter.setDatas(product_list);
        } else {
            product_list = new ArrayList<>();
            product_list.addAll(BmobIMApplication.product_list);
            more_list_adapter.setDatas(product_list);
        }
        more_list_adapter.notifyDataSetChanged();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Toast.makeText(getApplicationContext(), "结束监听", Toast.LENGTH_SHORT).show();
                    if (clickPosition == 0) {
                        product_list = BmobIMApplication.product_list;
                        more_list_adapter.setDatas(product_list);
                    } else {
                        product_list = BmobIMApplication.product;
                        more_list_adapter.setDatas(product_list);
                    }
                    //swipeRefreshLayout.setEnabled(false);
                    break;
                case 2:
                    Toast.makeText(getApplicationContext(), "点击结束监听", Toast.LENGTH_SHORT).show();
                    //swipeRefreshLayout.setEnabled(false);
                    product_list = BmobIMApplication.product;
                    more_list_adapter.setDatas(product_list);
                    break;
                case 3:
                    if (clickPosition == 0) {
                        Toast.makeText(getApplicationContext(), "加载更多" + BmobIMApplication.product_list, Toast.LENGTH_SHORT).show();
                        product_list = BmobIMApplication.product_list;
                    } else {
                        Toast.makeText(getApplicationContext(), "加载更多" + BmobIMApplication.product, Toast.LENGTH_SHORT).show();
                        product_list = BmobIMApplication.product;
                    }

                    //swipeRefreshLayout.setEnabled(false);

                    more_list_adapter.setDatas(product_list);
                    break;
                default:
                    break;
            }
            more_list_adapter.notifyDataSetChanged();
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.display_null://加载更多
                if (clickPosition == 0) {
                    new FindUtils().findProduct(2, BmobIMApplication.product_list.size(), null, getApplicationContext());
                    //more_list_adapter.setDatas();
                } else {
                    if (product_list.size() >= 10) {
                        new FindUtils().findProduct(3, BmobIMApplication.product.size(), name_datas[clickPosition], getApplicationContext());
                    }
                }
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Message msg = mHandler.obtainMessage();
                                msg.what = 3;
                                mHandler.sendMessage(msg);
                            }
                        });
                    }
                }.start();


                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        position = App.a;
//        if (position != 100) {
//            mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
//            //mRecyclerView.setAdapter(new List_Dis_RecyclAdapter(this,name_datas));
//            adapter = new List_Dis_RecyclAdapter(this, name_datas, position);
//            mRecyclerView.setAdapter(adapter);
//        }
        //adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //App.a = 100;
    }

    @Override
    protected String title() {
        return "全部产品";
    }

    //    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Log.i("*******************", "返回了data="+data.getIntExtra("分类",1));
//        if (data != null) {
//            if (requestCode == 1) {
//                if (resultCode == 103) {
//                   position= data.getIntExtra("分类",1);
//                    Log.i("*******************", "返回了" + position);
//                    mRecyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
//                    //mRecyclerView.setAdapter(new List_Dis_RecyclAdapter(this,name_datas));
//                    adapter=new List_Dis_RecyclAdapter(this,name_datas,position);
//                    mRecyclerView.setAdapter(adapter);
//                    adapter.notifyDataSetChanged();
//                }
//            }
//        }
//
//    }
}
