package com.zqzr.licaitong.ui.home;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sys.blackcat.stickheaderlayout.IpmlScrollChangListener;
import com.sys.blackcat.stickheaderlayout.StickHeaderLayout;
import com.zqzr.licaitong.R;
import com.zqzr.licaitong.adapter.FragmentAdapter;
import com.zqzr.licaitong.base.BaseActivity;
import com.zqzr.licaitong.ui.find.fragment.DynamicFragment;
import com.zqzr.licaitong.ui.find.fragment.NewsFragment;
import com.zqzr.licaitong.utils.ActivityUtils;
import com.zqzr.licaitong.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import cn.carbs.android.indicatorview.library.IndicatorView;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/16 13:44
 * <p/>
 * Description:
 */

public class TenderDetailAct extends BaseActivity implements View.OnClickListener {

    private StickHeaderLayout mLayout;
    private FragmentAdapter mAdapter;
    private ViewPager mViewPager;
    private TextView mTvTitle,mTvPredictIncome,mTvLimit,mTvStart,mTvsubscribe,mTvRepayment,mTvIncremental,mTvReduce,mTvAdd,mTvSum,mTvSubscribe;
    private IndicatorView mIndicatorViewEven;
    private LinearLayout mLlBack;

    @Override
    protected void initView() {
        setContentView(R.layout.act_tender_detail);

        mLayout = (StickHeaderLayout) findViewById(R.id.stick_header_layout);
        mAdapter = new FragmentAdapter(getSupportFragmentManager(), getFragment());
        mViewPager = (ViewPager) findViewById(R.id.vp_view_pager);
        mIndicatorViewEven = (IndicatorView) this.findViewById(R.id.indicator_view);
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

        //上部控件
        mLlBack = (LinearLayout) findViewById(R.id.ll_back);
        mTvTitle = (TextView) findViewById(R.id.tv_detail_title);
        mTvPredictIncome = (TextView) findViewById(R.id.tv_detial_predictIncome);
        mTvLimit = (TextView) findViewById(R.id.tv_detail_limit);
        mTvStart = (TextView) findViewById(R.id.tv_detail_start);
        mTvsubscribe = (TextView) findViewById(R.id.tv_detail_subscribe);
        mTvRepayment = (TextView) findViewById(R.id.tv_repayment);
        mTvIncremental = (TextView) findViewById(R.id.tv_incremental);
        //预约
        mTvReduce = (TextView) findViewById(R.id.tv_reduce);
        mTvAdd = (TextView) findViewById(R.id.tv_add);
        mTvSum = (TextView) findViewById(R.id.tv_sum);
        mTvSubscribe = (TextView) findViewById(R.id.tv_subscribe);

        mLlBack.setOnClickListener(this);
        mTvReduce.setOnClickListener(this);
        mTvAdd.setOnClickListener(this);
        mTvsubscribe.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    private List<Fragment> getFragment() {
        List<Fragment> list = new ArrayList<>();
        list.add(new NewsFragment());
        list.add(new DynamicFragment());
        return list;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_back:
                ActivityUtils.pop();
                break;
            case R.id.tv_reduce:
                break;
            case R.id.tv_add:
                break;
            case R.id.tv_subscribe:
                break;
        }
    }
}