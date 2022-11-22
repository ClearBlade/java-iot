package com.clearblade.cloud.iot.v1.updatedeviceregistry;

import com.clearblade.cloud.iot.v1.DeviceManagerClient;
import com.clearblade.cloud.iot.v1.utils.DeviceRegistry;
import com.clearblade.cloud.iot.v1.utils.LogLevel;
import com.clearblade.cloud.iot.v1.utils.RegistryName;

public class SyncUpdateDeviceRegistry {
	
	public static void main(String[] args) throws Exception {
		syncUpdateDeviceRegistry();
	}

	public static void syncUpdateDeviceRegistry() throws Exception {
		DeviceManagerClient deviceManagerClient = new DeviceManagerClient();
		RegistryName name = RegistryName.of("ingressdevelopmentenv", "us-central1", "Rashmi_Registry_Test");
		
		
		UpdateDeviceRegistryRequest request = UpdateDeviceRegistryRequest.Builder.newBuilder()
				.setDeviceRegistry(DeviceRegistry.newBuilder().setId("Rashmi_Registry_Test")
												 .setName(name.getRegistryFullName())
 						                         .setLogLevel(LogLevel.DEBUG)
 						                         .build())
				.setName(name.getRegistryFullName()).setUpdateMask("logLevel").build();
		
		DeviceRegistry response = deviceManagerClient.updateDeviceRegistry(request);
		if(response != null) {
			System.out.println(response.toBuilder().getName() + "::" + response.toBuilder().getLogLevel());
		}
	}
}
