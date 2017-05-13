package cn.bmob.imdemo.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;

import butterknife.Bind;
import cn.bmob.imdemo.BmobIMApplication;
import cn.bmob.imdemo.R;
import cn.bmob.imdemo.adapter.DiscoverFragmentListAdapter;
import cn.bmob.imdemo.adapter.ImageAdapter;
import cn.bmob.imdemo.adapter.base.MyrecyclerViewAdapter;
import cn.bmob.imdemo.base.BaseActivity;
import cn.bmob.imdemo.util.FullyGridLayoutManager;

/**
 * Created by Administrator on 2017/1/3.
 */

public class AddressActivity extends BaseActivity implements View.OnClickListener {
    private String letter[] = {"A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "W", "X", "Y", "Z"};
    private String letters[] = {"北京", "上海", "广州", "深圳", "杭州"};
    private String addss[][] = {
            {"鞍山", "安康", "安阳", "安庆", "安顺", "澳门"},
            {"北京", "白城", "白山", "本溪", "包头", "巴彦淖尔", "保定", "宝鸡", "滨州", "巴音郭楞", "博尔塔拉", "白银", "蚌埠", "亳州", "毕节", "巴中", "保山", "百色", "北海"},
            {"重庆", "长春", "朝阳", "赤峰", "承德", "沧州", "长治", "昌吉", "常州", "滁州", "池州", "长沙", "郴州", "常德", "成都", "潮州", "楚雄", "崇左"},
            {"大理", "大连", "东莞", "丹东", "大庆", "大兴安岭", "德宏", "德阳", "德州", "定西", "迪庆", "东营"},
            {"鄂尔多斯", "恩施", "鄂州"},
            {"福州", "防城港", "佛山", "抚顺", "抚州", "阜新", "阜阳"},
            {"广州", "桂林", "贵阳", "甘南", "赣州", "甘孜", "广安", "广元", "贵港", "果洛"},
            {"杭州", "哈尔滨", "合肥", "海口", "呼和浩特", "海北", "海东", "海南", "海西", "邯郸", "汉中", "鹤壁", "河池", "鹤岗", "黑河", "衡水", "衡阳", "河源", "贺州", "红河", "淮安", "淮北", "怀化", "淮南", "黄冈", "黄南", "黄山", "黄石", "惠州", "葫芦岛", "呼伦贝尔", "湖州", "菏泽"},
            {"济南", "佳木斯", "吉安", "江门", "焦作", "嘉兴", "嘉峪关", "揭阳", "吉林", "金昌", "晋城", "景德镇", "荆门", "荆州", "金华", "济宁", "晋中", "锦州", "九江", "酒泉"},
            {"昆明", "开封"},
            {"兰州", "拉萨", "来宾", "莱芜", "廊坊", "乐山", "凉山", "连云港", "聊城", "辽阳", "辽源", "丽江", "临沧", "临汾", "临夏", "临沂", "林芝", "丽水", "六安", "六盘水", "柳州", "陇南", "龙岩", "娄底", "漯河", "洛阳", "泸州", "吕梁"},
            {"马鞍山", "茂名", "眉山", "梅州", "绵阳", "牡丹江"},
            {"南京", "南昌", "南宁", "宁波", "南充", "南平", "南通", "南阳", "那曲", "内江", "宁德", "怒江"},
            {"盘锦", "攀枝花", "平顶山", "平凉", "萍乡", "莆田", "濮阳"},
            {"青岛", "黔东南", "黔南", "黔西南", "庆阳", "清远", "秦皇岛", "钦州", "齐齐哈尔", "泉州", "曲靖", "衢州"},
            {"日喀则", "日照"},
            {"上海", "深圳", "苏州", "沈阳", "石家庄", "三门峡", "三明", "三亚", "商洛", "商丘", "上饶", "山南", "汕头", "汕尾", "韶关", "绍兴", "邵阳", "十堰", "朔州", "四平", "绥化", "遂宁", "随州", "宿迁", "宿州"},
            {"天津", "太原", "泰安", "泰州", "台州", "唐山", "天水", "铁岭", "铜川", "通化", "通辽", "铜陵", "铜仁", "台湾"},
            {"武汉", "乌鲁木齐", "无锡", "威海", "潍坊", "文山", "温州", "乌海", "芜湖", "乌兰察布", "武威", "梧州"},
            {"厦门", "西安", "西宁", "襄樊", "湘潭", "湘西", "咸宁", "咸阳", "孝感", "邢台", "新乡", "信阳", "新余", "忻州", "西双版纳", "宣城", "许昌", "徐州", "香港", "锡林郭勒", "兴安"},
            {"银川", "雅安", "延安", "延边", "盐城", "阳江", "阳泉", "扬州", "烟台", "宜宾", "宜昌", "宜春", "营口", "益阳", "永州", "岳阳", "榆林", "运城", "云浮", "玉树", "玉溪", "玉林"},
            {"杂多县", "赞皇县", "枣强县", "枣阳市", "枣庄", "泽库县", "增城市", "曾都区", "泽普县", "泽州县", "札达县", "扎赉特旗", "扎兰屯市", "扎鲁特旗", "扎囊县", "张北县", "张店区",
                    "章贡区", "张家港", "张家界", "张家口", "漳平市", "漳浦县", "章丘市", "樟树市", "张湾区", "彰武县", "漳县", "张掖", "漳州", "长子县", "湛河区", "湛江", "站前区",
                    "沾益县", "诏安县", "召陵区", "昭平县", "肇庆", "昭通", "赵县", "昭阳区", "招远市", "肇源县", "肇州县", "柞水县", "柘城县", "浙江", "镇安县", "振安区", "镇巴县",
                    "正安县", "正定县", "正定新区", "正蓝旗", "正宁县", "蒸湘区", "正镶白旗", "正阳县", "郑州", "镇海区", "镇江", "浈江区", "镇康县", "镇赉县", "镇平县", "振兴区",
                    "镇雄县", "镇原县", "志丹县", "治多县", "芝罘区", "枝江市", "芷江侗族自治县", "织金县", "中方县", "中江县", "钟楼区", "中牟县", "中宁县", "中山", "中山区",
                    "钟山区", "钟山县", "中卫", "钟祥市", "中阳县", "中原区", "周村区", "周口", "周宁县", "舟曲县", "舟山", "周至县", "庄河市", "诸城市", "珠海", "珠晖区", "诸暨市",
                    "驻马店", "准格尔旗", "涿鹿县", "卓尼", "涿州市", "卓资县", "珠山区", "竹山县", "竹溪县", "株洲", "株洲县", "淄博", "子长县", "淄川区", "自贡", "秭归县", "紫金县",
                    "自流井区", "资溪县", "资兴市", "资阳"}};

    private int width, height;
    private LinearLayout mLin_add;

    private TextView mCurrent_city;
    @Bind(R.id.ac_back)
    ImageView mAc_back;//返回
    @Bind(R.id.ac_search)
    EditText mAc_search;//搜索
    @Bind(R.id.addre_rec)
    RecyclerView mAddre_rec;
    String address = null;

    private AMapLocation location;
    int i;

    @Bind(R.id.search_address_lin)
    LinearLayout search_address_lin;
    @Bind(R.id.search_address)
    TextView search_address;
    String str;//结果

    @Bind(R.id.add_gridview)
    GridView add_gridview;
    private ImageAdapter gridviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_add);
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        width = metric.widthPixels;     // 屏幕宽度（像素）
        height = metric.heightPixels;   // 屏幕高度（像素）
        mAc_search.setHint("输入城市名称");
        initView();
        initData();
        initAdapter();
    }

    public void initView() {
        mLin_add = (LinearLayout) findViewById(R.id.lin_add);
        mCurrent_city = (TextView) findViewById(R.id.current_city);
        mAc_back = (ImageView) findViewById(R.id.ac_back);
        mAc_back.setOnClickListener(this);

        if (BmobIMApplication.amapLocations.size() > 0) {
            location = BmobIMApplication.amapLocations.get(0);
            Toast.makeText(this, "当前的定位结果:位置" + location.getCity(), Toast.LENGTH_SHORT).show();
            address = location.getCity().substring(0, location.getCity().length() - 1);
            mCurrent_city.setText(address);
        } else {
            mCurrent_city.setText("定位失败");
            Toast.makeText(this, "定位失败", Toast.LENGTH_SHORT).show();
        }
        mCurrent_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mCurrent_city.getText().toString().trim().equals("定位失败")) {
                    Intent intent = new Intent();
                    intent.putExtra("add", address);
                    //App.address = location.getCity();
                    setResult(102, intent);
                    finish();
                }
            }
        });

        mAc_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                str = s.toString().trim();
                for (int a = 0; a < addss.length; a++) {
                    for (int b = 0; b < addss[a].length; b++) {
                        if (str.equals(addss[a][b])) {
                            Toast.makeText(getApplication(), "搜索成功", Toast.LENGTH_SHORT).show();
                            search_address_lin.setVisibility(View.VISIBLE);
                            search_address.setText(str);
                            search_address.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    search_address_lin.setVisibility(View.GONE);
                                    Intent intent = new Intent();
                                    intent.putExtra("add", str);
                                    //App.address = location.getCity();
                                    setResult(105, intent);
                                    finish();
                                }
                            });
                            break;
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mAc_search.setOnTouchListener(new View.OnTouchListener() {

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
                                                  search_address_lin.setVisibility(View.GONE);
                                              }
                                              return false;
                                          }
                                      }

        );
        mAc_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (search_address_lin.getVisibility()==View.GONE) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        search_address_lin.setVisibility(View.VISIBLE);
                    }
                }
                return false;
            }
        });
    }


    private void initData() {

    }

    ListView listView;

    private void initAdapter() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 3);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        manager.setSmoothScrollbarEnabled(true);
        mAddre_rec.setLayoutManager(manager);
        MyrecyclerViewAdapter mAddre_recAdapter = new MyrecyclerViewAdapter(this, letters);
        mAddre_rec.setAdapter(new MyrecyclerViewAdapter(this, letters));

        gridviewAdapter= new ImageAdapter(getApplication(), letters,4);
        add_gridview.setAdapter(gridviewAdapter);
        add_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("add", letters[position]);
                setResult(105, intent);
                finish();
            }
        });



        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        for (i = 0; i < letter.length; i++) {
            TextView textView = new TextView(this);
            textView.setWidth(width);
            textView.setHeight(30);
            textView.setPadding(15, 0, 0, 0);
            textView.setText(letter[i]);
            textView.setMaxLines(1);
            textView.setTextSize(12);
            textView.setTextColor(0xff000000);
            mLin_add.addView(textView, layoutParams);

            listView = new ListView(this);
            ListView.LayoutParams listP = new ListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mLin_add.addView(listView, listP);

            listView.setAdapter(new DiscoverFragmentListAdapter(this, addss[i]));
            setListViewHeightBasedOnChildren(listView);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ac_back:
                Intent intent = new Intent();
                intent.putExtra("add", address);
                //App.address = location.getCity();
                setResult(105, intent);
                finish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //返回键,event.getRepeatCount()重复次数
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.putExtra("add", address);
            //App.address = location.getCity();
            setResult(105, intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }



    //嵌套显示1行解决
    public void setListViewHeightBasedOnChildren(ListView listViewHeightBasedOnChildren) {
        ListAdapter listAdapter = listViewHeightBasedOnChildren.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listViewHeightBasedOnChildren);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listViewHeightBasedOnChildren.getLayoutParams();
        params.height = totalHeight
                + (listViewHeightBasedOnChildren.getDividerHeight() * (listAdapter.getCount() - 1));
        listViewHeightBasedOnChildren.setLayoutParams(params);
    }
}
