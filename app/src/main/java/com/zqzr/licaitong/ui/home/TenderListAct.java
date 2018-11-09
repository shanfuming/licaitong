package com.zqzr.licaitong.ui.home;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.zqzr.licaitong.MyApplication;
import com.zqzr.licaitong.R;
import com.zqzr.licaitong.adapter.DropMenuAdapter;
import com.zqzr.licaitong.adapter.TenderListAdapter;
import com.zqzr.licaitong.base.BaseActivity;
import com.zqzr.licaitong.base.BaseParams;
import com.zqzr.licaitong.base.Constant;
import com.zqzr.licaitong.bean.Banner;
import com.zqzr.licaitong.bean.Login;
import com.zqzr.licaitong.bean.Menu;
import com.zqzr.licaitong.bean.Product;
import com.zqzr.licaitong.bean.Tender;
import com.zqzr.licaitong.http.OKGO_GetData;
import com.zqzr.licaitong.utils.ActivityUtils;
import com.zqzr.licaitong.utils.JsonUtil;
import com.zqzr.licaitong.utils.Logger;
import com.zqzr.licaitong.utils.SPUtil;
import com.zqzr.licaitong.utils.Utils;
import com.zqzr.licaitong.view.KeyDownLoadingDialog;

import java.lang.annotation.AnnotationFormatError;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/16 13:43
 * <p/>
 * Description:
 */

public class TenderListAct extends BaseActivity implements View.OnClickListener {

    private ArrayList<Menu> menu = new ArrayList<>();
    private ArrayList<Menu> limit = new ArrayList<>();
    private ArrayList<Menu> start = new ArrayList<>();
    private boolean isLimit, isStart;
    private ListView mTenderListView;
    private DropMenuAdapter limitAdapter, menuAdapter;
    private LinearLayout mLlConditionLimit, mLlConditionStart, mLlMenuList;
    private ListView mConditionListView;
    private MaterialRefreshLayout mRefreshLayout;
    private TextView mTvConditionLimit, mTvConditionStart;
    private ImageView mIvConditionLimit, mIvConditionStart;
    private View mask;
    private TenderListAdapter tenderListAdapter;
    private TextView mTvProperty, mTvPiaoju;
    private LinearLayout mLlPiaoju;
    private int type;
    private int currentType;
    private int currentPage = 1;
    private int nextPage = 2;
    private ArrayList<Product.Data.CList> dataList = new ArrayList<>();
    private ArrayList<Login> propertyList = new ArrayList<>();
    private ArrayList<Login> piaojuList = new ArrayList<>();
    private ArrayList<Login> directList = new ArrayList<>();
    private KeyDownLoadingDialog loadingDialog;


    @Override
    protected void onStart() {
        super.onStart();
        type = getIntent().getIntExtra("type", -1);
        setBackOption(true);
        //设置标题
        if (type == Constant.NUMBER_0) {
            setTitle("票据投资");
        }
        if (type == Constant.NUMBER_1) {
            setTitle("保理投资");
        }
        //默认加载数据
        if (type == Constant.NUMBER_0) {
            getDataList(0, currentPage, false);
            mLlPiaoju.setVisibility(View.VISIBLE);
        }
        if (type == Constant.NUMBER_1) {
            getDataList(1, currentPage, false);
            mLlPiaoju.setVisibility(View.GONE);
        }
        currentType = type;
    }

