package com.zqzr.licaitong.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class PowerListView extends ListView {

	public PowerListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public PowerListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PowerListView(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
	
}
