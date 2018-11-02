package com.zqzr.licaitong.ui.own;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.zqzr.licaitong.R;
import com.zqzr.licaitong.base.BaseActivity;
import com.zqzr.licaitong.base.BaseParams;
import com.zqzr.licaitong.base.Constant;
import com.zqzr.licaitong.bean.Getcode;
import com.zqzr.licaitong.http.OKGO_GetData;
import com.zqzr.licaitong.ui.own.select_shengshiqu.ShengShiQuActivity;
import com.zqzr.licaitong.utils.JsonUtil;
import com.zqzr.licaitong.utils.RegularUtil;
import com.zqzr.licaitong.utils.Utils;
import com.zqzr.licaitong.view.SuccessAndFailDialog;

import java.util.TreeMap;

import static com.zqzr.licaitong.base.Constant.CITY_SELECT_CODE;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/13 17:59
 * <p/>
 * Description:
 */

public class ApplyPlannerAct extends BaseActivity implements View.OnClickListener {

    private EditText mEtUserName,mEtPhone,mEtAddress;
    private LinearLayout mLlApplyCity;
    private TextView mTvApplyCity,mTvApply;
    private SuccessAndFailDialog successDialog;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case Constant.NUMBER_0:
                    successDialog.dismiss();
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void initView() {
        setContentView(R.layout.act_own_register_apply);

        mEtUserName = (EditText) findViewById(R.id.et_apply_userName);
        mEtPhone = (EditText) findViewById(R.id.et_apply_phone);
        mLlApplyCity = (LinearLayout) findViewById(R.id.ll_apply_city);
        mTvApplyCity = (TextView) findViewById(R.id.tv_apply_city);
        mEtAddress = (EditText) findViewById(R.id.et_apply_address);
        mTvApply = (TextView) findViewById(R.id.tv_apply);

        mLlApplyCity.setOnClickListener(this);
        mTvApply.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        successDialog = new SuccessAndFailDialog(this);
        successDialog.setContent("申请成功",true);
        successDialog.setDes("",true);
        successDialog.setImg(R.mipmap.success,true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_apply_city:
                selectCity();
                break;

            case R.id.tv_apply:
                apply();
                break;
        }
    }

    /**
     * 选择省市
     */
    private void selectCity() {
        // 省市区三级联动
        Intent intent = new Intent(this, ShengShiQuActivity.class);
        startActivityForResult(intent, 20000);
        // 添加开启底部window的动画
        overridePendingTransition(R.anim.dialog_enter, 0);
    }

    private String addProvince;
    private String addCity;
    private String addDistrict;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == CITY_SELECT_CODE) {
            addProvince = data.getStringExtra("ProviceName");
            addCity = data.getStringExtra("CityName");
            addDistrict = data.getStringExtra("DistrictName");
            mTvApplyCity.setText(addProvince+addCity);
        }
    }

    private void apply(){
        //判断手机号是否符合
        if (!RegularUtil.isPhone(mEtPhone.getText().toString())){
            Utils.toast(Constant.Login_UserName_Null);
            return;
        }
        //判断其他参数是否为空
        if (TextUtils.isEmpty(mEtUserName.getText().toString())){
            Utils.toast(Constant.Apply_UserName_Null);
            return;
        }

        if (TextUtils.isEmpty(mEtAddress.getText().toString())){
            Utils.toast(Constant.Apply_Address_Null);
            return;
        }

        if (TextUtils.isEmpty(addProvince)||TextUtils.isEmpty(addCity)||TextUtils.isEmpty(mTvApplyCity.getText().toString())){
            Utils.toast(Constant.Apply_City_Null);
            return;
        }

        TreeMap<String,String> params = new TreeMap<>();
        params.put("phone",mEtPhone.getText().toString());
        params.put("userRealName",mEtUserName.getText().toString());
        params.put("province",addProvince);
        params.put("city",addCity);
        params.put("address",mEtAddress.getText().toString());

        PostRequest<String> postRequest = OKGO_GetData.getDatePost(this, BaseParams.Apply_Planner,params);
        postRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                if (!TextUtils.isEmpty(response.body())) {
                    Getcode getcode = JsonUtil.parseJsonToBean(response.body(), Getcode.class);
                    if (200 == Integer.parseInt(getcode.code)) {
                        successDialog.show();
                        handler.sendEmptyMessageDelayed(Constant.NUMBER_0,2000);
                    } else {
                        Utils.toast(getcode.message);
                    }
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                Utils.toast(Constant.NetWork_Error);
            }
        });

    }
}
