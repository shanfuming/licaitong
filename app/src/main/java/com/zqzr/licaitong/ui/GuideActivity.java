package com.zqzr.licaitong.ui;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.umeng.analytics.MobclickAgent;
import com.zqzr.licaitong.R;
import com.zqzr.licaitong.adapter.GuideAdapter;
import com.zqzr.licaitong.base.BaseActivity;

import java.util.ArrayList;

public class GuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

	private ViewPager mGuideView;
	private ArrayList<View> views;
	private GuideAdapter guideAdapter;

	@Override
	protected void initView() {
		setContentView(R.layout.activity_guide);

		mGuideView = (ViewPager) findViewById(R.id.guide_viewpager);
		LayoutInflater inflater = LayoutInflater.from(this);
		views = new ArrayList<View>();
		// 初始化引导图片列表
		views.add(inflater.inflate(R.layout.what_new_one, null));
		views.add(inflater.inflate(R.layout.what_new_two, null));
		views.add(inflater.inflate(R.layout.what_new_three, null));
		// 初始化Adapter
		guideAdapter = new GuideAdapter(views, this);
		mGuideView.setAdapter(guideAdapter);
		// 绑定回调
		mGuideView.setOnPageChangeListener(this);
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

	@Override
	public void onPageSelected(int position) {}

	@Override
	public void onPageScrollStateChanged(int state) {}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}
	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onDestroy() {
//		this.finishBroadcastReceiver();
		super.onDestroy();
	}
}
