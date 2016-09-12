Welcome to MaxPicture!
===================


Can add multiple pictures and with the function of sliding around after click to enlarge the GridView 

----------
Demo
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
####Annotation 
    If need to join the ScrollView scroll bars, if you don't need to scroll bar, so you just need to use ImageGridView. 
    

----------


### Avtivity
```java
//Pictures photos can directly into the network address, local address images, and images of drawable 
ArrayList<String> photos = new ArrayList<String>();
//Controls the initialization 
ImageGridView image_gridView = null;
image_gridView=(ImageGridView)findViewById(R.id.image_gridView);
//        OnClick
        image_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent in = new Intent();
                in.setClass(MainActivity.this, MaxPictureActivity.class);
                in.putExtra("pos", i);//Will pass, I click for the current position 
                in.putStringArrayListExtra("imageAddress", photos);//Will pass,Photos to show the pictures of the collection address 
                startActivity(in);
            }
        });
/**
 *  MainActivity.this:Context
 *  photos:Photos to show the pictures of the collection address
 * */
        image_gridView.setAdapter(new GridViewImageAdapter(MainActivity.this, photos));
```

    Later there will be updated to include image amplifier to amplify the full screen after double-click local double refers to the scale, such as single refers to mobile operations 
----------

> Email:jiaosiyuan785@outlook.com  <br/> 
> Email:785220866@qq.com


