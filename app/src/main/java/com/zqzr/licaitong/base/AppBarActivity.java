package com.zqzr.licaitong.base;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zqzr.licaitong.R;
import com.zqzr.licaitong.utils.Utils;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/3/21 15:02
 * <p/>
 * Description: 带AppBar的activity
 */
public class AppBarActivity extends AppCompatActivity {
    private ImageView mAppBarLeftIv, mAppBarRightIv;
    private RelativeLayout mAppbarBg;
    private FrameLayout mAppbarLeftParent;
    private TextView mAppBarLeftTv, mAppBarRightTv;
    private TextView mAppBarTitle, mAppBarError;
    private FrameLayout mAppBarRightParent;
    // title bar

    @Override
    protected void onStart() {
        super.onStart();
        mAppbarBg = (RelativeLayout) findViewById(R.id.appbar_bg);
        mAppbarLeftParent = (FrameLayout) findViewById(R.id.left_parent);
        mAppBarLeftIv = (ImageView) findViewById(R.id.appbar_left);
        mAppBarLeftTv = (TextView) findViewById(R.id.appbar_left_text);
        mAppBarTitle = (TextView) findViewById(R.id.appbar_title);
        mAppBarRightParent = (FrameLayout) findViewById(R.id.right_parent);
        mAppBarRightIv = (ImageView) findViewById(R.id.appbar_right);
        mAppBarRightTv = (TextView) findViewById(R.id.appbar_right_text);
        mAppBarError = (TextView) findViewById(R.id.appbar_error);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    ///////////////////////////////////////////////////////////////////////////
    // title
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 为appbar设置标题
     */
    @Override
    public void setTitle(CharSequence title) {
        if (!TextUtils.isEmpty(title)) {
            mAppBarTitle.setText(title);
        }
    }

    public void setTitle(CharSequence title, @ColorInt int color) {
        mAppBarTitle.setText(title);
        mAppBarTitle.setTextColor(color);
    }

    ///////////////////////////////////////////////////////////////////////////
    // left
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 设置是否显示back键
     */
    public void setBackOption(boolean option) {
        if (option) {
            mAppBarLeftIv.setVisibility(View.VISIBLE);
            mAppbarLeftParent.setEnabled(true);
            mAppbarLeftParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        } else {
            mAppbarLeftParent.setOnClickListener(null);
            mAppbarLeftParent.setEnabled(false);
            mAppBarLeftIv.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 设置是否显示back键
     */
    public void setBackOption(boolean option,View.OnClickListener clickListener) {
        if (option) {
            mAppBarLeftIv.setVisibility(View.VISIBLE);
            mAppbarLeftParent.setEnabled(true);
            mAppbarLeftParent.setOnClickListener(clickListener);
        } else {
            mAppbarLeftParent.setOnClickListener(null);
            mAppbarLeftParent.setEnabled(false);
            mAppBarLeftIv.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 设置左侧文字
     */
    public void setLeftText(CharSequence text, View.OnClickListener listener, @ColorInt int color) {
        mAppBarLeftTv.setText(text);
        mAppBarLeftTv.setVisibility(View.VISIBLE);
        mAppBarLeftIv.setVisibility(View.GONE);
        if (null != listener) {
            mAppbarLeftParent.setOnClickListener(listener);
        }
        if (color != 0) {
            mAppBarLeftTv.setTextColor(color);
        }
    }

    public void setLeftText(CharSequence text, View.OnClickListener listener) {
        setLeftText(text, listener, 0);
    }

    /**
     * 设置左侧返回健的图片和监听事件
     *
     * @param resId
     * @param listener
     */
    public void setLeftImage(@DrawableRes int resId, View.OnClickListener listener) {
        mAppBarLeftIv.setImageResource(resId);
        mAppBarLeftIv.setVisibility(View.VISIBLE);
        mAppBarLeftTv.setVisibility(View.GONE);
        if (null != listener) {
            mAppbarLeftParent.setOnClickListener(listener);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // right
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 设置右侧文字
     */
    public void setRightText(CharSequence text, View.OnClickListener listener, @ColorInt int color) {
        mAppBarRightTv.setText(text);
        mAppBarRightTv.setVisibility(View.VISIBLE);
        mAppBarRightIv.setVisibility(View.GONE);
        if (null != listener) {
            mAppBarRightParent.setOnClickListener(listener);
        }
        if (color != 0) {
            mAppBarRightTv.setTextColor(color);
        }
    }

    public void removeRightText() {
        mAppBarRightTv.setVisibility(View.GONE);
        mAppBarRightParent.setClickable(false);
    }

    public void setRightText(CharSequence text, View.OnClickListener listener) {
        setRightText(text, listener, 0);
    }

    public void setReightImage(int resId, View.OnClickListener listener) {
        mAppBarRightIv.setImageResource(resId);
        mAppBarRightIv.setVisibility(View.VISIBLE);
        mAppBarRightTv.setVisibility(View.GONE);
        if (null != listener) {
            mAppBarRightParent.setOnClickListener(listener);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // appbar
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 从view中获取color，用以设置appbar的颜色
     */
    public void setAppBarColor(View view) {
        if (null != view) {
            Drawable drawable = view.getBackground();
            if (drawable instanceof GradientDrawable) {
                return;
            } else if (drawable instanceof ColorDrawable) {
                setParentSelector(((ColorDrawable) drawable).getColor());
                return;
            }
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;

            Palette palette = Palette.from(bitmapDrawable.getBitmap()).generate();
            // getVibrantSwatch();      // 有活力
            // getDarkVibrantSwatch();  // 有活力 暗色
            // getLightVibrantSwatch(); // 有活力 亮色
            // getMutedSwatch();        // 柔和
            // getDarkMutedSwatch();    // 柔和 暗色
            // getLightMutedSwatch();   // 柔和 亮色
            Palette.Swatch vibrant = palette.getLightVibrantSwatch();
            if (null != vibrant) {
                // getRgb(): the RGB value of this color.
                // getBodyTextColor(): the RGB value of a text color which can be displayed on top of this color.
                // getTitleTextColor(): the RGB value of a text color which can be displayed on top of this color.
                // getPopulation(): the amount of pixels which this swatch represents.
                // getHsl(): the HSL value of this color.
                setParentSelector(vibrant.getRgb());
            }
        }
    }

    /**
     * 设置appbar的颜色
     */
    public void setAppBarColor(@ColorInt int color) {
        setParentSelector(color);
    }

    /**
     * 为LeftImage设置Selector
     */
    private void setParentSelector(@ColorInt int color) {
        int colorBurn = Utils.colorBurn(color);
        if (color != 0) {
            mAppbarBg.setBackgroundColor(color);
            setLeftParentSelector(color, colorBurn);
        }

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.setStatusBarColor(colorBurn);
            window.setNavigationBarColor(colorBurn);
        }
    }

    /**
     * 为back键设置selector
     *
     * @param color
     *         normal
     * @param colorBurn
     *         press
     */
    public void setLeftParentSelector(@ColorInt int color, @ColorInt int colorBurn) {
        if (color != 0 && colorBurn != 0) {
            // 初始化一个空对象
            StateListDrawable listDrawable = new StateListDrawable();

            ColorDrawable drawable     = new ColorDrawable(color);
            ColorDrawable drawableBurn = new ColorDrawable(colorBurn);

            // 获取对应的属性值 Android框架自带的属性 attr
            int pressed        = android.R.attr.state_pressed;
            int window_focused = android.R.attr.state_window_focused;
            int focused        = android.R.attr.state_focused;
            int selected       = android.R.attr.state_selected;

            listDrawable.addState(new int[]{pressed, window_focused}, drawableBurn);
            listDrawable.addState(new int[]{pressed, -focused}, drawableBurn);
            listDrawable.addState(new int[]{selected}, drawableBurn);
            listDrawable.addState(new int[]{focused}, drawableBurn);
            // 没有任何状态时显示的图片，我们给它设置为空集合
            listDrawable.addState(new int[]{}, drawable);
            mAppbarLeftParent.setBackgroundDrawable(listDrawable);
        }
    }
}