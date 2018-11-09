package com.zqzr.licaitong.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Author: shanfuming
 * E-mail: shanfuming@zqzr.onaliyun.com
 * Time: 2018/10/27 16:13
 * <p/>
 * Description:
 */

public class SubscribeRecord  implements Serializable {
    public String ext;
    public String code;
    public String attach;
    public String message;
    public Data data;

    public class Data implements Serializable {
        public List<CList> cList;

        public class CList implements Serializable {

            public int productId;
            public Long addTime;
            public int fpId;
            public String fpName;
            public int reservationAmount;
            public int InvestmentId;
            public int id;
            public String userName;
            public int userId;
        }
    }
}
