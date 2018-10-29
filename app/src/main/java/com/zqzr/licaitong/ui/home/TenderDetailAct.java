package com.zqzr.licaitong.ui.home;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.sys.blackcat.stickheaderlayout.IpmlScrollChangListener;
import com.sys.blackcat.stickheaderlayout.StickHeaderLayout;
import com.zqzr.licaitong.R;
import com.zqzr.licaitong.adapter.FragmentAdapter;
import com.zqzr.licaitong.base.BaseActivity;
import com.zqzr.licaitong.base.BaseParams;
import com.zqzr.licaitong.base.Constant;
import com.zqzr.licaitong.bean.ProductDetail;
import com.zqzr.licaitong.http.OKGO_GetData;
import com.zqzr.licaitong.ui.find.fragment.DynamicFragment;
import com.zqzr.licaitong.ui.find.fragment.NewsFragment;
import com.zqzr.licaitong.ui.home.fragment.ProjectAuditFragment;
import com.zqzr.licaitong.ui.home.fragment.ProjectIntroduceBaoLiFragment;
import com.zqzr.licaitong.ui.home.fragment.ProjectIntroduceFragment;
import com.zqzr.licaitong.ui.home.fragment.ProjectMessureBaoLiFragment;
import com.zqzr.licaitong.ui.home.fragment.ProjectRecordFragment;
import com.zqzr.licaitong.utils.ActivityUtils;
import com.zqzr.licaitong.utils.JsonUtil;
import com.zqzr.licaitong.utils.Logger;
import com.zqzr.licaitong.utils.SPUtil;
import com.zqzr.licaitong.utils.Utils;
import com.zqzr.licaitong.view.KeyDownLoadingDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

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
    private TextView mTvTitle, mTvPredictIncome, mTvLimit, mTvStart, mTvsubscribe, mTvRepayment, mTvIncremental, mTvReduce, mTvAdd, mTvSum, mTvSubscribe, mTvRest;
    private LinearLayout mLlBack, mLlLimit, mLlStart, mLlAll, mLlRest, mLlIn, mLlAudit, mLlRecord;
    private int type, id;
    private KeyDownLoadingDialog loadingDialog;
    private TextView[] textViews = new TextView[3];
    private TextView[] textViewLines = new TextView[3];
    private int singleAmount,totalAmount,restAmount;

    @Override
    protected void initView() {
        type = getIntent().getIntExtra("type", -1);
        id = getIntent().getIntExtra("id", -1);
        if (type == 1) {
            setContentView(R.layout.act_tender_detail2);
            textViews[1] = (TextView) findViewById(R.id.tv_project_audit);
            textViews[1].setText(getResources().getString(R.string.introduce_measure_t));
            textViewLines[1] = (TextView) findViewById(R.id.tv_project_audit_line);
            textViews[2] = (TextView) findViewById(R.id.tv_project_record);
            textViewLines[2] = (TextView) findViewById(R.id.tv_project_record_line);
        } else {
            setContentView(R.layout.act_tender_detail);
            textViews[1] = (TextView) findViewById(R.id.tv_project_audit);
            textViewLines[1] = (TextView) findViewById(R.id.tv_project_audit_line);
            textViews[2] = (TextView) findViewById(R.id.tv_project_record);
            textViewLines[2] = (TextView) findViewById(R.id.tv_project_record_line);
        }
        mLayout = (StickHeaderLayout) findViewById(R.id.stick_header_layout);
        mAdapter = new FragmentAdapter(getSupportFragmentManager(), getFragment(type));
        mViewPager = (ViewPager) findViewById(R.id.vp_view_pager);
        textViews[0] = (TextView) findViewById(R.id.tv_project_in);
        textViewLines[0] = (TextView) findViewById(R.id.tv_project_in_line);

        mLlIn = (LinearLayout) findViewById(R.id.ll_in);
        mLlAudit = (LinearLayout) findViewById(R.id.ll_audit);
        mLlRecord = (LinearLayout) findViewById(R.id.ll_record);
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
        mViewPager.addOnPageChangeListener(pageListener);

        //上部控件
        mLlBack = (LinearLayout) findViewById(R.id.ll_back);
        mTvTitle = (TextView) findViewById(R.id.tv_detail_title);
        mTvPredictIncome = (TextView) findViewById(R.id.tv_detial_predictIncome);
        mTvLimit = (TextView) findViewById(R.id.tv_detail_limit);
        mTvStart = (TextView) findViewById(R.id.tv_detail_start);
        mTvsubscribe = (TextView) findViewById(R.id.tv_detail_subscribe);
        mTvRepayment = (TextView) findViewById(R.id.tv_repayment);
        mTvIncremental = (TextView) findViewById(R.id.tv_incremental);
        mTvRest = (TextView) findViewById(R.id.tv_detail_rest);
        mLlLimit = (LinearLayout) findViewById(R.id.ll_limit);
        mLlStart = (LinearLayout) findViewById(R.id.ll_start);
        mLlAll = (LinearLayout) findViewById(R.id.ll_all);
        mLlRest = (LinearLayout) findViewById(R.id.ll_rest);
        //预约
        mTvReduce = (TextView) findViewById(R.id.tv_reduce);
        mTvAdd = (TextView) findViewById(R.id.tv_add);
        mTvSum = (TextView) findViewById(R.id.tv_sum);
        mTvSubscribe = (TextView) findViewById(R.id.tv_subscribe);

        mLlBack.setOnClickListener(this);
        mTvReduce.setOnClickListener(this);
        mTvAdd.setOnClickListener(this);
        mTvsubscribe.setOnClickListener(this);
        mLlIn.setOnClickListener(this);
        mLlAudit.setOnClickListener(this);
        mLlRecord.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        loadingDialog = new KeyDownLoadingDialog(this);
        mViewPager.setCurrentItem(0);
        textViews[0].setTextColor(getResources().getColor(R.color.text_dark));
        textViewLines[0].setBackgroundColor(getResources().getColor(R.color.app_color_principal));
        getData();
    }

    /**
     * 获取数据
     */
    private void getData() {
        loadingDialog.show();
        TreeMap<String, String> params = new TreeMap<>();
        params.put("id", id + "");

        PostRequest<String> postRequest = OKGO_GetData.getDatePost(this, BaseParams.ProductDetail, params);
        postRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                if (!TextUtils.isEmpty(response.body())) {
                    ProductDetail productDetail = JsonUtil.parseJsonToBean(response.body(), ProductDetail.class);
                    if (200 == Integer.parseInt(productDetail.code) && productDetail.data != null) {
                        mTvTitle.setText(productDetail.data.name);
                        mTvPredictIncome.setText(productDetail.data.expectedYield + "%");
                        mTvLimit.setText(productDetail.data.projectDuration + "天");
                        mTvStart.setText(Utils.getWan(productDetail.data.purchaseAmount));
                        mTvsubscribe.setText(Utils.getWan(productDetail.data.proTotalAmount));
                        mTvRest.setText(Utils.getWan(productDetail.data.reservationAmount));
                        mTvIncremental.setText(Utils.getWan(productDetail.data.increasingAmountStr)+"及整数倍数递增");
                        singleAmount = productDetail.data.increasingAmount;
                        mTvSum.setText(Utils.getDouble2(productDetail.data.purchaseAmount));
                        totalAmount = productDetail.data.proTotalAmount;
                        restAmount = productDetail.data.reservationAmount;
                    } else {
                        Utils.toast(productDetail.message);
                    }
                    loadingDialog.dismiss();
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                Utils.toast(Constant.NetWork_Error);
                loadingDialog.dismiss();
            }
        });
    }

    private List<Fragment> getFragment(int type) {
        List<Fragment> list = new ArrayList<>();
        if (type != 1) {
            list.add(new ProjectIntroduceFragment());
            list.add(new ProjectAuditFragment());
        } else {
            list.add(new ProjectIntroduceBaoLiFragment());
            list.add(new ProjectMessureBaoLiFragment());
        }
        list.add(new ProjectRecordFragment());
        return list;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                ActivityUtils.pop();
                break;
            case R.id.tv_reduce:
                if (Integer.valueOf(mTvSum.getText().toString())-singleAmount < restAmount){
                    mTvSum.setText(Utils.getDouble2(singleAmount));
                }else{
                    mTvSum.setText(Utils.getDouble2(Integer.valueOf(mTvSum.getText().toString())-singleAmount));
                }
                break;
            case R.id.tv_add:
                if (Integer.valueOf(mTvSum.getText().toString())+singleAmount > totalAmount){

                }
                mTvSum.setText(Utils.getDouble2(Integer.valueOf(mTvSum.getText().toString())+singleAmount));
                break;
            case R.id.tv_subscribe:

                break;
            case R.id.ll_in:
                resetlaybg();
                mViewPager.setCurrentItem(0);
                textViews[0].setTextColor(getResources().getColor(R.color.text_dark));
                textViewLines[0].setBackgroundColor(getResources().getColor(R.color.app_color_principal));
                break;
            case R.id.ll_audit:
                resetlaybg();
                mViewPager.setCurrentItem(1);
                textViews[1].setTextColor(getResources().getColor(R.color.text_dark));
                textViewLines[1].setBackgroundColor(getResources().getColor(R.color.app_color_principal));
                break;
            case R.id.ll_record:
                resetlaybg();
                mViewPager.setCurrentItem(2);
                textViews[2].setTextColor(getResources().getColor(R.color.text_dark));
                textViewLines[2].setBackgroundColor(getResources().getColor(R.color.app_color_principal));
                break;
        }
    }

    /**
     * 重置textViews textViewLines
     */
    public void resetlaybg() {
        for (int i = 0; i < 3; i++) {
            textViews[i].setTextColor(getResources().getColor(R.color.text_grey));
            textViewLines[i].setBackgroundColor(getResources().getColor(R.color.transparent));
        }
    }

    /**
     * ViewPager切换监听方法
     */
    public ViewPager.OnPageChangeListener pageListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int position) {
            resetlaybg();
            textViews[position].setTextColor(getResources().getColor(R.color.text_dark));
            textViewLines[position].setBackgroundColor(getResources().getColor(R.color.app_color_principal));
        }
    };
}