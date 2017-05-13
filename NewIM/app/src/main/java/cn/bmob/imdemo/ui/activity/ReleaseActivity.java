package cn.bmob.imdemo.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;

import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.bmob.imdemo.BmobIMApplication;

import cn.bmob.imdemo.R;
import cn.bmob.imdemo.adapter.ImageAdapter;
import cn.bmob.imdemo.base.ParentWithNaviActivity;
import cn.bmob.imdemo.bean.Needs;
import cn.bmob.imdemo.bean.Product;
import cn.bmob.imdemo.bean.User;
import cn.bmob.imdemo.image.Adapter;
import cn.bmob.imdemo.model.UserModel;

import cn.bmob.imdemo.util.BottomMenuDialog;
import cn.bmob.imdemo.util.GlideLoader;
import cn.bmob.imdemo.util.MyDialog;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadBatchListener;


/**
 * Created by Administrator on 2016/12/28.
 */

public class ReleaseActivity extends ParentWithNaviActivity implements View.OnClickListener {

    private EditText mRe_name, mRe_price, mRe_diameter, mR_hole, mRe_width, mRe_high, mRe_tel;
    private TextView mRe_classification, mRe_surface, mRe_state, mRe_measurement, mRe_release_number, mRe_address;
    private final static int REQUEST_CODE = 1;
    private Intent intent;
    private MyDialog builder;
    private String str_title = null;


    @Bind(R.id.sv_need_title)
    EditText sv_need_title;
    @Bind(R.id.sv_need_details)
    EditText sv_need_details;
    @Bind(R.id.sv_need_address)
    TextView sv_need_address;
    @Bind(R.id.sv_need_phone)
    EditText sv_need_phone;

    @Bind(R.id.sv_release)
    ScrollView sv_release;
    @Bind(R.id.sv_need_release)
    ScrollView sv_need_release;
    View v1;
    //用户名
    private String username = null;
    int keyCodeNumbew = 1;
    private AMapLocation location;
    String address = null;

