package com.zqzr.licaitong.http;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/4/5 18:16
 * <p/>
 * Description: 网络请求参数名字典类
 */
public class RequestParams {
    ///////////////////////////////////////////////////////////////////////////
    // common参数
    ///////////////////////////////////////////////////////////////////////////
    public static final String APP_KEY            = "appkey";
    public static final String SIGNA              = "signa";
    public static final String TS                 = "ts";
    public static final String CLIENT             = "client";
    public static final String VERSION_NUMBER     = "versionNumber";
    // 登录参数
    public static final String TOKEN              = "oauthToken";
    public static final String USER_ID            = "userId";
    public static final String REFRESH_TOKEN      = "refresh_token";
    public static final String ISLOGON            = "isLogon";
    ///////////////////////////////////////////////////////////////////////////
    // 登录请求参数
    ///////////////////////////////////////////////////////////////////////////
    public static final String ID                 = "id";
    public static final String BONDID             = "bondId";
    public static final String USERNAME           = "username";
    public static final String PASSWORD           = "password";
    public static final String PHONE              = "phone";
    public static final String CODE               = "code";
    public static final String VALIDCODE          = "validCode";
    public static final String SB                 = "sb";
    /**
     * IMEI相关
     */
    public static final String ACKTOKEN           = "ackToken";
    public static final String ACKAPPKEY          = "ackAppkey";
    public static final String ACKAPPKEY_NUM      = "291DE5F14A880DBC78A401856EE5771C";
    //分页参数
    public static final String PAGE               = "page";
    public static final String PAGE_SIZE          = "pagesize";
    public static final String ROWS               = "rows";
    //产品相关
    public static final String UUID               = "uuid";
    public static final String BORROWID           = "borrowId";
    public static final String MONEY              = "money";
    public static final String CAPITAL            = "capital";
    public static final String CASH               = "cash";
    public static final String BANKCODE           = "bankCode";
    public static final String DIRPWD             = "pwd";
    public static final String PASSWORDDIRECT     = "passwordDirect";
    public static final String PAYPASSWORD        = "paypwd";
    public static final String REDPACKETIDS       = "redPacketIds";
    public static final String EXPIDS             = "experience_ids";
    public static final String UPIDS              = "upRateIds";
    public static final String SESSION_ID         = "session_id";
    public static final String INVESTCAPITAL      = "investCapital";
    public static final String NEW_PWD            = "new_pwd";
    public static final String OLD_PWD            = "old_pwd";
    public static final String NEW_PAYPWD         = "new_paypwd";
    public static final String OLD_PAYPWD         = "old_paypwd";
    public static final String TCOPIES            = "tCopies";
    public static final String BONDINVESTMENTID   = "bondInvestmentId";
    public static final String BORROWINVESTMENTID = "borrowInvestmentId";
    public static final String INVESTMENTID       = "investmentId";
    //实名认证
    public static final String REALNAME           = "realName";
    public static final String IDNO               = "idNo";
    public static final String certFileBack       = "certFileBack";
    public static final String certFileFront      = "certFileFront";
    //消息
    public static final String TYPE               = "type";
    public static final String IDS                = "ids";
    public static final String FLAG               = "flag";
    //记录相关
    public static final String STATUS             = "status";
    public static final String TENDER_ID          = "tender_id";
    //双乾授权
    public static final String AUTHORIZE_TYPE     = "type";
    public static final String AUTHORIZE_ONOFF    = "on_off";
    //重置密码
    public static final String RESETPAYPWD_NEWPWD = "new_paypwd";
    public static final String RESETPAYPWD_IDCARD = "id_card";
    public static final String SETPAYPWD_PWD      = "payPwd";
    public static final String PHONE_OR_EMAIL     = "phone_or_email";
    public static final String NEWPASSWORD        = "newPassword";
    //上传头像
    public static final String IMGURL             = "headPortraitUrl";
    //自动投标相关
    public static final String enable             = "enable";
}
