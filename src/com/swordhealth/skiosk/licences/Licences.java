package com.swordhealth.skiosk.licences;

import android.app.enterprise.license.EnterpriseLicenseManager;
import android.content.Context;
import android.content.SharedPreferences;
import com.sec.enterprise.knox.license.KnoxEnterpriseLicenseManager;
import com.swordhealth.skiosk.R;
import com.swordhealth.skiosk.constants.SAConstants;

/**
 * KNOX Licences activation
 * @author ivogabriel
 *
 */

public class Licences implements iLicences {
	
	private KnoxEnterpriseLicenseManager klmsMgr = null;
	private EnterpriseLicenseManager elmsMgr = null;
	private Context context;
	private SharedPreferences preferences;
	private final int KLM = 0;
	private final int ELM = 1;
	private boolean KLMActivated = false;
	private boolean ELMActivated = false;
	
	
	public Licences(Context context){
		this.context = context; 
	}
	
	/**
	 * Start licences activation
	 */
	public void activate (){
		preferences = context.getSharedPreferences(SAConstants.MY_PREFS_NAME, Context.MODE_PRIVATE);
		getLicencesState();
		LicenseReceiver.iLicences = this;
		activateKLM();

	
	}
	
	/**
	 * Activate KLM licence
	 */
	private void activateKLM(){
		
		klmsMgr = KnoxEnterpriseLicenseManager.getInstance(context);
		String key = context.getResources().getString(R.string.KLM_key);
		
		if(klmsMgr != null && !KLMActivated)
			klmsMgr.activateLicense(key);
		else
			KLMActivated(true);
		
	}
	
	/**
	 * Activate ELM licence
	 */
	private void activateELM(){
		
		elmsMgr = EnterpriseLicenseManager.getInstance(context);
		String key = context.getResources().getString(R.string.ELM_key);
		
		if(elmsMgr != null && !ELMActivated)
			elmsMgr.activateLicense(key);
		else
			ELMActivated(true);
		
	}

	@Override
	public void KLMActivated(boolean activated) {
		saveState(KLM, activated);
		
		if(activated)
			activateELM();
		
	}

	@Override
	public void ELMActivated(boolean activated) {
		saveState(ELM, activated);
		
	}
	
	/**
	 * Save state of activation
	 * @param type
	 * @param activated
	 */
	private void saveState( int type, boolean activated){
		
		switch (type) {
		case KLM:
			if(activated)
				preferences.edit().putString(SAConstants.KLM, SAConstants.ACTIVATED);
			else
				preferences.edit().putString(SAConstants.KLM, SAConstants.NOT_ACTIVATED);
			
			
			break;

		case ELM:
			
			if(activated)
				preferences.edit().putString(SAConstants.ELM, SAConstants.ACTIVATED);
			else
				preferences.edit().putString(SAConstants.ELM, SAConstants.NOT_ACTIVATED);
			break;
		}
		
		preferences.edit().commit();
		
	}
	
	/**
	 * Get state of licences activation
	 */
	private void getLicencesState(){
		String klmActive = preferences.getString(SAConstants.KLM, SAConstants.NOT_ACTIVATED);
		String elmActive = preferences.getString(SAConstants.ELM, SAConstants.NOT_ACTIVATED);
		
		if(klmActive.equals(SAConstants.ACTIVATED))
			KLMActivated = true;
		
		if (elmActive.equals(SAConstants.ACTIVATED))
			ELMActivated = true;
		
	}

}
