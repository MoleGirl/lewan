package cn.bmob.imdemo.util;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Administrator on 2017/1/9.
 */

public class MyDialog extends Dialog {
    public MyDialog(Context context) {
        super(context);
    }

    public MyDialog(Context context, View layout, int style) {

        super(context, style);

        setContentView(layout);

        Window window = getWindow();

        WindowManager.LayoutParams params = window.getAttributes();

        params.gravity = Gravity.CENTER;

        window.setAttributes(params);

    }
}
