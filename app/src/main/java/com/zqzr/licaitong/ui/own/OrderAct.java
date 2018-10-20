package com.zqzr.licaitong.ui.own;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.zqzr.licaitong.R;
import com.zqzr.licaitong.adapter.DropMenuAdapter;
import com.zqzr.licaitong.base.BaseActivity;
import com.zqzr.licaitong.bean.Menu;
import com.zqzr.licaitong.utils.DensityUtils;
import com.zqzr.licaitong.utils.Utils;
import com.zqzr.licaitong.view.SpacesItemDecoration;

import java.util.ArrayList;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/19 10:41
 * <p/>
 * Description:
 */

public class OrderAct extends BaseActivity implements View.OnClickListener {
    private LinearLayout mLlTime,mLlState,mLlProduct,mLlMenuList;
    private TextView mTvTime,mTvState,mTvProduct;

    private ArrayList<Menu> menu = new ArrayList<>();
    private ArrayList<Menu> time = new ArrayList<>();
    private ArrayList<Menu> state = new ArrayList<>();
    private ArrayList<Menu> product = new ArrayList<>();

    private MaterialRefreshLayout mRefreshLayout;
    private RecyclerView mOrderRecyclerView;
    private ListView mConditionListView;
    private View mask;

    private boolean isTime,isState,isProduct;
    private DropMenuAdapter menuAdapter;

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
        mOrderRecyclerView = (RecyclerView) findViewById(R.id.order_recyclerview) ;
        LinearLayoutManager mLayoutMgr = new LinearLayoutManager(this);
        mOrderRecyclerView.setLayoutManager(mLayoutMgr);
        //添加ItemDecoration，item之间的间隔
        int leftRight = DensityUtils.dp2px(this, 0f);
        int topBottom = DensityUtils.dp2px(this, 12f);
        mOrderRecyclerView.addItemDecoration(new SpacesItemDecoration(leftRight, topBottom,R.color.line_grey));


        mLlProduct.setOnClickListener(this);
        mLlState.setOnClickListener(this);
        mLlTime.setOnClickListener(this);
        mask.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        time.add(new Menu("全部"));
        time.add(new Menu("3个月以下"));
        time.add(new Menu("3-6个月"));
        time.add(new Menu("6-12个月"));
        time.add(new Menu("12个月以上"));

        state.add(new Menu("全部"));
        state.add(new Menu("待受理"));
        state.add(new Menu("待签约"));
        state.add(new Menu("待审核"));

        product.add(new Menu("全部"));
        product.add(new Menu("票据"));
        product.add(new Menu("保理"));

        menuAdapter = new DropMenuAdapter();
        mConditionListView.setAdapter(menuAdapter);
        mConditionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Utils.toast(menu.get(position).getName());
                for (int i = 0;i<menu.size();i++){
                    menu.get(i).setSelect(false);
                    if (i == position){
                        menu.get(i).setSelect(true);
                    }
                    menuAdapter.setMenuList(menu);
                }
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
        switch (v.getId()){
            case R.id.ll_condition_time:
                if (!isTime){
                    setConditionSelector(1);
                    menu.clear();
                    menu.addAll(time);
                    menuAdapter.setMenuList(menu);
                    isTime = true;
                }else{
                    mTvTime.setTextColor(getResources().getColor(R.color.text_grey));
                    mLlMenuList.setVisibility(View.GONE);
                    isTime = false;
                }
                break;
            case R.id.ll_condition_state:
                setConditionSelector(2);
                if (!isState){
                    setConditionSelector(1);
                    menu.clear();
                    menu.addAll(state);
                    menuAdapter.setMenuList(menu);
                    isState = true;
                }else{
                    mTvState.setTextColor(getResources().getColor(R.color.text_grey));
                    mLlMenuList.setVisibility(View.GONE);
                    isState = false;
                }
                break;
            case R.id.ll_condition_product:
                if (!isProduct){
                    setConditionSelector(3);
                    menu.clear();
                    menu.addAll(product);
                    menuAdapter.setMenuList(menu);
                    isProduct = true;
                }else{
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
    private void getDataFromServer(){

    }

    /**
     * 设置条件文字颜色和尖号方向
     */
    private void setConditionSelector(int type){
        mLlMenuList.setVisibility(View.VISIBLE);
        if(type == 1){//选中第一个条件
            mTvTime.setTextColor(getResources().getColor(R.color.text_dark));
            mTvState.setTextColor(getResources().getColor(R.color.text_grey));
            mTvProduct.setTextColor(getResources().getColor(R.color.text_grey));
        }

        if(type == 2){//选中第二个条件
            mTvTime.setTextColor(getResources().getColor(R.color.text_grey));
            mTvProduct.setTextColor(getResources().getColor(R.color.text_grey));
            mTvState.setTextColor(getResources().getColor(R.color.text_dark));
        }

        if(type == 3){//选中第三个条件
            mTvProduct.setTextColor(getResources().getColor(R.color.text_dark));
            mTvTime.setTextColor(getResources().getColor(R.color.text_grey));
            mTvState.setTextColor(getResources().getColor(R.color.text_grey));
        }
    }
}
