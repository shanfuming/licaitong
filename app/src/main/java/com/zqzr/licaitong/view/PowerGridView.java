package com.zqzr.licaitong.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class PowerGridView extends GridView {

	public PowerGridView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public PowerGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PowerGridView(Context context) {
		super(context);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
	
}
