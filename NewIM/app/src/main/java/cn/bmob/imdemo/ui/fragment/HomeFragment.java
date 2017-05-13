package cn.bmob.imdemo.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.imdemo.BmobIMApplication;
import cn.bmob.imdemo.R;
import cn.bmob.imdemo.adapter.DiscoverFragmentListAdapter;
import cn.bmob.imdemo.adapter.ImageAdapter;
import cn.bmob.imdemo.adapter.base.MyrecyclerViewAdapter;
import cn.bmob.imdemo.bean.Needs;
import cn.bmob.imdemo.bean.Product;
import cn.bmob.imdemo.ui.activity.DetailsActivity;
import cn.bmob.imdemo.ui.activity.DetailsNeedActivity;
import cn.bmob.imdemo.ui.activity.ListDisplayActivity;
import cn.bmob.imdemo.ui.activity.ListNeedActivity;
import cn.bmob.imdemo.ui.activity.SearchActivity;

/**
 * Created by Administrator on 2017/2/9.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {

    private LinearLayout mSsibao_more, mSibao_more_need;
    private TextView tv_desc;
    //private RecyclerView mRecycl;
    private MyrecyclerViewAdapter mva;
    private DiscoverFragmentListAdapter dfla;


    private List<Product> product_list = null;
    private List<Needs> needs_list = null;
    //private List<Advert> advert_list = null;
    private List<Product> product_advert = null;

    private ListView mDiscoverFragment_list;

    private TextView mHome_search;

    private TextView mAct_address;
    private AMapLocation location;
    String address = null;


    //private SwipeRefreshLayout home_sw_refresh;
    private List<String> listUri;
    private RollPagerView mRollViewPager;
    private GridView fragment_home_gridview;
    private ImageAdapter gridviewAdapter;
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        //initNaviView();初始化导航条
        //ButterKnife.bind(this, rootView);
        initViews();
        initData();
        initAdapter();
        return rootView;
    }

    private void initViews() {
        tv_desc = (TextView) rootView.findViewById(R.id.tv_desc);
        //mRecycl = (RecyclerView) rootView.findViewById(R.id.fra_recl);
        mSsibao_more = (LinearLayout) rootView.findViewById(R.id.sibao_more);
        mSibao_more_need = (LinearLayout) rootView.findViewById(R.id.sibao_more_need);
        mDiscoverFragment_list = (ListView) rootView.findViewById(R.id.discoverFragment_list);
        //home_sw_refresh = (SwipeRefreshLayout) rootView.findViewById(R.id.homefragment_sw_refresh);
        mRollViewPager = (RollPagerView) rootView.findViewById(R.id.roll_view_pager);
        mHome_search = (TextView) rootView.findViewById(R.id.home_search);
        mAct_address = (TextView) rootView.findViewById(R.id.act_address);
        fragment_home_gridview = (GridView) rootView.findViewById(R.id.fragment_home_gridview);


        mHome_search.setOnClickListener(this);
        mAct_address.setOnClickListener(this);
        mSsibao_more.setOnClickListener(this);
        mSibao_more_need.setOnClickListener(this);

        if (BmobIMApplication.amapLocations.size() > 0) {
            location = BmobIMApplication.amapLocations.get(0);
            Toast.makeText(getActivity(), "当前的定位结果:位置" + location.getCity(), Toast.LENGTH_SHORT).show();
            address = location.getCity().substring(0, location.getCity().length() - 1);
            mAct_address.setText(address);
        } else {
            //Toast.makeText(getActivity(), "定位失败", Toast.LENGTH_SHORT).show();
        }


    }


    private void initData() {
        //product_list = BmobIMApplication.product_list;//产品
        product_list = new ArrayList<>();//产品
        needs_list = BmobIMApplication.needs_list;//需求
        product_advert = BmobIMApplication.product_advert;//轮播
        listUri = new ArrayList<>();
        //头像
        for(int i = 0 ; i < product_advert.size() ; i++) {
            listUri.add(product_advert.get(i).getImgs().get(0).getUrl());
        }
        //推荐产品展示
        for (int j = 0; j <BmobIMApplication.product_list.size() ; j++) {
           if (BmobIMApplication.product_list.get(j).getRecommend()){
               product_list.add(BmobIMApplication.product_list.get(j));
               if (product_list.size()==4){
                   break;
               }
           }
        }

    }

    private void initAdapter() {
        //设置播放时间间隔
         mRollViewPager.setPlayDelay(3000);
//        //设置透明度
         mRollViewPager.setAnimationDurtion(500);
//        //设置适配器
         mRollViewPager.setAdapter(new TestLoopAdapter(mRollViewPager));
//        //设置指示器（顺序依次）
//        //自定义指示器图片
//        //设置圆点指示器颜色
//        //设置文字指示器
//        //隐藏指示器
//        //mRollViewPager.setHintView(new IconHintView(this, R.drawable.point_focus, R.drawable.point_normal));
         mRollViewPager.setHintView(new ColorPointHintView(getContext(), Color.YELLOW,Color.WHITE));
//        //mRollViewPager.setHintView(new TextHintView(this));
//        //mRollViewPager.setHintView(null);
        mRollViewPager.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getContext(),"Item "+position+" clicked",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), DetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Product", product_advert.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        //首页产品推荐
        gridviewAdapter= new ImageAdapter(getContext(), product_list);
        fragment_home_gridview.setAdapter(gridviewAdapter);
        fragment_home_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), DetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Product", product_list.get(position));
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });


//        FullyGridLayoutManager manager = new FullyGridLayoutManager(getActivity(), 2);
//        manager.setOrientation(GridLayoutManager.VERTICAL);
//        manager.setSmoothScrollbarEnabled(true);
//        mRecycl.setLayoutManager(manager);
//        mva = new MyrecyclerViewAdapter(getActivity(), product_list);
//        mRecycl.setAdapter(mva);

        //首页需求
        dfla = new DiscoverFragmentListAdapter(getActivity(), needs_list);
        mDiscoverFragment_list.setAdapter(dfla);
        mDiscoverFragment_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Needs needs = needs_list.get(position);
                Intent intent = new Intent(getContext(), DetailsNeedActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Needs", needs);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        //sw_refresh.setEnabled(true);//控件可用
        //setListener();

    }

    private void setListener() {
        //设置刷新时动画的颜色，可以设置4个
//        home_sw_refresh.setColorSchemeResources(
//                android.R.color.holo_blue_light,
//                android.R.color.holo_red_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_green_light);
//        home_sw_refresh.setOnRefreshListener(
//                new SwipeRefreshLayout.OnRefreshListener() {
//                    @Override
//                    public void onRefresh() {
//                        BmobIMApplication.product_list.clear();
//                        BmobIMApplication.needs_list.clear();
//                        new FindUtils().findProduct(getContext());
//                        new FindUtils().findNeeds(getContext());
//                        new Thread() {
//                            @Override
//                            public void run() {
//                                try {
//                                    Thread.sleep(3000);
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
//                                Message msg = mHandler.obtainMessage();
//                                msg.what = 1;
//                                mHandler.sendMessage(msg);
//                            }
//                        }.start();
//
//                    }
//                }
//        );
    }
    private class TestLoopAdapter extends LoopPagerAdapter {
        public TestLoopAdapter(RollPagerView viewPager) {
            super(viewPager);
        }
        @Override
        public View getView(ViewGroup container, int position) {
            SimpleDraweeView simpleDraweeView=new SimpleDraweeView(container.getContext());
            simpleDraweeView.setImageURI(Uri.parse(listUri.get(position)));
            simpleDraweeView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            simpleDraweeView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return simpleDraweeView;
        }

        @Override
        public int getRealCount() {
            return listUri.size();
        }
    }
//    private Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case 1:
//                    if (BmobIMApplication.product_list.size()!=0&&BmobIMApplication.needs_list.size()!=0){
//                        Toast.makeText(getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
//                        product_list.addAll(BmobIMApplication.product_list);
//                        needs_list.addAll(BmobIMApplication.needs_list);
//
//
//                        //mva.SetDatas(BmobIMApplication.product_list);
//                        dfla.SetNeeds(BmobIMApplication.needs_list);
//                        //mva.notifyDataSetChanged();
//                        dfla.notifyDataSetChanged();
//                    }else {
//                        Toast.makeText(getContext(), "请检查网络", Toast.LENGTH_SHORT).show();
//                    }
//                    home_sw_refresh.setRefreshing(false);
//                    break;
//
//            }
//
//        }
//    };


    @Override
    public void onResume() {
        super.onResume();
        //sw_refresh.setRefreshing(true);//自动开始刷新
        //query();
    }

//    @Override
//    protected String title() {
//        return "首页";
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_search:
                startActivity(new Intent(getContext(), SearchActivity.class));
                break;
            case R.id.act_address:
                //startActivityForResult(new Intent(getContext(), AddressActivity.class), 1);
                Toast.makeText(getContext(),"当前位置",Toast.LENGTH_SHORT).show();
                break;
            //产品更多
            case R.id.sibao_more:
                startActivity(new Intent(getContext(), ListDisplayActivity.class));
                break;
            //需求更多
            case R.id.sibao_more_need:
                startActivity(new Intent(getContext(), ListNeedActivity.class));
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == 102 || resultCode == 103|| resultCode == 104||resultCode == 105) {
                Bundle bundle = data.getExtras();
                String str = bundle.getString("add");
                Toast.makeText(getContext(), "收到返回结果" + str, Toast.LENGTH_SHORT).show();
                if (!mAct_address.getText().toString().trim().equals(str)) {
                    mAct_address.setText(str);
                }
            }
        }
    }
}
