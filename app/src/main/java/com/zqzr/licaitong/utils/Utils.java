package com.zqzr.licaitong.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zqzr.licaitong.R;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/2/23 14:55
 * <p/>
 * Description:
 */
public class Utils {
    /**
     * 获取当前屏幕的密度
     */
    public static DisplayMetrics getMetrics(Activity act) {
        return act.getResources().getDisplayMetrics();
    }

    /**
     * 以最省内存的方式读取本地资源的图片
     *
     * @param context
     *         上下文
     * @param resId
     *         资源ID
     *
     * @return bitmap
     */
    public static Bitmap readBitmap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        // 获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    /**
     * 保存文件
     */
    public static String saveFile(Context c, String filePath, String fileName, Bitmap bitmap) {
        byte[] bytes = bitmapToBytes(bitmap);
        return saveFile(c, filePath, fileName, bytes);
    }

    public static byte[] bitmapToBytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 蒲公英更新
     */
//    public void pgyUpdate(final Context context, final boolean isHome) {
//        PgyUpdateManager.register((Activity) context, new UpdateManagerListener() {
//            @Override
//            public void onNoUpdateAvailable() {
//                if (!isHome) {
//                    Toast.makeText(context, R.string.pgy_update_no, Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onUpdateAvailable(String s) {
//                final AppBean appBean = getAppBeanFromString(s);
//                DialogUtils.updateDialog(context, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        startDownloadTask(
//                                (Activity) context,
//                                appBean.getDownloadURL());
//                    }
//                }).show();
//            }
//        });
//    }

