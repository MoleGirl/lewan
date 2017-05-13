package cn.bmob.imdemo.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
//import com.google.android.gms.appindexing.Action;
//import com.google.android.gms.appindexing.AppIndex;
//import com.google.android.gms.appindexing.Thing;
//import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.imdemo.BmobIMApplication;
import cn.bmob.imdemo.R;
import cn.bmob.imdemo.bean.Product;
import cn.bmob.imdemo.bean.User;
import cn.bmob.imdemo.model.UserModel;
import cn.bmob.imdemo.util.MyDialog;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2016/12/29.
 */

public class DetailsActivity extends Activity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;
    private LinearLayout mTel_phone, lLinnerlayout;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private TextView mText_number;
    private int[] imgs = new int[]{R.drawable.gg1, R.drawable.gg1, R.drawable.gg1, R.drawable.gg1, R.drawable.gg1};
    private List<String> listUri;//图片地址的集合
    private List<View> points;
    // 图片的集合
    private List<ImageView> listImgs;
    int left = 0;// 上一次的点
    private Product product;

    private TextView product_name;
    private TextView product_price;
    private TextView product_address;
    private TextView product_number;
    private TextView product_user_name;
    private TextView product_category;
    private TextView product_diameter;
    private TextView product_rhole;
    private TextView product_surface;
    private TextView product_state;
    private TextView product_company;
    private TextView product_wide;
    private TextView product_high;


    private ImageView details_img_left;
    private ImageView details_img_right;

    private Boolean collectionof = false;//不收藏
    private MyDialog builder;
    private TextView mDia_collection_tv1_title, mDia_collection_tv1, mDia_collection_tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        //initNaviView();//初始化导航条
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        initView();
        initdata();
        initAdapter();


    }


    public void initView() {
        product_name = (TextView) findViewById(R.id.product_name);
        product_price = (TextView) findViewById(R.id.product_price);
        product_address = (TextView) findViewById(R.id.product_address);
        product_number = (TextView) findViewById(R.id.product_number);
        product_user_name = (TextView) findViewById(R.id.product_user_name);
        product_category = (TextView) findViewById(R.id.product_category);
        product_diameter = (TextView) findViewById(R.id.product_diameter);
        product_rhole = (TextView) findViewById(R.id.product_rhole);
        product_surface = (TextView) findViewById(R.id.product_surface);
        product_state = (TextView) findViewById(R.id.product_state);
        product_company = (TextView) findViewById(R.id.product_company);
        product_wide = (TextView) findViewById(R.id.product_wide);
        product_high = (TextView) findViewById(R.id.product_high);

        details_img_left = (ImageView) findViewById(R.id.details_img_left);
        details_img_right = (ImageView) findViewById(R.id.details_img_right);


        listUri = new ArrayList<>();
        Intent intent = this.getIntent();
        product = (Product) intent.getSerializableExtra("Product");
        Log.e("***之后product.getName()", product.getName());

        product_name.setText(product.getName());
        product_price.setText(product.getPrice());
        product_address.setText(product.getAddress());
        product_number.setText(product.getNumber());
        product_user_name.setText(product.getUsername());
        product_category.setText(product.getCategory());
        product_diameter.setText(product.getDiameter());
        product_rhole.setText(product.getRhole());
        product_surface.setText(product.getSurface());
        product_state.setText(product.getState());
        product_company.setText(product.getCompany());
        product_wide.setText(product.getWide());
        product_high.setText(product.getHigh());
        for (int i = 0; i < product.getImgs().size(); i++) {
            listUri.add(product.getImgs().get(i).getUrl());
        }


        mTel_phone = (LinearLayout) findViewById(R.id.tel_phone);
        lLinnerlayout = (LinearLayout) findViewById(R.id.linnerlayout);

        mText_number = (TextView) findViewById(R.id.text_number);
        mViewPager = (ViewPager) findViewById(R.id.viewp);
        mTel_phone.setOnClickListener(this);
        details_img_left.setOnClickListener(this);
        details_img_right.setOnClickListener(this);

        if (product.getUsername().equals(UserModel.getInstance().getCurrentUser().getUsername())) {
            details_img_right.setVisibility(View.GONE);
        }

        // 填充一个布局
        View v1 = getLayoutInflater().inflate(R.layout.item_dialog, null);
        builder = new MyDialog(this, v1, R.style.dialog);
        mDia_collection_tv1_title = (TextView) builder.getWindow().findViewById(R.id.tv1);
        mDia_collection_tv1 = (TextView) builder.getWindow().findViewById(R.id.dia_tv1);
        mDia_collection_tv2 = (TextView) builder.getWindow().findViewById(R.id.dia_tv2);

        //打开应用没有收藏过产品
        if (BmobIMApplication.productCollections.size()==0){
            for (int i = 0; i < UserModel.getInstance().getCurrentUser().getProduct_collection().size(); i++) {
                for (int j = 0; j < BmobIMApplication.product_list.size(); j++) {
                    if (UserModel.getInstance().getCurrentUser().getProduct_collection().get(i)
                            .equals(product.getObjectId())){
                        details_img_right.setImageResource(R.drawable.collection_red);
                        collectionof = true;
                        //return;
                    }
                }
            }
        }else {//打开应用收藏过产品
            for (int i = 0; i < BmobIMApplication.productCollections.size(); i++) {
                if (BmobIMApplication.productCollections.get(i).getObjectId().equals(product.getObjectId())) {
                    details_img_right.setImageResource(R.drawable.collection_red);
                    collectionof = true;
                    return;
                }
            }
            for (int i = 0; i < UserModel.getInstance().getCurrentUser().getProduct_collection().size(); i++) {
                for (int j = 0; j < BmobIMApplication.product_list.size(); j++) {
                    if (UserModel.getInstance().getCurrentUser().getProduct_collection().get(i)
                            .equals(product.getObjectId())){
                        details_img_right.setImageResource(R.drawable.collection_red);
                        collectionof = true;
                        //return;
                    }
                }
            }
        }




    }

    private void initdata() {

        listImgs = new ArrayList<ImageView>();
        points = new ArrayList<View>();

        ImageView image;// 图片对象
        View point;// 点对象
        LinearLayout.LayoutParams params;// 每个小点布局的样式
        for (int i = 0; i < listUri.size(); i++) {
            image = new ImageView(this);
            image.setBackgroundResource(imgs[i]);
            listImgs.add(image);

            point = new View(this);
            // 设置背景
            point.setBackgroundResource(R.drawable.selector_point);
            // 宽高
            params = new LinearLayout.LayoutParams(5, 5);

            if (i != 0) {
                params.leftMargin = 10;
            }
            // 默认状态下全为灰色
            point.setEnabled(false);

            // 添加到线性布局中
            lLinnerlayout.addView(point, params);
        }


    }

    private void initAdapter() {
        // 设置第0个点为打开状态
        lLinnerlayout.getChildAt(0).setEnabled(true);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setAdapter(new MyDetailsActAdapter());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.details_img_left:
                finish();
                break;
            case R.id.details_img_right:
                if (product.getUsername().equals(UserModel.getInstance().getCurrentUser().getUsername())) {
                    Toast.makeText(getApplicationContext(), "该需求为自己发布", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!collectionof) {
                    mDia_collection_tv1_title.setText("确定收藏");
                    mDia_collection_tv1.setText("确定");
                    mDia_collection_tv2.setText("放弃");
                    builder.show();
                    mDia_collection_tv1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            User u = new User();
                            u.setObjectId(UserModel.getInstance().getCurrentUser().getObjectId());
                            Log.e("*****当前产品id", product.getObjectId());
                            u.addUnique("product_collection", product.getObjectId());
                            u.update(getApplicationContext(), new UpdateListener() {
                                @Override
                                public void onSuccess() {
                                    details_img_right.setImageResource(R.drawable.collection_red);
                                    Toast.makeText(getApplicationContext(), "收藏成功", Toast.LENGTH_SHORT).show();
                                    //本地添加
                                    BmobIMApplication.productCollections.add(product);
                                    collectionof = !collectionof;
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    Log.e("**失败", i + "");
                                    Log.e("**失败", s);
                                    Toast.makeText(getApplicationContext(), "收藏失败", Toast.LENGTH_SHORT).show();

                                }
                            });
                            builder.dismiss();
                        }
                    });
                    mDia_collection_tv2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            builder.dismiss();
                        }
                    });
                } else if (collectionof) {
                    mDia_collection_tv1_title.setText("取消收藏");
                    mDia_collection_tv1.setText("确定");
                    mDia_collection_tv2.setText("放弃");
                    builder.show();
                    mDia_collection_tv1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            User u = new User();
                            u.setObjectId(UserModel.getInstance().getCurrentUser().getObjectId());
                            List<String> removeId = new ArrayList<>();
                            removeId.add(product.getObjectId());
                            u.removeAll("product_collection", removeId);
                            u.update(getApplicationContext(), new UpdateListener() {
                                @Override
                                public void onSuccess() {
                                    Toast.makeText(getApplicationContext(), "取消收藏成功", Toast.LENGTH_SHORT).show();
                                    details_img_right.setImageResource(R.drawable.collection_white);
                                    Log.i("bmob", "成功");
                                    for (int i = 0; i < BmobIMApplication.productCollections.size(); i++) {
                                        Log.e("删除之前", BmobIMApplication.productCollections.get(i).getObjectId());
                                    }
                                    Log.e("删除的数据", product.getObjectId());
                                    //本地删除
                                    BmobIMApplication.productCollections.remove(product);
                                    for (int j = 0; j < BmobIMApplication.productCollections.size(); j++) {
                                        Log.e("删除之后", BmobIMApplication.productCollections.get(j).getObjectId());
                                    }
                                    collectionof = !collectionof;
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    Toast.makeText(getApplicationContext(), "取消收藏失败", Toast.LENGTH_SHORT).show();
                                    Log.i("bmob", "失败");
                                }
                            });

                            builder.dismiss();
                        }
                    });
                    mDia_collection_tv2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            builder.dismiss();
                        }
                    });
                }
                break;

            case R.id.tel_phone:
