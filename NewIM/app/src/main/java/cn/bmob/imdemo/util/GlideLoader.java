package cn.bmob.imdemo.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yancy.imageselector.ImageLoader;

import cn.bmob.imdemo.R;

/**
 * Created by Administrator on 2017/2/26.
 */

public class GlideLoader implements ImageLoader {


    @Override
    public void displayImage(Context context, String path, ImageView imageView) {
        Glide.with(context).load(path).
                placeholder(R.drawable.imageselector_photo)
                .centerCrop()
                .into(imageView);
    }
}
