package com.swordhelth.skiosk.settings;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;

import com.swordhealth.skiosk.SystemConfiguration;
import com.swordhealth.skiosk.constants.SAConstants;

public class Battery {

	private iSettings iSettings;
	private Context context;
	private static boolean charging = false;
	private int level = 0;

	public Battery(Context context, iSettings iSettings) {
		this.context = context;
		this.iSettings = iSettings;
	}

	/**
	 * Registe receiver
	 */
	public void register() {

		// How are we charging?
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_BATTERY_CHANGED);
		filter.addAction(Intent.ACTION_POWER_CONNECTED);
		filter.addAction(Intent.ACTION_POWER_DISCONNECTED);

		Intent batteryStatus = context.registerReceiver(BatteryReceiver, filter);
		
		final int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
		final boolean isUsbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
		final boolean isAcCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
		
		if(isAcCharge || isUsbCharge)
			charging = true;
		else
			charging = false;
		
		level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
		
		SystemConfiguration.batteryLevel = level;
		SystemConfiguration.batteryCharging = charging;
		
		update();

	}

	public void unregister() {
		context.unregisterReceiver(BatteryReceiver);
	}

	private BroadcastReceiver BatteryReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();

			if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
				level = intent.getIntExtra("level", 0);
				SystemConfiguration.batteryLevel = level;
			} else if (action.equals(Intent.ACTION_POWER_CONNECTED)) {
				SystemConfiguration.batteryCharging = true;
				charging = true;
			} else if (action.equals(Intent.ACTION_POWER_DISCONNECTED)) {
				SystemConfiguration.batteryCharging = false;
				charging = false;
			}
			
			update();

		}

	};

	private void update() {
		if (iSettings != null) {
			Bundle data = new Bundle();
			data.putInt(SAConstants.BATTERY_LEVEL, level);
			data.putBoolean(SAConstants.BATTERY_CHARGING, charging);
			iSettings.updateState(SAConstants.BATTERY, data);
		}

	}

}
