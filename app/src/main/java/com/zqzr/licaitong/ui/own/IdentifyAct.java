package com.zqzr.licaitong.ui.own;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
import com.zqzr.licaitong.utils.ActivityUtils;
import com.zqzr.licaitong.utils.JsonUtil;
import com.zqzr.licaitong.utils.SPUtil;
import com.zqzr.licaitong.utils.Utils;
import com.zqzr.licaitong.view.SuccessAndFailDialog;

import java.util.TreeMap;

import static com.zqzr.licaitong.base.Constant.CITY_SELECT_CODE;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/19 19:28
 * <p/>
 * Description:
 */

public class IdentifyAct extends BaseActivity implements View.OnClickListener {
    private EditText mEtName, mEtIdCard, mEtAddress;
    private TextView mCommit;
    private TextView mTvSelectCity;
    private SuccessAndFailDialog successDialog;

    @Override
    protected void initView() {
        setContentView(R.layout.act_identify);

        mEtName = (EditText) findViewById(R.id.et_identify_name);
        mEtIdCard = (EditText) findViewById(R.id.et_identify_idcard);
        mEtAddress = (EditText) findViewById(R.id.et_identify_address);
        mCommit = (TextView) findViewById(R.id.tv_identify_commit);
        mTvSelectCity = (TextView) findViewById(R.id.tv_identify_city);

        mCommit.setOnClickListener(this);
        mTvSelectCity.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(getResources().getString(R.string.own_personIdentify));
        setBackOption(true);
    }

    @Override
    protected void initData() {
        successDialog = new SuccessAndFailDialog(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_identify_commit:
                commit();
                break;
            case R.id.tv_identify_city:
                selectCity();
                break;
        }
    }

    private void commit() {
        if (TextUtils.isEmpty(mEtName.getText().toString().trim())) {
            Utils.toast("请输入真实姓名");
            return;
        }
        if (TextUtils.isEmpty(mEtIdCard.getText().toString().trim())) {
            Utils.toast("请输入身份证号码");
            return;
        }

        if (TextUtils.isEmpty(addProvince)|| TextUtils.isEmpty(addCity)||TextUtils.isEmpty(mTvSelectCity.getText().toString())){
            Utils.toast(Constant.Apply_City_Null);
            return;
        }

        if (TextUtils.isEmpty(mEtAddress.getText().toString().trim())) {
            Utils.toast("请输入居住地址");
            return;
        }

        TreeMap<String,String> params = new TreeMap<>();
        params.put("id", SPUtil.getString("userid",""));
        params.put("name",mEtName.getText().toString());
        params.put("province",addProvince);
        params.put("city",addCity);
        params.put("contactAddress",mEtAddress.getText().toString());
        params.put("idNum",mEtIdCard.getText().toString());

        PostRequest<String> postRequest = OKGO_GetData.getDatePost(this, BaseParams.AuthenticationUser,params);
        postRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                if (!TextUtils.isEmpty(response.body())) {
                    Getcode getcode = JsonUtil.parseJsonToBean(response.body(), Getcode.class);
                    if (200 == Integer.parseInt(getcode.code)) {
                        successDialog.setContent("认证成功", true);
                        successDialog.setDes("请绑定回款银行卡", true);
                        successDialog.setImg(R.mipmap.success, true);
                        successDialog.setNext("下一步",true,R.drawable.fillet_loginbtn_normal);
                        successDialog.show();
                        successDialog.setClickListener(new SuccessAndFailDialog.ClickListener() {
                            @Override
                            public void onClickLook() {
                                Intent intent =  new Intent();
                                intent.putExtra("userRealName",mEtName.getText().toString());
                                intent.putExtra("idNum",mEtIdCard.getText().toString());
                                ActivityUtils.push(BankCardAct.class,intent);
                            }
                        });
                    } else {
                        successDialog.setContent("认证失败", true);
                        successDialog.setDes(getcode.message, true);
                        successDialog.setImg(R.mipmap.fail, true);
                        successDialog.setNext("返回",true,R.drawable.fillet_blue_btn_normal);
                        successDialog.show();
                        successDialog.setClickListener(new SuccessAndFailDialog.ClickListener() {
                            @Override
                            public void onClickLook() {
                                finish();
                            }
                        });
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

    /**
     * 选择省市
     */
    private void selectCity() {
        // 省市区三级联动
        Intent intent = new Intent(this, ShengShiQuActivity.class);
        startActivityForResult(intent, CITY_SELECT_CODE);
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
            mTvSelectCity.setText(addProvince + addCity);
        }
    }
}
