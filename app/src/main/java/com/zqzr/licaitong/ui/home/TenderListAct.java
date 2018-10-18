package com.zqzr.licaitong.ui.home;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.zqzr.licaitong.R;
import com.zqzr.licaitong.adapter.DropMenuAdapter;
import com.zqzr.licaitong.base.BaseActivity;
import com.zqzr.licaitong.bean.Menu;
import com.zqzr.licaitong.utils.Utils;

import java.util.ArrayList;
/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/16 13:43
 * <p/>
 * Description:
 */

public class TenderListAct extends BaseActivity implements View.OnClickListener{

    private ArrayList<Menu> menu = new ArrayList<>();
    private ArrayList<Menu> limit = new ArrayList<>();
    private ArrayList<Menu> start = new ArrayList<>();
    private boolean isLimit,isStart;

    private ListView mTenderListView;
    private DropMenuAdapter limitAdapter, menuAdapter;
    private LinearLayout mLlConditionLimit,mLlConditionStart,mLlMenuList;
    private ListView mConditionListView;
    private MaterialRefreshLayout mRefreshLayout;
    private TextView mTvConditionLimit,mTvConditionStart;
    private ImageView mIvConditionLimit,mIvConditionStart;
    private View mask;

    @Override
    protected void onStart() {
        super.onStart();
        setBackOption(true);
        //设置标题
        if(getIntent().getIntExtra("type",-1) == 1){
            setTitle("票据投资");
        }
        if (getIntent().getIntExtra("type",-1) == 2){
            setTitle("保理投资");
        }
    }

    @Override
    protected void initView() {
        setContentView(R.layout.act_home_tenderlist);

        mLlConditionLimit = (LinearLayout) findViewById(R.id.ll_condition_limit);
        mTvConditionLimit = (TextView) findViewById(R.id.tv_condition_limit);
        mIvConditionLimit = (ImageView) findViewById(R.id.iv_limitImg);
        mLlConditionStart = (LinearLayout) findViewById(R.id.ll_condition_start);
        mTvConditionStart = (TextView) findViewById(R.id.tv_condition_startMark);
        mIvConditionStart = (ImageView) findViewById(R.id.iv_startImg);
        mLlMenuList = (LinearLayout) findViewById(R.id.ll_menuList);
        mConditionListView = (ListView) findViewById(R.id.condition_listview);
        mRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.mrl_refreshLayout);
        mTenderListView = (ListView) findViewById(R.id.tenderList_listview);
        mask = findViewById(R.id.mask);

        mLlConditionLimit.setOnClickListener(this);
        mLlConditionStart.setOnClickListener(this);
        mask.setOnClickListener(this);

    }

    @Override
    protected void initData() {

        limit.add(new Menu("全部"));
        limit.add(new Menu("3个月以下"));
        limit.add(new Menu("3-6个月"));
        limit.add(new Menu("6-12个月"));
        limit.add(new Menu("12个月以上"));

        start.add(new Menu("全部"));
        start.add(new Menu("5万起投"));
        start.add(new Menu("10万起投"));
        start.add(new Menu("20万起投"));

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

        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {

            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_condition_limit:
                if (!isLimit){
                    setConditionSelector(true);
                    menu.clear();
                    menu.addAll(limit);
                    menuAdapter.setMenuList(menu);
                    isLimit = true;
                }else{
                    mTvConditionLimit.setTextColor(getResources().getColor(R.color.text_grey));
                    mIvConditionLimit.setImageDrawable(getResources().getDrawable(R.mipmap.drop_down_unselected_icon));
                    isLimit = false;
                    mLlMenuList.setVisibility(View.GONE);
                }
                break;
            case R.id.ll_condition_start:
                if (!isStart){
                    setConditionSelector(false);
                    menu.clear();
                    menu.addAll(start);
                    menuAdapter.setMenuList(menu);
                    isStart = true;
                }else{
                    mTvConditionStart.setTextColor(getResources().getColor(R.color.text_grey));
                    mIvConditionStart.setImageDrawable(getResources().getDrawable(R.mipmap.drop_down_unselected_icon));
                    mLlMenuList.setVisibility(View.GONE);
                    isStart = false;
                }
                break;
            case R.id.mask:
                mLlMenuList.setVisibility(View.GONE);
                mTvConditionStart.setTextColor(getResources().getColor(R.color.text_grey));
                mIvConditionStart.setImageDrawable(getResources().getDrawable(R.mipmap.drop_down_unselected_icon));
                mTvConditionLimit.setTextColor(getResources().getColor(R.color.text_grey));
                mIvConditionLimit.setImageDrawable(getResources().getDrawable(R.mipmap.drop_down_unselected_icon));
                break;
        }
    }

    /**
     * 设置条件文字颜色和尖号方向
     * @param isSelect 表示第一个条件是否被选中，选中为true,那么第二个条件必定是未选中的状态，反之亦成立！
     */
    private void setConditionSelector(boolean isSelect){
        mLlMenuList.setVisibility(View.VISIBLE);
        if(isSelect){//选中第一个条件
            mTvConditionLimit.setTextColor(getResources().getColor(R.color.text_dark));
            mIvConditionLimit.setImageDrawable(getResources().getDrawable(R.mipmap.drop_down_selected_icon));
            mTvConditionStart.setTextColor(getResources().getColor(R.color.text_grey));
            mIvConditionStart.setImageDrawable(getResources().getDrawable(R.mipmap.drop_down_unselected_icon));
        }else{//选中第二个条件
            mTvConditionLimit.setTextColor(getResources().getColor(R.color.text_grey));
            mIvConditionLimit.setImageDrawable(getResources().getDrawable(R.mipmap.drop_down_unselected_icon));
            mTvConditionStart.setTextColor(getResources().getColor(R.color.text_dark));
            mIvConditionStart.setImageDrawable(getResources().getDrawable(R.mipmap.drop_down_selected_icon));
        }
    }
}
