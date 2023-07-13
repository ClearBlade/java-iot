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
import java.util.logging.Level;
import java.util.logging.Logger;

import com.clearblade.cloud.iot.v1.binddevicetogateway.BindDeviceToGatewayRequest;
import com.clearblade.cloud.iot.v1.binddevicetogateway.BindDeviceToGatewayResponse;
import com.clearblade.cloud.iot.v1.createdevice.CreateDeviceRequest;
import com.clearblade.cloud.iot.v1.deletedevice.DeleteDeviceRequest;
import com.clearblade.cloud.iot.v1.deviceslist.DevicesListRequest;
import com.clearblade.cloud.iot.v1.deviceslist.DevicesListResponse;
import com.clearblade.cloud.iot.v1.devicestateslist.ListDeviceStatesRequest;
import com.clearblade.cloud.iot.v1.devicestateslist.ListDeviceStatesResponse;
import com.clearblade.cloud.iot.v1.devicetypes.Device;
import com.clearblade.cloud.iot.v1.devicetypes.DeviceConfig;
import com.clearblade.cloud.iot.v1.exception.ApplicationException;
import com.clearblade.cloud.iot.v1.getdevice.GetDeviceRequest;
import com.clearblade.cloud.iot.v1.listdeviceconfigversions.ListDeviceConfigVersionsRequest;
import com.clearblade.cloud.iot.v1.listdeviceconfigversions.ListDeviceConfigVersionsResponse;
import com.clearblade.cloud.iot.v1.listdeviceregistries.ListDeviceRegistriesRequest;
import com.clearblade.cloud.iot.v1.listdeviceregistries.ListDeviceRegistriesResponse;
import com.clearblade.cloud.iot.v1.modifycloudtodeviceconfig.ModifyCloudToDeviceConfigRequest;
import com.clearblade.cloud.iot.v1.sendcommandtodevice.SendCommandToDeviceRequest;
import com.clearblade.cloud.iot.v1.sendcommandtodevice.SendCommandToDeviceResponse;
import com.clearblade.cloud.iot.v1.unbinddevicefromgateway.UnbindDeviceFromGatewayRequest;
import com.clearblade.cloud.iot.v1.unbinddevicefromgateway.UnbindDeviceFromGatewayResponse;
import com.clearblade.cloud.iot.v1.updatedevice.UpdateDeviceRequest;
import com.clearblade.cloud.iot.v1.utils.ConfigParameters;
import org.json.simple.parser.ParseException;

public class ClearBladeDeviceManager {
    static Logger log = Logger.getLogger(ClearBladeDeviceManager.class.getName());
    ConfigParameters configParameters = ConfigParameters.getInstance();

    public Device getDevice(GetDeviceRequest request) throws ApplicationException {
        SyncClient syncClient = new SyncClient();
        String[] responseArray = syncClient.get(configParameters.getDevicesURLExtension(), request);
        if (responseArray[0] != null) {
            int responseCode = Integer.parseInt(responseArray[0]);
            if (responseCode == 200) {
                Device device = Device.newBuilder().build();
                device.loadFromString(responseArray[2]);
                return device;
            } else {
                throw new ApplicationException(responseArray[2]);
            }
        }
        return null;
    }

