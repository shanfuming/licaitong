package com.zqzr.licaitong.base;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import com.zqzr.licaitong.utils.ActivityUtils;
import com.zqzr.licaitong.utils.Utils;

import java.io.File;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/2/23 15:22
 * <p/>
 * Description:
 */
public class BaseParams {
    /**
     * 是否是debug模式
     */
    public static final  boolean IS_DEBUG            = true;
    /**
     * OCRkey
     */
    public static final String OCRAPPKEY           = "VdB1gDXNPJh6VHbCdU5hbfT1";
    /**
     * 测试服务器地址
     */
//    private static final String URL_BETA            = "http://10.16.8.43:8080";
    private static final String URL_BETA            = "http://10.16.8.33:8080";

    /**
     * 正式服务器地址
     */
    private static final String URL_RELEASE         = "https://www.51ztj.com";
    /**
     * 服务器地址
     */
    public static final String URL_ADDRESS         = IS_DEBUG ? URL_BETA : URL_RELEASE;
    /**
     * appkey
     */
    public static final String APP_KEY             = "6tqiC0AsASwO8T6MvMNfFJK1DQfsHiLC";
    /**
     * app_secret
     */
    public static final String APP_SECRET          = "gxixd9reotfifeqollfi0e2wgfkcey4x";
    /**
     * friday appkey
     */
    public static final String FRIDAY_APPKEY       = "6tqiC0AsASwO8T6MvMNfFJK1DQfsHiLC";
    /**
     * friday app secret
     */
    public static final String FRIDAY_APPSECRET    = "gxixd9reotfifeqollfi0e2wgfkcey4x";
    /**
     * ios传“IOS”，安卓传“ANDROID”
     */
    public static final String MOBILE_TYPE         = "ANDROID";
    /**
     * 发送验证码的短信平台号
     */
    public static final String SMS_SENDER          = "";
    /**
     * 加密是需要使用的密钥
     * DES加解密时KEY必须是16进制字符串,不可小于8位
     * E.G.    6C4E60E55552386C-
     * <p/>
     * 3DES加解密时KEY必须是6进制字符串,不可小于24位
     * E.G.    6C4E60E55552386C759569836DC0F83869836DC0F838C0F7
     */
    public static final String SECRET_KEY          = "6C4E60E55552386C759569836DC0F83869836DC0F838C0F7";
    /**
     * 根路径
     */
    public static final String ROOT_PATH           = Utils.getSDPath() + "/LiCaiTong";
    /**
     * crash文件保存路径
     */
    public static final String CRASH_PATH          = ROOT_PATH + "/crashLog";
    /**
     * SP文件名
     */
    public static final String SP_NAME             = "Ultron_params";
    public static final String SP_LOCK             = "lock_";
    public static final String SP_SIGN_OUT_TIME    = "sign_out_time";
    public static final String SP_IS_FIRST_INE     = "is_first_in";

    /**
     * 客服电话
     */
    public static final String servicePhone             = "4006069129";
    /**
     * 微信APPKey APPSecret
     */
    public static final String APPKeyWX            = "wxcd27d6ab6f4c3b53";//其他项目的key 需要替换
    public static final String APPSecretWx         = "0d62be666a2215d54e15e258d9702ba7";//其他项目的key 需要替换
    /**
     * 微博APPKey APPSecret
     */
    public static final String APPKeySina          = "3645032709";//其他项目的key 需要替换
    public static final String APPSecretSina       = "a4d989061815b4b80034b452feb829bb";//其他项目的key 需要替换
    /**
     * 登录
     */
    public static final String Do_Lgoin     = "/user/doLogin";
    /**
     * 注册下一步
     */
    public static final String Regist_Next = "/user/nextStep";
    /**
     * 注册发送验证码
     */
    public static final String Regist_Code = "/user/sendCode";

    /**
     * 申请理财师
     */
    public static final String Apply_Planner = "/user/financialPlanner";

    /**
     * 注册
     */
    public static final String Regist = "/user/register";
    /**
     * 忘记密码发送验证码
     */
    public static final String FindPwd_Code = "/user/forgetPassword";
    /**
     * 忘记密码
     */
    public static final String FindPwd = "/user/forgetPwSub";
    /**
     * 获取首页轮播图
     */
    public static final String Home_Banner = "/home/getSysScrollImages";
    /**
     * 活动
     */
    public static final String Activity = "/activity/homePage";
    /**
     * 意见反馈
     */
    public static final String Opinion = "/setting/opinion";
    /**
     * 获取版本信息
     */
    public static final String Version = "/commonSetting/getVersion";
    /**
     * 修改手机号
     */
    public static final String ChangePhone_Code = "/setting/changePhone";
    /**
     * 修改手机号
     */
    public static final String ChangePhone_Next = "/setting/nextStep";
    /**
     * 新闻和公司动态
     */
    public static final String Monent = "/moment/getMomentByParam";
    /**
     * 我的 页面信息
     */
    public static final String OwnPage = "/my/homePage";
    /**
     * 首页 投资分类和发现
     */
    public static final String GetTopOrSuggInvestions = "/home/getTopOrSuggInvestions";
    /**
     * 实名认证
     */
    public static final String AuthenticationUser = "/commonSetting/authenticationUser";
    /**
     * 绑定银行卡
     */
    public static final String BindBankCard = "/commonSetting/bankCheck";
    /**
     * 银行卡列表
     */
    public static final String BankCardList = "/my/bankCardList";
    /**
     * 解绑银行卡
     */
    public static final String UnBankCard = "/my/unbindBankCard";
    /**
     * 投资列表
     */
    public static final String ProductList = "/home/getHomeProductsByParam";
    /**
     * 投资详情
     */
    public static final String ProductDetail = "/home/getProductDetail";
}
