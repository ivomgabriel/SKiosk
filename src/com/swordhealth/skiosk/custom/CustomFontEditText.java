package com.swordhealth.skiosk.custom;


import com.swordhealth.skiosk.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

public class CustomFontEditText extends EditText {

	private static final String TAG = "CustomFont";

	public CustomFontEditText(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public CustomFontEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		setCustomFont(context, attrs);
	}

	public CustomFontEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setCustomFont(context, attrs);
	}

	private void setCustomFont(Context ctx, AttributeSet attrs) {
		TypedArray a = ctx.obtainStyledAttributes(attrs,
				R.styleable.CustomFontTxtView);
		String customFont = a
				.getString(R.styleable.CustomFontTxtView_customFont);
		setCustomFont(ctx, customFont);
		a.recycle();
	}

	public boolean setCustomFont(Context ctx, String asset) {
		Typeface tf = null;
		try {
			tf = Typeface.createFromAsset(ctx.getAssets(), asset);
		} catch (Exception e) {
			Log.e(TAG, "Error to get typeface: " + e.getMessage());
			return false;
		}
		setTypeface(tf);
		return true;
	}

}
