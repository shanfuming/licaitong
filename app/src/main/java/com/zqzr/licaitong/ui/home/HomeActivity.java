package com.zqzr.licaitong.ui.home;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.IconHintView;
import com.zqzr.licaitong.R;
import com.zqzr.licaitong.adapter.BannerAdapter;
import com.zqzr.licaitong.adapter.FindItemAdapter;
import com.zqzr.licaitong.adapter.HomeTouziListAdapter;
import com.zqzr.licaitong.base.BaseActivity;
import com.zqzr.licaitong.bean.FindItem;
import com.zqzr.licaitong.bean.HomeTouziType;
import com.zqzr.licaitong.utils.ActivityUtils;
import com.zqzr.licaitong.utils.Utils;
import com.zqzr.licaitong.view.PowerListView;

import java.util.ArrayList;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/13 12:55
 * <p/>
 * Description:
 */

public class HomeActivity extends BaseActivity implements View.OnClickListener {
    private RollPagerView bannerPager;
    private LinearLayout ll_piaoju,ll_baoli,ll_guquan,ll_fangchan;
    private PowerListView touziTypeListView,findListView;
    private ArrayList<HomeTouziType> touziTypesList = new ArrayList<>();
    private ArrayList<FindItem> findItemList = new ArrayList<>();
    private HomeTouziListAdapter homeTouziListAdapter;
    private FindItemAdapter findItemAdapter;

    @Override
    protected void initView() {
        setContentView(R.layout.act_home);

        bannerPager = (RollPagerView) findViewById(R.id.roll_view_pager);
        ll_piaoju = (LinearLayout) findViewById(R.id.ll_piaoju);
        ll_baoli = (LinearLayout) findViewById(R.id.ll_baoli);
        ll_guquan = (LinearLayout) findViewById(R.id.ll_guquan);
        ll_fangchan = (LinearLayout) findViewById(R.id.ll_fangchan);

        touziTypeListView = (PowerListView) findViewById(R.id.touziType_listview);
        findListView = (PowerListView) findViewById(R.id.find_listview);


        ll_piaoju.setOnClickListener(this);
        ll_baoli.setOnClickListener(this);
        ll_guquan.setOnClickListener(this);
        ll_fangchan.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        //轮播图
        bannerPager.setHintView(new IconHintView(this,R.mipmap.guide_point,R.mipmap.guide_point_cur));
        bannerPager.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Utils.toast("轮播图"+position);
            }
        });
        ArrayList<Integer> bannerList = new ArrayList<>();
        bannerList.add(R.mipmap.guide_01);
        bannerList.add(R.mipmap.guide_02);
        bannerList.add(R.mipmap.guide_03);

        bannerPager.setAdapter(new BannerAdapter(bannerList));
        //各种投资类型获取
        touziTypesList.add(new HomeTouziType(1,"票据投资","10.99%","预期年化收益","盛向泰票据001期","114天","8万提投"));
        touziTypesList.add(new HomeTouziType(2,"保理投资","10.11%","预期年化收益","盛向泰保理002期","116天","7万提投"));
        homeTouziListAdapter = new HomeTouziListAdapter(touziTypesList);
        touziTypeListView.setAdapter(homeTouziListAdapter);

        touziTypeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent();
                intent.putExtra("type",touziTypesList.get(position).getTypeId());
                ActivityUtils.push(TenderListAct.class,intent);
            }
        });

        findItemList.add(new FindItem("","发现1","2018年7月6日"));
        findItemList.add(new FindItem("","发现2","2018年8月9日"));

        findItemAdapter = new FindItemAdapter(findItemList);
        findListView.setAdapter(findItemAdapter);

        findListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_piaoju:
                Intent piaoju= new Intent();
                piaoju.putExtra("type",1);
                ActivityUtils.push(TenderListAct.class,piaoju);
                break;
            case R.id.ll_baoli:
                Intent baoli= new Intent();
                baoli.putExtra("type",2);
                ActivityUtils.push(TenderListAct.class,baoli);
                break;
            case R.id.ll_guquan:
                Intent guquan= new Intent();
                guquan.putExtra("type",3);
                ActivityUtils.push(TenderListAct.class,guquan);
                break;
            case R.id.ll_fangchan:
                Intent fangchan= new Intent();
                fangchan.putExtra("type",4);
                ActivityUtils.push(TenderListAct.class,fangchan);
                break;


        }
    }
}
