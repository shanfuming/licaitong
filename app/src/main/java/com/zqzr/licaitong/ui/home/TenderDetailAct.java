package com.zqzr.licaitong.ui.home;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.sys.blackcat.stickheaderlayout.IpmlScrollChangListener;
import com.sys.blackcat.stickheaderlayout.StickHeaderLayout;
import com.zqzr.licaitong.MyApplication;
import com.zqzr.licaitong.R;
import com.zqzr.licaitong.adapter.FragmentAdapter;
import com.zqzr.licaitong.base.BaseActivity;
import com.zqzr.licaitong.base.BaseParams;
import com.zqzr.licaitong.base.Constant;
import com.zqzr.licaitong.bean.Getcode;
import com.zqzr.licaitong.bean.Login;
import com.zqzr.licaitong.bean.ProductDetail;
import com.zqzr.licaitong.http.OKGO_GetData;
import com.zqzr.licaitong.ui.find.fragment.OnChangeFindActivityData;
import com.zqzr.licaitong.ui.home.fragment.ProjectAuditFragment;
import com.zqzr.licaitong.ui.home.fragment.ProjectIntroduceBaoLiFragment;
import com.zqzr.licaitong.ui.home.fragment.ProjectIntroduceFragment;
import com.zqzr.licaitong.ui.home.fragment.ProjectMessureBaoLiFragment;
import com.zqzr.licaitong.ui.home.fragment.ProjectRecordFragment;
import com.zqzr.licaitong.ui.own.BankCardAct;
import com.zqzr.licaitong.ui.own.IdentifyAct;
import com.zqzr.licaitong.ui.own.LoginAct;
import com.zqzr.licaitong.utils.ActivityUtils;
import com.zqzr.licaitong.utils.JsonUtil;
import com.zqzr.licaitong.utils.SPUtil;
import com.zqzr.licaitong.utils.Utils;
import com.zqzr.licaitong.view.KeyDownLoadingDialog;
import com.zqzr.licaitong.view.SuccessAndFailDialog;
import com.zqzr.licaitong.view.TipDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

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
    private TextView mTvTitle, mTvPredictIncome, mTvLimit, mTvStart, mTvTotal, mTvRepayment, mTvIncremental, mTvReduce, mTvAdd, mTvSum, mTvSubscribe, mTvRest;
    private LinearLayout mLlBack, mLlLimit, mLlStart, mLlAll, mLlRest, mLlIn, mLlAudit, mLlRecord;
    private int type, id;
    private KeyDownLoadingDialog loadingDialog;
    private TextView[] textViews = new TextView[3];
    private TextView[] textViewLines = new TextView[3];
    private double singleAmount, totalAmount, restAmount, startAmount;
    private SuccessAndFailDialog successDialog;
    private String url = "";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case Constant.NUMBER_1:
                    successDialog.dismiss();
                    finish();
                    break;
            }
        }
    };
    private ImageView mIvDetailPiaoJu;
    private LinearLayout mLlDetailBapli;
    private RelativeLayout mRlSubsribe;
    private ProjectIntroduceBaoLiFragment projectIntroduceBaoLiFragment;
    private ProjectIntroduceFragment projectIntroduceFragment;

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
            mRlSubsribe = (RelativeLayout) findViewById(R.id.rl_subscribe);
        } else {
            setContentView(R.layout.act_tender_detail);
            textViews[1] = (TextView) findViewById(R.id.tv_project_audit);
            textViewLines[1] = (TextView) findViewById(R.id.tv_project_audit_line);
            textViews[2] = (TextView) findViewById(R.id.tv_project_record);
            textViewLines[2] = (TextView) findViewById(R.id.tv_project_record_line);
            mRlSubsribe = (RelativeLayout) findViewById(R.id.rl_subscribe);
        }
        mLayout = (StickHeaderLayout) findViewById(R.id.stick_header_layout);
        mAdapter = new FragmentAdapter(getSupportFragmentManager(), getFragment(type));
        mViewPager = (ViewPager) findViewById(R.id.vp_view_pager);
        textViews[0] = (TextView) findViewById(R.id.tv_project_in);
        textViewLines[0] = (TextView) findViewById(R.id.tv_project_in_line);
        mIvDetailPiaoJu = (ImageView) findViewById(R.id.iv_detail_piaoju);
        mLlDetailBapli = (LinearLayout) findViewById(R.id.ll_detail_baoli);
        if (type == 1) {
            mIvDetailPiaoJu.setVisibility(View.GONE);
            mLlDetailBapli.setVisibility(View.VISIBLE);
        } else {
            mIvDetailPiaoJu.setVisibility(View.VISIBLE);
            mLlDetailBapli.setVisibility(View.GONE);
        }

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
        mTvTotal = (TextView) findViewById(R.id.tv_detail_total);
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
        mTvSubscribe.setOnClickListener(this);
        mLlIn.setOnClickListener(this);
        mLlAudit.setOnClickListener(this);
        mLlRecord.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        loadingDialog = new KeyDownLoadingDialog(this);
        successDialog = new SuccessAndFailDialog(this);
        successDialog.setContent("预约成功", true);
        successDialog.setDes("即将跳转到产品列表", true);
        successDialog.setImg(R.mipmap.success, true);
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
                    if (Integer.parseInt(JsonUtil.getFieldValue(response.body(), "code")) == 200) {
                        ProductDetail productDetail = JsonUtil.parseJsonToBean(response.body(), ProductDetail.class);
                        mTvTitle.setText(productDetail.data.name);
                        mTvPredictIncome.setText(productDetail.data.expectedYield + "%");
                        mTvLimit.setText(productDetail.data.projectDuration + "天");
                        mTvStart.setText(Utils.getWan(productDetail.data.purchaseAmount));
                        mTvTotal.setText(Utils.getWan(productDetail.data.proTotalAmount));
                        mTvRest.setText(Utils.getWan(productDetail.data.reservationAmount));
                        if (productDetail.data.increasingAmount >= 10000) {
                            mTvIncremental.setText(productDetail.data.increasingAmountStr + "万及整数倍数递增");
                        } else {
                            mTvIncremental.setText(productDetail.data.increasingAmountStr + "及整数倍数递增");
                        }
                        mTvSum.setText(Utils.getDouble2(productDetail.data.purchaseAmount));
                        if (productDetail.data.reservationAmount > 0) {
                            mRlSubsribe.setVisibility(View.VISIBLE);
                        }

                        singleAmount = productDetail.data.increasingAmount;
                        totalAmount = productDetail.data.proTotalAmount;
                        restAmount = productDetail.data.reservationAmount;
                        startAmount = productDetail.data.purchaseAmount;
                        url = productDetail.data.url;
                        projectIntroduceBaoLiFragment.setUrl(url);
                        projectIntroduceFragment.setUrl(url);

                    } else {
                        Utils.toast(JsonUtil.getFieldValue(response.body(), "message"));
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

    /**
     * 提示去实名或者绑定银行卡
     *
     * @param type 1实名 2绑卡
     */
    private void tip(final int type) {
        final TipDialog tipDialog = new TipDialog(this, true);
        tipDialog.setTitle("温馨提示");
        if (type == 1) {
            tipDialog.setContent("您还没有实名认证!");
            tipDialog.setButtonDes("取消", "去认证");
        } else {
            tipDialog.setContent("您还没有绑定银行卡!");
            tipDialog.setButtonDes("取消", "去绑卡");
        }
        tipDialog.show();
        tipDialog.setClickListener(new TipDialog.ClickListener() {
            @Override
            public void onClickExit() {
                tipDialog.dismiss();
            }

            @Override
            public void onClickConfirm() {
                if (type == 1) {
                    ActivityUtils.push(IdentifyAct.class);
                } else {
                    ActivityUtils.push(BankCardAct.class);
                }
                tipDialog.dismiss();
            }
        });
    }

    private List<Fragment> getFragment(int type) {
        List<Fragment> list = new ArrayList<>();
        projectIntroduceBaoLiFragment = new ProjectIntroduceBaoLiFragment();
        projectIntroduceFragment = new ProjectIntroduceFragment();
        if (type != 1) {
            list.add(projectIntroduceFragment);
            list.add(new ProjectAuditFragment());
        } else {
            list.add(projectIntroduceBaoLiFragment);
            list.add(new ProjectMessureBaoLiFragment());
        }
        list.add(new ProjectRecordFragment().setProductId(id + ""));
        return list;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                ActivityUtils.pop();
                break;
            case R.id.tv_reduce:
                if (restAmount > startAmount){
                    if (Double.valueOf(mTvSum.getText().toString()) - singleAmount < startAmount){
                        mTvSum.setText(Utils.getDouble2(startAmount));
                    }else{
                        mTvSum.setText(Utils.getDouble2(Double.valueOf(mTvSum.getText().toString()) - singleAmount));
                    }
                }else{
                    mTvSum.setText(Utils.getDouble2(restAmount));
                }

                break;
            case R.id.tv_add:
                if (Double.valueOf(mTvSum.getText().toString()) + singleAmount > restAmount) {
                    mTvSum.setText(Utils.getDouble2(restAmount));
                } else {
                    mTvSum.setText(Utils.getDouble2(Double.valueOf(mTvSum.getText().toString()) + singleAmount));
                }
                break;
            case R.id.tv_subscribe:
                if (MyApplication.getInstance().isLand()) {
                    subscribe();
                } else {
                    ActivityUtils.push(LoginAct.class);
                }
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
     * 预约
     */
    private void subscribe() {
        TreeMap<String, String> params = new TreeMap<>();
        params.put("productId", id + "");
        params.put("subscribeAmount", Utils.getDouble(Double.valueOf(mTvSum.getText().toString())));

        PostRequest<String> postRequest = OKGO_GetData.getDatePost(this, BaseParams.MakeReservation, params);
        postRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                if (!TextUtils.isEmpty(response.body())) {
                    if (Integer.parseInt(JsonUtil.getFieldValue(response.body(), "code")) == 200) {
                        successDialog.show();

                        handler.sendEmptyMessageDelayed(Constant.NUMBER_1, 3000);

                    } else if (Integer.parseInt(JsonUtil.getFieldValue(response.body(), "code")) == 10003) {
                        ActivityUtils.push(LoginAct.class);
                    } else if (10011 == Integer.parseInt(JsonUtil.getFieldValue(response.body(), "code"))) {
                        tip(1);
                    } else if (10023 == Integer.parseInt(JsonUtil.getFieldValue(response.body(), "code"))) {
                        tip(2);
                    } else {
                        Utils.toast(JsonUtil.getFieldValue(response.body(), "message"));
                    }
                }
                loadingDialog.dismiss();
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                Utils.toast(Constant.NetWork_Error);
                loadingDialog.dismiss();
            }
        });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(Constant.NUMBER_1);
        if (successDialog.isShowing()) {
            successDialog.dismiss();
        }
    }
}