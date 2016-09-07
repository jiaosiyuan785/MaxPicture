package com.example.gridviewimage.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.gridviewimage.R;

import java.util.List;

/**
 * Created by Administrator on 2016/5/29.
 */
public class GridViewImageAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<String> gridItemList;
    private Context context;


    public GridViewImageAdapter(Context context, List<String> list) {
        super();
        this.gridItemList = list;
        inflater = LayoutInflater.from(context);
        this.context = context;

    }

    @Override
    public int getCount() {
        if (null != gridItemList) {
            return gridItemList.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return gridItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.gridview_image_item, null);
            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
            viewHolder.image.setAdjustViewBounds(false);//设置边界对齐
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context).load(gridItemList.get(position)).diskCacheStrategy(DiskCacheStrategy.ALL).crossFade().thumbnail(0.1f).error(R.mipmap.image_error).placeholder(R.mipmap.ic_launcher).into(viewHolder.image);

        return convertView;
    }

    static class ViewHolder {
        public ImageView image;
    }
}
