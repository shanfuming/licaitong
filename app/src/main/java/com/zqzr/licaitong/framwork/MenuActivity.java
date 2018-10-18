package com.zqzr.licaitong.framwork;

import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.Toast;

import com.zqzr.licaitong.MyApplication;
import com.zqzr.licaitong.R;

public abstract class MenuActivity extends TabActivity {
	
	private Context mContext;
    private TabHost mTabHost;
    private TabWidget mTabWidget;
    private LayoutInflater mlayoutInflater;
    private View mViewBox;
    private FinshStartbroadCast mFinishBorad;
    
    private long timestamp = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mContext = MyApplication.getInstance();
        mlayoutInflater = getLayoutInflater();
        mTabHost = getTabHost();
        mTabWidget = getTabWidget();
        mTabWidget.setStripEnabled(false);
        initTabItem();
        initTabView();
        initTabSpace();
        initBroadCast();
    }
	
	protected void initTabItem(){}
    protected void initTabView(){}

    private void initTabSpace(){

        if(getTabItemCount() != getTabViewCount()){
            return;
        }

        for(int i = 0 ; i < getTabItemCount() ; i ++){
            getTabView(i).setTag(getTabItemId(i));
            String tabItemId = getTabItemId(i);
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(tabItemId);
            tabSpec.setIndicator(tabItemId);
            tabSpec.setContent(getTabItemIntent(i));
            mTabHost.addTab(tabSpec);
        }
    }

    abstract protected int getTabItemCount();
    abstract protected int getTabViewCount();

    abstract protected Intent getTabItemIntent(int position);
    abstract protected View getTabView(int position);
    abstract protected String getTabItemId(int posotion);
    abstract protected void setTabItemPic(View view,int position);

    protected void setCurrentTab(int index){

        mTabHost.setCurrentTab(index);
    }
    /**
     *
     */
    protected void initFirstViewPage(){
        updateTab(getTabView(0));
    }

    public void updateTab(View v){
        mTabHost.setCurrentTabByTag((String) v.getTag());
        v.setSelected(true);
        if(mViewBox != null && v.getId() != mViewBox.getId()){
            mViewBox.setSelected(false);
        }
        mViewBox = v;
    }

    @Override
    protected void onDestroy() {
        if(mFinishBorad != null)
            unregisterReceiver(mFinishBorad);
        super.onDestroy();
        //双击返回，退出程序，释放内存
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
        System.exit(0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(this instanceof MenuActivity){
            if(System.currentTimeMillis() - timestamp > 2000){
                timestamp = System.currentTimeMillis();
                Toast.makeText(MyApplication.getInstance(), "再一次退出程序", Toast.LENGTH_SHORT).show();
            }else {
                Intent intent = new Intent("exitApp");
                sendBroadcast(intent);
                finish();
            }
            return true;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }
    /**
     * 退出程序
     */
    private void initBroadCast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("finshMainActivity");
        mFinishBorad = new FinshStartbroadCast();
        registerReceiver(mFinishBorad, intentFilter);
    }

    private final class FinshStartbroadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    }

}
