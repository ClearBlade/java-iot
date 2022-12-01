package com.clearblade.cloud.iot.v1.samples.updatedevice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.clearblade.cloud.iot.v1.DeviceManagerClient;
import com.clearblade.cloud.iot.v1.devicetypes.Device;
import com.clearblade.cloud.iot.v1.devicetypes.DeviceCredential;
import com.clearblade.cloud.iot.v1.registrytypes.PublicKeyCredential;
import com.clearblade.cloud.iot.v1.registrytypes.PublicKeyFormat;
import com.clearblade.cloud.iot.v1.updatedevice.UpdateDeviceRequest;
import com.clearblade.cloud.iot.v1.utils.LogLevel;

public class SyncUpdateDevice {
	public static String  DEVICE = "";
	public static String  UPDATEMASK = "";
	public static String  ARG="";
	public static String[]  NEWARGS = null;
	public static String KEYFORMAT ="";
	public static String KEYVAL = "";

	public static void main(String[] args) {		
		DEVICE = System.getProperty("deviceName");
		UPDATEMASK = System.getProperty("updateMask");
		if(!(System.getProperty("arg").isBlank() || System.getProperty("arg").isEmpty()|| System.getProperty("arg")==null))
			ARG = System.getProperty("arg");
		if(System.getProperty("newArgs") != null)
			NEWARGS = System.getProperty("newArgs").split(","); 
		syncUpdateDevice();
	}

	public static void syncUpdateDevice() {
		DeviceManagerClient deviceManagerClient = new DeviceManagerClient();

		Device device = new Device();
		device.toBuilder().setId(DEVICE).setName(DEVICE).build();
		if(UPDATEMASK .equals("logLevel")) {
			device.toBuilder().setLogLevel(LogLevel.valueOf(ARG));
		}else if(UPDATEMASK .equals("blocked")) {
			device.toBuilder().setBlocked(Boolean.valueOf(ARG));
		}else if(UPDATEMASK .equals("metadata")) {
			Map<String, String> metadata = new HashMap<>();
			for(int i=0;i<NEWARGS.length;i++) {
				String key = NEWARGS[i];
				String val = NEWARGS[i+1];
				metadata.put(key,val);
			}
			device.toBuilder().setMetadata(metadata);
		}else if(UPDATEMASK.equals("credentials")) {
			List<DeviceCredential> listCredentials = new ArrayList<>();
			PublicKeyCredential publicKeyCredential = new PublicKeyCredential();
			publicKeyCredential.setFormat(PublicKeyFormat.valueOf(KEYFORMAT));
			publicKeyCredential.setKey(KEYVAL);
			DeviceCredential credential = new DeviceCredential();
			credential.setPublicKey(publicKeyCredential);
			listCredentials.add(credential);
			device.toBuilder().setCredentials(listCredentials);
		}

		UpdateDeviceRequest request = UpdateDeviceRequest.Builder.newBuilder().setName(DEVICE).setDevice(device)
				.setUpdateMask(UPDATEMASK).build();
		Device response = deviceManagerClient.updateDevice(request);
		if(response != null) {
			System.out.println("UpdateDevice execution successful");
		}else {
			System.out.println("UpdateDevice execution failed");
		}
	}
}