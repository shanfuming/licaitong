package com.zqzr.licaitong.ui.find;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sys.blackcat.stickheaderlayout.IpmlScrollChangListener;
import com.sys.blackcat.stickheaderlayout.StickHeaderLayout;
import com.zqzr.licaitong.R;
import com.zqzr.licaitong.adapter.FragmentAdapter;
import com.zqzr.licaitong.base.BaseActivity;
import com.zqzr.licaitong.ui.find.fragment.DynamicFragment;
import com.zqzr.licaitong.ui.find.fragment.NewsFragment;
import com.zqzr.licaitong.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import cn.carbs.android.indicatorview.library.IndicatorView;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/13 12:57
 * <p/>
 * Description:
 */

public class FindActivity extends BaseActivity {
    private StickHeaderLayout mLayout;
    private FragmentAdapter mAdapter;
    private ViewPager mViewPager;
    private TextView mAppbarTitle;
    private IndicatorView mIndicatorViewEven;

    @Override
    protected void initView() {
        setContentView(R.layout.act_find);

        mLayout = (StickHeaderLayout) findViewById(R.id.stick_header_layout);
        mAdapter = new FragmentAdapter(getSupportFragmentManager(), getFragment());
        mViewPager = (ViewPager) findViewById(R.id.vp_view_pager);
        mAppbarTitle = (TextView) findViewById(R.id.appbar_title);
        mIndicatorViewEven = (IndicatorView)this.findViewById(R.id.indicator_view);
        mLayout.setScroll(new IpmlScrollChangListener() {
            private int rColor = 0xff;
            private int gColor = 0xff;
            private int bColor = 0x00;

            @Override
            public boolean isReadyForPull() {
                ViewGroup view = (ViewGroup) mAdapter.getItem(mViewPager.getCurrentItem()).getView();
                if (view != null) {
                    return Utils.isOnTop(view);
                }
                return true;
            }

            @Override
            public void onStartScroll() {

            }

            @Override
            public void onStopScroll() {

            }

            @Override
            public void onScrollChange(int dy, int totallDy) {
//                int aColor = (dy * 255) / totallDy;
//                mAppbarTitle.setBackgroundColor(Color.argb(aColor, rColor, gColor, bColor));
            }
        });
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mIndicatorViewEven.setViewPager(mViewPager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle("发现");
        setBackOption(false);
    }

    private List<Fragment> getFragment() {
        List<Fragment> list = new ArrayList<>();
        list.add(new NewsFragment());
        list.add(new DynamicFragment());
        return list;
    }
}
