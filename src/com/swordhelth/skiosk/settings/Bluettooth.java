package com.swordhelth.skiosk.settings;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;

import com.swordhealth.skiosk.SystemConfiguration;
import com.swordhealth.skiosk.constants.SAConstants;

public class Bluettooth {
	
	private iSettings iSettings;
	private Context context;
	private int state;

	public Bluettooth(Context context,iSettings iSettings) {
		this.context = context;
		this.iSettings = iSettings;
	}

	public void register() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
		
		context.registerReceiver(BluettoothReceiver, filter);
		
		int state = BluetoothAdapter.getDefaultAdapter().getState();
		
		
		
		updateState(state);

	}

	public void unregister() {
		context.unregisterReceiver(BluettoothReceiver);
	}

	private BroadcastReceiver BluettoothReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			
			int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
			
			updateState(state);
			
		}

	};
	
	private void updateState(int state){
		
		if (state == BluetoothAdapter.STATE_OFF){
			state = SAConstants.BLE_OFF;
			Log.d("SETTINGS", "BLE OFF");
		}else{
			state = SAConstants.BLE_ON;
			Log.d("SETTINGS", "BLE ON");
		}
		
		SystemConfiguration.bluetoothState = state;
		
		
		Bundle data = new Bundle();
		data.putInt(SAConstants.BLUETOOTH_STATE,state);
		
		if(iSettings != null){
			iSettings.updateState(SAConstants.BLUETOOTH, data);
		}
		
	}

}


