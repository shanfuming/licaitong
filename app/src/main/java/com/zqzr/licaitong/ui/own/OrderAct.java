package com.zqzr.licaitong.ui.own;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.zqzr.licaitong.adapter.OrderAdapter;
import com.zqzr.licaitong.base.BaseActivity;
import com.zqzr.licaitong.base.BaseParams;
import com.zqzr.licaitong.base.Constant;
import com.zqzr.licaitong.bean.Login;
import com.zqzr.licaitong.bean.Menu;
import com.zqzr.licaitong.bean.Order;
import com.zqzr.licaitong.http.OKGO_GetData;
import com.zqzr.licaitong.utils.DensityUtils;
import com.zqzr.licaitong.utils.JsonUtil;
import com.zqzr.licaitong.utils.SPUtil;
import com.zqzr.licaitong.utils.Utils;
import com.zqzr.licaitong.utils.encryption.MD5Util;
import com.zqzr.licaitong.view.KeyDownLoadingDialog;
import com.zqzr.licaitong.view.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/19 10:41
 * <p/>
 * Description:
 */

public class OrderAct extends BaseActivity implements View.OnClickListener {
    private LinearLayout mLlTime, mLlState, mLlProduct, mLlMenuList;
    private TextView mTvTime, mTvState, mTvProduct;

    private ArrayList<Menu> menu = new ArrayList<>();
    private ArrayList<Menu> time = new ArrayList<>();
    private ArrayList<Menu> state = new ArrayList<>();
    private ArrayList<Menu> product = new ArrayList<>();

    private MaterialRefreshLayout mRefreshLayout;
    private RecyclerView mOrderRecyclerView;
    private ListView mConditionListView;
    private View mask;

    private boolean isTime, isState, isProduct;
    private DropMenuAdapter menuAdapter;

    private String currentLimit = "oneMonth";
    private String currentProductName = "";
    private String currentStatus = "";
    private int currentPage = 1, nextPage = 2;
    private ArrayList<Order.Data.CList> orders = new ArrayList<>();
    private KeyDownLoadingDialog loadingDialog;
    private OrderAdapter ordersAdapter;


    @Override
    protected void initView() {
        setContentView(R.layout.act_order);

        mLlTime = (LinearLayout) findViewById(R.id.ll_condition_time);
        mTvTime = (TextView) findViewById(R.id.tv_condition_time);
        mLlState = (LinearLayout) findViewById(R.id.ll_condition_state);
        mTvState = (TextView) findViewById(R.id.tv_condition_state);
        mLlProduct = (LinearLayout) findViewById(R.id.ll_condition_product);
        mTvProduct = (TextView) findViewById(R.id.tv_condition_product);

        mLlMenuList = (LinearLayout) findViewById(R.id.ll_menuList);
        mConditionListView = (ListView) findViewById(R.id.condition_listview);
        mask = findViewById(R.id.mask);

        mRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.mrl_refreshLayout);
        mRefreshLayout.setLoadMore(true);
        mOrderRecyclerView = (RecyclerView) findViewById(R.id.order_recyclerview);
        LinearLayoutManager mLayoutMgr = new LinearLayoutManager(this);
        mOrderRecyclerView.setLayoutManager(mLayoutMgr);
        //添加ItemDecoration，item之间的间隔
        int leftRight = DensityUtils.dp2px(this, 0f);
        int topBottom = DensityUtils.dp2px(this, 12f);
        mOrderRecyclerView.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom, 0));
        ordersAdapter = new OrderAdapter(orders);
        mOrderRecyclerView.setAdapter(ordersAdapter);

        mLlProduct.setOnClickListener(this);
        mLlState.setOnClickListener(this);
        mLlTime.setOnClickListener(this);
        mask.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        loadingDialog = new KeyDownLoadingDialog(this);
//        time.add(new Menu("全部"));
        time.add(new Menu("一个月"));
        time.add(new Menu("三个月"));
        time.add(new Menu("六个月"));
        time.add(new Menu("一年"));

//        state.add(new Menu("全部"));
        state.add(new Menu("待受理"));
        state.add(new Menu("待签约"));
        state.add(new Menu("已签约"));
        state.add(new Menu("签约作废"));
        state.add(new Menu("签约不成功"));
        state.add(new Menu("已取消"));
        state.add(new Menu("已退款"));
        state.add(new Menu("待赎回"));
        state.add(new Menu("已赎回"));
        state.add(new Menu("已还款"));

