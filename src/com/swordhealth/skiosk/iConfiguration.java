package com.swordhealth.skiosk;

public interface iConfiguration {
	
	void setVolume(int level);
	
	void setWifi(int state, String name , int signalPower);
	
	void setBluetooth(int state);
	
	void setBrightness(int level);
	

}
