package com.zqzr.licaitong.ui.own;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zqzr.licaitong.MyApplication;
import com.zqzr.licaitong.R;
import com.zqzr.licaitong.base.BaseActivity;
import com.zqzr.licaitong.base.BaseParams;
import com.zqzr.licaitong.utils.ActivityUtils;
import com.zqzr.licaitong.utils.Utils;
import com.zqzr.licaitong.view.TipDialog;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/19 16:30
 * <p/>
 * Description:
 */

public class SettingAct extends BaseActivity implements View.OnClickListener {

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    private RelativeLayout mRlSecurity,mRlHelp,mRlAbout,mRlOpinion,mRlContact;

    @Override
    protected void initView() {
        setContentView(R.layout.act_setting);

        mRlSecurity = (RelativeLayout) findViewById(R.id.rl_setting_security);
        mRlHelp = (RelativeLayout) findViewById(R.id.rl_setting_help);
        mRlAbout = (RelativeLayout) findViewById(R.id.rl_setting_version);
        mRlOpinion = (RelativeLayout) findViewById(R.id.rl_setting_opinion);
        mRlContact = (RelativeLayout) findViewById(R.id.rl_setting_contact);

        mRlAbout.setOnClickListener(this);
        mRlHelp.setOnClickListener(this);
        mRlSecurity.setOnClickListener(this);
        mRlOpinion.setOnClickListener(this);
        mRlContact.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(getResources().getString(R.string.own_setting_title));
        setBackOption(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_setting_version:
                ActivityUtils.push(SettingVersionAct.class);
                break;
            case R.id.rl_setting_security:
                ActivityUtils.push(SettingSecurityAct.class);
                break;
            case R.id.rl_setting_help:

                break;
            case R.id.rl_setting_opinion:
                if (MyApplication.getInstance().isLand()){
                    ActivityUtils.push(SettingOpinionAct.class);
                }else{
                    ActivityUtils.push(LoginAct.class);
                }
                break;
            case R.id.rl_setting_contact:
                callService();
                break;
        }
    }

    /**
     * 联系客服
     */
    private void callService() {
        if (Utils.isCurrentInTimeScope(9, 30, 18, 0)) {
            tip();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setMessage("您好，我们的工作时间是上午9:30-晚上21:00")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.create().show();
        }
    }

    /**
     * 提示
     */
    private void tip(){
        final TipDialog tipDialog = new TipDialog(this,true);
        tipDialog.setTitle("温馨提示");
        tipDialog.setContent("拨打理财通客服电话");
        tipDialog.setButtonDes("取消", "确定");
        tipDialog.show();
        tipDialog.setClickListener(new TipDialog.ClickListener() {
            @Override
            public void onClickExit() {
                tipDialog.dismiss();
            }

            @Override
            public void onClickConfirm() {
                call(SettingAct.this, BaseParams.servicePhone);
                tipDialog.dismiss();
            }
        });
    }

    /**
     * 拨打电话前申请权限
     * @param context
     * @param num
     */
    public static void call(Activity context, String num) {
        //6.0系统权限请求
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.CALL_PHONE)) {
                Toast.makeText(context, "请授权！", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                context.startActivity(intent);
            } else {
                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
            }
        } else {
            CallPhone(context,num);
        }
    }

    public static void CallPhone(Activity c,String num) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        //url:统一资源定位符
        //uri:统一资源标示符（更广）
        intent.setData(Uri.parse("tel:" + num));
        //开启系统拨号器
        c.startActivity(intent);
    }
}
