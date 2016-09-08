Welcome to MaxPicture!
===================


可以放入多张图片并带有点击放大后左右滑动的GridView

----------


示例
-------------

![image](https://github.com/jiaosiyuan785/MaxPicture/blob/master/gridviewimage/gif01.gif)

----------



### SDK
Sdk             | Version
----------------|----
minSdkVersion   | 14
targetSdkVersion| 24
#### <i class="icon-file"></i> Compile

compile 'com.github.maxpicture:gridviewimage:1.0.0'


----------


### View
```
<ScrollView
        android:id="@+id/scorll"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <com.example.gridviewimage.view.controls.ImageGridView
            android:id="@+id/image_gridView"
            android:layout_width="wrap_content"
            android:layout_height="match_parent"
            android:layout_margin="10dp"
            android:columnWidth="60dp"
            android:gravity="center"
            android:horizontalSpacing="10dp"
            android:numColumns="3"
            android:stretchMode="columnWidth"
            android:verticalSpacing="10dp" />
    </ScrollView>
```
####注释
    如果需要滑动条就加入ScrollView,如果不需要滑动条，那么只需要使用ImageGridView即可.


----------


### Avtivity
```java
ArrayList<String> photos = new ArrayList<String>();
//控件初始化
ImageGridView image_gridView = null;
image_gridView=(ImageGridView)findViewById(R.id.image_gridView);
//        单点事件
        image_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent in = new Intent();
                in.setClass(MainActivity.this, MaxPictureActivity.class);
                in.putExtra("pos", i);//必传项,i为当前点击的位置
                in.putStringArrayListExtra("imageAddress", photos);//必传项,photos为要显示的图片地址集合
                startActivity(in);
            }
        });
/**
 *  MainActivity.this:为当前界面上下文
 *  photos:photos为要显示的图片地址集合
 * */
        image_gridView.setAdapter(new GridViewImageAdapter(MainActivity.this, photos));
```
----------

>后期还会有更新，包括图片放大全屏后的双击局部放大，双指缩放，单指移动等操作
----------

> Email:jiaosiyuan785@outlook.com
> 


