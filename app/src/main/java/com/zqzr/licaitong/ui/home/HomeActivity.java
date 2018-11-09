package com.zqzr.licaitong.ui.home;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.IconHintView;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;
import com.zqzr.licaitong.MyApplication;
import com.zqzr.licaitong.R;
import com.zqzr.licaitong.adapter.BannerAdapter;
import com.zqzr.licaitong.adapter.FindItemAdapter;
import com.zqzr.licaitong.adapter.HomeFindAdapter;
import com.zqzr.licaitong.adapter.HomeTouziListAdapter;
import com.zqzr.licaitong.base.BaseActivity;
import com.zqzr.licaitong.base.BaseParams;
import com.zqzr.licaitong.base.Constant;
import com.zqzr.licaitong.bean.Banner;
import com.zqzr.licaitong.bean.FindItem;
import com.zqzr.licaitong.bean.HomeTouziTypeAndFind;
import com.zqzr.licaitong.bean.Login;
import com.zqzr.licaitong.http.OKGO_GetData;
import com.zqzr.licaitong.ui.CommonWebviewAct;
import com.zqzr.licaitong.ui.MainActivity;
import com.zqzr.licaitong.utils.ActivityUtils;
import com.zqzr.licaitong.utils.JsonUtil;
import com.zqzr.licaitong.utils.SPUtil;
import com.zqzr.licaitong.utils.Utils;
import com.zqzr.licaitong.view.KeyDownLoadingDialog;
import com.zqzr.licaitong.view.PowerListView;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/13 12:55
 * <p/>
 * Description:
 */

public class HomeActivity extends BaseActivity implements View.OnClickListener {
    private RollPagerView bannerPager;
    private LinearLayout ll_piaoju, ll_baoli, ll_guquan, ll_fangchan;
    private PowerListView touziTypeListView, findListView;
    private ArrayList<HomeTouziTypeAndFind.Data.Inve> touziTypesList = new ArrayList<>();
    private ArrayList<HomeTouziTypeAndFind.Data.Moments> findItemList = new ArrayList<>();
    private ArrayList<Banner.Data> bannerList = new ArrayList<>();
    private HomeTouziListAdapter homeTouziListAdapter;
    private HomeFindAdapter findItemAdapter;
    private KeyDownLoadingDialog loadingDialog;
    private BannerAdapter bannerAdapter;
    private TextView mTvFindMore;
    private MaterialRefreshLayout mRefreshLayout;

    @Override
    protected void initView() {
        setContentView(R.layout.act_home);

        bannerPager = (RollPagerView) findViewById(R.id.roll_view_pager);
        ll_piaoju = (LinearLayout) findViewById(R.id.ll_piaoju);
        ll_baoli = (LinearLayout) findViewById(R.id.ll_baoli);
        ll_guquan = (LinearLayout) findViewById(R.id.ll_guquan);
        ll_fangchan = (LinearLayout) findViewById(R.id.ll_fangchan);
        mTvFindMore = (TextView) findViewById(R.id.tv_find_more);
        mRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.mrl_refreshLayout);

        touziTypeListView = (PowerListView) findViewById(R.id.touziType_listview);
        findListView = (PowerListView) findViewById(R.id.find_listview);


        ll_piaoju.setOnClickListener(this);
        ll_baoli.setOnClickListener(this);
        ll_guquan.setOnClickListener(this);
        ll_fangchan.setOnClickListener(this);
        mTvFindMore.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        loadingDialog = new KeyDownLoadingDialog(this);
        loadingDialog.show();

        getBannerList();
        getTouziList();

