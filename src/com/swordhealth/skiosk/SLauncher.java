package com.swordhealth.skiosk;

import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.admin.DevicePolicyManager;
import android.app.enterprise.kioskmode.KioskMode;
import android.app.enterprise.kioskmode.KioskSetting;
import android.app.enterprise.knoxcustom.KnoxCustomManager;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.swordhealth.skiosk.constants.SAConstants;
import com.swordhealth.skiosk.information.Clock;
import com.swordhealth.skiosk.information.iClock;
import com.swordhealth.skiosk.licences.Administrator;
import com.swordhealth.skiosk.licences.AdministratorReceiver;
import com.swordhealth.skiosk.licences.Licences;
import com.swordhealth.skiosk.licences.iAdministrator;
import com.swordhelth.skiosk.settings.Battery;
import com.swordhelth.skiosk.settings.Bluettooth;
import com.swordhelth.skiosk.settings.SettingsState;
import com.swordhelth.skiosk.settings.Volume;
import com.swordhelth.skiosk.settings.Wifi;

public class SLauncher extends Activity {

	private Fragment fragment;
	private FragmentManager fragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		try {
			if (!SystemConfiguration.sound) {
				Uri uri = Uri.parse("android.resource://"
						+ this.getPackageName() + "/raw/beep1time");
				Ringtone sound = RingtoneManager.getRingtone(this, uri);
				sound.play();
				SystemConfiguration.sound = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		fragmentManager = getFragmentManager();
		fragment = new Fragment_KioskMode();
		fragmentManager.beginTransaction()
				.add(R.id.content_main, fragment, SAConstants.FRAG_KIOSK_MODE)
				.commit();

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		// clock.start();
		super.onResume();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onPause() {
		// clock.stop();
		super.onPause();
	}

}
