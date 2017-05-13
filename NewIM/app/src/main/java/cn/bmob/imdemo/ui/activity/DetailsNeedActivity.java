package cn.bmob.imdemo.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.bmob.imdemo.BmobIMApplication;
import cn.bmob.imdemo.R;
import cn.bmob.imdemo.base.ParentWithNaviActivity;
import cn.bmob.imdemo.bean.Needs;
import cn.bmob.imdemo.bean.User;
import cn.bmob.imdemo.model.UserModel;
import cn.bmob.imdemo.ui.SearchUserActivity;
import cn.bmob.imdemo.util.MyDialog;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2017/1/9.
 */

public class DetailsNeedActivity extends ParentWithNaviActivity implements View.OnClickListener {

    private LinearLayout mNeed_message, mNeed_collection, mNeed_phone;
    private ImageView mImg_collection;
    private MyDialog builder;
    private TextView mDia_collection_tv1_title, mDia_collection_tv1, mDia_collection_tv2;
    private Boolean collectionof = false;//不收藏
    private Needs needs;
    private SimpleDraweeView mNeed_dis_img;
    @Bind(R.id.list_user_name)
    TextView list_user_name;
    @Bind(R.id.list_details_address)
    TextView list_details_address;
    @Bind(R.id.list_details_title)//需求名字/标题
            TextView list_details_title;
    @Bind(R.id.list_details_need)//需求介绍/内容
            TextView list_details_need;

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_need_details);
        initNaviView();//初始化导航条

        initView();
        initData();
        initAdapter();
    }


    public void initView() {
        mNeed_dis_img = (SimpleDraweeView) findViewById(R.id.need_dis_img);


        Intent intent = this.getIntent();
        needs = (Needs) intent.getSerializableExtra("Needs");
        list_user_name.setText(needs.getUsername());
        list_details_address.setText(needs.getNeed_address());
        list_details_title.setText(needs.getNeed_title());
        list_details_need.setText(needs.getNeed_details());
        for (int i = 0; i < BmobIMApplication.users.size(); i++) {
            if (BmobIMApplication.users.get(i).getUsername().equals(needs.getUsername())) {
                if (BmobIMApplication.users.get(i).getHand() != null) {
                    Glide.with(getApplicationContext()).load(BmobIMApplication.users.get(i).getHand().getUrl()).centerCrop().into(mNeed_dis_img);
                } else {
                    Glide.with(getApplicationContext()).load("http://bmob-cdn-9396.b0.upaiyun.com/2017/03/11/1f07fe1b409642f7804a7cd16a113522.png").centerCrop().into(mNeed_dis_img);
                }
            }
        }

        mImg_collection = (ImageView) findViewById(R.id.img_collection);

        mNeed_message = (LinearLayout) findViewById(R.id.need_message);
        mNeed_collection = (LinearLayout) findViewById(R.id.need_collection);
        mNeed_phone = (LinearLayout) findViewById(R.id.need_phone);


        mNeed_message.setOnClickListener(this);
        mNeed_collection.setOnClickListener(this);
        mNeed_phone.setOnClickListener(this);


        // 填充一个布局
        View v1 = getLayoutInflater().inflate(R.layout.item_dialog, null);
        builder = new MyDialog(this, v1, R.style.dialog);
        mDia_collection_tv1_title = (TextView) builder.getWindow().findViewById(R.id.tv1);
        mDia_collection_tv1 = (TextView) builder.getWindow().findViewById(R.id.dia_tv1);
        mDia_collection_tv2 = (TextView) builder.getWindow().findViewById(R.id.dia_tv2);


        //打开应用没有收藏过产品
        if (BmobIMApplication.needCollections.size()==0){
            Log.e("****", "打开应用后还没有收藏需求");
            for (int i = 0; i < UserModel.getInstance().getCurrentUser().getNeeds_collection().size(); i++) {
                for (int j = 0; j < BmobIMApplication.needs_list.size(); j++) {
                    if (UserModel.getInstance().getCurrentUser().getNeeds_collection().get(i)
                            .equals(needs.getObjectId())){
                        mImg_collection.setImageResource(R.drawable.collection_red);
                        Log.e("****当前收藏需求id:", UserModel.getInstance().getCurrentUser().getNeeds_collection().get(i));
                        Log.e("****全部收藏需求id:", needs.getObjectId());
                        collectionof = true;
                        //return;
                    }
                }
            }
        }else {//打开应用收藏过产品
            Log.e("****", "打开应用后收藏过需求");
            for (int i = 0; i < BmobIMApplication.needCollections.size(); i++) {
                if (BmobIMApplication.needCollections.get(i).getObjectId().equals(needs.getObjectId())) {
                    mImg_collection.setImageResource(R.drawable.collection_red);
                    collectionof = true;
                    return;
                }
            }
            for (int i = 0; i < UserModel.getInstance().getCurrentUser().getNeeds_collection().size(); i++) {
                for (int j = 0; j < BmobIMApplication.needs_list.size(); j++) {
                    if (UserModel.getInstance().getCurrentUser().getNeeds_collection().get(i)
                            .equals(needs.getObjectId())){
                        mImg_collection.setImageResource(R.drawable.collection_red);
                        collectionof = true;
                        //return;
                    }
                }
            }
        }



    }

    private void initData() {

    }

    private void initAdapter() {
    }


    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.need_message:
                Log.e("*******传递的用户", needs.getUsername());
                Log.e("*******当前用户", UserModel.getInstance().getCurrentUser().getUsername());
                if (needs.getUsername().equals(UserModel.getInstance().getCurrentUser().getUsername())) {
                    Toast.makeText(getApplicationContext(), "该需求为自己发布", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent1 = new Intent(getApplicationContext(), SearchUserActivity.class);
                intent1.putExtra("username", needs.getUsername());
                startActivity(intent1);


                Toast.makeText(this, "聊天界面", Toast.LENGTH_SHORT).show();
                break;

            case R.id.need_collection:
                if (needs.getUsername().equals(UserModel.getInstance().getCurrentUser().getUsername())) {
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
                            String id = needs.getObjectId();
                            Log.e("**需求的id", id);
                            u.addUnique("needs_collection", needs.getObjectId());
                            u.update(getApplicationContext(), new UpdateListener() {
                                @Override
                                public void onSuccess() {
                                    mImg_collection.setImageResource(R.drawable.collection_red);
                                    toast("收藏成功");
                                    //本地添加
                                    BmobIMApplication.needCollections.add(needs);
                                    collectionof = !collectionof;
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    Log.e("**失败", i + "");
                                    Log.e("**失败", s);

                                    toast("收藏失败");
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
                            removeId.add(needs.getObjectId());
                            u.removeAll("needs_collection", removeId);
                            u.update(getApplicationContext(), new UpdateListener() {
                                @Override
                                public void onSuccess() {
                                    Toast.makeText(getApplicationContext(), "取消收藏成功", Toast.LENGTH_SHORT).show();
                                    mImg_collection.setImageResource(R.drawable.collection_blue);
                                    Log.i("bmob", "成功");
                                    //本地删除
                                    BmobIMApplication.needCollections.remove(needs);
                                    collectionof = !collectionof;
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    Toast.makeText(getApplicationContext(), "取消收藏失败", Toast.LENGTH_SHORT).show();
                                    Log.i("**bmob", "i" + i + "");
                                    Log.i("**bmob", "s" + s);
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
            case R.id.need_phone:
                if (needs.getUsername().equals(UserModel.getInstance().getCurrentUser().getUsername())) {
                    Toast.makeText(getApplicationContext(), "该需求为自己发布", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(this, "打电话界面", Toast.LENGTH_SHORT).show();
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
        String number = needs.getNeed_phone();
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
    protected String title() {
        return "需求详情";
    }
}
