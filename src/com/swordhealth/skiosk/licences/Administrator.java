package com.swordhealth.skiosk.licences;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class Administrator {

	private Activity activity_;
	private DevicePolicyManager mDPM;
	private iAdministrator iAdministrator_;
	private final int ACTIVATION_REQUEST = 47;
	private ComponentName deviceAdminComponentName;

	/**
	 *  Admin mode
	 * @param activity
	 * @param iAdministrator
	 */
	public Administrator(Activity activity, iAdministrator iAdministrator) {
		this.activity_ = activity;
		this.iAdministrator_ = iAdministrator;
	}

	/**
	 * Enable admin permissions
	 */
	public void enable() {

		mDPM = (DevicePolicyManager) activity_
				.getSystemService(Context.DEVICE_POLICY_SERVICE);
		// Set interface to response
		AdministratorReceiver.iAdministratior_ = iAdministrator_;
		// Get Component name
		deviceAdminComponentName = new ComponentName(activity_,
				AdministratorReceiver.class);
		// Verify if admin is enable
		if (!mDPM.isAdminActive(deviceAdminComponentName)) {
			// Call request to activate
			Intent intent = new Intent(
					DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
			intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
					deviceAdminComponentName);
			intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
					"EXPLANATION");
			activity_.startActivityForResult(intent, ACTIVATION_REQUEST);

		} else {
			iAdministrator_.setState(true);
		}

	}
	
	/**
	 * Didable admin permissions
	 */
	public void disable(){
		if(mDPM.isAdminActive(deviceAdminComponentName)){
			mDPM.removeActiveAdmin(deviceAdminComponentName);
		}
		
	}
	
	/**
	 * Admin is Enable?
	 * @return true or false
	 */
	public boolean isEnabled(){
		
		if(mDPM.isAdminActive(deviceAdminComponentName))
			return true;
		else
			return false;
		
		
	}

}
