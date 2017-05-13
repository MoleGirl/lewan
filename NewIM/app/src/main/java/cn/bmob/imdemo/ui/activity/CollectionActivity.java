package cn.bmob.imdemo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.bmob.imdemo.BmobIMApplication;
import cn.bmob.imdemo.R;
import cn.bmob.imdemo.adapter.DiscoverFragmentListAdapter;
import cn.bmob.imdemo.adapter.More_list_adapter;
import cn.bmob.imdemo.base.ParentWithNaviActivity;
import cn.bmob.imdemo.bean.Needs;
import cn.bmob.imdemo.bean.Product;
import cn.bmob.imdemo.model.UserModel;

/**
 * Created by Administrator on 2017/3/2.
 */

public class CollectionActivity extends ParentWithNaviActivity implements View.OnClickListener{
    @Bind(R.id.my_collection_prodect)
    TextView my_collection_prodect;
    @Bind(R.id.my_collection_needs)
    TextView my_collection_needs;
    @Bind(R.id.my_collection_list)
    ListView my_collection_list;
    Intent intent;
    String title;

    private DiscoverFragmentListAdapter disAdapter;
    private More_list_adapter proAdapter;
    private List<Product> productCollections = new ArrayList<>();//收藏的产品
    private List<Needs> needCollections = new ArrayList<>();//收藏的需求

    private List<Product> product_release = new ArrayList<>();//发布的产品
    private List<Needs> needs_release = new ArrayList<>();//发布的需求

    @Override
    protected String title() {
        return title;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        initNaviView();
        initView();
        initdata();
        initAdapter();
    }