    /**
     * 保存图片到本地
     */
    public static String saveFile(Context context, String filePath, String fileName, byte[] bytes) {
        if (!PermissionCheck.getInstance().checkPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            return "";
        }

        String fileFullName = "";
        FileOutputStream fos          = null;
        try {
            String suffix = "";
            if (filePath == null || filePath.trim().length() == 0) {
                return null;
            }
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            File fullFile = new File(filePath, fileName + suffix);
            fileFullName = fullFile.getPath();
            fos = new FileOutputStream(new File(filePath, fileName + suffix));
            fos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            fileFullName = "";
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    fileFullName = "";
                }
            }
        }
        return fileFullName;
    }

    /**
     * 递归删除文件和文件夹
     *
     * @param file
     *         要删除的根目录
     */
    public static void deleteFile(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
                return;
            }
            if (file.isDirectory()) {
                File[] childFile = file.listFiles();
                if (childFile == null || childFile.length == 0) {
                    file.delete();
                    return;
                }
                for (File f : childFile) {
                    deleteFile(f);
                }
                file.delete();
            }
        }
    }

    /**
     * 判断网络连接状况
     */
    public static boolean isConnect(Context context) {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                // 获取网络连接管理的对象
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    // 判断当前网络是否已经连接
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.d("error", e.toString());
        }
        Toast.makeText(context, context.getString(R.string.app_network_error), Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * toast 过滤 5s内禁止重复出现
     */
    private static final long                  Interval = 3 * 1000;
    private static final SoftMap<String, Long> map      = new SoftMap<>();
    private static Toast CURR_TOAST;

    public static void toast(Context context, String msg) {
        long preTime = 0;
        if (map.containsKey(msg)) {
            preTime = map.get(msg);
        }
        final long now = System.currentTimeMillis();
        if (now >= preTime + Interval) {
            if (CURR_TOAST != null) {
                CURR_TOAST.cancel();
            }
            if (context != null) {
                Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
                toast.show();
                map.put(msg, now);
                CURR_TOAST = toast;
            }
        }
    }

    public static void toast(int id) {
        Context context = ActivityUtils.peek();
        toast(context, context.getString(id));
    }

    public static void toast(String msg) {
        toast(ActivityUtils.peek(), msg);
    }

    /**
     * 颜色加深处理
     *
     * @param RGBValues
     *         RGB的值，由alpha（透明度）、red（红）、green（绿）、blue（蓝）构成，
     *         Android中我们一般使用它的16进制，
     *         例如："#FFAABBCC",最左边到最右每两个字母就是代表alpha（透明度）、
     *         red（红）、green（绿）、blue（蓝）。每种颜色值占一个字节(8位)，值域0~255
     *         所以下面使用移位的方法可以得到每种颜色的值，然后每种颜色值减小一下，在合成RGB颜色，颜色就会看起来深一些了
     */
    public static int colorBurn(int RGBValues) {
        int alpha = RGBValues >> 24;
        int red   = RGBValues >> 16 & 0xFF;
        int green = RGBValues >> 8 & 0xFF;
        int blue  = RGBValues & 0xFF;
        red = (int) Math.floor(red * (1 - 0.1));
        green = (int) Math.floor(green * (1 - 0.1));
        blue = (int) Math.floor(blue * (1 - 0.1));
        // return Color.rgb(red, green, blue);
        return Color.argb(alpha, red, green, blue);
    }

    /**
     * 是否是Collection的实现类
     */
    public static boolean isInstanceOfCollection(Class clazz) {
        Type genType = clazz.getGenericSuperclass();
        Type[] params  = ((ParameterizedType) genType).getActualTypeArguments();
        String result  = params[0].toString();
        return result.contains(Collection.class.getName());
    }

    /**
     * 判断是否是为String
     *
     * @param object
     *
     * @return
     */
    private static boolean isString(Object object) {
        if (object instanceof String) {
            return true;
        }
        return false;
    }

    /**
     * map 转换为 json
     *
     * @param data
     *
     * @return
     */
    public static String mapData2Json(Map<String, Object> data) {
        String json = "{";
        for (String key : data.keySet()) {
            if (isString(data.get(key))) {
                json = json + "\"" + key + "\":\"" + data.get(key) + "\",";
            } else {
                json = json + "\"" + key + "\":" + data.get(key) + ",";
            }
        }
        json = json.substring(0, json.length() - 1) + "}";
        return json;
    }

    /**
     * InputStream --> String
     *
     * @param is
     *
     * @return
     */
    public static String inputStream2String(InputStream is) {
        try {
            BufferedReader in     = new BufferedReader(new InputStreamReader(is));
            StringBuffer buffer = new StringBuffer();
            String line   = "";
            while ((line = in.readLine()) != null) {
                buffer.append(line);
            }
            in.close();
            return buffer.toString();
        } catch (IOException e) {
            return "";
        }
    }

    /**
     * File --> InputStream
     *
     * @param file
     *
     * @return
     */
    public static InputStream file2InputStream(File file) {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    /**
     * File --> String
     *
     * @param file
     *
     * @return
     */
    public static String flie2String(File file) {
        InputStream in = file2InputStream(file);
        if (in != null) {
            return inputStream2String(in);
        }
        return null;
    }

    /**
     * 删除 map中空键值
     *
     * @param map
     */
    public static TreeMap<String, Object> removeNull(Map<String, Object> map) {

        TreeMap<String, Object> newMap = new TreeMap<>();
        if(map == null || map.size() == 0){
            return  newMap;
        }
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() != null) {
                newMap.put(entry.getKey(), entry.getValue());
            }
        }
        return newMap;
    }

    public static byte[] getBytes(final String data, final String charset) {
        notNull(data, "Input");
        notEmpty(charset, "Charset");
        try {
            return data.getBytes(charset);
        } catch (final UnsupportedEncodingException e) {
            return data.getBytes();
        }
    }

    public static <T> T notNull(final T argument, final String name) {
        if (argument == null) {
            throw new IllegalArgumentException(name + " may not be null");
        }
        return argument;
    }

    public static <T extends CharSequence> T notEmpty(final T argument, final String name) {
        if (argument == null) {
            throw new IllegalArgumentException(name + " may not be null");
        }
        if (TextUtils.isEmpty(argument)) {
            throw new IllegalArgumentException(name + " may not be empty");
        }
        return argument;
    }

    /**
     * 加载图片
     * @param view
     * @param url
     * @param drawable
     */
    public static void loadImg(ImageView view, String url, Drawable drawable){
        if (drawable!=null){
            Glide.with(ActivityUtils.peek()).load(url).placeholder(drawable).into(view);//设置默认加载图
        }else{
            Glide.with(ActivityUtils.peek()).load(url).placeholder(drawable).into(view);//默认加载图
        }
    }

    /**
     * 判断listView 是否可以滑动
     *
     * @param viewGroup
     * @return
     */
    public static boolean isOnTop(ViewGroup viewGroup) {
        int[] groupLocation = new int[2];
        viewGroup.getLocationOnScreen(groupLocation);
        int[] itemLocation = new int[2];
        if (viewGroup.getChildAt(0) != null) {
            viewGroup.getChildAt(0).getLocationOnScreen(itemLocation);
            return groupLocation[1] == itemLocation[1];
        }
        return false;
    }

    /**
     * 获取VersionCode
     */
    public static int getVersion() {
        try {
            Context context = ActivityUtils.peek();
            PackageManager pm      = context.getPackageManager();//context为当前Activity上下文
            PackageInfo pi      = pm.getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return 1;
        }
    }

    /**
     * 获取VersionCode
     */
    public static String getVersionName() {
        try {
            Context context = ActivityUtils.peek();
            PackageManager pm      = context.getPackageManager();//context为当前Activity上下文
            PackageInfo pi      = pm.getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "--";
        }
    }

    /**
     * 获取SD卡的根目录
     */
    public static String getSDPath() {
        File sdDir = null;
        // 判断sd卡是否存在
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            // 获取跟目录
            sdDir = Environment.getExternalStorageDirectory();
        }
        if (sdDir == null) {
            return "";
        } else {
            return sdDir.toString();
        }
    }
}