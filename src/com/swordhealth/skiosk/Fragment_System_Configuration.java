package com.swordhealth.skiosk;

import com.swordhealth.skiosk.constants.SAConstants;
import com.swordhelth.skiosk.settings.Brightness;
import com.swordhelth.skiosk.settings.SettingsState;
import com.swordhelth.skiosk.settings.iSettings;

import android.app.Fragment;
import android.app.enterprise.kioskmode.KioskMode;
import android.app.enterprise.kioskmode.KioskSetting;
import android.app.enterprise.knoxcustom.KnoxCustomManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.media.AudioManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Switch;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

public class Fragment_System_Configuration extends Fragment implements
		iConfiguration {

	private View rootView;
	private ImageView imgVolume, imgWifi, imgBluetooth, imgBrightness;
	private TextView txtVolume, txtWifi, txtBrightness;
	private EditText txtPassword, txtSSID;
	private LinearLayout btConnect;
	private SeekBar volumeBar, brightnessBar;
	private ImageView bluetoothSwitch;
	private iSettings iSettings_;
	private KnoxCustomManager KCM = KnoxCustomManager.getInstance();
	private KioskSetting kSettings;
	private KioskMode kioskModeService;
	private AudioManager mgr;

	public Fragment_System_Configuration() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_system_settings,
				container, false);

		this.kioskModeService = KioskMode.getInstance(getActivity());
		this.KCM = KnoxCustomManager.getInstance();
		this.kSettings = new KioskSetting();
		this.mgr = (AudioManager) getActivity().getSystemService(
				Context.AUDIO_SERVICE);
		SettingsState.iConfiguration = this;

		this.imgVolume = (ImageView) this.rootView.findViewById(R.id.volume);
		this.txtVolume = (TextView) this.rootView.findViewById(R.id.volumetext);

		this.txtWifi = (TextView) this.rootView.findViewById(R.id.wifitext);
		this.imgWifi = (ImageView) this.rootView.findViewById(R.id.wifi);
		this.txtSSID = (EditText) this.rootView.findViewById(R.id.ssid);
		this.txtPassword = (EditText) this.rootView.findViewById(R.id.password);
		this.btConnect = (LinearLayout) this.rootView
				.findViewById(R.id.connect);

		this.imgBluetooth = (ImageView) this.rootView
				.findViewById(R.id.bluetooth);

		bluetoothSwitch = (ImageView) this.rootView
				.findViewById(R.id.bluetoothswitch);

		switch (SystemConfiguration.bluetoothState) {

		case SAConstants.BLE_ON:
			imgBluetooth.setImageResource(R.drawable.ble_on);
			break;
		}

		bluetoothSwitch.setOnClickListener(bluetoothListener);

		brightnessBar = (SeekBar) this.rootView
				.findViewById(R.id.brightnessbar);
		brightnessBar.setOnSeekBarChangeListener(brightnessListener);
		brightnessBar.setMax(255);
		brightnessBar.setProgress(255);

		volumeBar = (SeekBar) this.rootView.findViewById(R.id.volumebar);
		volumeBar.setMax(15);
		volumeBar.setOnSeekBarChangeListener(volumeListener);
		volumeBar.setProgress(SystemConfiguration.volumeLevel);

		setWifi(SystemConfiguration.wifiState, SystemConfiguration.wifiName,
				SystemConfiguration.wifiSignalPower);

		setBluetooth(SystemConfiguration.bluetoothState);
		
		setBrightness(SystemConfiguration.brightnessLevel);

		this.btConnect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				KCM.setWifiState(true, txtSSID.getText().toString(),
						txtPassword.getText().toString());

				WifiManager wifiManager = (WifiManager) getActivity()
						.getSystemService(Context.WIFI_SERVICE);
				// setup a wifi configuration
				WifiConfiguration wc = new WifiConfiguration();
				wc.SSID = txtSSID.getText().toString();
				wc.preSharedKey = txtSSID.getText().toString();
				wc.status = WifiConfiguration.Status.ENABLED;
				wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
				wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
				wc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
				wc.allowedPairwiseCiphers
						.set(WifiConfiguration.PairwiseCipher.TKIP);
				wc.allowedPairwiseCiphers
						.set(WifiConfiguration.PairwiseCipher.CCMP);
				wc.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
				// connect to and enable the connection
				int netId = wifiManager.addNetwork(wc);
				wifiManager.setWifiEnabled(true);
				wifiManager.disconnect();
				wifiManager.enableNetwork(netId, true);
				wifiManager.reconnect();


			}
		});

		return rootView;
	}

	@Override
	public void setVolume(int volumeLevel) {

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

		volumeBar.setProgress(volumeLevel);

		volumeBar.setOnSeekBarChangeListener(volumeListener);

	}

	@Override
	public void setWifi(int state, String name, int signalPower) {

		switch (state) {
		case SAConstants.WIFI_CONNECTED:

			switch (signalPower) {
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
		}

		txtWifi.setText(name);

	}

	@Override
	public void setBluetooth(int state) {
		switch (state) {
		case SAConstants.BLE_ON:
			bluetoothSwitch.setImageResource(R.drawable.switch_on);
			imgBluetooth.setImageResource(R.drawable.ble_on);
			break;
		case SAConstants.BLE_OFF:
			bluetoothSwitch.setImageResource(R.drawable.switch_off);
			imgBluetooth.setImageResource(R.drawable.ble_off);
			break;
		}

	}

	@Override
	public void setBrightness(int level) {
		
		LayoutParams layoutpars = getActivity().getWindow().getAttributes();
		// Set the brightness of this window
		layoutpars.screenBrightness = level / (float) 255;
		// Apply attribute changes to this window
		getActivity().getWindow().setAttributes(layoutpars);

	}

	private OnClickListener bluetoothListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
			if (adapter.isEnabled()) {
				adapter.disable();
			} else {
				adapter.enable();
			}

		}
	};

	/**
	 * Brightness controller
	 */
	private OnSeekBarChangeListener brightnessListener = new OnSeekBarChangeListener() {

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {

			Settings.System.putInt(getActivity().getContentResolver(),
					android.provider.Settings.System.SCREEN_BRIGHTNESS,
					progress);
			
			Brightness.updateState(progress);

		}
	};

	/**
	 * Volume controller
	 */
	private OnSeekBarChangeListener volumeListener = new OnSeekBarChangeListener() {

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {

			mgr.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
		}
	};

}
