package com.zqzr.licaitong.base;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/2/18 16:23
 * <p/>
 * Description: 常量类
 */
public class Constant {
    /**
     * number
     */
    public static final int    NUMBER__1               = -1;
    public static final int    NUMBER_0                = 0;
    public static final int    NUMBER_1                = 1;
    public static final int    NUMBER_2                = 2;
    public static final int    NUMBER_3                = 3;
    public static final int    NUMBER_4                = 4;
    public static final int    NUMBER_5                = 5;
    public static final int    NUMBER_6                = 6;
    public static final int    NUMBER_7                = 7;
    public static final int    NUMBER_8                = 8;
    public static final int    NUMBER_9                = 9;
    /**
     * status
     */
    public static final String STATUS__1               = "-1";
    public static final String STATUS_0                = "0";
    public static final String STATUS_1                = "1";
    public static final String STATUS_2                = "2";
    public static final String STATUS_3                = "3";
    public static final String STATUS_4                = "4";
    public static final String STATUS_5                = "5";
    public static final String STATUS_6                = "6";
    public static final String STATUS_7                = "7";
    public static final String STATUS_8                = "8";
    public static final String STATUS_9                = "9";
    /**
     * true or false
     */
    public static final String TRUE                    = "true";
    public static final String FALSE                   = "false";
    /** 符号 */
    public static final String SYMBOL_PLUS      = "+";
    public static final String SYMBOL_MINUS     = "-";
    public static final int CITY_SELECT_CODE = 10000;
    /**
     * 投资状态值
     */
    public static final String STATUS_INVESTMENT_SB    = "失败";
    public static final String STATUS_INVESTMENT_TZZ   = "投资中";
    public static final String STATUS_INVESTMENT_HKZ   = "回款中";
    public static final String STATUS_INVESTMENT_YHK   = "已回款";
    public static final String STATUS_INVESTMENT_ZRZ   = "转让中";
    public static final String STATUS_INVESTMENT_YZR   = "已转让";
    public static final String STATUS_INVESTMENT_SDTB  = "手动投标";
    public static final String STATUS_INVESTMENT_ZDTB  = "自动投标";
    /**
     * 充值状态
     */
    public static final String STATUS_RECHARGE_CZSB    = "充值失败";
    public static final String STATUS_RECHARGE_CZCG    = "充值成功";
    public static final String STATUS_RECHARGE_SHZ     = "审核中";
    public static final String STATUS_RECHARGE_CZCSZ   = "充值初始化";
    public static final String STATUS_RECHARGE_CLZ     = "充值处理中";
    public static final String STATUS_RECHARGE_RGCL    = "人工处理";
    /**
     * 提现状态
     */
    public static final String STATUS_WITHDRAW_TXSB    = "提现失败";
    public static final String STATUS_WITHDRAW_TXCG    = "提现成功";
    public static final String STATUS_WITHDRAW_TXZ     = "提现中";
    public static final String STATUS_WITHDRAW_SHZ     = "审核中";
    public static final String STATUS_WITHDRAW_CSH     = "提现初始化";
    public static final String STATUS_WITHDRAW_RGCL    = "人工处理";
    /**
     * 理财类型
     */
    public static final String TYPE_FINANCING_DQHB     = "每月还息到期还本";
    public static final String TYPE_FINANCING_YCXHK    = "一次性还本付息";
    public static final String TYPE_FINANCING_FQHK     = "按月分期还款";
    public static final String TYPE_FINANCING_TQFX     = "一次性先息后本";
    public static final String TYPE_FINANCING_TQFXDQHB = "每月提前还息到期还本";
    /**
     * 借款标状态
     */
    public static final String STATUS_LOAN_ZBZ         = "招标中";
    public static final String STATUS_LOAN_CSWTG       = "初审未通过";
    public static final String STATUS_LOAN_MBDS        = "满标待审";
    public static final String STATUS_LOAN_HZK         = "还款中";
    public static final String STATUS_LOAN_FSWTG       = "复审未通过";
    public static final String STATUS_LOAN_YCH         = "已撤回";
    public static final String STATUS_LOAN_LB          = "流标";
    public static final String STATUS_LOAN_YHK         = "已还款";
    /**
     * 投资状态
     */
    public static final String STATUS_INVEST_SRDCL     = "投资中";
    public static final String STATUS_INVEST_DHK       = "待回款";
    public static final String STATUS_INVEST_JS        = "已结算";
    public static final String STATUS_INVEST_SB1       = "复审不通过";
    public static final String STATUS_INVEST_SB2       = "投资撤回";

    //请求公钥的接口
    public static final String URL = "http://mtest.cbhb.com.cn/mbank/common/MCPerPublicPinQry.do";

    //各种通知
    public static final String LOGINSUCCESS = "登录成功";
    public static final String REGISTSUCCESS = "注册成功";
}