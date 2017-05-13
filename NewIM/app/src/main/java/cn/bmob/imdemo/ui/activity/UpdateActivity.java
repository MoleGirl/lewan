package cn.bmob.imdemo.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import c.b.BP;
import c.b.PListener;
import cn.bmob.newim.BmobIM;
import cn.bmob.imdemo.BmobIMApplication;
import cn.bmob.imdemo.R;
import cn.bmob.imdemo.base.ParentWithNaviActivity;
import cn.bmob.imdemo.bean.User;
import cn.bmob.imdemo.model.UserModel;
import cn.bmob.imdemo.ui.LoginActivity;
import cn.bmob.imdemo.util.ClearWriteEditText;
import cn.bmob.imdemo.util.NToast;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2017/3/7.
 */

public class UpdateActivity extends ParentWithNaviActivity {


    String str;
    private Intent intent;
    ProgressDialog dialog;

    @Override
    protected String title() {
        return str;
    }

    @Override
    public Object right() {
        String right = "确定";
        if (str.equals("支付情况")) {
            right = "去支付";
        }
        return right;
    }


    private EditText oldPasswordEdit, newPasswordEdit, newPassword2Edit;
    private LinearLayout mLin_nickname, mLin_pass, mLin_pay;
    private TextView mLin_pay_state, mLin_pay_time;

    private ClearWriteEditText mUpdate_name;
    ProgressDialog progressDialog;