    @Override
    protected void initView() {
        setContentView(R.layout.act_home_tenderlist);
        //暂无用-筛选条件
        mLlConditionLimit = (LinearLayout) findViewById(R.id.ll_condition_limit);
        mTvConditionLimit = (TextView) findViewById(R.id.tv_condition_limit);
        mIvConditionLimit = (ImageView) findViewById(R.id.iv_limitImg);
        mLlConditionStart = (LinearLayout) findViewById(R.id.ll_condition_start);
        mTvConditionStart = (TextView) findViewById(R.id.tv_condition_startMark);
        mIvConditionStart = (ImageView) findViewById(R.id.iv_startImg);
        mLlMenuList = (LinearLayout) findViewById(R.id.ll_menuList);
        mConditionListView = (ListView) findViewById(R.id.condition_listview);


        mLlPiaoju = (LinearLayout) findViewById(R.id.ll_piaoju);
        mTvProperty = (TextView) findViewById(R.id.tv_property);
        mTvPiaoju = (TextView) findViewById(R.id.tv_piaoju);
        mRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.mrl_refreshLayout);
        mRefreshLayout.setLoadMore(true);
        mTenderListView = (ListView) findViewById(R.id.tenderList_listview);
        mask = findViewById(R.id.mask);

        mTvProperty.setOnClickListener(this);
        mTvPiaoju.setOnClickListener(this);
        mask.setOnClickListener(this);

    }

    @Override
    protected void initData() {
//        limit.add(new Menu("全部"));
//        limit.add(new Menu("3个月以下"));
//        limit.add(new Menu("3-6个月"));
//        limit.add(new Menu("6-12个月"));
//        limit.add(new Menu("12个月以上"));
//
//        start.add(new Menu("全部"));
//        start.add(new Menu("5万起投"));
//        start.add(new Menu("10万起投"));
//        start.add(new Menu("20万起投"));
//
//        for(int i=0;i<20;i++){
//            tenders.add(new Tender("盛向泰票据00"+i+"期","10.1"+i+"%","11"+i+"天",i+"万"));
//        }
//
//        menuAdapter = new DropMenuAdapter();
//        mConditionListView.setAdapter(menuAdapter);
//        mConditionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                Utils.toast(menu.get(position).getName());
//                for (int i = 0;i<menu.size();i++){
//                    menu.get(i).setSelect(false);
//                    if (i == position){
//                        menu.get(i).setSelect(true);
//                    }
//                    menuAdapter.setMenuList(menu);
//                }
//            }
//        });
        loadingDialog = new KeyDownLoadingDialog(this);
        tenderListAdapter = new TenderListAdapter(dataList);
        mTenderListView.setAdapter(tenderListAdapter);

        //刷新加载更多
        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                getDataList(currentType, currentPage, false);
                mRefreshLayout.finishRefreshing();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                getDataList(currentType, nextPage, true);
                nextPage = nextPage + 1;
                mRefreshLayout.finishRefreshLoadMore();
            }
        });

        mTenderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("type", dataList.get(position).type);
                intent.putExtra("id", dataList.get(position).id);
                ActivityUtils.push(TenderDetailAct.class, intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.ll_condition_limit:
//                if (!isLimit){
//                    setConditionSelector(true);
//                    menu.clear();
//                    menu.addAll(limit);
//                    menuAdapter.setMenuList(menu);
//                    isLimit = true;
//                }else{
//                    mTvConditionLimit.setTextColor(getResources().getColor(R.color.text_grey));
//                    mIvConditionLimit.setImageDrawable(getResources().getDrawable(R.mipmap.drop_down_unselected_icon));
//                    isLimit = false;
//                    mLlMenuList.setVisibility(View.GONE);
//                }
//                break;
//            case R.id.ll_condition_start:
//                if (!isStart){
//                    setConditionSelector(false);
//                    menu.clear();
//                    menu.addAll(start);
//                    menuAdapter.setMenuList(menu);
//                    isStart = true;
//                }else{
//                    mTvConditionStart.setTextColor(getResources().getColor(R.color.text_grey));
//                    mIvConditionStart.setImageDrawable(getResources().getDrawable(R.mipmap.drop_down_unselected_icon));
//                    mLlMenuList.setVisibility(View.GONE);
//                    isStart = false;
//                }
//                break;
//            case R.id.mask:
//                mLlMenuList.setVisibility(View.GONE);
//                mTvConditionStart.setTextColor(getResources().getColor(R.color.text_grey));
//                mIvConditionStart.setImageDrawable(getResources().getDrawable(R.mipmap.drop_down_unselected_icon));
//                mTvConditionLimit.setTextColor(getResources().getColor(R.color.text_grey));
//                mIvConditionLimit.setImageDrawable(getResources().getDrawable(R.mipmap.drop_down_unselected_icon));
//                break;
            case R.id.tv_property:
                setBg(1);
                getDataList(0, currentPage, false);
                currentType = 0;
                break;
            case R.id.tv_piaoju:
                setBg(2);
                getDataList(4, currentPage, false);
                currentType = 4;
                break;
        }
    }

    private void setBg(int isFirst) {
        if (isFirst == 1) {
            mTvPiaoju.setTextColor(getResources().getColor(R.color.app_color_principal));
            mTvPiaoju.setBackgroundResource(R.drawable.fillet_btn_diable_right);
            mTvProperty.setTextColor(getResources().getColor(R.color.white));
            mTvProperty.setBackgroundResource(R.drawable.fillet_btn_left);
        } else {
            mTvPiaoju.setTextColor(getResources().getColor(R.color.white));
            mTvPiaoju.setBackgroundResource(R.drawable.fillet_btn_right);
            mTvProperty.setTextColor(getResources().getColor(R.color.app_color_principal));
            mTvProperty.setBackgroundResource(R.drawable.fillet_btn_disable_left);
        }
    }

    private void getDataList(int type, int pageNum, final boolean isload) {
        loadingDialog.show();
        TreeMap<String, String> params = new TreeMap<>();
        params.put("token", SPUtil.getString("token", ""));
        params.put("userId", SPUtil.getString("userid", ""));
        params.put("pageNum", pageNum + "");
        params.put("type", "" + type);
        params.put("duration", "0");

        PostRequest<String> postRequest = OKGO_GetData.getDatePost(this, BaseParams.ProductList, params);
        postRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                if (!TextUtils.isEmpty(response.body())) {
                    if (Integer.parseInt(JsonUtil.getFieldValue(response.body(), "code")) == 200) {
                        Product product = JsonUtil.parseJsonToBean(response.body(), Product.class);
                        if (!isload) {
                            dataList.clear();
                        }
                        dataList.addAll(product.data.cList);
                        tenderListAdapter.notifyDataSetChanged();

                    } else {
                        Utils.toast(JsonUtil.getFieldValue(response.body(), "message"));
                    }

                    if (loadingDialog.isShowing()) {
                        loadingDialog.dismiss();
                    }
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                Utils.toast(Constant.NetWork_Error);
                if (loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
            }
        });
    }

    /**
     * 设置条件文字颜色和尖号方向
     *
     * @param isSelect 表示第一个条件是否被选中，选中为true,那么第二个条件必定是未选中的状态，反之亦成立！
     */
    private void setConditionSelector(boolean isSelect) {
        mLlMenuList.setVisibility(View.VISIBLE);
        if (isSelect) {//选中第一个条件
            mTvConditionLimit.setTextColor(getResources().getColor(R.color.text_dark));
            mIvConditionLimit.setImageDrawable(getResources().getDrawable(R.mipmap.drop_down_selected_icon));
            mTvConditionStart.setTextColor(getResources().getColor(R.color.text_grey));
            mIvConditionStart.setImageDrawable(getResources().getDrawable(R.mipmap.drop_down_unselected_icon));
        } else {//选中第二个条件
            mTvConditionLimit.setTextColor(getResources().getColor(R.color.text_grey));
            mIvConditionLimit.setImageDrawable(getResources().getDrawable(R.mipmap.drop_down_unselected_icon));
            mTvConditionStart.setTextColor(getResources().getColor(R.color.text_dark));
            mIvConditionStart.setImageDrawable(getResources().getDrawable(R.mipmap.drop_down_selected_icon));
        }
    }
}
