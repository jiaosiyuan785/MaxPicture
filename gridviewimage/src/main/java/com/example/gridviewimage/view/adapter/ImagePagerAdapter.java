package com.example.gridviewimage.view.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.example.gridviewimage.R;
import com.example.gridviewimage.view.acitvity.MaxPictureActivity;

import java.util.ArrayList;

/**
 * Created by 焦思远 on 2016/9/6.
 */
public class ImagePagerAdapter extends PagerAdapter {
    ArrayList<View> viewContainter = new ArrayList<View>();
    ArrayList<String> imageList = new ArrayList<String>();
    Context context;

    public ImagePagerAdapter(ArrayList<View> viewArrayList, Context context, ArrayList<String> imgList) {
        super();
        this.viewContainter = viewArrayList;
        this.context = context;
        this.imageList = imgList;
    }

    //viewpager中的组件数量
    @Override
    public int getCount() {
        return viewContainter.size();
    }

    //滑动切换的时候销毁当前的组件
    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object) {
        container.removeView(viewContainter.get(position));
    }

    //每次滑动的时候生成的组件
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewContainter.get(position % viewContainter.size()), 0);
        final ImageView imageView = (ImageView) ((MaxPictureActivity) context).findViewById(R.id.imageView);
        final ProgressBar progressBar = (ProgressBar) ((MaxPictureActivity) context).findViewById(R.id.progressBar2);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MaxPictureActivity) context).finish();
            }
        });
        Glide.with(context).load(imageList.get(position)).diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().thumbnail(0.1f).error(R.mipmap.image_error).into(
                new GlideDrawableImageViewTarget(imageView) {
                    @Override
                    public void onResourceReady(GlideDrawable drawable, GlideAnimation<? super GlideDrawable> anim) {
                        super.onResourceReady(drawable, anim);
                        progressBar.setVisibility(View.GONE);
                    }
                });
        return viewContainter.get(position % viewContainter.size());
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }
}
