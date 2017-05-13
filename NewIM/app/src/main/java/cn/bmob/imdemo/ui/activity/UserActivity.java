package cn.bmob.imdemo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;

import java.io.File;
import java.util.ArrayList;

import cn.bmob.imdemo.BmobIMApplication;
import cn.bmob.imdemo.R;
import cn.bmob.imdemo.base.ParentWithNaviActivity;
import cn.bmob.imdemo.bean.User;
import cn.bmob.imdemo.model.UserModel;
import cn.bmob.imdemo.util.BottomMenuDialog;
import cn.bmob.imdemo.util.GlideLoader;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by Administrator on 2017/3/7.
 */

public class UserActivity extends ParentWithNaviActivity implements View.OnClickListener {

    User user;
    Intent intent;
    private SimpleDraweeView mUser_avatar;
    private TextView mTv_my_username, mTv_my_phone, mTv_my_pass,mTv_my_pay;
    private RelativeLayout mRl_my_portrait, mRl_my_username, mRl_my_telephone, mRl_my_pass,mRl_my_pay;

    private BottomMenuDialog dialog;//上传图片dia
    private ArrayList<String> path = new ArrayList<>();
    private final static int REQUEST_CODE = 1;
    String url;

    @Override
    protected String title() {
        return "个人资料";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initNaviView();
        initView();
        initdata();
        initAdapter();
    }

    protected void initView() {
        user = (User) getBundle().getSerializable("u");
        mUser_avatar = (SimpleDraweeView) findViewById(R.id.user_avatar);
        mTv_my_username = (TextView) findViewById(R.id.tv_my_username);
        mTv_my_phone = (TextView) findViewById(R.id.tv_my_phone);//手机
        mTv_my_pass = (TextView) findViewById(R.id.tv_my_pass);
        mTv_my_pay = (TextView) findViewById(R.id.tv_my_pay);//支付
        mRl_my_portrait = (RelativeLayout) findViewById(R.id.rl_my_portrait);
        mRl_my_username = (RelativeLayout) findViewById(R.id.rl_my_username);
        mRl_my_telephone = (RelativeLayout) findViewById(R.id.rl_my_telephone);
        mRl_my_pass = (RelativeLayout) findViewById(R.id.rl_my_pass);
        mRl_my_pay = (RelativeLayout) findViewById(R.id.rl_my_pay);


        mRl_my_portrait.setOnClickListener(this);
        mRl_my_username.setOnClickListener(this);
        mRl_my_telephone.setOnClickListener(this);
        mRl_my_pass.setOnClickListener(this);
        mRl_my_pay.setOnClickListener(this);

        mTv_my_username.setText(user.getUsername());
        if (BmobIMApplication.path!=null){//本地不为空
            Glide.with(getApplicationContext()).load(BmobIMApplication.path).centerCrop().into(mUser_avatar);
        }else {//本地为空
            if (UserModel.getInstance().getCurrentUser().getHand()!= null) {
                Glide.with(getApplicationContext()).load(UserModel.getInstance().getCurrentUser().getHand().getUrl()).centerCrop().into(mUser_avatar);
            }
        }
    }

    private void initdata() {
        Log.e("****支付状态", UserModel.getInstance().getCurrentUser().getPay()+"");
        //未支付
        if (!UserModel.getInstance().getCurrentUser().getPay()){
            mTv_my_pay.setText("未支付");
        }else {
            mTv_my_pay.setText("已支付");
        }
    }

