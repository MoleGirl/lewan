package cn.bmob.imdemo.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.imdemo.BmobIMApplication;
import cn.bmob.imdemo.R;
import cn.bmob.imdemo.adapter.More_list_adapter;
import cn.bmob.imdemo.base.ParentWithNaviActivity;
import cn.bmob.imdemo.bean.Product;
import cn.bmob.imdemo.util.RefreshListView;

/**
 * Created by Administrator on 2017/3/9.
 */

public class ProductStateActivity extends ParentWithNaviActivity {

    private Intent intent;
    private String str;
    private RefreshListView mList_state;
    private ProgressDialog pd;
    private List<Product> listPro;
    private More_list_adapter more_list_adapter;
    private ListView mSearch_product_list;

    @Override
    protected String title() {
        return str;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_state);
        initNaviView();//初始化导航条
        initView();
        initData();
        initAdapter();

    }

    protected void initView() {
        mSearch_product_list = (ListView) findViewById(R.id.search_product_list);
        intent = this.getIntent();
        str = intent.getStringExtra("Product");
    }

    private void initData() {
        listPro = new ArrayList<>();
        if (BmobIMApplication.product_list.size() > 0) {
            for (int i = 0; i < BmobIMApplication.product_list.size(); i++) {
                if (BmobIMApplication.product_list.get(i).getCategory().equals(str)) {
                    listPro.add(BmobIMApplication.product_list.get(i));
                }
            }
        }
    }
    private void initAdapter() {
        //产品列表
        if (listPro.size() > 0) {
            more_list_adapter = new More_list_adapter(this, listPro);
            mSearch_product_list.setAdapter(more_list_adapter);
            mSearch_product_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Product product = listPro.get(position);
                    Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Product", product);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }
    }
}
