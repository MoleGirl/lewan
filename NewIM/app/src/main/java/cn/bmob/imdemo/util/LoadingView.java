package cn.bmob.imdemo.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import cn.bmob.imdemo.R;

/**
 * Created by Administrator on 2017/3/2.
 */

public class LoadingView extends LinearLayout {

    public LoadingView(Context context) {
        super(context);
        init();
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.loading_view, this);
    }

}