    private void initAdapter() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_my_portrait:
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
                                .singleSelect()
                                .crop()
                                // 多选时的最大数量   （默认 9 张）
                                //.mutiSelectMaxSize(1)
                                // 已选择的图片路径
                                //.pathList(path)
                                // 拍照后存放的图片路径（默认 /temp/picture）
                                //.filePath("/ImageSelector/Pictures")
                                // 开启拍照功能 （默认开启）
                                //.showCamera()
                                .requestCode(1000)
                                .build();
                        ImageSelector.open(UserActivity.this, imageConfig);   // 开启图片选择器
                    }
                });
                dialog.show();
                break;
            case R.id.rl_my_username:
                intent = new Intent();
                intent.setClass(this, UpdateActivity.class);
                intent.putExtra("str", "昵称");
                startActivityForResult(intent, 200);
                break;
            case R.id.rl_my_pay://支付情况
                intent = new Intent();
                intent.setClass(this, UpdateActivity.class);
                intent.putExtra("str", "支付");
                startActivityForResult(intent, 200);
                break;
            case R.id.rl_my_pass:
                intent = new Intent();
                intent.setClass(this, UpdateActivity.class);
                intent.putExtra("str", "密码");
                startActivityForResult(intent, 200);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1000 && resultCode == RESULT_OK && data != null) {
            Toast.makeText(this, "回来了", Toast.LENGTH_SHORT).show();
            path.clear();
            path = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            for (int i = 0; i < path.size(); i++) {
                Log.e("****图片路径", path.get(i));
            }
            path.add(path.get(0));
            //上传
            final BmobFile bmobFile = new BmobFile(new File(path.get(0)));
            bmobFile.upload(getApplication(), new UploadFileListener() {
                @Override
                public void onSuccess() {
                    User u = new User();
                    u.setObjectId(UserModel.getInstance().getCurrentUser().getObjectId());
                    u.setHand(bmobFile);
                    //u.addUnique("hand",bmobFile);
                    //u.setAvatar(path.get(0));
                    u.update(getApplicationContext(), UserModel.getInstance().getCurrentUser().getObjectId(), new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(getApplicationContext(), "设置成功", Toast.LENGTH_LONG).show();
                            Glide.with(getApplicationContext()).load(path.get(0)).centerCrop().into(mUser_avatar);
                            BmobIMApplication.path = path.get(0);//设置给本地
//                            BmobQuery<User> query = new BmobQuery<User>();
//                            query.addWhereEqualTo("username", BmobIMApplication.user.getUsername());
//                            query.findObjects(getApplicationContext(), new FindListener<User>() {
//                                @Override
//                                public void onSuccess(List<User> list) {
//                                    for (int i = 0; i < list.size(); i++) {
//                                        Toast.makeText(getApplicationContext(), "本地头像更新成功", Toast.LENGTH_LONG).show();
//                                        UserModel.getInstance().getCurrentUser().setHand(list.get(i).getHand());
//                                        return;
//                                    }
//                                }
//
//                                @Override
//                                public void onError(int i, String s) {
//
//                                }
//                            });

                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Log.e("***i", i + "");
                            Log.e("***s", s);
                            Toast.makeText(getApplicationContext(), "上传失败,请检查网络", Toast.LENGTH_LONG).show();
                        }
                    });
//                    u.signUp(getApplicationContext(), new SaveListener() {
//                        @Override
//                        public void onSuccess() {
//                            Toast.makeText(getApplicationContext(),"设置成功",Toast.LENGTH_LONG).show();
//                            Glide.with(getApplicationContext()).load(path.get(0)).centerCrop().into(mUser_avatar);
//                        }
//
//                        @Override
//                        public void onFailure(int i, String s) {
//                            Log.e("***i",i+"");
//                            Log.e("***s",s);
//                            Toast.makeText(getApplicationContext(),"上传失败,,请检查网络",Toast.LENGTH_LONG).show();
//                        }
//                    });
                }

                @Override
                public void onFailure(int i, String s) {

                }
            });
        } else if (requestCode == 200 && resultCode == 201 && data != null) {
            Bundle bundle = data.getExtras();
            mTv_my_username.setText(bundle.getString("nickname"));
        } else if (data == null) {
            Toast.makeText(this, "没有选择", Toast.LENGTH_SHORT).show();
        }
    }
}
