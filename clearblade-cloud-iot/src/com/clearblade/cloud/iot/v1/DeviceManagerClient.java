/*
 * Copyright 2023 ClearBlade Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright 2022 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.clearblade.cloud.iot.v1;

import java.io.IOException;
import java.util.logging.Logger;

import com.clearblade.cloud.iot.v1.binddevicetogateway.BindDeviceToGatewayRequest;
import com.clearblade.cloud.iot.v1.binddevicetogateway.BindDeviceToGatewayResponse;
import com.clearblade.cloud.iot.v1.createdevice.CreateDeviceRequest;
import com.clearblade.cloud.iot.v1.createdeviceregistry.CreateDeviceRegistryRequest;
import com.clearblade.cloud.iot.v1.deletedevice.DeleteDeviceRequest;
import com.clearblade.cloud.iot.v1.deletedeviceregistry.DeleteDeviceRegistryRequest;
import com.clearblade.cloud.iot.v1.deviceslist.DevicesListRequest;
import com.clearblade.cloud.iot.v1.deviceslist.DevicesListResponse;
import com.clearblade.cloud.iot.v1.devicestateslist.ListDeviceStatesRequest;
import com.clearblade.cloud.iot.v1.devicestateslist.ListDeviceStatesResponse;
import com.clearblade.cloud.iot.v1.devicetypes.Device;
import com.clearblade.cloud.iot.v1.devicetypes.DeviceConfig;
import com.clearblade.cloud.iot.v1.exception.ApplicationException;
import com.clearblade.cloud.iot.v1.getdevice.GetDeviceRequest;
import com.clearblade.cloud.iot.v1.getdeviceregistry.GetDeviceRegistryRequest;
import com.clearblade.cloud.iot.v1.listdeviceconfigversions.ListDeviceConfigVersionsRequest;
import com.clearblade.cloud.iot.v1.listdeviceconfigversions.ListDeviceConfigVersionsResponse;
import com.clearblade.cloud.iot.v1.listdeviceregistries.ListDeviceRegistriesRequest;
import com.clearblade.cloud.iot.v1.listdeviceregistries.ListDeviceRegistriesResponse;
import com.clearblade.cloud.iot.v1.modifycloudtodeviceconfig.ModifyCloudToDeviceConfigRequest;
import com.clearblade.cloud.iot.v1.registrytypes.DeviceRegistry;
import com.clearblade.cloud.iot.v1.sendcommandtodevice.SendCommandToDeviceRequest;
import com.clearblade.cloud.iot.v1.sendcommandtodevice.SendCommandToDeviceResponse;
import com.clearblade.cloud.iot.v1.unbinddevicefromgateway.UnbindDeviceFromGatewayRequest;
import com.clearblade.cloud.iot.v1.unbinddevicefromgateway.UnbindDeviceFromGatewayResponse;
import com.clearblade.cloud.iot.v1.updatedevice.UpdateDeviceRequest;
import com.clearblade.cloud.iot.v1.updatedeviceregistry.UpdateDeviceRegistryRequest;

public class DeviceManagerClient {

	static Logger log = Logger.getLogger(DeviceManagerClient.class.getName());

	/**
	 * Calling getDevice api
	 * 
	 * @param request
	 * @return Device object
	 * @throws IOException
	 * @throws ApplicationException
	 */

	public Device getDevice(GetDeviceRequest request) {
		ClearBladeDeviceManager cbManager = new ClearBladeDeviceManager();
		return cbManager.getDevice(request);
	}

	/**
	 * Calling createDevice api
	 * 
	 * @param request
	 * @return Device object
	 * @throws IOException
	 * @throws ApplicationException
	 */
	public Device createDevice(CreateDeviceRequest request) {
		log.info("createDevice");
		ClearBladeDeviceManager cbManager = new ClearBladeDeviceManager();
		return cbManager.createDevice(request);
	}

	/**
	 * Calling bindDeviceToGateway api
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws ApplicationException
	 */
	public BindDeviceToGatewayResponse bindDeviceToGateway(BindDeviceToGatewayRequest request) {
		ClearBladeDeviceManager cbManager = new ClearBladeDeviceManager();
		return cbManager.bindDeviceToGateway(request);
	}

	/**
	 * Calling unbindDeviceFromGateway api
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws ApplicationException
	 */
	public UnbindDeviceFromGatewayResponse unbindDeviceFromGateway(UnbindDeviceFromGatewayRequest request) {
		ClearBladeDeviceManager cbManager = new ClearBladeDeviceManager();
		return cbManager.unbindDeviceFromGateway(request);
	}

	/**
	 * Calling deleteDevice api
	 * 
	 * @param request
	 * @throws IOException
	 * @throws ApplicationException
	 */
	public void deleteDevice(DeleteDeviceRequest request) {
		ClearBladeDeviceManager cbManager = new ClearBladeDeviceManager();
		cbManager.deleteDevice(request);
	}

	/**
	 * Calling updateDevice api
	 * 
	 * @param request
	 * @return Device object
	 */
	public Device updateDevice(UpdateDeviceRequest request) {
		ClearBladeDeviceManager cbManager = new ClearBladeDeviceManager();
		return cbManager.updateDevice(request);
	}

	public SendCommandToDeviceResponse sendCommandToDevice(SendCommandToDeviceRequest request) {
		ClearBladeDeviceManager cbManager = new ClearBladeDeviceManager();
		return cbManager.sendCommandToDevice(request);

	}

	public DevicesListResponse listDevices(DevicesListRequest request) {
		log.info("listDevices");
		ClearBladeDeviceManager cbManager = new ClearBladeDeviceManager();
		return cbManager.listDevices(request);
	}

	public DeviceConfig modifyCloudToDeviceConfig(ModifyCloudToDeviceConfigRequest request) {
		ClearBladeDeviceManager cbManager = new ClearBladeDeviceManager();
		return cbManager.modifyCloudToDeviceConfig(request);
	}

	public ListDeviceStatesResponse listDeviceStates(ListDeviceStatesRequest request) {
		ClearBladeDeviceManager cbManager = new ClearBladeDeviceManager();
		return cbManager.listDeviceStates(request);

	}

	public ListDeviceConfigVersionsResponse listDeviceConfigVersions(ListDeviceConfigVersionsRequest request) {
		ClearBladeDeviceManager cbManager = new ClearBladeDeviceManager();
		return cbManager.listDeviceConfigVersions(request);

	}

	// Registry Apis
	/**
	 * Calling getDeviceRegistry api
	 * 
	 * @param request
	 * @return DeviceRegistry object
	 * @throws IOException
	 * @throws ApplicationException
	 */
	public DeviceRegistry getDeviceRegistry(GetDeviceRegistryRequest request) {
		ClearBladeRegistryManager cbManager = new ClearBladeRegistryManager();
		return cbManager.getRegistry(request);
	}

	public DeviceRegistry createDeviceRegistry(CreateDeviceRegistryRequest request) {
		ClearBladeRegistryManager cbManager = new ClearBladeRegistryManager();
		return cbManager.createDeviceRegistry(request);
	}

	public DeviceRegistry updateDeviceRegistry(UpdateDeviceRegistryRequest request) {
		ClearBladeRegistryManager cbManager = new ClearBladeRegistryManager();
		return cbManager.updateDeviceRegistry(request);
	}

	public void deleteDeviceRegistry(DeleteDeviceRegistryRequest request) {
		ClearBladeRegistryManager cbManager = new ClearBladeRegistryManager();
		cbManager.deleteDeviceRegistry(request);
	}

	public ListDeviceRegistriesResponse listDeviceRegistries(ListDeviceRegistriesRequest request) {
		ClearBladeDeviceManager cbManager = new ClearBladeDeviceManager();
		return cbManager.listDeviceRegistries(request);
	}

}
