package cn.bmob.imdemo.util;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2017/2/9.
 */

public class Myrecycler extends RecyclerView.ItemDecoration {
    private int space;

    public Myrecycler(int space) {
        this.space = space;
    }
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if(parent.getChildPosition(view) != 0)
            outRect.top = space;
    }
}
