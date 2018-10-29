package com.zqzr.licaitong.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/7/28 13:49
 * <p>
 * Description: 常用的日期格式化方法
 */
@SuppressWarnings("unused")
public class DateUtil {
    public enum Format {
        /** 日期 + 时间类型格式，到秒 */
        SECOND("yyyy-MM-dd HH:mm:ss"),
        /** 日期 + 时间类型格式，到分 */
        MINUTE("yyyy-MM-dd HH:mm"),
        /** 日期类型格式，到日 */
        DATE("yyyy-MM-dd"),
        /** 日期类型格式，到月 */
        MONTH("yyyy-MM"),
        /** 日期类型格式，到月 */
        MONTH_CHINA("yyyy年MM月"),
        /** 时间类型的格式 */
        TIME("HH:mm:ss");
        // 格式化格式
        private String value;

        Format(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    // 注意SimpleDateFormat不是线程安全的
    private static SoftHashMap<String, ThreadLocal<SimpleDateFormat>> map = new SoftHashMap<>();

    /**
     * 日期格式化
     */
    public static String formatter(Format format, Object date) {
        if (date == null) {
            return "";
        } else {
            SimpleDateFormat sdf = null;
            String key = format.getValue();
            if (map.containsKey(key)) {
                sdf = map.get(key).get();
            }
            if (null == sdf) {
                sdf = new SimpleDateFormat(key, Locale.getDefault());
                ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<>();
                threadLocal.set(sdf);
                map.put(key, threadLocal);
            }
            return sdf.format(new Date(ConverterUtil.getLong(date.toString())));
        }
    }

    /**
     * 日期格式化
     */
    public static String formatter(String format, Object date) {
        if (date == null) {
            return "";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
            return sdf.format(new Date(ConverterUtil.getLong(date.toString())));
        }
    }

    /**
     * 日期格式化
     */
    public static String formatter(String format, long date) {
        if (date  <= 0) {
            return "";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
            return sdf.format(new Date(date));
        }
    }

    /**
     * 计算两个时间之间的天数 -- 自然日
     *
     * @param startDate
     * @param endDate
     *
     * @return
     */
    public static int computeDays(Object startDate, Object endDate) {
        int days = 0;
        //时间转换类
        try {
            SimpleDateFormat sdf       = new SimpleDateFormat("yyyy-MM-dd");
            Date date1     = sdf.parse(formatter("yyyy-MM-dd", startDate.toString()));
            Date date2     = sdf.parse(formatter("yyyy-MM-dd", endDate.toString()));
            Calendar calendar1 = Calendar.getInstance();
            Calendar calendar2 = Calendar.getInstance();
            calendar1.setTime(date1);
            calendar2.setTime(date2);
            //设置时间为0时
            calendar1.set(Calendar.HOUR_OF_DAY, 0);
            calendar1.set(Calendar.MINUTE, 0);
            calendar1.set(Calendar.SECOND, 0);
            calendar2.set(Calendar.HOUR_OF_DAY, 0);
            calendar2.set(Calendar.MINUTE, 0);
            calendar2.set(Calendar.SECOND, 0);
            //得到两个日期相差的天数
            days = ((int)(calendar2.getTime().getTime()/1000)-(int)(calendar1.getTime().getTime()/1000))/3600/24;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    /**
     * 计算两个时间戳之间的天数 -- 按时间
     *
     * @param start
     * @param end
     *
     * @return
     */
    public static int computeDays(long start, long end) {
        long timeInterval = end - start;
        if(timeInterval > 0) {
            double days = timeInterval/1000f/60/60/24;
            return (int) Math.ceil(days);
        }
        return 0;
    }

    /**
     * 初略的剩余时间，年、个月、天、小时、分钟
     */
    public static String getTimeLeft(Object time) {
        if (time == null) {
            return "";
        } else {
            long diffValue = ConverterUtil.getLong(time.toString());

            long minute = 1000 * 60;
            long hour   = minute * 60;
            long day    = hour * 24;
            long month  = day * 30;
            long year   = month * 12;

            long _year  = diffValue / year;
            long _month = diffValue / month;
            long _day   = diffValue / day;
            long _hour  = diffValue / hour;
            long _min   = diffValue / minute;

            if (_year >= 1) {
                return (_year) + "年";
            } else if (_month >= 1) {
                return (_month) + "个月";
            } else if (_day >= 1) {
                return (_day) + "天";
            } else if (_hour >= 1) {
                return (_hour) + "小时";
            } else {
                return (_min) + "分钟";
            }
        }
    }

    /**
     * 倒计时格式化，时:分:秒
     */
    public static String getCountdownTime(Object time) {
        if (time == null) {
            return "";
        } else {
            long diffValue = ConverterUtil.getLong(time.toString());
            long day       = diffValue / (24 * 60 * 60 * 1000);
            long hour      = (diffValue / (60 * 60 * 1000) - day * 24);
            long min       = ((diffValue / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long sec       = (diffValue / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            return (hour > 9 ? hour : ("0" + hour)) + ":" + (min > 9 ? min : ("0" + min)) + ":" + (sec > 9 ? sec : ("0" + sec));
        }
    }

    /**
     * 倒计时格式化，时:分:秒
     */
    public static String getCountdownTime(Object time, String type) {
        if (time == null) {
            return "";
        } else {
            long diffValue = ConverterUtil.getLong(time.toString());
            long day       = diffValue / (24 * 60 * 60 * 1000);
            long hour      = (diffValue / (60 * 60 * 1000) - day * 24);
            long min       = ((diffValue / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long sec       = (diffValue / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            return (hour > 9 ? hour : ("0" + hour)) + "时" + (min > 9 ? min : ("0" + min)) + "分" + (sec > 9 ? sec : ("0" + sec)) + "秒";
        }
    }

    /**
     * 倒计时格式化，时:分:秒
     */
    public static String getCountdownMin(Object time) {
        if (time == null) {
            return "";
        } else {
            long diffValue = ConverterUtil.getLong(time.toString());
            long day       = diffValue / (24 * 60 * 60 * 1000);
            long hour      = (diffValue / (60 * 60 * 1000) - day * 24);
            long min       = ((diffValue / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long sec       = (diffValue / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            return (min > 9 ? min : ("0" + min)) + ":" + (sec > 9 ? sec : ("0" + sec));
        }
    }

    /**
     * 开售时间格式化
     *
     * @param time
     *         开售时间戳
     * @param current
     *         当前服务器时间戳
     */
    public static String getTimeOfSale(Object time, Object current) {
        if (time == null || current == null) {
            return "";
        }
        long saleTime    = ConverterUtil.getLong(time.toString());
        long currentTime = ConverterUtil.getLong(current.toString());
        long millisecond = saleTime - currentTime;

        // 出售时间小于3小时
        if (millisecond < 3 * 60 * 60 * 1000 && millisecond > 0) {
            return "距离开抢 " + getCountdownTime(millisecond);
        } else if (millisecond >= 3 * 60 * 60 * 1000) {
            // 出售时间不是当天
            if (!formatter(Format.DATE, time).equals(formatter(Format.DATE, current))) {
                return formatter("MM月dd日 HH:mm", saleTime) + " 开始抢购";
            } else {
                return formatter("HH:mm", saleTime) + " 即将开售";
            }
        }
        return "";
    }
}