    @Override
    public ParentWithNaviActivity.ToolBarListener setToolBarListener() {
        return new ParentWithNaviActivity.ToolBarListener() {
            @Override
            public void clickLeft() {
                finish();
            }

            @Override
            public void clickRight() {
                update();
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        initNaviView();
        initView();
        initdata();
        initAdapter();
    }

    protected void initView() {
        mLin_nickname = (LinearLayout) findViewById(R.id.lin_nickname);
        mLin_pass = (LinearLayout) findViewById(R.id.lin_pass);
        mLin_pay = (LinearLayout) findViewById(R.id.lin_pay);
        mLin_pay_state = (TextView) findViewById(R.id.lin_pay_state);
        mLin_pay_time = (TextView) findViewById(R.id.lin_pay_time);
        mUpdate_name = (ClearWriteEditText) findViewById(R.id.update_name);

        intent = getIntent();
        String data = intent.getStringExtra("str");
        if (data.equals("昵称")) {
            str = "修改昵称";
            mLin_pass.setVisibility(View.GONE);
            mLin_nickname.setVisibility(View.VISIBLE);
            mLin_pay.setVisibility(View.GONE);
        } else if (data.equals("密码")) {
            str = "修改密码";
            mLin_pass.setVisibility(View.VISIBLE);
            mLin_pay.setVisibility(View.GONE);
            mLin_nickname.setVisibility(View.GONE);
        } else if (data.equals("支付")) {
            str = "支付情况";
            mLin_pass.setVisibility(View.GONE);
            mLin_nickname.setVisibility(View.GONE);
            mLin_pay.setVisibility(View.VISIBLE);
            if (UserModel.getInstance().getCurrentUser().getPay().equals("2")) {
                mLin_pay_state.setText("支付状态:未支付");
            } else {
                mLin_pay_state.setText("支付状态:已支付");
                // mLin_pay_time.setText("支付时间:" + UserModel.getInstance().getCurrentUser().getPay_time().toString());
            }

        }

        oldPasswordEdit = (EditText) findViewById(R.id.old_password);
        newPasswordEdit = (EditText) findViewById(R.id.new_password);
        newPassword2Edit = (EditText) findViewById(R.id.new_password2);
        //mConfirm = (Button) findViewById(R.id.update_pswd_confirm);
        //mConfirm.setOnClickListener(this);
        //mConfirm.setEnabled(false);
        oldPasswordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setConformButtonState();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        newPasswordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setConformButtonState();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        newPassword2Edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setConformButtonState();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setConformButtonState() {
        if (TextUtils.isEmpty(oldPasswordEdit.getText().toString().trim())
                && TextUtils.isEmpty(newPasswordEdit.getText().toString().trim())
                && TextUtils.isEmpty(oldPasswordEdit.getText().toString().trim())) {
            //mConfirm.setEnabled(false);
        } else {
            //mConfirm.setEnabled(true);
        }

    }

    private void initdata() {
    }

    private void initAdapter() {
    }

    public void update() {
        if (str.equals("修改昵称")) {
            if (mUpdate_name.getText().toString().trim().equals("")) {
                Toast.makeText(this, "昵称为空", Toast.LENGTH_LONG).show();
                return;
            }
            BmobUser newUser = new BmobUser();
            newUser.setUsername(mUpdate_name.getText().toString().trim());
            newUser.update(getApplication(), UserModel.getInstance().getCurrentUser().getObjectId(), new UpdateListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_LONG).show();
                    UserModel.getInstance().getCurrentUser().setUsername(mUpdate_name.getText().toString().trim());
                    BmobIMApplication.user.setUsername(mUpdate_name.getText().toString().trim());
                    Intent intent = new Intent();
                    intent.putExtra("nickname", mUpdate_name.getText().toString().trim());
                    setResult(201, intent);
                    finish();
                }

                @Override
                public void onFailure(int i, String s) {
                    Log.e("**i", i + "");
                    Log.e("**s", s);
                    if (i == 202) {
                        Toast.makeText(getApplicationContext(), mUpdate_name.getText().toString().trim() + "被占用", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "修改失败,请检查网络", Toast.LENGTH_LONG).show();
                    }
                }
            });

        } else if (str.equals("修改密码")) {
            String old = oldPasswordEdit.getText().toString().trim();
            String new1 = newPasswordEdit.getText().toString().trim();
            String new2 = newPassword2Edit.getText().toString().trim();
            // String cachePassword = sp.getString(SealConst.SEALTALK_LOGING_PASSWORD, "");
            if (TextUtils.isEmpty(old)) {
                NToast.shortToast(getApplicationContext(), "原密码不能为空");
                return;
            }
            if (TextUtils.isEmpty(new1)) {
                NToast.shortToast(getApplicationContext(), "新密码不能为空");
                return;
            }

            if (new1.length() < 6 || new1.length() > 16) {
                NToast.shortToast(getApplicationContext(), "密码必须为6-16位字符，区分大小写");
                return;
            }

            if (TextUtils.isEmpty(new2)) {
                NToast.shortToast(getApplicationContext(), "确认密码不能为空");
                return;
            }
//                if (!cachePassword.equals(old)) {
//                    NToast.shortToast(getApplicationContext(), "原密码填写错误");
//                    return;
//                }
            if (!new1.equals(new2)) {
                NToast.shortToast(getApplicationContext(), "填写的确认密码与新密码不一致");
                return;
            }

            if (new1.equals(old)) {
                NToast.shortToast(getApplicationContext(), "新旧密码一致");
                return;
            }
            progressDialog = new ProgressDialog(this);
            progressDialog.show();
            //LoadDialog.show(getApplicationContext());

            BmobUser.updateCurrentUserPassword(getApplicationContext(), old, new1, new UpdateListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(getApplicationContext(), "密码修改成功", Toast.LENGTH_LONG).show();
                    UserModel.getInstance().logout();
                    //可断开连接
                    BmobIM.getInstance().disConnect();
                    finish();
                    startActivity(LoginActivity.class, null);
                    //LoadDialog.dismiss(getApplicationContext());
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(int i, String s) {

                    Toast.makeText(getApplicationContext(), "密码修改失败", Toast.LENGTH_LONG).show();
                    //LoadDialog.dismiss(getApplicationContext());
                    progressDialog.dismiss();
                }
            });
        } else if (str.equals("支付情况")) {
            if (!UserModel.getInstance().getCurrentUser().getPay()) {
                //支付
                new AlertDialog.Builder(this).setMessage("支付选择")
                        .setNegativeButton("支付宝", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.e("****", "调用支付宝");
                                pay(true);
//                        BP.pay("丝网宝支付", "丝网宝会员", 20, true, new PListener() {
//
//                            @Override
//                            public void orderId(String s) {
//
//                            }
//
//                            @Override
//                            public void succeed() {
//                                Toast.makeText(getApplicationContext(), "支付成功", Toast.LENGTH_LONG).show();
//                                User u = new User();
//                                u.setPay(true);
//                                u.update(getApplicationContext(), UserModel.getInstance().getCurrentUser().getObjectId(), new UpdateListener() {
//                                    @Override
//                                    public void onSuccess() {
//                                        Toast.makeText(getApplicationContext(), "提交数据成功", Toast.LENGTH_LONG).show();
//                                        UserModel.getInstance().getCurrentUser().setPay(true);
//                                    }
//
//                                    @Override
//                                    public void onFailure(int i, String s) {
//                                        Toast.makeText(getApplicationContext(), "提交数据失败", Toast.LENGTH_LONG).show();
//
//                                    }
//                                });
//                            }
//
//                            @Override
//                            public void fail(int i, String s) {
//                                Toast.makeText(getApplicationContext(), "支付未认证", Toast.LENGTH_LONG).show();
//                                Log.e("****i", i + "");
//                                Log.e("****s", s );
//
//                            }
//
//                            @Override
//                            public void unknow() {
//
//                            }
//                        });
                            }
                        }).setPositiveButton("微信", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.e("****", "调用微信");
                        pay(false);
//                        BP.pay("丝网宝支付", "丝网宝会员", 20, false, new PListener() {
//                            @Override
//                            public void orderId(String s) {
//
//                            }
//
//                            @Override
//                            public void succeed() {
//                                Toast.makeText(getApplicationContext(), "支付成功", Toast.LENGTH_LONG).show();
//                                User u = new User();
//                                u.setPay(true);
//                                u.update(getApplicationContext(), UserModel.getInstance().getCurrentUser().getObjectId(), new UpdateListener() {
//                                    @Override
//                                    public void onSuccess() {
//                                        Toast.makeText(getApplicationContext(), "提交数据成功", Toast.LENGTH_LONG).show();
//                                        UserModel.getInstance().getCurrentUser().setPay(true);
//                                    }
//
//                                    @Override
//                                    public void onFailure(int i, String s) {
//                                        Toast.makeText(getApplicationContext(), "提交数据失败", Toast.LENGTH_LONG).show();
//
//                                    }
//                                });
//                            }
//
//                            @Override
//                            public void fail(int i, String s) {
//
//                            }
//
//                            @Override
//                            public void unknow() {
//
//                            }
//                        });
                    }
                }).show();

            } else {
                Toast.makeText(getApplication(), "您已支付", Toast.LENGTH_LONG).show();
            }
        }

    }

    private static final int REQUESTPERMISSION = 101;
    // 此为微信支付插件的官方最新版本号,请在更新时留意更新说明
    int PLUGINVERSION = 7;

    /**
     * 调用支付
     *
     * @param alipayOrWechatPay 支付类型，true为支付宝支付,false为微信支付
     */
    void pay(final boolean alipayOrWechatPay) {
        if (alipayOrWechatPay) {//支付宝支付
            if (!checkPackageInstalled("com.eg.android.AlipayGphone",
                    "https://www.alipay.com")) { // 支付宝支付要求用户已经安装支付宝客户端
                Toast.makeText(getApplicationContext(), "请安装支付宝客户端", Toast.LENGTH_SHORT)
                        .show();
                return;
            }
        } else {
            if (checkPackageInstalled("com.tencent.mm", "http://weixin.qq.com")) {// 需要用微信支付时，要安装微信客户端，然后需要插件
                // 有微信客户端，看看有无微信支付插件
                int pluginVersion = BP.getPluginVersion(this);
                Log.e("**当前版本号*", pluginVersion + "");
                //if (pluginVersion < PLUGINVERSION) {// 为0说明未安装支付插件,
                if (pluginVersion == 0) {
                    // 否则就是支付插件的版本低于官方最新版
                    Toast.makeText(
                            getApplicationContext(),
                            pluginVersion == 0 ? "监测到本机尚未安装支付插件,无法进行支付,请先安装插件(无流量消耗)"
                                    : "监测到本机的支付插件不是最新版,最好进行更新,请先更新插件(无流量消耗)",
                            Toast.LENGTH_SHORT).show();
                    installApk("bp.db");
                }
//                    installBmobPayPlugin("bp.db");


                //return;
                //   }
            } else {// 没有安装微信
                Toast.makeText(getApplicationContext(), "请安装微信客户端", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        showDialog("正在获取订单...");
        //final String name = getName();

//        try {
//            Intent intent = new Intent(Intent.ACTION_MAIN);
//            intent.addCategory(Intent.CATEGORY_LAUNCHER);
//            ComponentName cn = new ComponentName("com.bmob.app.sport",
//                    "com.bmob.app.sport.wxapi.BmobActivity");
//            intent.setComponent(cn);
//            this.startActivity(intent);
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }

        BP.pay("丝网宝支付", "丝网宝会员", 20, alipayOrWechatPay, new PListener() {

            // 因为网络等原因,支付结果未知(小概率事件),出于保险起见稍后手动查询
            @Override
            public void unknow() {
                Toast.makeText(getApplicationContext(), "支付结果未知,请稍后手动查询", Toast.LENGTH_SHORT)
                        .show();
                //tv.append(name + "'s pay status is unknow\n\n");
                hideDialog();
            }

            // 支付成功,如果金额较大请手动查询确认
            @Override
            public void succeed() {
                Toast.makeText(getApplicationContext(), "支付成功", Toast.LENGTH_LONG).show();
                User u = new User();
                u.setPay(true);
                u.update(getApplicationContext(), UserModel.getInstance().getCurrentUser().getObjectId(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getApplicationContext(), "提交数据成功", Toast.LENGTH_LONG).show();
                        UserModel.getInstance().getCurrentUser().setPay(true);
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(getApplicationContext(), "提交数据失败", Toast.LENGTH_LONG).show();

                    }
                });


                //tv.append(name + "'s pay status is success\n\n");
                hideDialog();
            }

            // 无论成功与否,返回订单号
            @Override
            public void orderId(String orderId) {
                // 此处应该保存订单号,比如保存进数据库等,以便以后查询
                //order.setText(orderId);
                //tv.append(name + "'s orderid is " + orderId + "\n\n");
                showDialog("获取订单成功!请等待跳转到支付页面~");
            }

            // 支付失败,原因可能是用户中断支付操作,也可能是网络原因
            @Override
            public void fail(int code, String reason) {
                Log.e("***支付失败错误码",code+"");
                // 当code为-2,意味着用户中断了操作
                // code为-3意味着没有安装BmobPlugin插件
                if (code == -3) {
                    Toast.makeText(
                            getApplicationContext(),
                            "监测到你尚未安装支付插件,无法进行支付,请先安装插件(已打包在本地,无流量消耗),安装结束后重新支付",
                            Toast.LENGTH_SHORT).show();
//                    installBmobPayPlugin("bp.db");
                    installApk("bp.db");
                } else {
                    Toast.makeText(getApplicationContext(), "支付中断!", Toast.LENGTH_SHORT)
                            .show();
                }
                //tv.append(name + "'s pay status is fail, error code is \n"
                //  + code + " ,reason is " + reason + "\n\n");
                hideDialog();
            }
        });
    }


    /**
     * 检查某包名应用是否已经安装
     *
     * @param packageName 包名
     * @param browserUrl  如果没有应用市场，去官网下载
     * @return
     */
    private boolean checkPackageInstalled(String packageName, String browserUrl) {
        try {
            // 检查是否有支付宝客户端
            getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            // 没有安装支付宝，跳转到应用市场
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=" + packageName));
                startActivity(intent);
            } catch (Exception ee) {// 连应用市场都没有，用浏览器去支付宝官网下载
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(browserUrl));
                    startActivity(intent);
                } catch (Exception eee) {
                    Toast.makeText(getApplicationContext(),
                            "您的手机上没有没有应用市场也没有浏览器，请安装支付宝/微信吧",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
        return false;
    }


    private void installApk(String s) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUESTPERMISSION);
        } else {
            installBmobPayPlugin(s);
        }
    }

    void showDialog(String message) {
        try {
            if (dialog == null) {
                dialog = new ProgressDialog(this);
                dialog.setCancelable(true);
            }
            dialog.setMessage(message);
            dialog.show();
        } catch (Exception e) {
            // 在其他线程调用dialog会报错
        }
    }

    void hideDialog() {
        if (dialog != null && dialog.isShowing())
            try {
                dialog.dismiss();
            } catch (Exception e) {
            }
    }


    /**
     * 安装assets里的apk文件
     *
     * @param fileName
     */
    void installBmobPayPlugin(String fileName) {
        try {
            InputStream is = getAssets().open(fileName);
            File file = new File(Environment.getExternalStorageDirectory()
                    + File.separator + fileName + ".apk");
            if (file.exists())
                file.delete();
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] temp = new byte[1024];
            int i = 0;
            while ((i = is.read(temp)) > 0) {
                fos.write(temp, 0, i);
            }
            fos.close();
            is.close();

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.parse("file://" + file),
                    "application/vnd.android.package-archive");
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
