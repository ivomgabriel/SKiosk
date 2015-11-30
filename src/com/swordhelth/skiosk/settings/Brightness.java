package com.swordhelth.skiosk.settings;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.provider.Settings.SettingNotFoundException;

import com.swordhealth.skiosk.SystemConfiguration;
import com.swordhealth.skiosk.constants.SAConstants;

public class Brightness {

	private static iSettings iSettings;
	private static Activity context;
	private static int curBrightnessValue = 255;

	public Brightness(Activity context, iSettings iSettings) {
		Brightness.context = context;
		Brightness.iSettings = iSettings;
	}

	public void register() {
		
		try {
			curBrightnessValue = android.provider.Settings.System.getInt(
					context.getContentResolver(),
					android.provider.Settings.System.SCREEN_BRIGHTNESS);
		} catch (SettingNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		updateState(curBrightnessValue);
	}

	public static void updateState(int level) {
		
		SystemConfiguration.brightnessLevel = level;

		Bundle data = new Bundle();
		data.putInt(SAConstants.BRIGHTNESS_LEVEL, level);

		if (iSettings != null) {
			iSettings.updateState(SAConstants.BRIGHTNESS, data);
		}

	}

}
