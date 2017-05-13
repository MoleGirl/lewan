package cn.bmob.imdemo.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.imdemo.R;
import cn.bmob.imdemo.adapter.DiscoverFragmentListAdapter;
import cn.bmob.imdemo.base.BaseActivity;

/**
 * Created by Administrator on 2017/1/9.
 */

public class SearchActivity extends BaseActivity implements View.OnClickListener {


    private ImageView mAc_back, mSearch_img;
    private TextView mSearch_text, mJieguo;
    private EditText mAc_search;
    private ListView mSearch_list, mSearch_list_jieguo;
    private LinearLayout mSearch_linear;
    private int id[] = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e,
            R.drawable.f, R.drawable.g, R.drawable.h, R.drawable.i, R.drawable.g, R.drawable.k,
            R.drawable.l, R.drawable.m, R.drawable.n, R.drawable.o, R.drawable.p, R.drawable.q};
    int id_jieguo[] = null;
    String classificationText_jieguo[];
    private String classificationText[] = {"编织类", "焊接类", "冲拉类", "护栏网", "边坡防护网",
            "尼龙塑料网", "加工服务/深加工", "窗纱网", "装饰网", "筛网/筛具/过滤器", "养殖笼具",
            "立柱", "线材", "板材", "非织造", "刺/刺网/次管", "丝网机械"};
    private String jieguoText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sibao_search);
        initView();
        initData();
        initAdapter();
    }

    protected void initView() {
        mAc_back = (ImageView) findViewById(R.id.ac_back);
        mSearch_img = (ImageView) findViewById(R.id.search_img);
        mSearch_text = (TextView) findViewById(R.id.search_text);
        mJieguo = (TextView) findViewById(R.id.jieguo);
        mAc_search = (EditText) findViewById(R.id.ac_search);
        mSearch_list = (ListView) findViewById(R.id.search_list);
        //mSearch_list_jieguo = (ListView) findViewById(R.id.search_list_jieguo);

        mSearch_linear = (LinearLayout) findViewById(R.id.search_linear);


        mAc_back.setOnClickListener(this);
        mSearch_linear.setOnClickListener(this);

    }

    private void initData() {
    }

    private void initAdapter() {
        if (mAc_search.getText().toString().trim().equals("")) {
            mJieguo.setVisibility(View.GONE);
            mSearch_linear.setVisibility(View.GONE);
        }
        mAc_search.addTextChangedListener(new TextWatcher() {
                                              @Override
                                              public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                                  Log.i("*******************", "before被执行" + s.toString().trim());
                                              }

                                              /**
                                               * 内容
                                               * 开始位置
                                               * 变化之前字节数
                                               * 变化之后字节数
                                               */

                                              @Override
                                              public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                  String str = s.toString().trim();
                                                  //Toast.makeText(getApplication(),"输入的内容是",Toast.LENGTH_SHORT).show();
                                                  Log.i("*******************", "输入的内容是" + s.toString().trim());
                                                  if (str.equals("") || count == 0) {
                                                      mJieguo.setVisibility(View.GONE);
                                                      mSearch_linear.setVisibility(View.GONE);
                                                  } else {
                                                      for (int i = 0; i < classificationText.length; i++) {
                                                          //if (classificationText[i].contains(s.toString()) ) {
                                                          if (classificationText[i].indexOf(str) >= 0) {
                                                              if (str.equals("网") || str.equals("类")) {
                                                                  Toast.makeText(getApplication(), "请换一个关键词搜索", Toast.LENGTH_SHORT).show();
                                                                  break;
                                                              }
                                                              Log.i("*******************", "匹配成功的内容是" + classificationText[i]);
                                                              //|| s.toString().contains(classificationText[i])
                                                              mSearch_img.setImageResource(id[i]);
                                                              mSearch_text.setText(classificationText[i]);
                                                              jieguoText = classificationText[i];
                                                              mJieguo.setVisibility(View.VISIBLE);
                                                              mSearch_linear.setVisibility(View.VISIBLE);
                                                              Toast.makeText(getApplication(), "搜索成功", Toast.LENGTH_SHORT).show();
                                                              break;

                                                          }

                                                      }
                                                  }
                                              }

                                              @Override
                                              public void afterTextChanged(Editable s) {
                                                  Log.i("*******************", "afterText被执行" + s.toString().trim());

                                              }
                                          }

        );
        mAc_search.setOnTouchListener(new View.OnTouchListener()

                                      {

                                          @Override
                                          public boolean onTouch(View v, MotionEvent event) {
                                              // et.getCompoundDrawables()得到一个长度为4的数组，分别表示左右上下四张图片
                                              Drawable drawable = mAc_search.getCompoundDrawables()[2];
                                              //如果右边没有图片，不再处理
                                              if (drawable == null)
                                                  return false;
                                              //如果不是按下事件，不再处理
                                              if (event.getAction() != MotionEvent.ACTION_UP)
                                                  return false;
                                              if (event.getX() > mAc_search.getWidth()
                                                      - mAc_search.getPaddingRight()
                                                      - drawable.getIntrinsicWidth()) {
                                                  mAc_search.setText("");
                                              }
                                              return false;
                                          }
                                      }

        );
        mSearch_list.setAdapter(new DiscoverFragmentListAdapter(this, id, classificationText));
        //mSearch_list.setAdapter(new Search_list_Adapter(this, id, classificationText));
        mSearch_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ProductStateActivity.class);
                intent.putExtra("Product", classificationText[position]);
                startActivity(intent);

            }
        }
        );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ac_back:
                finish();
                break;
            case R.id.search_linear:
                Intent intent = new Intent(getApplicationContext(), ProductStateActivity.class);
                intent.putExtra("Product", jieguoText);
                startActivity(intent);

                break;
        }

    }
}