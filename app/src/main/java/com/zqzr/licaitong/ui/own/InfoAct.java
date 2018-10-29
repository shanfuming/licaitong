package com.zqzr.licaitong.ui.own;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.zqzr.licaitong.R;
import com.zqzr.licaitong.base.BaseActivity;
import com.zqzr.licaitong.base.Constant;
import com.zqzr.licaitong.utils.GlideImageLoader;
import com.zqzr.licaitong.utils.Utils;
import com.zqzr.licaitong.view.CircleImageView;
import com.zqzr.licaitong.view.SelectDialog;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/20 11:34
 * <p/>
 * Description:
 */

public class InfoAct extends BaseActivity {
    private CircleImageView mIvIcon;
    private TextView mTvTrueName, mTvPhone, mTvIDCardNum, mTvInviteCode;
    private RelativeLayout mRlHead;
    private ArrayList<ImageItem> imageItems;
    private int maxImgCount = 1;

    @Override
    protected void initView() {
        setContentView(R.layout.act_own_info);

        mIvIcon = (CircleImageView) findViewById(R.id.iv_info_icon);
        mTvTrueName = (TextView) findViewById(R.id.tv_info_trueName);
        mTvPhone = (TextView) findViewById(R.id.tv_info_phone);
        mTvIDCardNum = (TextView) findViewById(R.id.tv_info_IDcardNum);
        mTvInviteCode = (TextView) findViewById(R.id.tv_info_plannerInviteCode);
        mRlHead = (RelativeLayout) findViewById(R.id.rl_info_head);

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        Utils.loadImg(mIvIcon, intent.getStringExtra("headPortraitUrl"),null);
        mTvTrueName.setText(intent.getStringExtra("realName"));
        mTvPhone.setText(intent.getStringExtra("phone"));
        mTvIDCardNum.setText(intent.getStringExtra("idNo"));
        mTvInviteCode.setText(intent.getStringExtra("inviteCode"));

        mRlHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectIcon();
            }
        });

        initImagePicker();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setBackOption(true);
        setTitle(getResources().getString(R.string.own_info));
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
        imagePicker.setFocusWidth(200);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(200);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
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
                        Intent intent = new Intent(InfoAct.this, ImageGridActivity.class);
                        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS,true); // 是否是直接打开相机
                        startActivityForResult(intent, Constant.REQUEST_CODE_SELECT);
                        break;
                    case 1:
                        //打开选择,本次允许选择的数量
                        ImagePicker.getInstance().setSelectLimit(maxImgCount - imageItems.size());
                        Intent intent1 = new Intent(InfoAct.this, ImageGridActivity.class);
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
                if (images != null){
                    imageItems.addAll(images);
                    getQiNiuToken();
                }
            }
        }
    }

    /**
     * 获取七牛云token
     */
    private void getQiNiuToken() {

    }

    /**
     * 上传图片到七牛
     * @param filePath 要上传的图片路径
     * @param token 在七牛官网上注册的token
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
                uploadIcon();
            }

        }, null);
    }

    /**
     * 上传头像
     */
    private void uploadIcon() {

    }
}
