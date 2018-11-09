package com.zqzr.licaitong.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.zqzr.licaitong.R;
import com.zqzr.licaitong.bean.Banner;
import com.zqzr.licaitong.utils.ActivityUtils;
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

    private ArrayList<Banner.Data> bannerList;
    private Context context;

    public BannerAdapter(Context context,ArrayList<Banner.Data> bannerList) {
        this.bannerList = bannerList;
        this.context = context;
    }

    @Override
    public View getView(ViewGroup container, int position) {

        ImageView imageView = new ImageView(container.getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setBackground(ActivityUtils.peek().getResources().getDrawable(R.mipmap.load_moren));
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //设置banner图片
        Utils.loadImg(context,imageView,bannerList.get(position).imageUrl,null);
        return imageView;
    }

    @Override
    public int getCount() {
        return bannerList.size();
    }
}
