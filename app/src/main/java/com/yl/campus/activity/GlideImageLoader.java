package com.yl.campus.activity;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yl.campus.R;
import com.youth.banner.loader.ImageLoader;

/**
 * 图片轮播的图片加载器
 * Created by Luke on 2017/9/27.
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path).placeholder(R.mipmap.ic_launcher)
                .centerCrop().into(imageView);
    }
}
