package com.clearblade.cloud.iot.v1.getdeviceregistry;

import java.util.logging.Logger;

import com.clearblade.cloud.iot.v1.DeviceManagerClient;
import com.clearblade.cloud.iot.v1.utils.DeviceRegistry;
import com.clearblade.cloud.iot.v1.utils.RegistryName;

public class AsyncGetDeviceRegistry {
	static Logger log = Logger.getLogger(AsyncGetDeviceRegistry.class.getName());

	public static void main(String[] args) {
		asyncGetDeviceRegistry();
	}

	public static void asyncGetDeviceRegistry() {
		DeviceManagerClient deviceManagerClient = new DeviceManagerClient();
		GetDeviceRegistryRequest request = GetDeviceRegistryRequest.Builder.newBuilder()
				.setName(RegistryName.of("ingressdevelopmentenv", "us-central1", "Rashmi_Registry_Test").getRegistryFullName())
		        .build();
		DeviceRegistry response = deviceManagerClient.getDeviceRegistry(request);
		System.out.println(response.toString());
	}

}