//                Intent intent = new Intent(); // 意图对象：动作 + 数据
//                intent.setAction(Intent.ACTION_CALL); // 设置动作
//                Uri data = Uri.parse("tel:" + "15988889999"); // 设置数据
//                intent.setData(data);
//                //Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+"15988889999"));
//                startActivity(intent);
                // 检查是否获得了权限（Android6.0运行时权限）
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // 没有获得授权，申请授权
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.CALL_PHONE)) {
                        // 返回值：
//                          如果app之前请求过该权限,被用户拒绝, 这个方法就会返回true.
//                          如果用户之前拒绝权限的时候勾选了对话框中”Don’t ask again”的选项,那么这个方法会返回false.
//                          如果设备策略禁止应用拥有这条权限, 这个方法也返回false.
                        // 弹窗需要解释为何需要该权限，再次请求授权
                        Toast.makeText(this, "请授权！", Toast.LENGTH_LONG).show();

                        // 帮跳转到该应用的设置界面，让用户手动授权
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    } else {
                        // 不需要解释为何需要该权限，直接请求授权
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                MY_PERMISSIONS_REQUEST_CALL_PHONE);
                    }
                } else {
                    // 已经获得授权，可以打电话
                    CallPhone();
                }
                break;
        }
    }

    private void CallPhone() {
        String number = mText_number.getText().toString();
        if (TextUtils.isEmpty(number)) {
            // 提醒用户
            // 注意：在这个匿名内部类中如果用this则表示是View.OnClickListener类的对象，
            // 所以必须用MainActivity.this来指定上下文环境。
            Toast.makeText(this, "号码不能为空！", Toast.LENGTH_SHORT).show();
        } else {
            // 拨号：激活系统的拨号组件
            Intent intent = new Intent(); // 意图对象：动作 + 数据
            intent.setAction(Intent.ACTION_CALL); // 设置动作
            Uri data = Uri.parse("tel:" + number); // 设置数据
            intent.setData(data);
            startActivity(intent); // 激活Activity组件
        }
    }

    // 处理权限申请的回调
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 授权成功，继续打电话
                    CallPhone();
                } else {
                    // 授权失败！
                    Toast.makeText(this, "授权失败！", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }


    @Override
    public void onPageSelected(int position) {
        // Toast.makeText(this,"第"+position+"个",Toast.LENGTH_SHORT).show();
        lLinnerlayout.getChildAt(left).setEnabled(false); // 上一个点
        lLinnerlayout.getChildAt(position).setEnabled(true);// 当前的点
        left = position;// 当前点赋值给上一个点
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    class MyDetailsActAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return listUri.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            SimpleDraweeView simpleDraweeView = new SimpleDraweeView(container.getContext());
            simpleDraweeView.setImageURI(Uri.parse(listUri.get(position)));
            simpleDraweeView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            simpleDraweeView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            //ImageView imageView = listImgs.get(position);
            container.addView(simpleDraweeView);
            return simpleDraweeView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