//        product.add(new Menu("全部"));
        product.add(new Menu("票据"));
        product.add(new Menu("保理"));
        product.add(new Menu("房产"));
        product.add(new Menu("股权"));
        product.add(new Menu("票据直投"));

        menuAdapter = new DropMenuAdapter();
        mConditionListView.setAdapter(menuAdapter);
        mConditionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (isTime) {
                    currentLimit = Utils.dateLimit(menu.get(position).getName());
                }
                if (isState) {
                    currentStatus = Utils.orderStatus(menu.get(position).getName());
                }
                if (isProduct) {
                    currentProductName = Utils.productName(menu.get(position).getName());
                }

                for (int i = 0; i < menu.size(); i++) {
                    menu.get(i).setSelect(false);
                    if (i == position) {
                        menu.get(i).setSelect(true);
                    }
                    menuAdapter.setMenuList(menu);
                }

                getDataFromServer(currentLimit, currentProductName, currentStatus, currentPage, false);
            }
        });
        loadingDialog.show();
        getDataFromServer(currentLimit, currentProductName, currentStatus, currentPage, false);

        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                getDataFromServer(currentLimit, currentProductName, currentStatus, currentPage, false);
                mRefreshLayout.finishRefreshing();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                getDataFromServer(currentLimit, currentProductName, currentStatus, nextPage, true);
                nextPage = nextPage + 1;
                mRefreshLayout.finishRefreshLoadMore();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(getResources().getString(R.string.own_order));
        setBackOption(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_condition_time:
                if (!isTime) {
                    setConditionSelector(1);
                    menu.clear();
                    menu.addAll(time);
                    menuAdapter.setMenuList(menu);
                    isTime = true;
                } else {
                    mTvTime.setTextColor(getResources().getColor(R.color.text_grey));
                    mLlMenuList.setVisibility(View.GONE);
                    isTime = false;
                }
                break;
            case R.id.ll_condition_state:
                setConditionSelector(2);
                if (!isState) {
                    setConditionSelector(1);
                    menu.clear();
                    menu.addAll(state);
                    menuAdapter.setMenuList(menu);
                    isState = true;
                } else {
                    mTvState.setTextColor(getResources().getColor(R.color.text_grey));
                    mLlMenuList.setVisibility(View.GONE);
                    isState = false;
                }
                break;
            case R.id.ll_condition_product:
                if (!isProduct) {
                    setConditionSelector(3);
                    menu.clear();
                    menu.addAll(product);
                    menuAdapter.setMenuList(menu);
                    isProduct = true;
                } else {
                    mTvProduct.setTextColor(getResources().getColor(R.color.text_grey));
                    mLlMenuList.setVisibility(View.GONE);
                    isProduct = false;
                }
                break;
            case R.id.mask:
                mLlMenuList.setVisibility(View.GONE);
                mTvTime.setTextColor(getResources().getColor(R.color.text_grey));
                mTvState.setTextColor(getResources().getColor(R.color.text_grey));
                mTvProduct.setTextColor(getResources().getColor(R.color.text_grey));
                break;
        }
    }

    /**
     * 获取数据
     */
    private void getDataFromServer(String dataStatus, String type, String status, int page, final boolean isLoad) {
        TreeMap<String, String> params = new TreeMap<>();
        params.put("userId", SPUtil.getString("userid", ""));
        params.put("dataStatus", dataStatus);
        params.put("type", type);
        params.put("status", status);
        params.put("pageNum", page + "");

        PostRequest<String> postRequest = OKGO_GetData.getDatePost(this, BaseParams.Orders, params);
        postRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                if (!TextUtils.isEmpty(response.body())) {
                    if (Integer.parseInt(JsonUtil.getFieldValue(response.body(), "code")) == 200) {
                        Order order = JsonUtil.parseJsonToBean(response.body(), Order.class);
                        if (isLoad) {
                            orders.addAll(order.data.cList);

                        } else {
                            orders.clear();
                            orders.addAll(order.data.cList);
                        }
                        ordersAdapter.notifyDataSetChanged();
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
     * 设置条件文字颜色和尖号方向
     */
    private void setConditionSelector(int type) {
        mLlMenuList.setVisibility(View.VISIBLE);
        if (type == 1) {//选中第一个条件
            mTvTime.setTextColor(getResources().getColor(R.color.text_dark));
            mTvState.setTextColor(getResources().getColor(R.color.text_grey));
            mTvProduct.setTextColor(getResources().getColor(R.color.text_grey));
        }

        if (type == 2) {//选中第二个条件
            mTvTime.setTextColor(getResources().getColor(R.color.text_grey));
            mTvProduct.setTextColor(getResources().getColor(R.color.text_grey));
            mTvState.setTextColor(getResources().getColor(R.color.text_dark));
        }

        if (type == 3) {//选中第三个条件
            mTvProduct.setTextColor(getResources().getColor(R.color.text_dark));
            mTvTime.setTextColor(getResources().getColor(R.color.text_grey));
            mTvState.setTextColor(getResources().getColor(R.color.text_grey));
        }
    }
}
