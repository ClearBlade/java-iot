package com.clearblade.cloud.iot.v1.samples.updatedeviceregistry;

import com.clearblade.cloud.iot.v1.DeviceManagerClient;
import com.clearblade.cloud.iot.v1.registrytypes.DeviceRegistry;
import com.clearblade.cloud.iot.v1.registrytypes.RegistryName;
import com.clearblade.cloud.iot.v1.updatedeviceregistry.UpdateDeviceRegistryRequest;
import com.clearblade.cloud.iot.v1.utils.LogLevel;

public class SyncUpdateDeviceRegistry {

	public static String  PROJECT = "";
	public static String  LOCATION = "";
	public static String  REGISTRY = "";
	public static String  LOGLEVEL = "";

	public static void main(String[] args) throws Exception{		
		PROJECT = System.getProperty("projectName");
		LOCATION = System.getProperty("location");
		REGISTRY = System.getProperty("registryName");
		LOGLEVEL = System.getProperty("logLevel");
		syncUpdateDeviceRegistry();
	}

	public static void syncUpdateDeviceRegistry() throws Exception {
		DeviceManagerClient deviceManagerClient = new DeviceManagerClient();
		RegistryName name = RegistryName.of(PROJECT, LOCATION, REGISTRY);

		UpdateDeviceRegistryRequest request = UpdateDeviceRegistryRequest.Builder.newBuilder()
				.setDeviceRegistry(DeviceRegistry.newBuilder().setId(REGISTRY)
						.setName(name.getRegistryFullName())
						.setLogLevel(LogLevel.valueOf(LOGLEVEL))
						.build())
				.setName(name.getRegistryFullName()).setUpdateMask("logLevel").build();

		DeviceRegistry response = deviceManagerClient.updateDeviceRegistry(request);
		if(response != null) {
			System.out.println("UpdateDeviceRegistry execution successful");
		}else {
			System.out.println("UpdateDeviceRegistry execution failed");
		}
	}
}