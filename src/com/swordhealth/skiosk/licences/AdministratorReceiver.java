package com.swordhealth.skiosk.licences;

import android.app.Activity;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.app.enterprise.EnterpriseDeviceManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


/**
 * Admin ativation receiver
 * @author ivogabriel
 *
 */
public class AdministratorReceiver extends DeviceAdminReceiver {
	
	public static iAdministrator iAdministratior_;
	
	@Override
    public void onEnabled(Context context, Intent intent) {
        super.onEnabled(context, intent);
        //iAdministratior_.setState(true);
        Log.i("Device Admin: ", "Enabled");
    }

    @Override
    public String onDisableRequested(Context context, Intent intent) {
        return "Admin disable requested";
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        super.onDisabled(context, intent);
        Log.i("Device Admin: ", "Disabled");
    }

    @Override
    public void onPasswordChanged(Context context, Intent intent) {
        super.onPasswordChanged(context, intent);
        Log.i("Device Admin: ", "Password changed");
    }
	

}
