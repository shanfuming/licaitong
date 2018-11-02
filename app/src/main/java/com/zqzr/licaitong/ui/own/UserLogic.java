package com.zqzr.licaitong.ui.own;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;

import com.zqzr.licaitong.R;
import com.zqzr.licaitong.ui.own.gesturelock.logic.LockLogic;
import com.zqzr.licaitong.utils.ActivityUtils;
import com.zqzr.licaitong.utils.SPUtil;
import com.zqzr.licaitong.view.TipDialog;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/31 13:11
 * <p/>
 * Description:
 */

public class UserLogic {

    /**
     * 用户登出(手势密码5次错误)
     */
    public static void signOutPwdTime(String prompt,String confirmTitle) {
        LockLogic.getInstance().resetErrInputCount();
        removePersonInfo();
        createDialog(prompt, confirmTitle, new TipDialog.ClickListener() {
            @Override
            public void onClickExit() {
                ActivityUtils.onExit();
            }

            @Override
            public void onClickConfirm() {
                ActivityUtils.push(LoginAct.class);
            }
        });
    }

    /**
     * 用户登出(到登录界面)
     */
    public static void signOutToLogin(String prompt,String confirmTitle) {
        removePersonInfo();
        createDialog(prompt, confirmTitle, new TipDialog.ClickListener() {
            @Override
            public void onClickExit() {
                ActivityUtils.onExit();
            }

            @Override
            public void onClickConfirm() {
                ActivityUtils.push(LoginAct.class);
            }
        });
    }

    /**
     * 创建dialog
     * @param msg 提示标题
     * @param confirmTitle 确定后的操作
     * @param clickListener
     */
    public static void createDialog(String msg,String confirmTitle,TipDialog.ClickListener clickListener) {

        final TipDialog tipDialog = new TipDialog(ActivityUtils.peek());
        tipDialog.setTitle(msg);
        tipDialog.setButtonDes("取消",confirmTitle);
        tipDialog.show();
        tipDialog.setClickListener(clickListener);
    }

    /**
     * 清除个人信息
     */
    public static void removePersonInfo(){
        SPUtil.remove("token");
        SPUtil.remove("userid");
    }

}