    public Device asyncGetDevice(GetDeviceRequest request) throws ApplicationException {
        try {
            AsyncClient asyncClient = new AsyncClient();
            String[] responseArray = asyncClient.get(configParameters.getDevicesURLExtension(), request);
            if (responseArray[0] != null) {
                int responseCode = Integer.parseInt(responseArray[0]);
                if (responseCode == 200) {
                    Device device = Device.newBuilder().build();
                    device.loadFromString(responseArray[2]);
                    return device;
                } else {
                    throw new ApplicationException(responseArray[2]);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage(), e);
        }
        return null;
    }

    public Device createDevice(CreateDeviceRequest request) throws ApplicationException {
        log.info("createDevice: parent: " + request.getParent());
        SyncClient syncClient = new SyncClient();
        String[] params = request.getParams();
        String reqParams = params[0];
        String body = params[1];
        log.info("createDevice: params");
        log.info("createDevice: reqParams: " + reqParams);
        log.info("createDevice: body: " + body);
        String[] responseArray = syncClient.post(configParameters.getDevicesURLExtension(), reqParams, body, request);
        if (responseArray[0] != null) {
            log.info("createDevice: responseArray NOT NULL");
            int responseCode = Integer.parseInt(responseArray[0]);
            log.info("createDevice: response code: " + responseCode);
            if (responseCode == 200) {
                Device deviceObj = Device.newBuilder().build();
                deviceObj.loadFromString(responseArray[2]);
                return deviceObj;
            } else {
                throw new ApplicationException(responseArray[2]);
            }
        } else {
            log.info("createDevice: responseArray NULL");
            throw new ApplicationException("");
        }
    }

    public Device asyncCreateDevice(CreateDeviceRequest request) throws ApplicationException {
        try {
            AsyncClient asyncClient = new AsyncClient();
            String[] params = request.getParams();
            String reqParams = params[0];
            String body = params[1];
            String[] responseArray = asyncClient.post(configParameters.getDevicesURLExtension(), reqParams, body, request);
            if (responseArray[0] != null) {
                int responseCode = Integer.parseInt(responseArray[0]);
                if (responseCode == 200) {
                    Device deviceObj = Device.newBuilder().build();
                    deviceObj.loadFromString(responseArray[2]);
                    return deviceObj;
                } else {
                    throw new ApplicationException(responseArray[2]);
                }
            }
            return null;
        } catch (Exception e) {
            // TODO: handle exception
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    public void deleteDevice(DeleteDeviceRequest request) throws ApplicationException {
        SyncClient syncClient = new SyncClient();
        String[] responseArray = syncClient.delete(configParameters.getDevicesURLExtension(), request.toString(), false, request);
        if (responseArray[0] != null) {
            int responseCode = Integer.parseInt(responseArray[0]);
            if (responseCode != 200) {
                throw new ApplicationException(responseArray[2]);
            }
        } else {
            throw new ApplicationException("");
        }
    }

    public void asyncDeleteDevice(DeleteDeviceRequest request) throws ApplicationException {
        try {
            AsyncClient asyncClient = new AsyncClient();
            String[] responseArray = asyncClient.delete(configParameters.getDevicesURLExtension(), request.toString(), request);
            if (responseArray[0] != null) {
                int responseCode = Integer.parseInt(responseArray[0]);
                if (responseCode != 200) {
                    throw new ApplicationException(responseArray[2]);
                }
            } else {
                throw new ApplicationException("");
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    public Device updateDevice(UpdateDeviceRequest request) throws ApplicationException {
        try {
            SyncClient syncClient = new SyncClient();
            String[] params = request.getBodyAndParams();
            String reqParams = params[0];
            String body = params[1];
            String[] responseArray;
            responseArray = syncClient.update(configParameters.getDevicesURLExtension(), reqParams, body, request);
            if (responseArray[0] != null) {
                int responseCode = Integer.parseInt(responseArray[0]);
                if (responseCode == 200) {
                    Device deviceObj = Device.newBuilder().build();
                    deviceObj.loadFromString(responseArray[2]);
                    return deviceObj;
                } else {
                    throw new ApplicationException(responseArray[2]);
                }
            } else {
                throw new ApplicationException();
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            Thread.currentThread().interrupt();
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    public Device asyncUpdateDevice(UpdateDeviceRequest request) throws ApplicationException {
        try {
            AsyncClient asyncClient = new AsyncClient();
            String[] params = request.getBodyAndParams();
            String reqParams = params[0];
            String body = params[1];
            String[] responseArray = asyncClient.update(configParameters.getDevicesURLExtension(), reqParams, body, request);
            if (responseArray[0] != null) {
                int responseCode = Integer.parseInt(responseArray[0]);
                if (responseCode == 200) {
                    Device deviceObj = Device.newBuilder().build();
                    deviceObj.loadFromString(responseArray[2]);
                    return deviceObj;
                } else {
                    throw new ApplicationException(responseArray[2]);
                }
            } else {
                throw new ApplicationException(responseArray[2]);
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    public BindDeviceToGatewayResponse bindDeviceToGateway(BindDeviceToGatewayRequest request) throws ApplicationException {
        SyncClient syncClient = new SyncClient();
        String[] paramBody = request.getBodyAndParams();
        String[] responseArray = syncClient.post(configParameters.getCloudiotURLExtension(), paramBody[0], paramBody[1], request);
        BindDeviceToGatewayResponse response = BindDeviceToGatewayResponse.Builder.newBuilder().build();
        if (responseArray[0] != null) {
            int responseCode = Integer.parseInt(responseArray[0]);
            if (responseCode == 200) {
                response.setHttpStatusCode(Integer.parseInt(responseArray[0]));
                response.setHttpStatusResponse(responseArray[1]);
            } else {
                throw new ApplicationException(responseArray[2]);
            }
        }
        return response;
    }

    public BindDeviceToGatewayResponse asyncBindDeviceToGateway(BindDeviceToGatewayRequest request) throws ApplicationException {
        try {
            AsyncClient asyncClient = new AsyncClient();
            String[] paramBody = request.getBodyAndParams();
            String[] responseArray = asyncClient.post(configParameters.getCloudiotURLExtension(), paramBody[0], paramBody[1], request);
            BindDeviceToGatewayResponse response = BindDeviceToGatewayResponse.Builder.newBuilder().build();
            if (responseArray[0] != null) {
                int responseCode = Integer.parseInt(responseArray[0]);
                if (responseCode == 200) {
                    response.setHttpStatusCode(Integer.parseInt(responseArray[0]));
                    response.setHttpStatusResponse(responseArray[1]);
                } else {
                    throw new ApplicationException(responseArray[2]);
                }
            }
            return response;
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
        }
        return null;
    }

    public UnbindDeviceFromGatewayResponse unbindDeviceFromGateway(UnbindDeviceFromGatewayRequest request) throws ApplicationException {
        SyncClient syncClinet = new SyncClient();
        String[] paramBody = request.getBodyAndParams();
        String[] responseArray = syncClinet.post(configParameters.getCloudiotURLExtension(), paramBody[0], paramBody[1], request);
        UnbindDeviceFromGatewayResponse response = UnbindDeviceFromGatewayResponse.Builder.newBuilder().build();
        if (responseArray[0] != null) {
            int responseCode = Integer.parseInt(responseArray[0]);
            if (responseCode == 200) {
                response.setHttpStatusCode(Integer.parseInt(responseArray[0]));
                response.setHttpStatusResponse(responseArray[1]);
            } else {
                throw new ApplicationException(responseArray[2]);
            }
        }
        return response;
    }

    public UnbindDeviceFromGatewayResponse asyncUnbindDeviceFromGateway(UnbindDeviceFromGatewayRequest request) throws ApplicationException {
        try {
            AsyncClient asyncClinet = new AsyncClient();
            String[] paramBody = request.getBodyAndParams();
            String[] responseArray = asyncClinet.post(configParameters.getCloudiotURLExtension(), paramBody[0], paramBody[1], request);
            UnbindDeviceFromGatewayResponse response = UnbindDeviceFromGatewayResponse.Builder.newBuilder().build();
            if (responseArray[0] != null) {
                int responseCode = Integer.parseInt(responseArray[0]);
                if (responseCode == 200) {
                    response.setHttpStatusCode(Integer.parseInt(responseArray[0]));
                    response.setHttpStatusResponse(responseArray[1]);
                } else {
                    throw new ApplicationException(responseArray[2]);
                }
            }
            return response;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    public SendCommandToDeviceResponse sendCommandToDevice(SendCommandToDeviceRequest request) throws ApplicationException {
        SyncClient syncClient = new SyncClient();
        String[] paramBody = request.getBodyAndParams();
        String[] responseArray = syncClient.post(configParameters.getDevicesURLExtension(), paramBody[0], paramBody[1], request);
        SendCommandToDeviceResponse response = SendCommandToDeviceResponse.Builder.newBuilder().build();
        if (responseArray[0] != null) {
            int responseCode = Integer.parseInt(responseArray[0]);
            if (responseCode == 200 || responseCode == 204) {
                response.setHttpStatusCode(Integer.parseInt(responseArray[0]));
                response.setHttpStatusResponse(responseArray[1]);
            } else {
                throw new ApplicationException(responseArray[2]);
            }
        }
        return response;
    }

    public SendCommandToDeviceResponse asyncSendCommandToDevice(SendCommandToDeviceRequest request) throws ApplicationException {
        try {
            AsyncClient asyncClient = new AsyncClient();
            String[] paramBody = request.getBodyAndParams();
            String[] responseArray = asyncClient.post(configParameters.getDevicesURLExtension(), paramBody[0], paramBody[1], request);
            SendCommandToDeviceResponse response = SendCommandToDeviceResponse.Builder.newBuilder().build();
            if (responseArray[0] != null) {
                int responseCode = Integer.parseInt(responseArray[0]);
                if (responseCode == 200 || responseCode == 204) {
                    response.setHttpStatusCode(Integer.parseInt(responseArray[0]));
                    response.setHttpStatusResponse(responseArray[1]);
                } else {
                    throw new ApplicationException(responseArray[2]);
                }
            }
            return response;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    public DevicesListResponse listDevices(DevicesListRequest request) throws ApplicationException {
        log.info("listDevices: request: " + request.getParamsForList());
        SyncClient syncClient = new SyncClient();
        String[] responseArray = syncClient.get(configParameters.getDevicesURLExtension(), request.getParamsForList(), request);
        log.info("listDevices: responseArray: " + responseArray);
        if (responseArray[0] != null) {
            int responseCode = Integer.parseInt(responseArray[0]);
            if (responseCode == 200) {
                return DevicesListResponse.Builder.newBuilder().buildResponse(responseArray[2]).build();
            } else {
                throw new ApplicationException(responseArray[2]);
            }
        }
        return null;
    }

    public DevicesListResponse asyncListDevices(DevicesListRequest request) throws ApplicationException {
        try {
            AsyncClient asyncClient = new AsyncClient();
            String[] responseArray = asyncClient.get(configParameters.getDevicesURLExtension(), request.getParamsForList(), request);
            if (responseArray[0] != null) {
                int responseCode = Integer.parseInt(responseArray[0]);
                if (responseCode == 200) {
                    return DevicesListResponse.Builder.newBuilder().buildResponse(responseArray[2]).build();
                } else {
                    throw new ApplicationException(responseArray[2]);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage(), e);
        }
        return null;
    }

    public DeviceConfig modifyCloudToDeviceConfig(ModifyCloudToDeviceConfigRequest request) throws ApplicationException {
        SyncClient syncClient = new SyncClient();
        String[] paramBody = request.getBodyAndParams();
        String[] responseArray = syncClient.post(configParameters.getDevicesURLExtension(), paramBody[0], paramBody[1], request);
        DeviceConfig deviceConfig = DeviceConfig.newBuilder().build();
        if (responseArray[0] != null) {
            if (Integer.parseInt(responseArray[0]) == 200) deviceConfig.loadFromString(responseArray[2]);
            else throw new ApplicationException(responseArray[2]);
        }
        return deviceConfig;
    }

    public DeviceConfig asyncModifyCloudToDeviceConfig(ModifyCloudToDeviceConfigRequest request) throws ApplicationException {
        try {
            AsyncClient asyncClient = new AsyncClient();
            String[] paramBody = request.getBodyAndParams();
            String[] responseArray = asyncClient.post(configParameters.getDevicesURLExtension(), paramBody[0], paramBody[1], request);
            DeviceConfig deviceConfig = DeviceConfig.newBuilder().build();
            if (responseArray[0] != null) {
                if (Integer.parseInt(responseArray[0]) == 200) deviceConfig.loadFromString(responseArray[2]);
                else throw new ApplicationException(responseArray[2]);
            }
            return deviceConfig;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    public ListDeviceStatesResponse listDeviceStates(ListDeviceStatesRequest request) throws ApplicationException {
        SyncClient syncClient = new SyncClient();
        String[] responseArray = syncClient.get(configParameters.getDevicesStatesURLExtension(), request.getParamsForList(), request);
        if (responseArray[0] != null) {
            int responseCode = Integer.parseInt(responseArray[0]);
            if (responseCode == 200) {
                return ListDeviceStatesResponse.Builder.newBuilder().buildResponse(responseArray[2]).build();
            } else throw new ApplicationException(responseArray[2]);
        }
        return null;
    }

    public ListDeviceStatesResponse asyncListDeviceStates(ListDeviceStatesRequest request) throws ApplicationException {
        try {
            AsyncClient asyncClient = new AsyncClient();
            String[] responseArray = asyncClient.get(configParameters.getDevicesStatesURLExtension(), request.getParamsForList(), request);
            if (responseArray[0] != null) {
                int responseCode = Integer.parseInt(responseArray[0]);
                if (responseCode == 200) {
                    return ListDeviceStatesResponse.Builder.newBuilder().buildResponse(responseArray[2]).build();
                } else throw new ApplicationException(responseArray[2]);
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage(), e);
        }
        return null;
    }

    public ListDeviceConfigVersionsResponse listDeviceConfigVersions(ListDeviceConfigVersionsRequest request) throws ApplicationException {
        SyncClient syncClient = new SyncClient();
        String[] responseArray = syncClient.get(configParameters.getCloudiotConfigURLExtension(), request.getParamsForList(), request);
        if (responseArray[0] != null) {
            int responseCode = Integer.parseInt(responseArray[0]);
            if (responseCode == 200) {
                return ListDeviceConfigVersionsResponse.Builder.newBuilder().buildResponse(responseArray[2]).build();
            } else throw new ApplicationException(responseArray[2]);
        }
        return null;
    }

    public ListDeviceConfigVersionsResponse asyncListDeviceConfigVersions(ListDeviceConfigVersionsRequest request) throws ApplicationException {
        try {
            AsyncClient asyncClient = new AsyncClient();
            String[] responseArray = asyncClient.get(configParameters.getCloudiotConfigURLExtension(), request.getParamsForList(), request);
            if (responseArray[0] != null) {
                int responseCode = Integer.parseInt(responseArray[0]);
                if (responseCode == 200) {
                    return ListDeviceConfigVersionsResponse.Builder.newBuilder().buildResponse(responseArray[2]).build();
                } else throw new ApplicationException(responseArray[2]);
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage(), e);
        }
        return null;
    }

    public ListDeviceRegistriesResponse listDeviceRegistries(ListDeviceRegistriesRequest request) throws ApplicationException {
        SyncClient syncClient = new SyncClient();
        String[] responseArray = syncClient.get(configParameters.getCloudiotURLExtension(), request.getParamsForList(), true);
        if (responseArray[0] != null) {
            int responseCode = Integer.parseInt(responseArray[0]);
            if (responseCode == 200) {
                return ListDeviceRegistriesResponse.Builder.newBuilder().buildResponse(responseArray[2]).build();
            } else throw new ApplicationException(responseArray[2]);
        }
        return null;
    }

    public ListDeviceRegistriesResponse asyncListDeviceRegistries(ListDeviceRegistriesRequest request) throws ApplicationException, IOException {
        AsyncClient asyncClient = new AsyncClient();
        String[] responseArray = asyncClient.asyncListDeviceRegistries(configParameters.getCloudiotURLExtension(), request.getParamsForList(), true);
        if (responseArray[0] != null) {
            int responseCode = Integer.parseInt(responseArray[0]);
            if (responseCode == 200) {
                return ListDeviceRegistriesResponse.Builder.newBuilder().buildResponse(responseArray[2]).build();
            } else throw new ApplicationException(responseArray[2]);
        }
        return null;
    }
}
