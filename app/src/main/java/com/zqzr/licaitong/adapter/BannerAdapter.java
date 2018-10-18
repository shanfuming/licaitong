package com.zqzr.licaitong.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.zqzr.licaitong.utils.Utils;

import java.util.ArrayList;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/16 16:11
 * <p/>
 * Description:
 */

public class BannerAdapter extends StaticPagerAdapter {

    private ArrayList<Integer> bannerList;

    public BannerAdapter(ArrayList<Integer> bannerList) {
        this.bannerList = bannerList;
    }

    @Override
    public View getView(ViewGroup container, int position) {

        ImageView imageView = new ImageView(container.getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //设置banner图片
//        Utils.loadImg(imageView,,null);
        imageView.setImageResource(bannerList.get(position));
        return imageView;
    }

    @Override
    public int getCount() {
        return bannerList.size();
    }
}
