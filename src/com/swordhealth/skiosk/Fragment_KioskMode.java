package com.swordhealth.skiosk;

import java.util.List;

import com.swordhealth.skiosk.constants.SAConstants;
import com.swordhealth.skiosk.information.Clock;
import com.swordhealth.skiosk.information.iClock;
import com.swordhealth.skiosk.licences.Administrator;
import com.swordhealth.skiosk.licences.Licences;
import com.swordhealth.skiosk.licences.iAdministrator;
import com.swordhelth.skiosk.settings.Battery;
import com.swordhelth.skiosk.settings.Bluettooth;
import com.swordhelth.skiosk.settings.Brightness;
import com.swordhelth.skiosk.settings.SettingsState;
import com.swordhelth.skiosk.settings.Volume;
import com.swordhelth.skiosk.settings.Wifi;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.enterprise.kioskmode.KioskMode;
import android.app.enterprise.kioskmode.KioskSetting;
import android.app.enterprise.knoxcustom.KnoxCustomManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.MessageQueue.IdleHandler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Fragment_KioskMode extends Fragment implements iClock,
		iAdministrator {

	private View rootView;
	private Fragment fragment;
	private FragmentManager fragmentManager;
	private TextView txtClock;
	private TextView txtDate;
	private TextView btStart;
	private Clock clock;
	private ImageView btSettings;
	static final int ACTIVATION_REQUEST = 1;
	
	public Fragment_KioskMode(){
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_kiosk_mode, container,
				false);
		
		fragmentManager = getFragmentManager();

		// Set clock time
		txtClock = (TextView) rootView.findViewById(R.id.clock);
		txtDate = (TextView) rootView.findViewById(R.id.date);
		btStart = (TextView) rootView.findViewById(R.id.start);
		
		btSettings = (ImageView) rootView.findViewById(R.id.settings);
		btSettings.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				fragment = new Fragment_Configuration();
				fragmentManager.beginTransaction()
						.add(R.id.content_modal, fragment, SAConstants.FRAG_CONFIGURATION).commit();
				
			}
		});
		
		Administrator admin = new Administrator(getActivity(), this);
		admin.enable();

		Licences licences = new Licences(getActivity());
		licences.activate();
		
		SKioskMode kioskMode = new SKioskMode(getActivity());
		kioskMode.enable();
		
		
//		KnoxCustomManager KCM = KnoxCustomManager.getInstance();
//
//		KioskSetting kSettings = new KioskSetting();
//		KioskMode kioskModeService = KioskMode.getInstance(getActivity());
//
//		// kSettings.SettingsChanges = false;
//		// kSettings.StatusBarExpansion = false;
//		// kSettings.HomeKey = false;
//		// kSettings.AirCommand = false;
//		// kSettings.AirView = false;
//		// kSettings.MultiWindow = false;
//		// kSettings.SmartClip = false;
//		// kSettings.TaskManager = false;
//		// kSettings.NavigationBar = true;
//		// kSettings.StatusBar = true;
//		// kSettings.SystemBar = true;
//		// kSettings.WipeRecentTasks = true;
//		// kSettings.ClearAllNotifications = true;
//		//
//		// kioskModeService.enableKioskMode(kSettings);
//
//		List<Integer> keyList = kioskModeService.getHardwareKeyList();
//
//		// kioskModeService.allowHardwareKeys(keyList, false);
//		// KCM.setToastEnabledState(false);
//
//		kioskModeService.allowHardwareKeys(keyList, true);
//		kioskModeService.disableKioskMode();


		SettingsState settingsState = new SettingsState(getActivity(), rootView);
		Volume volume = new Volume(getActivity(), settingsState.getISettings());
		Battery battery = new Battery(getActivity(),
				settingsState.getISettings());
		Wifi wifi = new Wifi(getActivity(), settingsState.getISettings());
		Bluettooth bluetooth = new Bluettooth(getActivity(),
				settingsState.getISettings());
		Brightness brightness = new Brightness(getActivity(), settingsState.getISettings());
		
		// Register system configs listener
		brightness.register();
		bluetooth.register();
		wifi.register();
		volume.register();
		battery.register();

		// Start time update
		clock = new Clock(getActivity(), this);

		btStart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage("pt.mobilesword");
			    intent.addCategory(Intent.CATEGORY_LAUNCHER);
			    startActivity(intent);
			}
		});

		

		return rootView;
	}

	// Update time
	@Override
	public void update(String time, String date) {
		txtClock.setText(time);
		txtDate.setText(date);

	}

	@Override
	public boolean setState(boolean enable) {
		// TODO Auto-generated method stub
		return false;
	}

}
