package cn.bmob.imdemo.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.imdemo.BmobIMApplication;
import cn.bmob.imdemo.R;
import cn.bmob.imdemo.base.ParentWithNaviActivity;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.ResetPasswordByEmailListener;

/**
 * Created by Administrator on 2017/3/13.
 */

public class ForgetpasswordActivity extends ParentWithNaviActivity implements View.OnClickListener{

    private EditText mEt_emile,mEt_username;
    private Button mBtn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        initNaviView();//初始化导航条
        initView();
        initData();
        initAdapter();
    }
    public void initView() {
        mEt_emile = (EditText) findViewById(R.id.et_emile);
        mEt_username = (EditText) findViewById(R.id.et_username);
        mBtn_register = (Button) findViewById(R.id.btn_register);
        mBtn_register.setOnClickListener(this);
    }
    private void initData() {


    }

    private void initAdapter() {
    }

    @Override
    protected String title() {
        return "忘记密码";
    }

    @Override
    public void onClick(View v) {
        if (!isEmail(mEt_emile.getText().toString())){
            Toast.makeText(this, "邮箱格式不正确", Toast.LENGTH_LONG).show();
            return;
        }

        if (mEt_emile.getText().toString().equals("")){
            Toast.makeText(this,"邮箱不能为空",Toast.LENGTH_LONG).show();
            return;
        }

        if (mEt_username.getText().toString().equals("")){
            Toast.makeText(this,"昵称不能为空",Toast.LENGTH_LONG).show();
            return;
        }
        if (BmobIMApplication.users.size()>0){
            for (int i = 0; i <BmobIMApplication.users.size() ; i++) {
                if (BmobIMApplication.users.get(i).getUsername().equals(mEt_username.getText().toString().trim())){
                    if (BmobIMApplication.users.get(i).getEmail().equals("")){
                        Toast.makeText(this,"该用户没有设置邮箱",Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (!BmobIMApplication.users.get(i).getEmail().equals(mEt_emile.getText().toString().trim())){
                        Toast.makeText(this,"该用户邮箱不正确",Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (!BmobIMApplication.users.get(i).getEmailVerified()){
                        Toast.makeText(this,"该用户邮箱没有验证",Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (BmobIMApplication.users.get(i).getEmail().equals(mEt_emile.getText().toString().trim())
                            &&BmobIMApplication.users.get(i).getEmailVerified()){
                        BmobUser.resetPasswordByEmail(getApplicationContext(), mEt_emile.getText().toString().trim(), new ResetPasswordByEmailListener() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(getApplicationContext()
                                        ,"重置密码请求成功，请到" + mEt_emile.getText().toString().trim() + "邮箱进行密码重置操作"
                                        ,Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                Toast.makeText(getApplicationContext(),"请求错误,请检查网络",Toast.LENGTH_LONG).show();
                            }
                        });

                       return;
                    }

                }else {
                    Toast.makeText(this, "没有该用户", Toast.LENGTH_LONG).show();
                }
            }
        }else {
            Toast.makeText(this, "没有该用户", Toast.LENGTH_LONG).show();
            return;
        }

    }

    //判断email格式是否正确
    public boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        //String telRegex = "[1][358]\\d{9}";手机号
        return m.matches();
    }
}
