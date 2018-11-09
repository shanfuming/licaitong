package com.zqzr.licaitong.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.zqzr.licaitong.R;
import com.zqzr.licaitong.ui.MainActivity;
import com.zqzr.licaitong.utils.ActivityUtils;
import com.zqzr.licaitong.utils.SPUtil;

import java.util.List;

public class GuideAdapter extends PagerAdapter {

    private List<View> mViews;
    private Activity mActivity;

    public GuideAdapter(List<View> mViews, Activity mActivity) {
        this.mViews = mViews;
        this.mActivity = mActivity;
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView(mViews.get(arg1));
    }

    @Override
    public int getCount() {
        if (mViews != null) {
            return mViews.size();
        }
        return 0;
    }

    @Override
    public Object instantiateItem(final View arg0, final int arg1) {
        ((ViewPager) arg0).addView(mViews.get(arg1), 0);

        if (arg1 == mViews.size() - 1) {
            View view = mViews.get(arg1);
            final ImageView imageView = (ImageView) view.findViewById(R.id.iv_guide3);
            TextView mStartWeiboImageButton = (TextView) arg0.findViewById(R.id.iv_start_weibo);
            mStartWeiboImageButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    setGuided();
                    //加入动画
                    goHome();
                }

            });
        }
        return mViews.get(arg1);
    }

    private void goHome() {
        ActivityUtils.push(MainActivity.class);
        ActivityUtils.pop(mActivity);
    }

    /**
     * 未使用过引导页，则弹出引导，如已弹出过，保存状态
     */
    private void setGuided() {
        SPUtil.setValue("isFirstOpen", false);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return (arg0 == arg1);
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(View arg0) {
    }

}