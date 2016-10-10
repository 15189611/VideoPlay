package com.charles.videoplay.util;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by Charles on 2016/10/11.
 */

public class ImageLoadUtils {
    /**
     * 设置简单的圆头像
     *
     * @param headIconUrl
     */
    public static void setSimpleCircleHeadIcon(Context context,
                                               String headIconUrl, @DrawableRes int defaultResId, final ImageView view) {
        if (TextUtils.isEmpty(headIconUrl)) {
            setDefaultSimpleCircleHeadIcon(context, defaultResId, view);
            return;
        }
        Glide.with(context).load(headIconUrl).placeholder(defaultResId)
                .centerCrop().error(defaultResId).crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new SimpleCircleTransform(context)).into(view);
    }

    /**
     * 设置默认的简单的圆头像
     *
     */
    public static void setDefaultSimpleCircleHeadIcon(Context context,
                                                      @DrawableRes int defaultResId, final ImageView view) {
        Glide.with(context).load(defaultResId).placeholder(defaultResId)
                .crossFade().centerCrop().error(defaultResId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new SimpleCircleTransform(context)).into(view);

    }

    /**
     * 异步加载图片
     * @param imgUrl 图片url
     * @param imageView
     * @param drawableRes 默认图片资源id
     */
    public static void displayImage(Context context,String imgUrl,ImageView imageView, int drawableRes){
        Glide
                .with(context)
                .load(imgUrl)
                .centerCrop()
                .placeholder(drawableRes)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);

    }

    public static void displayListViewImage(Context context,String imgUrl,ImageView imageView, int drawableRes){
        Glide
                .with(context)
                .load(imgUrl)
                .centerCrop()
                .placeholder(drawableRes)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }
}
