package com.example.gridviewimage.view.acitvity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import com.example.gridviewimage.R;
import com.example.gridviewimage.view.adapter.ImagePagerAdapter;
import com.example.gridviewimage.view.controls.ImageViewPager;

import java.util.ArrayList;
/**
 * Created by Kate on 2016/5/29.
 */
public class MaxPictureActivity extends Activity {
    private TextView text_num;
    private int pos = 1;
    private ArrayList<String> imageList;
    ImageViewPager pager = null;
    ArrayList<View> viewContainter = new ArrayList<View>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_max_picture);
        getIntentValue();
        initView();
    }

    private void getIntentValue() {
        pos = getIntent().getIntExtra("pos", 0) + 1;
        imageList = getIntent().getStringArrayListExtra("imageAddress");
    }

    private void initView() {
        pager = (ImageViewPager) this.findViewById(R.id.viewpager);
        text_num = (TextView) findViewById(R.id.text_num);
        text_num.setText(pos + "/" + imageList.size());
        for (int i = 0; i < imageList.size(); i++) {
            viewContainter.add(LayoutInflater.from(this).inflate(R.layout.viewpager_page, null));
        }

        pager.addOnPageChangeListener(new PageChangeListener());
        pager.setAdapter(new ImagePagerAdapter(viewContainter,MaxPictureActivity.this,imageList));
        pager.setCurrentItem(pos - 1);
    }


    private class PageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            text_num.setText(position + 1 + "/" + imageList.size());
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}