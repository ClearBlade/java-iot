package com.clearblade.cloud.iot.v1.samples.getdeviceregistry;

import com.clearblade.cloud.iot.v1.DeviceManagerAsyncClient;
import com.clearblade.cloud.iot.v1.getdeviceregistry.GetDeviceRegistryRequest;
import com.clearblade.cloud.iot.v1.registrytypes.DeviceRegistry;
import com.clearblade.cloud.iot.v1.registrytypes.RegistryName;

public class AsyncGetDeviceRegistry {
	public static String PROJECT = "";
	public static String  LOCATION = "";
	public static String  REGISTRY = "";
	
	public static void main(String[] args) {		
		PROJECT = System.getProperty("projectName");
		LOCATION = System.getProperty("location");
		REGISTRY = System.getProperty("registryName");
		asyncGetDeviceRegistry();
	}

	public static void asyncGetDeviceRegistry() {
		DeviceManagerAsyncClient deviceManagerAsyncClient = new DeviceManagerAsyncClient();
		GetDeviceRegistryRequest request = GetDeviceRegistryRequest.Builder.newBuilder()
				.setName(RegistryName.of(PROJECT, LOCATION, REGISTRY).getRegistryFullName())
				.build();
		DeviceRegistry response = deviceManagerAsyncClient.getDeviceRegistry(request);
		if(response != null) {
			System.out.println("GetDeviceRegistry execution successful ::"+response.toBuilder().getId());

		}else {
			System.out.println("GetDeviceRegistry execution failed");			
		}
	}

}