        //轮播图
        bannerPager.setHintView(new IconHintView(this, R.mipmap.guide_point, R.mipmap.guide_point_cur));
        bannerPager.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (!TextUtils.isEmpty(bannerList.get(position).title)) {
                    Intent intent = new Intent();
                    intent.putExtra("title", bannerList.get(position).title);
                    intent.putExtra("content", bannerList.get(position).content);
                    intent.putExtra("redirectUrl", bannerList.get(position).url);
                    ActivityUtils.push(CommonWebviewAct.class, intent);
                }
            }
        });
        bannerAdapter = new BannerAdapter(HomeActivity.this, bannerList);
        bannerPager.setAdapter(bannerAdapter);

        //各种投资类型获取
        homeTouziListAdapter = new HomeTouziListAdapter(touziTypesList);
        touziTypeListView.setAdapter(homeTouziListAdapter);

        findItemAdapter = new HomeFindAdapter(this, findItemList);
        findListView.setAdapter(findItemAdapter);


        findListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!TextUtils.isEmpty(findItemList.get(position).title)) {
                    Intent intent = new Intent();
                    intent.putExtra("title", findItemList.get(position).title);
                    intent.putExtra("redirectUrl", findItemList.get(position).urlHref);
                    intent.putExtra("content", findItemList.get(position).content);
                    ActivityUtils.push(CommonWebviewAct.class, intent);
                }
            }
        });

        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                getBannerList();
                getTouziList();
                mRefreshLayout.finishRefreshing();
            }
        });

    }

    private void getTouziList() {
        TreeMap<String, String> params = new TreeMap<>();
        PostRequest<String> postRequest = OKGO_GetData.getDatePost(this, BaseParams.GetTopOrSuggInvestions, params);
        postRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                if (!TextUtils.isEmpty(response.body())) {

                    if (Integer.parseInt(JsonUtil.getFieldValue(response.body(), "code")) == 200) {
                        HomeTouziTypeAndFind homeTouziTypeAndFind = JsonUtil.parseJsonToBean(response.body(), HomeTouziTypeAndFind.class);
                        if (homeTouziTypeAndFind.data.inve != null) {
                            touziTypesList.clear();
                            touziTypesList.addAll(homeTouziTypeAndFind.data.inve);
                        }
                        if (homeTouziTypeAndFind.data.moments != null) {
                            findItemList.clear();
                            findItemList.addAll(homeTouziTypeAndFind.data.moments);
                        }
                        homeTouziListAdapter.notifyDataSetChanged();
                        findItemAdapter.notifyDataSetChanged();

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
     * 获取banner
     */
    private void getBannerList() {
        TreeMap<String, String> params = new TreeMap<>();
        params.put("isDisplay", "1");

        PostRequest<String> postRequest = OKGO_GetData.getDatePost(this, BaseParams.Home_Banner, params);
        postRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                if (!TextUtils.isEmpty(response.body())) {

                    if (Integer.parseInt(JsonUtil.getFieldValue(response.body(), "code")) == 200) {
                        Banner banner = JsonUtil.parseJsonToBean(response.body(), Banner.class);
                        if (banner.data!=null){
                            bannerList.clear();
                            bannerList.addAll(banner.data);
                        }
                        bannerAdapter.notifyDataSetChanged();

                    } else {
                        Utils.toast(JsonUtil.getFieldValue(response.body(), "message"));
                    }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_piaoju:
                Intent piaoju = new Intent();
                piaoju.putExtra("type", 0);
                ActivityUtils.push(TenderListAct.class, piaoju);
                break;
            case R.id.ll_baoli:
                Intent baoli = new Intent();
                baoli.putExtra("type", 1);
                ActivityUtils.push(TenderListAct.class, baoli);
                break;
            case R.id.ll_guquan:
//                Intent guquan= new Intent();
//                guquan.putExtra("type",3);
//                ActivityUtils.push(TenderListAct.class,guquan);
                Utils.toast(Constant.Wait);
                break;
            case R.id.ll_fangchan:
//                Intent fangchan= new Intent();
//                fangchan.putExtra("type",4);
//                ActivityUtils.push(TenderListAct.class,fangchan);
                Utils.toast(Constant.Wait);
                break;
            case R.id.tv_find_more:
                Intent intent = new Intent();
                intent.putExtra("tab",1);
                ActivityUtils.push(MainActivity.class, intent);
                break;

        }
    }
}