    @Bind(R.id.gridview)
    GridView mGridview;
    @Bind(R.id.phototv)
    TextView phototv;
    private BottomMenuDialog dialog;
    private RecyclerView.Adapter adapter;
    private ArrayList<String> path = new ArrayList<>();
    private String paths[];//路径的集合
    private RecyclerView recycler;
    private ImageAdapter gridviewAdapter;
    private Product product;//上传的产品
    private Needs needs;//上传的需求
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_release);
        initNaviView();//初始化导航条
        initView();
        initAdapter();
    }


    protected void initView() {
        progressDialog = new ProgressDialog(this);
        mRe_name = (EditText) findViewById(R.id.re_name);
        mRe_price = (EditText) findViewById(R.id.re_price);
        mRe_diameter = (EditText) findViewById(R.id.re_diameter);
        mR_hole = (EditText) findViewById(R.id.re_hole);
        mRe_width = (EditText) findViewById(R.id.re_width);
        mRe_high = (EditText) findViewById(R.id.re_high);
        mRe_tel = (EditText) findViewById(R.id.re_tel);

        recycler = (RecyclerView) findViewById(R.id.recycler);

        mRe_classification = (TextView) findViewById(R.id.re_classification);
        mRe_surface = (TextView) findViewById(R.id.re_surface);
        mRe_state = (TextView) findViewById(R.id.re_state);
        mRe_measurement = (TextView) findViewById(R.id.re_measurement);
        mRe_release_number = (TextView) findViewById(R.id.re_release_number);
        mRe_address = (TextView) findViewById(R.id.re_address);


        mRe_classification.setOnClickListener(this);
        mRe_surface.setOnClickListener(this);
        mRe_state.setOnClickListener(this);
        mRe_measurement.setOnClickListener(this);
        mRe_release_number.setOnClickListener(this);
        mRe_address.setOnClickListener(this);
        phototv.setOnClickListener(this);
        sv_need_address.setOnClickListener(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String str = bundle.getString("str");

        if (str.equals("发布产品")) {
            sv_release.setVisibility(View.VISIBLE);
            str_title = "发布产品";
        } else if (str.equals("发布需求")) {
            sv_need_release.setVisibility(View.VISIBLE);
            str_title = "发布需求";
        }
        // 填充一个布局
        v1 = getLayoutInflater().inflate(R.layout.item_dialog, null);
        builder = new MyDialog(ReleaseActivity.this, v1, R.style.dialog);
        username = UserModel.getInstance().getCurrentUser().getUsername();//用户名


        if (BmobIMApplication.amapLocations.size() > 0) {
            location = BmobIMApplication.amapLocations.get(0);
            Toast.makeText(this, "当前的定位结果:位置" + location.getCity(), Toast.LENGTH_SHORT).show();
            address = location.getCity().substring(0, location.getCity().length() - 1);
            mRe_address.setText(address);
        } else {
            Toast.makeText(this, "定位失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void initAdapter() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recycler.setLayoutManager(gridLayoutManager);
        adapter = new Adapter(getApplication(), path);
        recycler.setAdapter(adapter);


        gridviewAdapter = new ImageAdapter(getApplication(), path, 3);
        mGridview.setAdapter(gridviewAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.re_classification:
                intent = new Intent();
                intent.setClass(ReleaseActivity.this, ReClassificationActivity.class);
                intent.putExtra("str", "分类");
                startActivityForResult(intent, REQUEST_CODE);
                //startActivity(new Intent(ReleaseActivity.this, ReClassificationActivity.class));
                break;

            case R.id.re_surface:
                intent = new Intent();
                intent.setClass(ReleaseActivity.this, ReClassificationActivity.class);
                intent.putExtra("str", "表面");
                startActivityForResult(intent, REQUEST_CODE);
                //startActivity(new Intent(ReleaseActivity.this, ReClassificationActivity.class));
                break;
            case R.id.re_state:
                intent = new Intent();
                intent.setClass(ReleaseActivity.this, ReClassificationActivity.class);
                intent.putExtra("str", "状态");
                startActivityForResult(intent, REQUEST_CODE);
                //startActivity(new Intent(ReleaseActivity.this, ReClassificationActivity.class));
                break;
            case R.id.re_measurement:
                intent = new Intent();
                intent.setClass(ReleaseActivity.this, ReClassificationActivity.class);
                intent.putExtra("str", "单位");
                startActivityForResult(intent, REQUEST_CODE);
                //startActivity(new Intent(ReleaseActivity.this, ReClassificationActivity.class));
                break;
            case R.id.re_release_number:
                intent = new Intent();
                intent.setClass(ReleaseActivity.this, ReClassificationActivity.class);
                intent.putExtra("str", "数量");
                startActivityForResult(intent, REQUEST_CODE);
                //startActivity(new Intent(ReleaseActivity.this, ReClassificationActivity.class));
                break;
            case R.id.re_address:
                startActivityForResult(new Intent(this, AddressActivity.class), REQUEST_CODE);
                break;
            case R.id.phototv://上传图片
                showPhotoDialog();
                break;
            case R.id.sv_need_address://需求地址
                startActivityForResult(new Intent(this, AddressActivity.class), REQUEST_CODE);
                break;
        }
    }

    private void showPhotoDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = new BottomMenuDialog(this);
        //拍照
        dialog.setConfirmListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }


            }
        });
        //图库
        dialog.setMiddleListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }

                ImageConfig imageConfig = new ImageConfig.Builder(
                        // GlideLoader 可用自己用的缓存库
                        new GlideLoader())
                        // 如果在 4.4 以上，则修改状态栏颜色 （默认黑色）
                        .steepToolBarColor(getResources().getColor(R.color.blue))
                        // 标题的背景颜色 （默认黑色）
                        .titleBgColor(getResources().getColor(R.color.blue))
                        // 提交按钮字体的颜色  （默认白色）
                        .titleSubmitTextColor(getResources().getColor(R.color.white))
                        // 标题颜色 （默认白色）
                        .titleTextColor(getResources().getColor(R.color.white))
                        // 开启多选   （默认为多选）  (单选 为 singleSelect)
                        //.singleSelect()
                        //.crop()
                        // 多选时的最大数量   （默认 9 张）
                        .mutiSelectMaxSize(5)
                        // 已选择的图片路径
                        .pathList(path)
                        // 拍照后存放的图片路径（默认 /temp/picture）
                        .filePath("/ImageSelector/Pictures")
                        // 开启拍照功能 （默认开启）
                        .showCamera()
                        .requestCode(1000)
                        .build();
                ImageSelector.open(ReleaseActivity.this, imageConfig);   // 开启图片选择器
            }
        });
        dialog.show();


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            Bundle bundle = data.getExtras();
            if (resultCode == 1) {
                String str1 = bundle.getString("back");
                mRe_classification.setText(str1);
            } else if (resultCode == 2) {
                String str2 = bundle.getString("back");
                mRe_surface.setText(str2);
            } else if (resultCode == 3) {
                String str3 = bundle.getString("back");
                mRe_state.setText(str3);
            } else if (resultCode == 4) {
                String str4 = bundle.getString("back");
                mRe_measurement.setText(str4);
            } else if (resultCode == 5) {
                String str5 = bundle.getString("back");
                mRe_release_number.setText(str5);
            } else if (resultCode == 105) {
                String str6 = bundle.getString("add");
                mRe_address.setText(str6);
                sv_need_address.setText(str6);
            }
        } else if (requestCode == 1000 && resultCode == RESULT_OK && data != null) {
            Toast.makeText(this, "回来了", Toast.LENGTH_SHORT).show();
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);

            for (String path : pathList) {
                Log.i("ImagePathList", path);
            }
            path.clear();
            path.addAll(pathList);
            gridviewAdapter.notifyDataSetChanged();
        } else if (data == null) {
            Toast.makeText(this, "没有选择", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //返回键,event.getRepeatCount()重复次数
        if (keyCode == KeyEvent.KEYCODE_BACK && keyCodeNumbew == 1) {
            if (!builder.isShowing()) {
                TextView title = (TextView) builder.findViewById(R.id.tv1);
                TextView left = (TextView) builder.findViewById(R.id.dia_tv1);
                TextView right = (TextView) builder.findViewById(R.id.dia_tv2);
                title.setText("是否放弃");
                left.setText("确定");
                right.setText("否");
                left.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder.dismiss();
                        finish();
                    }
                });
                right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder.dismiss();
                    }
                });
                builder.show();
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK && keyCodeNumbew == 2) {
            toast("请稍等,正在上传...");
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected String title() {
        return str_title;
    }


    @Override
    public Object right() {
        return R.drawable.right;
    }


    @Override
    public ToolBarListener setToolBarListener() {
        return new ToolBarListener() {
            @Override
            public void clickLeft() {
                if (keyCodeNumbew == 1) {
                    builder.setCancelable(false);//不可撤销
                    builder.show();
                    TextView title = (TextView) builder.findViewById(R.id.tv1);
                    TextView left = (TextView) builder.findViewById(R.id.dia_tv1);
                    TextView right = (TextView) builder.findViewById(R.id.dia_tv2);
                    title.setText("是否放弃");
                    left.setText("确定");
                    right.setText("否");
                    left.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            builder.dismiss();
                            finish();
                        }
                    });
                    right.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            builder.dismiss();
                        }
                    });
                } else {
                    toast("请稍等,正在上传....");
                }

            }

            @Override
            public void clickRight() {
                if (keyCodeNumbew == 1) {
                    if (str_title.equals("发布产品")) {
                        if (mRe_name.getText().toString().trim().equals("")) {
                            Toast.makeText(ReleaseActivity.this, "产品名称不能为空", Toast.LENGTH_SHORT).show();
                        } else if (mRe_price.getText().toString().trim().equals("")) {
                            Toast.makeText(ReleaseActivity.this, "产品价格不能为空", Toast.LENGTH_SHORT).show();
                        } else if (mRe_classification.getText().toString().trim().equals("请选择")) {
                            Toast.makeText(ReleaseActivity.this, "选择产品分类", Toast.LENGTH_SHORT).show();
                        } else if (mRe_diameter.getText().toString().trim().equals("")) {
                            Toast.makeText(ReleaseActivity.this, "产品丝径不能为空", Toast.LENGTH_SHORT).show();
                        } else if (mR_hole.getText().toString().trim().equals("")) {
                            Toast.makeText(ReleaseActivity.this, "产品网孔/目不能为空", Toast.LENGTH_SHORT).show();
                        } else if (mRe_width.getText().toString().trim().equals("")) {
                            Toast.makeText(ReleaseActivity.this, "产品宽度不能为空", Toast.LENGTH_SHORT).show();
                        } else if (mRe_high.getText().toString().trim().equals("")) {
                            Toast.makeText(ReleaseActivity.this, "产品高度不能为空", Toast.LENGTH_SHORT).show();
                        } else if (mRe_surface.getText().toString().trim().equals("请选择")) {
                            Toast.makeText(ReleaseActivity.this, "选择产品表面处理方式", Toast.LENGTH_SHORT).show();
                        } else if (mRe_state.getText().toString().trim().equals("请选择")) {
                            Toast.makeText(ReleaseActivity.this, "选择产品状态", Toast.LENGTH_SHORT).show();
                        } else if (mRe_measurement.getText().toString().trim().equals("请选择")) {
                            Toast.makeText(ReleaseActivity.this, "选择产品单位", Toast.LENGTH_SHORT).show();
                        } else if (mRe_release_number.getText().toString().trim().equals("请选择")) {
                            Toast.makeText(ReleaseActivity.this, "选择产品数量", Toast.LENGTH_SHORT).show();
                        } else if (mRe_address.getText().toString().trim().equals("定位失败")) {
                            Toast.makeText(ReleaseActivity.this, "产品所在地不能为空", Toast.LENGTH_SHORT).show();
                        } else if (mRe_tel.getText().toString().trim().equals("") || mRe_tel.getText().toString().trim().length() != 11) {
                            Toast.makeText(ReleaseActivity.this, "请填写正确的联系方式", Toast.LENGTH_SHORT).show();
                        } else if (path.size() == 0) {
                            Toast.makeText(ReleaseActivity.this, "请选择介绍图片", Toast.LENGTH_SHORT).show();
                        } else {
                            builder.show();
                        }
                    } else if (str_title.equals("发布需求")) {
                        if (sv_need_title.getText().toString().trim().equals("")) {
                            Toast.makeText(ReleaseActivity.this, "需求名称不能为空", Toast.LENGTH_SHORT).show();
                        } else if (sv_need_details.getText().toString().trim().equals("")) {
                            Toast.makeText(ReleaseActivity.this, "需求详情不能为空", Toast.LENGTH_SHORT).show();
                        } else if (sv_need_phone.getText().toString().trim().equals("") ||
                                sv_need_phone.getText().length() != 11) {
                            Toast.makeText(ReleaseActivity.this, "联系方式不正确", Toast.LENGTH_SHORT).show();
                        } else if (sv_need_address.getText().toString().trim().equals("定位") ||
                                sv_need_address.getText().toString().trim().equals("")) {
                            Toast.makeText(ReleaseActivity.this, "请选择位置", Toast.LENGTH_SHORT).show();
                        } else {
                            builder.show();
                        }

                    }
                    TextView title = (TextView) builder.findViewById(R.id.tv1);
                    TextView left = (TextView) builder.findViewById(R.id.dia_tv1);
                    TextView right = (TextView) builder.findViewById(R.id.dia_tv2);
                    title.setText("确认发布");
                    left.setText("确定");
                    right.setText("否");
                    left.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //支付
                            if (UserModel.getInstance().getCurrentUser().getPay()){
                                builder.dismiss();
                                keyCodeNumbew = 2;
                                save();

                            }else {//未支付
                                if (UserModel.getInstance().getCurrentUser().getRelease_no().intValue()>=1){
                                    toast("您已经发布过一次,未支付,不能再次发布");
                                    //发布=0
                                }else if (UserModel.getInstance().getCurrentUser().getRelease_no().intValue()==0){
                                    toast("未支付,只能发布一次,已支付,请忽略");
                                    builder.dismiss();
                                    keyCodeNumbew = 2;
                                    save();
                                    //已支付,发布>=1
                                }
                            }

                        }
                    });
                    right.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            builder.dismiss();
                        }
                    });

                } else {
                    toast("请稍等,正在上传...");
                }
            }
        };
    }

    public void save() {
        progressDialog.show();
        if (str_title.equals("发布产品")) {
            paths = new String[path.size()];
            for (int i = 0; i < path.size(); i++) {
                paths[i] = path.get(i);
            }

            BmobFile.uploadBatch(this, paths, new UploadBatchListener() {
                @Override
                public void onSuccess(List<BmobFile> list, List<String> list1) {

                    if (list.size() != paths.length) {
                        return;
                    }
                    product = new Product();
                    //注意：不能调用gameScore.setObjectId("")方法
                    product.setName(mRe_name.getText().toString().trim());
                    product.setPrice(mRe_price.getText().toString().trim());
                    product.setCategory(mRe_classification.getText().toString());
                    product.setDiameter(mRe_diameter.getText().toString().trim());
                    product.setRhole(mR_hole.getText().toString().trim());
                    product.setWide(mRe_width.getText().toString().trim());
                    product.setHigh(mRe_high.getText().toString().trim());
                    product.setSurface(mRe_surface.getText().toString().trim());
                    product.setState(mRe_state.getText().toString().trim());
                    product.setCompany(mRe_measurement.getText().toString().trim());
                    product.setNumber(mRe_release_number.getText().toString().trim());
                    product.setAddress(mRe_address.getText().toString().trim());
                    product.setTel(mRe_tel.getText().toString().trim());
                    product.setAdvert_bool(false);//是否轮播
                    product.setRecommend(false);//是否推荐
                    product.setImgs(list);
                    product.setUsername(UserModel.getInstance().getCurrentUser().getUsername());
                    product.save(getApplicationContext(), new SaveListener() {
                        @Override
                        public void onSuccess() {
                            Integer a = new Integer(1);
                            User u = new User();
                            //发布次数
                            u.setRelease_no(UserModel.getInstance().getCurrentUser().getRelease_no() + a);
                            //本地次数添加
                            UserModel.getInstance().getCurrentUser()
                                    .setRelease_no(UserModel.getInstance().
                                            getCurrentUser().getRelease_no() + a);
                            u.update(getApplicationContext(), UserModel.getInstance().getCurrentUser().getObjectId(), new UpdateListener() {
                                @Override
                                public void onSuccess() {
                                    BmobIMApplication.product_release.add(product);
                                    viewShow("创建数据成功");
                                }

                                @Override
                                public void onFailure(int i, String s) {

                                }
                            });

                        }

                        @Override
                        public void onFailure(int i, String s) {
                            viewShow("创建数据");
                        }
                    });
                }

                @Override
                public void onProgress(int i, int i1, int i2, int i3) {

                }

                @Override
                public void onError(int i, String s) {

                }
            });


        } else if (str_title.equals("发布需求")) {
            needs = new Needs();
            needs.setNeed_title(sv_need_title.getText().toString().trim());
            needs.setNeed_details(sv_need_details.getText().toString().trim());
            needs.setNeed_phone(sv_need_phone.getText().toString().trim());
            needs.setNeed_address(sv_need_address.getText().toString().trim());
            needs.setUsername(UserModel.getInstance().getCurrentUser().getUsername());
            needs.save(this, new SaveListener() {
                @Override
                public void onSuccess() {
                    Integer a = new Integer(1);
                    User u = new User();
                    //发布次数
                    u.setRelease_no(UserModel.getInstance().getCurrentUser().getRelease_no() + a);
                    //本地次数添加
                    UserModel.getInstance().getCurrentUser()
                            .setRelease_no(UserModel.
                                    getInstance().getCurrentUser().getRelease_no() + a);
                    u.update(getApplicationContext(), UserModel.getInstance().getCurrentUser().getObjectId(), new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            BmobIMApplication.needs_release.add(needs);
                            viewShow("创建数据成功");
                        }

                        @Override
                        public void onFailure(int i, String s) {

                        }
                    });
                }

                @Override
                public void onFailure(int i, String s) {
                    viewShow("创建数据");
                }
            });
        }
    }

    public void viewShow(String str) {
        progressDialog.dismiss();
        keyCodeNumbew = 1;
        tv_left.setClickable(true);
        toast(str);
        finish();
    }
}