    public void initView() {
        intent = getIntent();
        String string = intent.getStringExtra("title");
        if (string.equals("我的收藏")){
            title = "我的收藏";
        }else if (string.equals("我的发布")){
            title = "我的发布";
        }


        my_collection_prodect.setTextColor(0xff4f9ef6);
        my_collection_prodect.setOnClickListener(this);
        my_collection_needs.setOnClickListener(this);

    }
    private void initdata() {

//        needCollections = BmobIMApplication.needCollections;
//        for (int i = 0; i <BmobIMApplication.productCollections.size() ; i++) {
//            Log.e("调用时", BmobIMApplication.productCollections.get(i).getObjectId());
//        }
//
//        productCollections = BmobIMApplication.productCollections;
        //打开应用没有收藏过产品
        if (BmobIMApplication.productCollections.size()==0){
            for (int i = 0; i < UserModel.getInstance().getCurrentUser().getProduct_collection().size(); i++) {
                for (int j = 0; j < BmobIMApplication.product_list.size(); j++) {
                    if (UserModel.getInstance().getCurrentUser().getProduct_collection().get(i)
                            .equals(BmobIMApplication.product_list.get(j).getObjectId())){
                        productCollections.add(BmobIMApplication.product_list.get(j));
                    }
                }
            }
        }else {//打开应用收藏过产品
            productCollections.addAll(BmobIMApplication.productCollections);
            for (int i = 0; i < UserModel.getInstance().getCurrentUser().getProduct_collection().size(); i++) {
                for (int j = 0; j < BmobIMApplication.product_list.size(); j++) {
                    if (UserModel.getInstance().getCurrentUser().getProduct_collection().get(i)
                            .equals(BmobIMApplication.product_list.get(j).getObjectId())){
                        productCollections.add(BmobIMApplication.product_list.get(j));
                    }
                }
            }
        }

        //打开应用没有收藏过需求
        if (BmobIMApplication.needCollections.size()==0){
            for (int i = 0; i < UserModel.getInstance().getCurrentUser().getNeeds_collection().size(); i++) {
                for (int j = 0; j < BmobIMApplication.needs_list.size(); j++) {
                    if (UserModel.getInstance().getCurrentUser().getNeeds_collection().get(i)
                            .equals(BmobIMApplication.needs_list.get(j).getObjectId())){
                        needCollections.add(BmobIMApplication.needs_list.get(j));
                    }
                }
            }
        }else {//打开应用收藏过需求
            needCollections.addAll(BmobIMApplication.needs_release);
            for (int i = 0; i < UserModel.getInstance().getCurrentUser().getNeeds_collection().size(); i++) {
                for (int j = 0; j < BmobIMApplication.needs_list.size(); j++) {
                    if (UserModel.getInstance().getCurrentUser().getNeeds_collection().get(i)
                            .equals(BmobIMApplication.needs_list.get(j).getObjectId())){
                        needCollections.add(BmobIMApplication.needs_list.get(j));
                    }
                }
            }
        }





        //打开应用后没有发布产品
        if (BmobIMApplication.product_release.size()==0){
            //发布的产品
            for (int j = 0; j < BmobIMApplication.product_list.size(); j++) {
                if (BmobIMApplication.product_list.get(j).getUsername().equals(UserModel.getInstance().getCurrentUser().getUsername())){
                    product_release.add(BmobIMApplication.product_list.get(j));
                }
            }
        }else {//打开应用后发布过产品
            product_release.addAll(BmobIMApplication.product_release);//添加本地产品
            for (int j = 0; j < BmobIMApplication.product_list.size(); j++) {
                if (BmobIMApplication.product_list.get(j).getUsername().equals(UserModel.getInstance().getCurrentUser().getUsername())){
                    product_release.add(BmobIMApplication.product_list.get(j));//添加数据库产品
                }
            }

        }

        //打开应用后没有发布需求
        if (BmobIMApplication.needs_release.size()==0){
            //发布的产品
            for (int j = 0; j < BmobIMApplication.needs_list.size(); j++) {
                if (BmobIMApplication.needs_list.get(j).getUsername().equals(UserModel.getInstance().getCurrentUser().getUsername())){
                    needs_release.add(BmobIMApplication.needs_list.get(j));
                }
            }
        }else {//打开应用后发布过需求
            needs_release.addAll(BmobIMApplication.needs_release);//添加本地产品
            for (int j = 0; j < BmobIMApplication.needs_list.size(); j++) {
                if (BmobIMApplication.needs_list.get(j).getUsername().equals(UserModel.getInstance().getCurrentUser().getUsername())){
                    needs_release.add(BmobIMApplication.needs_list.get(j));//添加数据库产品
                }
            }

        }
    }
    private void initAdapter() {
        if (title.equals("我的收藏")){
            proAdapter = new More_list_adapter(this, productCollections);
            my_collection_list.setAdapter(proAdapter);
            my_collection_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Product", productCollections.get(position));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });

        }else if (title.equals("我的发布")) {
            proAdapter = new More_list_adapter(this, product_release);
            my_collection_list.setAdapter(proAdapter);
            my_collection_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Product", product_release.get(position));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });

            //disAdapter = new DiscoverFragmentListAdapter(this, needCollections);
            //my_collection_list.setAdapter(disAdapter);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.my_collection_prodect:
                my_collection_prodect.setTextColor(0xff4f9ef6);
                my_collection_needs.setTextColor(0xff000000);
                if (title.equals("我的收藏")){
                    proAdapter = new More_list_adapter(this, productCollections);
                    my_collection_list.setAdapter(proAdapter);
                    my_collection_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("Product", productCollections.get(position));
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                }else if (title.equals("我的发布")){
                    proAdapter = new More_list_adapter(this, product_release);
                    my_collection_list.setAdapter(proAdapter);
                    my_collection_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("Product", product_release.get(position));
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                }
                proAdapter.notifyDataSetChanged();
                break;
            case R.id.my_collection_needs:
                my_collection_prodect.setTextColor(0xff000000);
                my_collection_needs.setTextColor(0xff4f9ef6);
                if (title.equals("我的收藏")){
                    disAdapter = new DiscoverFragmentListAdapter(this, needCollections);
                    my_collection_list.setAdapter(disAdapter);
                    my_collection_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Needs needs = needCollections.get(position);
                            Intent intent = new Intent(getApplicationContext(), DetailsNeedActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("Needs", needs);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                }else if (title.equals("我的发布")){
                    disAdapter = new DiscoverFragmentListAdapter(this, needs_release);
                    my_collection_list.setAdapter(disAdapter);
                    my_collection_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Needs needs = needs_release.get(position);
                            Intent intent = new Intent(getApplicationContext(), DetailsNeedActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("Needs", needs);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                }

                disAdapter.notifyDataSetChanged();
                break;

        }
    }
}
