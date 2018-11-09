package com.zqzr.licaitong.ui.own;

import android.app.DialogFragment;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.zqzr.licaitong.MyApplication;
import com.zqzr.licaitong.R;
import com.zqzr.licaitong.base.BaseActivity;
import com.zqzr.licaitong.base.BaseParams;
import com.zqzr.licaitong.base.Constant;
import com.zqzr.licaitong.bean.FindItem;
import com.zqzr.licaitong.bean.Login;
import com.zqzr.licaitong.bean.OwnInfo;
import com.zqzr.licaitong.bean.QiNiuToken;
import com.zqzr.licaitong.http.OKGO_GetData;
import com.zqzr.licaitong.utils.ActivityUtils;
import com.zqzr.licaitong.utils.GlideImageLoader;
import com.zqzr.licaitong.utils.JsonUtil;
import com.zqzr.licaitong.utils.SPUtil;
import com.zqzr.licaitong.utils.Utils;
import com.zqzr.licaitong.view.CircleImageView;
import com.zqzr.licaitong.view.KeyDownLoadingDialog;
import com.zqzr.licaitong.view.SelectDialog;
import com.zqzr.licaitong.view.TipDialog;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/13 13:07
 * <p/>
 * Description:
 */

public class OwnActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout mLlPerson, mLlSetting, mLlMoneyRecord, mLlOrder, mLlReturnPlan, mLlInfo;
    private TextView mTvOwnMoney, mTvIncoming, mTvIncomed;
    private RelativeLayout mRlIdentify, mRlBankcard, mRlPlanner, mRlEvalution, mRlQualitied;
    private CircleImageView mIvIcon;
    private TextView mTvUserName;
    private ImageView mIvCanSee;
    private boolean isNotSee = true;
    private String all, incomed, incomeing, plannerName, plannerPhone, plannerCity, realName, idNo, phone, headPortraitUrl, inviteCode;
    private TextView mTvIsIdentify;
    private int realNameStatus, bankStatus;
    private ArrayList<ImageItem> imageItems = new ArrayList<>();
    private int maxImgCount = 1;
    private KeyDownLoadingDialog loadingDialog;
    private String doman = "";

    @Override
    protected void initView() {
        setContentView(R.layout.act_own);

        mLlPerson = (LinearLayout) findViewById(R.id.ll_person);
        mIvIcon = (CircleImageView) findViewById(R.id.iv_person_icon);
        mLlSetting = (LinearLayout) findViewById(R.id.ll_setting);
        mTvOwnMoney = (TextView) findViewById(R.id.tv_own_money);
        mTvIncoming = (TextView) findViewById(R.id.tv_incoming);
        mTvIncomed = (TextView) findViewById(R.id.tv_incomed);
        mTvUserName = (TextView) findViewById(R.id.own_username);
        mIvCanSee = (ImageView) findViewById(R.id.iv_cansee);
        mTvIsIdentify = (TextView) findViewById(R.id.tv_isIdentify);

        mLlMoneyRecord = (LinearLayout) findViewById(R.id.ll_moneyRecord);
        mLlOrder = (LinearLayout) findViewById(R.id.ll_order);
        mLlReturnPlan = (LinearLayout) findViewById(R.id.ll_returnPlan);
        mLlInfo = (LinearLayout) findViewById(R.id.ll_info);

        mRlIdentify = (RelativeLayout) findViewById(R.id.rl_own_identify);
        mRlBankcard = (RelativeLayout) findViewById(R.id.rl_own_bankcard);
        mRlPlanner = (RelativeLayout) findViewById(R.id.rl_own_planner);
        mRlEvalution = (RelativeLayout) findViewById(R.id.rl_own_evalution);
        mRlQualitied = (RelativeLayout) findViewById(R.id.rl_own_qualitied);

        mLlMoneyRecord.setOnClickListener(this);
        mLlOrder.setOnClickListener(this);
        mLlReturnPlan.setOnClickListener(this);
        mLlInfo.setOnClickListener(this);
        mRlIdentify.setOnClickListener(this);
        mRlBankcard.setOnClickListener(this);
        mRlPlanner.setOnClickListener(this);
        mRlEvalution.setOnClickListener(this);
        mRlQualitied.setOnClickListener(this);
        mLlPerson.setOnClickListener(this);
        mLlSetting.setOnClickListener(this);
        mIvCanSee.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        initImagePicker();
        loadingDialog = new KeyDownLoadingDialog(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (MyApplication.getInstance().isLand()) {
            getOwnData();
            mTvIsIdentify.setVisibility(View.VISIBLE);
        } else {
            mTvUserName.setText("");
            mTvOwnMoney.setText("******");
            mTvIncomed.setText("******");
            mTvIncoming.setText("******");
            mTvIsIdentify.setVisibility(View.GONE);
        }
    }

    /**
     * 获取我的页面信息
     */
    private void getOwnData() {
        TreeMap<String, String> params = new TreeMap<>();

        PostRequest<String> postRequest = OKGO_GetData.getDatePost(this, BaseParams.OwnPage, params);
        postRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                if (!TextUtils.isEmpty(response.body())) {
                    if (Integer.parseInt(JsonUtil.getFieldValue(response.body(), "code")) == 200) {
                        OwnInfo ownInfo = JsonUtil.parseJsonToBean(response.body(), OwnInfo.class);
                        if (ownInfo.data!=null){
                            mTvUserName.setText(Utils.getEncodeStr(ownInfo.data.phone));
                            mTvOwnMoney.setText(Utils.getDouble2(ownInfo.data.amountTotalInvestment));
                            mTvIncomed.setText(Utils.getDouble2(ownInfo.data.incomeCollected));
                            mTvIncoming.setText(Utils.getDouble2(ownInfo.data.incomeCollecting));
                            Utils.loadImg(OwnActivity.this,mIvIcon, ownInfo.data.headPortraitUrl, null);
                            mTvIsIdentify.setText(Utils.getIdentifyType(ownInfo.data.realNameStatus));

                            all = Utils.getDouble2(ownInfo.data.amountTotalInvestment);
                            incomed = Utils.getDouble2(ownInfo.data.incomeCollected);
                            incomeing = Utils.getDouble2(ownInfo.data.incomeCollecting);
                            plannerCity = ownInfo.data.lcsCity;
                            plannerName = ownInfo.data.lcsRealName;
                            plannerPhone = ownInfo.data.lcsPhone;
                            realName = ownInfo.data.realName;
                            idNo = ownInfo.data.idNo;
                            phone = ownInfo.data.phone;
                            headPortraitUrl = ownInfo.data.headPortraitUrl;
                            inviteCode = ownInfo.data.referralCode;
                            realNameStatus = ownInfo.data.realNameStatus;
                            bankStatus = ownInfo.data.bankStatus;
                            SPUtil.setValue("usericon", headPortraitUrl);
                        }

                    } else if (Integer.parseInt(JsonUtil.getFieldValue(response.body(), "code")) == 10003) {

                        SPUtil.clear();
                        MyApplication.getInstance().updataLand(false);

                        Intent intent = new Intent();
                        intent.putExtra("turn", 1);
                        ActivityUtils.push(LoginAct.class, intent);
                    } else {
                        Utils.toast(JsonUtil.getFieldValue(response.body(), "message"));
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_moneyRecord:
                if (MyApplication.getInstance().isLand()) {
                    ActivityUtils.push(MoneyRecordAct.class);
                } else {
                    ActivityUtils.push(LoginAct.class);
                }
                break;
            case R.id.ll_order:
                if (MyApplication.getInstance().isLand()) {
                    ActivityUtils.push(OrderAct.class);
                } else {
                    ActivityUtils.push(LoginAct.class);
                }
                break;
            case R.id.ll_returnPlan:
                if (MyApplication.getInstance().isLand()) {
                    ActivityUtils.push(ReturnPlanAct.class);
                } else {
                    ActivityUtils.push(LoginAct.class);
                }
                break;
            case R.id.rl_own_identify:
                if (MyApplication.getInstance().isLand()) {
                    if (realNameStatus != 1) {//判断是否实名
                        ActivityUtils.push(IdentifyAct.class);
                    } else {
                        Utils.toast("您已实名认证");
                    }
                } else {
                    ActivityUtils.push(LoginAct.class);
                }
                break;
            case R.id.rl_own_bankcard:
                if (MyApplication.getInstance().isLand()) {
                    if (realNameStatus != 1) {
                        tip(1);
                    } else {
                        Intent intent = new Intent();
                        intent.putExtra("userRealName", realName);
                        intent.putExtra("idNum", idNo);
                        ActivityUtils.push(BankCardAct.class, intent);
                    }
                } else {
                    ActivityUtils.push(LoginAct.class);
                }
                break;
            case R.id.rl_own_planner:
                if (MyApplication.getInstance().isLand()) {
                    Intent intent = new Intent();
                    intent.putExtra("plannerName", plannerName);
                    intent.putExtra("plannerPhone", plannerPhone);
                    intent.putExtra("plannerCity", plannerCity);
                    ActivityUtils.push(PlannerAct.class, intent);
                } else {
                    ActivityUtils.push(LoginAct.class);
                }
                break;
            case R.id.rl_own_evalution:
                Utils.toast(Constant.Wait);
                break;
            case R.id.rl_own_qualitied:
                Utils.toast(Constant.Wait);
                break;
            case R.id.ll_person:
                if (MyApplication.getInstance().isLand()) {
                    selectIcon();
                } else {
                    ActivityUtils.push(LoginAct.class);
                }
                break;
            case R.id.ll_setting:
                if (MyApplication.getInstance().isLand()) {
                    ActivityUtils.push(SettingAct.class);
                } else {
                    ActivityUtils.push(LoginAct.class);
                }
                break;
            case R.id.ll_info:
                if (MyApplication.getInstance().isLand()) {
                    if (realNameStatus != 1) {
                        tip(1);
                    } else if (bankStatus == 1) {
                        tip(2);
                    } else {
                        Intent intent = new Intent();
                        intent.putExtra("realName", realName);
                        intent.putExtra("idNo", idNo);
                        intent.putExtra("phone", phone);
                        intent.putExtra("inviteCode", inviteCode);
                        ActivityUtils.push(InfoAct.class, intent);
                    }
                } else {
                    ActivityUtils.push(LoginAct.class);
                }
                break;

            case R.id.iv_cansee:
                if (MyApplication.getInstance().isLand()) {
                    mIvCanSee.setClickable(true);
                    setCanSee(isNotSee);
                    isNotSee = !isNotSee;
                } else {
                    mIvCanSee.setClickable(false);
                }
                break;

        }
    }

    /**
     * 提示去实名或者绑定银行卡
     *
     * @param type 1实名 2绑卡
     */
    private void tip(final int type) {
        final TipDialog tipDialog = new TipDialog(this, true);
        tipDialog.setTitle("温馨提示");
        if (type == 1) {
            tipDialog.setContent("您还没有实名认证!");
            tipDialog.setButtonDes("取消", "去认证");
        } else {
            tipDialog.setContent("您还没有绑定银行卡!");
            tipDialog.setButtonDes("取消", "去绑卡");
        }
        tipDialog.show();
        tipDialog.setClickListener(new TipDialog.ClickListener() {
            @Override
            public void onClickExit() {
                tipDialog.dismiss();
            }

            @Override
            public void onClickConfirm() {
                if (type == 1) {
                    ActivityUtils.push(IdentifyAct.class);
                } else {
                    ActivityUtils.push(BankCardAct.class);
                }
                tipDialog.dismiss();
            }
        });
    }

    /**
     * 设置信息可见或不可见
     */
    private void setCanSee(boolean isNotSee) {
        if (isNotSee) {
            mTvOwnMoney.setText("******");
            mTvIncomed.setText("******");
            mTvIncoming.setText("******");
        } else {
            mTvOwnMoney.setText(all);
            mTvIncomed.setText(incomed);
            mTvIncoming.setText(incomeing);
        }
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(true);                            //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
        imagePicker.setMultiMode(false);                      //多选
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
//        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
//        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
    }

    private SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names) {
        SelectDialog dialog = new SelectDialog(this, R.style.transparentFrameWindowStyle, listener, names);
        if (!this.isFinishing()) {
            dialog.show();
        }
        return dialog;
    }

    /**
     * 选择头像
     */
    private void selectIcon() {
        List<String> names = new ArrayList<>();
        names.add("拍照");
        names.add("相册");
        showDialog(new SelectDialog.SelectDialogListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: // 直接调起相机
                        //打开选择,本次允许选择的数量
                        ImagePicker.getInstance().setSelectLimit(maxImgCount - imageItems.size());
                        Intent intent = new Intent(OwnActivity.this, ImageGridActivity.class);
                        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                        startActivityForResult(intent, Constant.REQUEST_CODE_SELECT);
                        break;
                    case 1:
                        //打开选择,本次允许选择的数量
                        ImagePicker.getInstance().setSelectLimit(maxImgCount - imageItems.size());
                        Intent intent1 = new Intent(OwnActivity.this, ImageGridActivity.class);
                        startActivityForResult(intent1, Constant.REQUEST_CODE_SELECT);
                        break;
                    default:
                        break;
                }
            }
        }, names);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == Constant.REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null) {
                    imageItems.addAll(images);
                    getQiNiuToken(imageItems.get(0).path);
                }
            }
        }
    }

    /**
     * 获取七牛云token
     */
    private void getQiNiuToken(final String path) {
        loadingDialog.show();
        TreeMap<String, String> params = new TreeMap<>();

        PostRequest<String> postRequest = OKGO_GetData.getDatePost(this, BaseParams.GetQiniuToken, params);
        postRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                if (!TextUtils.isEmpty(response.body())) {
                    if (Integer.parseInt(JsonUtil.getFieldValue(response.body(), "code")) == 200) {
                        QiNiuToken qiNiuToken = JsonUtil.parseJsonToBean(response.body(), QiNiuToken.class);
                        uploadImageToQiniu(path, qiNiuToken.data.token);
                        doman = qiNiuToken.data.doman;
                    } else {
                        Utils.toast(JsonUtil.getFieldValue(response.body(), "message"));
                    }
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                Utils.toast("网络繁忙，请稍后再试!");
                loadingDialog.dismiss();
            }
        });
    }

    /**
     * 上传图片到七牛
     *
     * @param filePath 要上传的图片路径
     * @param token    在七牛官网上注册的token
     */
    private void uploadImageToQiniu(String filePath, String token) {
        UploadManager uploadManager = new UploadManager();
        // 设置图片名字
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String key = "icon_" + sdf.format(new Date());
        uploadManager.put(filePath, key, token, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject res) {
                // info.error中包含了错误信息，可打印调试
                // 上传成功后将key值上传到自己的服务器
                uploadIcon(doman + key);
            }

        }, null);
    }

    /**
     * 上传头像
     */
    private void uploadIcon(final String url) {
        TreeMap<String, String> params = new TreeMap<>();
        params.put("headPortraitUrl", url);

        PostRequest<String> postRequest = OKGO_GetData.getDatePost(this, BaseParams.UploadIcon, params);
        postRequest.execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                if (!TextUtils.isEmpty(response.body())) {
                    if (Integer.parseInt(JsonUtil.getFieldValue(response.body(), "code")) == 200) {
                        Utils.loadImg(OwnActivity.this,mIvIcon, url, null);
                        SPUtil.setValue("usericon", url);
                    } else {
                        Utils.toast(JsonUtil.getFieldValue(response.body(), "message"));
                    }
                    loadingDialog.dismiss();
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                Utils.toast("网络繁忙，请稍后再试!");
                loadingDialog.dismiss();
            }
        });
    }
}
