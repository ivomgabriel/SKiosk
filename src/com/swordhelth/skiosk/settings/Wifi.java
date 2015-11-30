package com.swordhelth.skiosk.settings;

import com.swordhealth.skiosk.SystemConfiguration;
import com.swordhealth.skiosk.constants.SAConstants;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Bundle;

public class Wifi {

	private iSettings iSettings;
	private Context context;
	private int state = 3;
	private int power = 0;
	private String name = "";
	private ConnectivityManager connectivityManager;

	public Wifi(Context context, iSettings iSettings) {
		this.context = context;
		this.iSettings = iSettings;
	}

	/**
	 * Registe receiver
	 */
	public void register() {

		IntentFilter filter = new IntentFilter();
		filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
		filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
		filter.addAction("android.net.wifi.STATE_CHANGE");

		connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		context.registerReceiver(WifiReceiver, filter);

		// How are we charging?
		update();

	}

	public void unregister() {
		context.unregisterReceiver(WifiReceiver);
	}

	private BroadcastReceiver WifiReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
				update();
		}

	};

	private void update() {

		NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

		WifiManager wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);

		boolean isConnected = activeNetwork != null
				&& activeNetwork.isConnected();

		boolean isConnecting = activeNetwork != null
				&& activeNetwork.isConnectedOrConnecting();

		if (isConnecting)
			state = SAConstants.WIFI_CONNECTED;
		else if (isConnected)
			state = SAConstants.WIFI_CONNECTING;
		else
			state = SAConstants.WIFI_NOT_CONNECTED;

		switch (state) {
		case SAConstants.WIFI_CONNECTED:

			WifiInfo wifiInfo = wifiManager.getConnectionInfo();
			name = wifiInfo.getSSID();
			power = WifiManager.calculateSignalLevel(wifiInfo.getRssi(), 3);
			
			try {
				name = name.substring(1, name.length() - 1);
			} catch (Exception e) {

			}
			
			break;

		case SAConstants.WIFI_CONNECTING:
				name = "CONNECTING";
			break;

		case SAConstants.WIFI_NOT_CONNECTED:
				name = "DISCONNECTED";
			break;
		}

		

		SystemConfiguration.wifiState = state;
		SystemConfiguration.wifiSignalPower = power;
		SystemConfiguration.wifiName = name;

		Bundle data = new Bundle();
		data.putInt(SAConstants.STATE, state);
		data.putString(SAConstants.NAME, name);
		data.putInt(SAConstants.POWER, power);

		if (iSettings != null)
			iSettings.updateState(SAConstants.WIFI, data);

	}

}
