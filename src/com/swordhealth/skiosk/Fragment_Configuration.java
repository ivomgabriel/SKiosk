package com.swordhealth.skiosk;

import com.swordhealth.skiosk.constants.SAConstants;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Fragment_Configuration extends Fragment {

	private View rootView;
	private LinearLayout systemSettings, swordSettings;
	private Fragment fragment;
	private FragmentManager fragmentManager;
	private RelativeLayout background;
	private LinearLayout content;
	
	public Fragment_Configuration(){
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_settings, container,
				false);
		
		content = (LinearLayout) rootView.findViewById(R.id.content);
		content.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		background = (RelativeLayout) rootView.findViewById(R.id.background);
		background.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				fragmentManager.beginTransaction()
				.remove(getFragmentManager().findFragmentByTag(SAConstants.FRAG_CONFIGURATION)).commit();
				
			}
		});
		

		systemSettings = (LinearLayout) rootView
				.findViewById(R.id.system_settings);
		systemSettings.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		swordSettings = (LinearLayout) rootView
				.findViewById(R.id.sword_settings);
		swordSettings.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
		fragmentManager = getFragmentManager();
		fragment = new Fragment_System_Configuration();
		fragmentManager.beginTransaction()
				.add(R.id.settings_content, fragment, SAConstants.FRAG_SYSTEM_CONFIGURATION).commit();

		return rootView;
	}
	
	
	

}
