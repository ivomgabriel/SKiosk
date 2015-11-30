package com.swordhelth.skiosk.settings;

import com.swordhealth.skiosk.SystemConfiguration;
import com.swordhealth.skiosk.constants.SAConstants;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;

public class Volume {

	private iSettings iSettings;
	private Context context;
	private AudioManager audioManager;

	public Volume(Context context,iSettings iSettings) {
		this.context = context;
		this.iSettings = iSettings;
	}

	public void register() {
		IntentFilter filter = new IntentFilter();
		audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		filter.addAction("android.media.VOLUME_CHANGED_ACTION");
		context.registerReceiver(VolumeReceiver, filter);
		
		updateState();

	}

	public void unregister() {
		context.unregisterReceiver(VolumeReceiver);
	}

	private BroadcastReceiver VolumeReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();

			if (action.equals("android.media.VOLUME_CHANGED_ACTION")) {
				updateState();
			}
		}

	};
	
	private void updateState(){
		
		Bundle data = new Bundle();
		int volume_level= audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		data.putInt(SAConstants.VOLUME_LEVEL, volume_level);
		
		SystemConfiguration.volumeLevel = volume_level;
		
		if(iSettings != null){
			iSettings.updateState(SAConstants.VOLUME, data);
		}
		
	}

}
