package com.example.gridviewimage.view.acitvity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import com.example.gridviewimage.R;
import com.example.gridviewimage.view.adapter.ImagePagerAdapter;

import java.util.ArrayList;

public class MaxPictureActivity extends Activity {
    private TextView text_num;
    private int pos = 1;
    private ArrayList<String> imageList;
    ViewPager pager = null;
    ArrayList<View> viewContainter = new ArrayList<View>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_max_picture);
        getIntentValue();
        initView();
    }

    /*获取上个界面传递过来的数据*/
    private void getIntentValue() {
        pos = getIntent().getIntExtra("pos", 0) + 1;
        imageList = getIntent().getStringArrayListExtra("imageAddress");
    }

    /*初始化控件，设置初始数据*/
    private void initView() {
        pager = (ViewPager) this.findViewById(R.id.viewpager);
        text_num = (TextView) findViewById(R.id.text_num);
//        设置默认显示
        text_num.setText(pos + "/" + imageList.size());
//        添加图片到viewpage的集合中
        for (int i = 0; i < imageList.size(); i++) {
            viewContainter.add(LayoutInflater.from(this).inflate(R.layout.viewpager_page, null));
        }

        pager.addOnPageChangeListener(new PageChangeListener());
        pager.setAdapter(new ImagePagerAdapter(viewContainter,MaxPictureActivity.this,imageList));
        pager.setCurrentItem(pos - 1);
    }


    /*OnPageChangeListener实现*/
    private class PageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        //position为当前界面所在位置
        @Override
        public void onPageSelected(int position) {
            text_num.setText(position + 1 + "/" + imageList.size());
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}