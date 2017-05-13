package cn.bmob.imdemo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.imdemo.BmobIMApplication;
import cn.bmob.imdemo.R;
import cn.bmob.imdemo.adapter.DiscoverFragmentListAdapter;
import cn.bmob.imdemo.base.ParentWithNaviActivity;
import cn.bmob.imdemo.bean.Needs;
import cn.bmob.imdemo.util.FindUtils;


/**
 * Created by Administrator on 2017/1/9.
 */

public class ListNeedActivity extends ParentWithNaviActivity {
    //private RefreshListView mList_need_all;
    private List<Needs> needs_list = new ArrayList<>();
    DiscoverFragmentListAdapter df;
    private SwipeRefreshLayout list_needs_swrefresh;
    private ListView mList_need_all;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_need);
        initNaviView();//初始化导航条
        initView();
        initData();
        initAdapter();
        //0195ff
    }


    public void initView() {
        list_needs_swrefresh = (SwipeRefreshLayout) findViewById(R.id.list_needs_swrefresh);
        mList_need_all = (ListView) findViewById(R.id.list_need_all);

    }

    private void initData() {
        needs_list = BmobIMApplication.needs_list;
    }

    private void initAdapter() {
        df = new DiscoverFragmentListAdapter(this, needs_list, 4);
        mList_need_all.setAdapter(df);
        mList_need_all.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Needs needs = needs_list.get(position);
                Intent intent = new Intent(getApplicationContext(), DetailsNeedActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Needs", needs);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        setListener();
    }

    private void setListener() {
        //设置刷新时动画的颜色，可以设置4个
        list_needs_swrefresh.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);

        list_needs_swrefresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        BmobIMApplication.product_list.clear();
                        new FindUtils().findNeeds(getApplicationContext());
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (BmobIMApplication.needs_list.size() > 0) {
                                    Toast.makeText(getApplicationContext(), "刷新成功", Toast.LENGTH_LONG).show();
                                    needs_list = new ArrayList<>();
                                    needs_list.addAll(BmobIMApplication.needs_list);
                                    df.setDatas(needs_list);
                                } else {
                                    Toast.makeText(getApplicationContext(), "没有获取到新的数据", Toast.LENGTH_LONG).show();
                                }
                                df.notifyDataSetChanged();
                                list_needs_swrefresh.setRefreshing(false);
                            }
                        }, 3000);
                    }
                }
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected String title() {
        return "全部需求";
    }
}
