package com.example.gridviewimage.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.gridviewimage.R;
import com.example.gridviewimage.view.acitvity.MaxPictureActivity;
import com.example.gridviewimage.view.controls.TestImageView;

import java.util.ArrayList;

/**
 * Created by Kate on 2016/9/6.
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

    @Override
    public int getCount() {
        return viewContainter.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object) {
        container.removeView(viewContainter.get(position));
    }

    public static Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();

        return bitmap;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewContainter.get(position % viewContainter.size()), 0);
        final TestImageView imageView = (TestImageView) ((MaxPictureActivity) context).findViewById(R.id.imageView);
        final ProgressBar progressBar = (ProgressBar) ((MaxPictureActivity) context).findViewById(R.id.progressBar2);
        imageView.setClickable(true);
        Glide.with(context).load(imageList.get(position)).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).thumbnail(0.1f).error(R.mipmap.image_error).into(
                new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                        imageView.setImageBitmap(bitmap);
                        imageView.imageBitmap(bitmap);
                        progressBar.setVisibility(View.GONE);
                    }
                }
        );
        return viewContainter.get(position % viewContainter.size());
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }
}