package com.clearblade.cloud.iot.v1.devicestateslist;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.clearblade.cloud.iot.v1.DeviceManagerClient;
import com.clearblade.cloud.iot.v1.utils.DeviceName;
import com.clearblade.cloud.iot.v1.utils.DeviceState;

public class SyncDeviceStatesList {
	static Logger log = Logger.getLogger(SyncDeviceStatesList.class.getName());

	public static void main(String[] args) {
		syncDeviceStatesList();
	}

	public static void syncDeviceStatesList() {
		DeviceManagerClient deviceManagerClient = new DeviceManagerClient();

		ListDeviceStatesRequest request = ListDeviceStatesRequest.Builder.newBuilder().setName(DeviceName
				.of("ingressdevelopmentenv", "us-central1", "Rashmi_Registry_Test", "Rashmi_Device_Test")
				.toString()).setNumStates(1643330779).build();
		ListDeviceStatesResponse response = deviceManagerClient.listDeviceStates(request);

		for (DeviceState element : response.getDeviceStatesList()) {
			System.out.println(element.toString());
		}
	}

}
