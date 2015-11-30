package com.swordhelth.skiosk.settings;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.style.TtsSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.swordhealth.skiosk.R;
import com.swordhealth.skiosk.iConfiguration;
import com.swordhealth.skiosk.constants.SAConstants;

public class SettingsState implements iSettings {

	private Context context;
	private View rootView;
	private ImageView imgVolume, imgBattery, imgWifi, imgBluetooth;
	private TextView txtVolume, txtBattery, txtWifi, txtBluetooth;
	public static iConfiguration iConfiguration;
	private int volumeLevel;
	private int wifiState;
	private int bluetoothState;
	private int brightnessLevel;
	private int wifiSignalPower;
	private String wifiName;

	public SettingsState(Context context, View rootView) {
		this.context = context;
		this.rootView = rootView;

		this.imgVolume = (ImageView) this.rootView.findViewById(R.id.volume);
		this.txtVolume = (TextView) this.rootView.findViewById(R.id.volumetext);

		this.imgBattery = (ImageView) this.rootView.findViewById(R.id.battery);
		this.txtBattery = (TextView) this.rootView
				.findViewById(R.id.batterytext);

		this.txtWifi = (TextView) this.rootView.findViewById(R.id.wifitext);
		this.imgWifi = (ImageView) this.rootView.findViewById(R.id.wifi);

		this.imgBluetooth = (ImageView) this.rootView
				.findViewById(R.id.bluetooth);
		this.txtBluetooth = (TextView) this.rootView
				.findViewById(R.id.bluetoothtext);
	}

	public void update() {

	}

	@Override
	public void updateState(int type, Bundle data) {

		switch (type) {
		case SAConstants.VOLUME:
			setVolume(data);
			break;
		case SAConstants.BATTERY:
			setBattery(data);
			break;
		case SAConstants.WIFI:
			setWifi(data);
			break;
		case SAConstants.BLUETOOTH:
			setBluetooth(data);
			break;
			
		case SAConstants.BRIGHTNESS:
			setBrightness(data);
			break;
		}

	}
	
	private void setBrightness(Bundle data){
		
		int level = data.getInt(SAConstants.BRIGHTNESS_LEVEL);
		
		
		if (iConfiguration != null)
			iConfiguration.setBrightness(level);
	}

	private void setBluetooth(Bundle data) {

		 bluetoothState = data.getInt(SAConstants.BLUETOOTH_STATE);

		switch (bluetoothState) {
		case SAConstants.BLE_ON:
			imgBluetooth.setImageResource(R.drawable.ble_on);
			txtBluetooth.setText("ON");
			break;

		case SAConstants.BLE_OFF:
			imgBluetooth.setImageResource(R.drawable.ble_off);
			txtBluetooth.setText("OFF");
			break;
		}

		// Update configuration fragment
		if (iConfiguration != null)
			iConfiguration.setBluetooth(bluetoothState);

	}

	private void setWifi(Bundle data) {

		wifiState = data.getInt(SAConstants.STATE);
		wifiSignalPower = 0;
		wifiName = "";

		switch (wifiState) {
		case SAConstants.WIFI_CONNECTED:
			wifiSignalPower = data.getInt(SAConstants.POWER);
			wifiName = data.getString(SAConstants.NAME);

			switch (wifiSignalPower) {
			case 3:
				imgWifi.setImageResource(R.drawable.wifi_high);
				break;
			case 2:
				imgWifi.setImageResource(R.drawable.wifi_medium);
				break;
			case 1:
				imgWifi.setImageResource(R.drawable.wifi_low);
				break;
			case 0:
				imgWifi.setImageResource(R.drawable.wifi_very_low);
				break;
			}

			txtWifi.setText(wifiName);
			break;

		case SAConstants.WIFI_CONNECTING:
			wifiName = "CONNECTING";
			txtWifi.setText(wifiName);
			imgWifi.setImageResource(R.drawable.wifi_connecting);
			break;
		case SAConstants.WIFI_NOT_CONNECTED:
			wifiName = "DISCONNECTED";
			txtWifi.setText(wifiName);
			imgWifi.setImageResource(R.drawable.wifi_off);
			break;
		}

		// Update configuration fragment
		if (iConfiguration != null)
			iConfiguration.setWifi(wifiState, wifiName, wifiSignalPower);

	}

	/**
	 * Update battery UI
	 * 
	 * @param data
	 */
	private void setBattery(Bundle data) {
		int level = data.getInt(SAConstants.BATTERY_LEVEL);
		boolean charging = data.getBoolean(SAConstants.BATTERY_CHARGING);

		if (charging)
			imgBattery.setImageResource(R.drawable.battery_charging);
		else if (level >= 1 && level < 10)
			imgBattery.setImageResource(R.drawable.battery_low);
		else if (level >= 10 && level < 20)
			imgBattery.setImageResource(R.drawable.battery_20);
		else if (level >= 20 && level < 40)
			imgBattery.setImageResource(R.drawable.battery_40);
		else if (level >= 40 && level < 60)
			imgBattery.setImageResource(R.drawable.battery_60);
		else if (level >= 60 && level < 90)
			imgBattery.setImageResource(R.drawable.battery_80);
		else if (level >= 90 && level <= 100)
			imgBattery.setImageResource(R.drawable.battery_full);

		if (charging)
			txtBattery.setText("CHARGING");
		else
			txtBattery.setText(level + "%");

	}

	/**
	 * Update Volume UI
	 * 
	 * @param data
	 */
	private void setVolume(Bundle data) {
		volumeLevel = data.getInt(SAConstants.VOLUME_LEVEL);

		if (volumeLevel <= 15 && volumeLevel > 9)
			imgVolume.setImageResource(R.drawable.volume_full);
		else if (volumeLevel <= 9 && volumeLevel > 4)
			imgVolume.setImageResource(R.drawable.volume_medium);
		else if (volumeLevel <= 4 && volumeLevel >= 1)
			imgVolume.setImageResource(R.drawable.volume_low);
		else if (volumeLevel == 0)
			imgVolume.setImageResource(R.drawable.volume_off);

		if (volumeLevel == 0)
			txtVolume.setText("MUTE");
		else
			txtVolume.setText("" + volumeLevel);

		// Update configuration fragment
		if (iConfiguration != null)
			iConfiguration.setVolume(volumeLevel);

	}

	public iSettings getISettings() {
		return this;
	}

	public void setIConfiguration(iConfiguration iConfig) {
		iConfiguration = iConfig;
		
		if(iConfiguration != null){
			iConfiguration.setVolume(volumeLevel);
			iConfiguration.setWifi(wifiState, wifiName, wifiSignalPower);
			iConfiguration.setBluetooth(bluetoothState);
		}
	}

}
