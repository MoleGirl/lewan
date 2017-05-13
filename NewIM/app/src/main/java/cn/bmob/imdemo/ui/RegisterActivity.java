package cn.bmob.imdemo.ui;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.imdemo.R;
import cn.bmob.imdemo.base.ParentWithNaviActivity;
import cn.bmob.imdemo.event.FinishEvent;
import cn.bmob.imdemo.model.BaseModel;
import cn.bmob.imdemo.model.UserModel;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;


/**注册界面
 * @author :smile
 * @project:RegisterActivity
 * @date :2016-01-15-18:23
 */
public class RegisterActivity extends ParentWithNaviActivity {
    @Bind(R.id.et_emile)
    EditText et_emile;
    @Bind(R.id.et_username)
    EditText et_username;
    @Bind(R.id.et_password)
    EditText et_password;

    @Bind(R.id.btn_register)
    Button btn_register;

    @Bind(R.id.et_password_again)
    EditText et_password_again;
    public int T = 60; //倒计时时长
    private Handler mHandler = new Handler();

    Boolean isEmail = false;
    @Override
    protected String title() {
        return "注册";
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initNaviView();
    }

    @OnClick(R.id.btn_register)
    public void onRegisterClick(View view){
        if (!isEmail(et_emile.getText().toString())){
            Toast.makeText(this, "邮箱格式不正确", Toast.LENGTH_LONG).show();
            return;
        }

        if (et_emile.getText().toString().equals("")){
            Toast.makeText(this,"邮箱不能为空",Toast.LENGTH_LONG).show();
            return;
        }


        if(et_password.getText().toString().trim().length()<6||et_password.getText().toString().trim().length()>15){
            Toast.makeText(this, "密码大于6位,小于15位", Toast.LENGTH_LONG).show();
            return;
        }

        UserModel.getInstance().register(et_emile.getText().toString(),et_username.getText().toString(), et_password.getText().toString(),et_password_again.getText().toString(),new LogInListener() {
            @Override
            public void done(Object o, BmobException e) {
                if(e==null){
                    Toast.makeText(RegisterActivity.this,"注册成功,邮件已发送,请验证",Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(new FinishEvent());
                    //startActivity(MainActivity.class, null, true);
                    startActivity(LoginActivity.class, null, true);
                }else{
                    if(e.getErrorCode()== BaseModel.CODE_NOT_EQUAL){
                        et_password_again.setText("");
                    }
                    toast(e.getMessage()+"("+e.getErrorCode()+")");
                }
            }
        });
    }
    //判断email格式是否正确
    public boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        //String telRegex = "[1][358]\\d{9}";手机号
        return m.matches();
    }


    /**
     * 自定义倒计时类，实现Runnable接口
     */
//    class MyCountDownTimer implements Runnable{
//
//        @Override
//        public void run() {
//
//            //倒计时开始，循环
//            while (T > 0) {
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        but_code.setClickable(false);
//                        but_code.setText(T + "");
//                    }
//                });
//                try {
//                    Thread.sleep(1000); //强制线程休眠1秒，就是设置倒计时的间隔时间为1秒。
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                T--;
//            }
//
//            //倒计时结束，也就是循环结束
//            mHandler.post(new Runnable() {
//                @Override
//                public void run() {
//                    but_code.setClickable(true);
//                    but_code.setText("获取验证码");
//                }
//            });
//            T = 60; //最后再恢复倒计时时长
//        }
//    }

}
