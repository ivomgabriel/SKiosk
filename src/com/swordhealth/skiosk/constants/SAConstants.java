package com.swordhealth.skiosk.constants;

public interface SAConstants {

	String ENABLE_ADMIN = "Enable Admin";
	String DISABLE_ADMIN = "Disable Admin";
	String MY_PREFS_NAME = "Licences";
	String ADMIN = "admin";
	String KLM = "klm";
	String ELM = "elm";
	String ACTIVATED = "activated";
	String NOT_ACTIVATED = "not_activated";
	String KIOSK_MODE = "kiosk_mode";

	int WIFI = 1;
	int BLUETOOTH = 2;
	int BRIGHTNESS = 3;
	int VOLUME = 4;
	int BATTERY = 5;
	
	String VOLUME_LEVEL = "volume_level";
	String BATTERY_LEVEL = "battery_level";
	String BATTERY_CHARGING = "battery_charging";
	String BATTERY_UNCHARGING = "battery_uncharging";
	String BRIGHTNESS_LEVEL = "brightness_level";
	String BLUETOOTH_STATE = "bluetooth_state";
	
	
	int WIFI_CONNECTED = 0;
	int WIFI_CONNECTING = 1;
	int WIFI_NOT_CONNECTED = 2;
	
	String NAME = "name";
	String POWER = "power";
	String STATE ="state";
	
	int BLE_OFF = 0;
	int BLE_ON = 1;
	
	String FRAG_KIOSK_MODE = "frag_kiosk_mode";
	String FRAG_CONFIGURATION = "frag_configuration";
	String FRAG_SWORD_CONFIGURATION ="frag_sword_configuration";
	String FRAG_SYSTEM_CONFIGURATION = "frag_system_configuration";

	String ADMIN_ALREADY_ENABLED = "Admin already enabled";
	String ADMIN_ALREADY_DISABLED = "Admin already disabled";
	String DEVICE_ADMIN_ENABLED = "Device Admin enabled";
	String DEVICE_ADMIN_DISABLED = "Device Admin disabled";
	String DISABLE_ADMIN_WARNING = "Do you want to disable the administrator?";
	String CANCELLED_ENABLING_DEVICE_ADMIN = "Cancelled Enabling Device Administrator";
	String CANCELLED_DISABLING_DEVICE_ADMIN = "Cancelled Disabling Device Administrator";

	String PWD_RESET_TOKEN = "SamSung1!";
	String KLM_ACTIVATION_SUCCESS = "KLM activation successful";
	String KLM_ACTIVATION_FAILURE = "KLM activation failed with errorcode: ";
	String ELM_ACTIVATION_SUCCESS = "ELM activation successful";
	String ELM_ACTIVATION_FAILURE = "ELM activation failed with errorcode: ";
	String LICENSE_KEY = "License Key";
	String ENTER_KLM_KEY = "Please enter the KLM key";
	String ENTER_ELM_KEY = "Please enter the ELM key";
	String OK = "OK";
	String CANCEL = "Cancel";
	String LICENSE_RESULT = "License result";
	String PACKAGE_NAME = "com.samsung.knox.samples.container.rcp";
	String KNOX_B2B = "knox-b2b";
	String CONTAINER_CREATION = "Container Creation";
	String CONTAINER_CREATION_FAILED = "Container creation failed with errorcode: ";
	String CONTAINER_CREATION_PROGRESS = "Container creation in progress with id: ";
	String FAKE_INTENT = "Intent belongs to another MDM or it is a fake intent";
	String CONTAINER_CREATED_SUCCESSFULLY = "Container created successfully";
	String NOT_SUPPORTED = "This API is not supported on this device";
	String MODEL_TAG = "Model";

	String CONTACTS = "CONTACTS";
	String CALENDAR = "CALENDAR";
	String BOOKMARKS = "BOOKMARKS";
	String CLIPBOARD = "CLIPBOARD";
	String CALL_LOG = "CALL_LOG";
	String SMS = "SMS";
	String NOTIFICATIONS = "NOTIFICATIONS";
	String SHORTCUTS = "SHORTCUTS";

	int NONE = -1;
	int INITIAL_STATE = 0;
	int RESULT_ENABLE_ADMIN = 1;
	int RESULT_DISABLE_ADMIN = 2;
	int RESULT_KLM_ACTIVATED = 3;
	int RESULT_ELM_ACTIVATED = 4;
	int DEFAULT_ERROR = -888;



}