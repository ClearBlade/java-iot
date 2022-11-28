package com.clearblade.cloud.iot.v1.samples.getdeviceregistry;

import java.util.logging.Logger;

import com.clearblade.cloud.iot.v1.DeviceManagerClient;
import com.clearblade.cloud.iot.v1.getdeviceregistry.GetDeviceRegistryRequest;
import com.clearblade.cloud.iot.v1.registrytypes.DeviceRegistry;
import com.clearblade.cloud.iot.v1.registrytypes.RegistryName;

public class AsyncGetDeviceRegistry {
	static Logger log = Logger.getLogger(AsyncGetDeviceRegistry.class.getName());
	public static String PROJECT = "";
	public static String  LOCATION = "";
	public static String  REGISTRY = "";
	
	public static void main(String[] args) {		
		PROJECT = args[0];
		LOCATION = args[1];
		REGISTRY = args[2];
		asyncGetDeviceRegistry();
	}

	public static void asyncGetDeviceRegistry() {
		DeviceManagerClient deviceManagerClient = new DeviceManagerClient();
		GetDeviceRegistryRequest request = GetDeviceRegistryRequest.Builder.newBuilder()
				.setName(RegistryName.of(PROJECT, LOCATION, REGISTRY).getRegistryFullName())
				.build();
		DeviceRegistry response = deviceManagerClient.getDeviceRegistry(request);
		System.out.println(response.toString());
	}

}
