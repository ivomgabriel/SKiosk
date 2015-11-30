package com.swordhealth.skiosk.licences;

import android.app.enterprise.license.EnterpriseLicenseManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.sec.enterprise.knox.license.KnoxEnterpriseLicenseManager;
import com.swordhealth.skiosk.constants.SAConstants;
import com.swordhealth.skiosk.utils.SAUIHelper;

/**
 * KNOX licences ativation receiver
 * @author ivogabriel
 *
 */
public class LicenseReceiver extends BroadcastReceiver {

	private Context mContext;
	public static iLicences iLicences;
	@Override
	public void onReceive(Context context, Intent intent) {

		mContext = context;

		if (intent != null) {
			String action = intent.getAction();
			if (action == null || iLicences == null) {
				return;
			} else if (action.equals(EnterpriseLicenseManager.ACTION_LICENSE_STATUS)) {
				int errorCode = intent.getIntExtra(EnterpriseLicenseManager.EXTRA_LICENSE_ERROR_CODE,
						SAConstants.DEFAULT_ERROR);

				
				if (errorCode == EnterpriseLicenseManager.ERROR_NONE) {
					SAUIHelper.showToast(mContext, SAConstants.ELM_ACTIVATION_SUCCESS);
					iLicences.ELMActivated(true);
				} else {
					iLicences.ELMActivated(false);
					SAUIHelper.showToast(mContext, SAConstants.ELM_ACTIVATION_FAILURE + errorCode);
					}

			} else if (action.equals(KnoxEnterpriseLicenseManager.ACTION_LICENSE_STATUS)) {
				int errorCode = intent.getIntExtra(KnoxEnterpriseLicenseManager.EXTRA_LICENSE_ERROR_CODE,
						SAConstants.DEFAULT_ERROR);

				if (errorCode == KnoxEnterpriseLicenseManager.ERROR_NONE) {
					SAUIHelper.showToast(mContext, SAConstants.KLM_ACTIVATION_SUCCESS);
					iLicences.KLMActivated(true);
				} else {
					iLicences.KLMActivated(false);
					SAUIHelper.showToast(mContext, SAConstants.KLM_ACTIVATION_FAILURE + errorCode);
				}
			}
		}
	}

}
