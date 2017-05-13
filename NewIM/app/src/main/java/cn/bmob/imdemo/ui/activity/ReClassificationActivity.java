package cn.bmob.imdemo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import butterknife.Bind;
import cn.bmob.imdemo.R;
import cn.bmob.imdemo.adapter.ReListAdapter;
import cn.bmob.imdemo.base.ParentWithNaviActivity;

public class ReClassificationActivity extends ParentWithNaviActivity {

    private ListView mAll_list;
    private String mClassification_lists[] = {"编织网", "焊接类", "冲拉类", "护栏网", "边坡防护网",
            "尼龙塑料网", "窗纱网", "装饰网", "筛网/筛具/过滤器", "养殖笼具", "加工服务/深加工",
            "立柱", "线材", "板材", "非织造", "刺/刺网/刺管", "丝网机械"};
    private String mSurface_lists[] = {"冷镀锌", "电镀锌", "改拨", "浸塑", "喷塑", "涂塑",
            "酸洗", "抛光", "静电喷涂"};
    private String mState_lists[] = {"现货", "排产", "定做"};
    private String mRelease_lists[] = {"大量现货", "少量现货", "随时定做"};
    private String mMeasurement_lists[] = {"张", "套", "米", "平米", "吨", "公斤", "千克", "卷", "件"};

    public final static int RESULT_CODE = 2;
    private String title=null;
    @Bind(R.id.tv_left)
    ImageView tv_left;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.re_classification);
        initNaviView();
        tv_left.setVisibility(View.INVISIBLE);
        initView();
        initData();

    }


    protected void initView() {
        mAll_list = (ListView) findViewById(R.id.all_list);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String str = bundle.getString("str");

        if (str.equals("分类")) {
            title="产品分类";
            listViewShow(1,mClassification_lists);
        } else if (str.equals("表面")) {
            title="产品表面处理";
            listViewShow(2,mSurface_lists);


        } else if (str.equals("状态")) {
            title="产品状态";
            listViewShow(3,mState_lists);

        } else if (str.equals("单位")) {
            title="产品计量单位";
            listViewShow(4,mMeasurement_lists);

        }else if (str.equals("数量")) {
            title="产品数量";
            listViewShow(5,mRelease_lists);

        }
    }
    private void listViewShow(final int i,final String[] datas) {
        mAll_list.setAdapter(new ReListAdapter(ReClassificationActivity.this, datas));
        mAll_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("back", datas[position]);
                setResult(i, intent);
                finish();
            }
        });

    }

    private void initData() {
    }

    @Override
    protected String title() {
        return title;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //返回键,event.getRepeatCount()重复次数
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Toast.makeText(this,"请选择分类",Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
