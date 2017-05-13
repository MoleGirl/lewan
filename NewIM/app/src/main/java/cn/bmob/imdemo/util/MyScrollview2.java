package cn.bmob.imdemo.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2017/1/3.
 */

public class MyScrollview2 extends ScrollView {


    public MyScrollview2(Context context) {
        super(context);
    }

    public MyScrollview2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollview2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        return false;
    }
}
