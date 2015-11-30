package com.swordhealth.skiosk.information;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.swordhealth.skiosk.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.format.Time;

/**
 * Time and Date
 * @author ivogabriel
 *
 */
public class Clock{
	
	private BroadcastReceiver brodBroadcastReceiver_;
	private iClock iClock_;
	private Context context;
	private Time clock;
	
	public Clock(Context context, iClock iClock){
		this.context = context;
		this.iClock_ = iClock;
		clock = new Time();
		updateTime();
		
	}
	
	/**
	 *  Start update clock each minute
	 */
	public void start(){
		brodBroadcastReceiver_ = new BroadcastReceiver() {
			
			@Override
			public void onReceive(Context context, Intent intent) {
				updateTime();
				
			}
		};
		
		context.registerReceiver(brodBroadcastReceiver_, new IntentFilter(Intent.ACTION_TIME_TICK));
	}
	
	/**
	 *  Stop clock updating
	 */
	public void stop(){
		if (brodBroadcastReceiver_ != null)
	        context.unregisterReceiver(brodBroadcastReceiver_);
	}
	
	/**
	 * Update time and date each minute
	 */
	private void updateTime(){
		// Get time in moment
		clock.setToNow();
		// Format clock
		String strClock = clock.format("%k:%M");
		// Get Calendar
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		// Get month number
		int month = calendar.get(Calendar.MONTH);
		// Get month name
		String strMonth = context.getResources().getStringArray(R.array.month_names)[month];
		// Get day of week
		String strDayOfWeek = context.getResources().getStringArray(R.array.days_names)[getDayOfWeek(calendar)];
		// Get date 
		String strDate =  strDayOfWeek + ", " +calendar.get(Calendar.DAY_OF_MONTH) + " " + strMonth+ " " + calendar.get(Calendar.YEAR);
		// Udpate clock
		iClock_.update(strClock, strDate);
	}
	
	/**
	 * Get day of week ( Monday, Thus...)
	 * @param calendar
	 * @return Day of week
	 */
	private int getDayOfWeek(Calendar calendar){
		int day_of_week = calendar.get(Calendar.DAY_OF_WEEK);
		
		if((day_of_week - 1) == -1)
			return 6;
		else
			return (day_of_week - 1);
	}

}
