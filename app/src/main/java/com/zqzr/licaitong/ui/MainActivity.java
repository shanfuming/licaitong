package com.zqzr.licaitong.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zqzr.licaitong.MyApplication;
import com.zqzr.licaitong.R;
import com.zqzr.licaitong.framwork.MenuActivity;
import com.zqzr.licaitong.framwork.TabItem;
import com.zqzr.licaitong.ui.activity.AActivity;
import com.zqzr.licaitong.ui.find.FindActivity;
import com.zqzr.licaitong.ui.home.HomeActivity;
import com.zqzr.licaitong.ui.own.LoginAct;
import com.zqzr.licaitong.ui.own.OwnActivity;
import com.zqzr.licaitong.utils.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends MenuActivity implements OnClickListener {

    public static final String CORCLE = "Circle";
    public static final String CLASSROOM = "classroom";
    public static final String FIND = "find";
    public static final String SHOP = "Shop";
    public static final String MY = "My";

    public static String HASADDBILL = "hasaddbill";

    private List<TabItem> mTabItem;
    private List<View> mTabView;
    private LinearLayout mTvBtnGrabTreature,mTvBtnPublish, mTvBtnOwn, mTvBtnFind;

    private int cartCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFirstViewPage();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int tab = intent.getIntExtra("tab", -1);
        if (tab != -1) {
            switch (tab) {
                case 0:
                    updateTab(mTvBtnGrabTreature);
                    break;
                case 1:
                    updateTab(mTvBtnPublish);
                    break;
                case 2:
                    updateTab(mTvBtnFind);
                    break;
                case 3:
                    updateTab(mTvBtnOwn);
                    break;
            }
        }
    }

    @Override
    protected void initTabItem() {
        mTabItem = new ArrayList<TabItem>();

        TabItem grabTreatureItem = new TabItem(CORCLE, R.drawable.main_tab1_selector, new Intent(this, HomeActivity.class));
        TabItem publishItem = new TabItem(CLASSROOM, R.drawable.main_tab2_selector, new Intent(this, FindActivity.class));
        TabItem findItem = new TabItem(FIND, R.drawable.main_tab2_selector, new Intent(this, AActivity.class));
        TabItem ownItem = new TabItem(MY, R.drawable.main_tab4_selector, new Intent(this, OwnActivity.class));

        mTabItem.add(grabTreatureItem);
        mTabItem.add(publishItem);
        mTabItem.add(findItem);
        mTabItem.add(ownItem);

    }

    @Override
    protected void initTabView() {

        mTabView = new ArrayList<View>();
        mTvBtnGrabTreature = (LinearLayout) findViewById(R.id.btn_circle_tv);
        mTvBtnPublish = (LinearLayout) findViewById(R.id.btn_classroom_tv);
        mTvBtnOwn = (LinearLayout) findViewById(R.id.btn_my_tv);
        mTvBtnFind = (LinearLayout) findViewById(R.id.btn_find_tv);
        mTvBtnGrabTreature.setOnClickListener(this);
        mTvBtnPublish.setOnClickListener(this);
        mTvBtnFind.setOnClickListener(this);
        mTvBtnOwn.setOnClickListener(this);

        mTabView.add(mTvBtnGrabTreature);
        mTabView.add(mTvBtnPublish);
        mTabView.add(mTvBtnFind);
        mTabView.add(mTvBtnOwn);
    }

    @Override
    protected int getTabItemCount() {
        return mTabItem.size();
    }

    @Override
    protected int getTabViewCount() {
        return mTabView.size();
    }

    @Override
    protected Intent getTabItemIntent(int position) {
        return mTabItem.get(position).getIntent();
    }

    @Override
    protected View getTabView(int position) {
        return mTabView.get(position);
    }

    @Override
    protected String getTabItemId(int posotion) {
        return mTabItem.get(posotion).getTitle();
    }

    @Override
    protected void setTabItemPic(View view, int position) {
        Drawable image = getResources().getDrawable(mTabItem.get(position).getIcon());
        ((ImageView) view).setImageDrawable(image);
    }

    @Override
    public void onClick(View v) {
        updateTab(v);
        switch (v.getId()){
            case R.id.btn_my_tv:
                if (!MyApplication.getInstance().isLand()){
                    Intent intent = new Intent();
                    intent.putExtra("turn",1);
                    ActivityUtils.push(LoginAct.class,intent);
                }
                break;
        }
    }
}
