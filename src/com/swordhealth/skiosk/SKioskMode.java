package com.swordhealth.skiosk;

import java.util.ArrayList;
import java.util.List;

import com.swordhealth.skiosk.constants.SAConstants;

import android.app.admin.DevicePolicyManager;
import android.app.enterprise.kioskmode.KioskMode;
import android.app.enterprise.kioskmode.KioskSetting;
import android.app.enterprise.knoxcustom.KnoxCustomManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.KeyEvent;

public class SKioskMode {

	private Context context;
	private SharedPreferences preferences;
	private KnoxCustomManager KCM = KnoxCustomManager.getInstance();
	private KioskSetting kSettings;
	private KioskMode kioskModeService;

	public SKioskMode(Context context) {
		this.context = context;
		this.preferences = context.getSharedPreferences(
				SAConstants.MY_PREFS_NAME, Context.MODE_PRIVATE);
		this.kSettings = new KioskSetting();
		this.kioskModeService = KioskMode.getInstance(context);
	}

	public void enable() {
		
		// Set Key to volume control
		KCM.setVolumeControlStream(3);
		KCM.setVolumePanelEnabledState(false);

		// Set state of each component
		kSettings.SettingsChanges = false;
		kSettings.StatusBarExpansion = false;
		kSettings.HomeKey = false;
		kSettings.AirCommand = false;
		kSettings.AirView = false;
		kSettings.MultiWindow = false;
		kSettings.SmartClip = false;
		kSettings.TaskManager = false;
		kSettings.NavigationBar = true;
		kSettings.StatusBar = true;
		kSettings.SystemBar = true;
		kSettings.WipeRecentTasks = true;
		kSettings.ClearAllNotifications = true;

		// Enable kiosk mode
		kioskModeService.enableKioskMode(kSettings);

		// Get Hardware key list
		List<Integer> keyList = kioskModeService.getHardwareKeyList();

		// Disable all hardware buttons
		kioskModeService.allowHardwareKeys(keyList, false);
		
		List<Integer> allowKeyList = new ArrayList<Integer>();
		allowKeyList.add(KeyEvent.KEYCODE_POWER);
		
		kioskModeService.allowHardwareKeys(allowKeyList, true);


		// Disable toast
		KCM.setToastEnabledState(false);

		// Update state
		preferences.edit().putBoolean(SAConstants.KIOSK_MODE, true);

	}

	public void disable() {

		// Set state of each component
		kSettings.SettingsChanges = true;
		kSettings.StatusBarExpansion = true;
		kSettings.HomeKey = true;
		kSettings.AirCommand = true;
		kSettings.AirView = true;
		kSettings.MultiWindow = true;
		kSettings.SmartClip = true;
		kSettings.TaskManager = true;
		kSettings.NavigationBar = true;
		kSettings.StatusBar = true;
		kSettings.SystemBar = true;
		kSettings.WipeRecentTasks = true;
		kSettings.ClearAllNotifications = true;

		// Enable kiosk mode
		kioskModeService.enableKioskMode(kSettings);

		// Get Hardware key list
		List<Integer> keyList = kioskModeService.getHardwareKeyList();

		kioskModeService.allowHardwareKeys(keyList, true);

		kioskModeService.disableKioskMode();

		// Disable toast
		KCM.setToastEnabledState(true);

		preferences.edit().putBoolean(SAConstants.KIOSK_MODE, false);

	}

}
