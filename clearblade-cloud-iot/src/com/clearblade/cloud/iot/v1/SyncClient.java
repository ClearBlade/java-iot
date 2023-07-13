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
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.clearblade.cloud.iot.v1.binddevicetogateway.BindDeviceToGatewayRequest;
import com.clearblade.cloud.iot.v1.createdevice.CreateDeviceRequest;
import com.clearblade.cloud.iot.v1.createdeviceregistry.CreateDeviceRegistryRequest;
import com.clearblade.cloud.iot.v1.deletedevice.DeleteDeviceRequest;
import com.clearblade.cloud.iot.v1.deletedeviceregistry.DeleteDeviceRegistryRequest;
import com.clearblade.cloud.iot.v1.deviceslist.DevicesListRequest;
import com.clearblade.cloud.iot.v1.devicestateslist.ListDeviceStatesRequest;
import com.clearblade.cloud.iot.v1.exception.ApplicationException;
import com.clearblade.cloud.iot.v1.getdevice.GetDeviceRequest;
import com.clearblade.cloud.iot.v1.getdeviceregistry.GetDeviceRegistryRequest;
import com.clearblade.cloud.iot.v1.listdeviceconfigversions.ListDeviceConfigVersionsRequest;
import com.clearblade.cloud.iot.v1.modifycloudtodeviceconfig.ModifyCloudToDeviceConfigRequest;
import com.clearblade.cloud.iot.v1.sendcommandtodevice.SendCommandToDeviceRequest;
import com.clearblade.cloud.iot.v1.unbinddevicefromgateway.UnbindDeviceFromGatewayRequest;
import com.clearblade.cloud.iot.v1.updatedevice.UpdateDeviceRequest;
import com.clearblade.cloud.iot.v1.updatedeviceregistry.UpdateDeviceRegistryRequest;
import com.clearblade.cloud.iot.v1.utils.AuthParams;
import com.clearblade.cloud.iot.v1.utils.ConfigParameters;
import com.clearblade.cloud.iot.v1.utils.Constants;
import org.json.simple.parser.ParseException;

public class SyncClient {

    static Logger log = Logger.getLogger(SyncClient.class.getName());
    private ConfigParameters configParameters = ConfigParameters.getInstance();

    private AuthParams authParams = new AuthParams();

    /**
     * Method used to generate URL for apicall
     *
     * @param apiName - path to api
     * @param params  - parameters to be attached to request
     * @return URL formed and to be used
     */
    private String generateURL(AuthParams authParams, String apiName, String params) {
        return authParams.getApiBaseURL().concat(configParameters.getEndpointPort()).concat(configParameters.getWebhook()).concat(authParams.getUserSystemKey()).concat(apiName).concat("?" + params);
    }

    /**
     * Method used to generate URL for apicall
     *
     * @param apiName - path to api
     * @param params  - parameters to be attached to request
     * @return URL formed and to be used
     */
    private String generateAdminURL(AuthParams authParams, String apiName, String params) {
        return authParams.getBaseURL().concat(configParameters.getWebhook()).concat(authParams.getAdminSystemKey()).concat(apiName).concat("?" + params);
    }

    public String[] get(String apiName, GetDeviceRequest request) {
        try {
            authParams.setRegistryCredentials(request.getName().getProject(), request.getName().getRegistry(), request.getName().getLocation());
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        String finalURL = generateURL(authParams, apiName, request.toString());
        String token = authParams.getUserToken();
        return get(finalURL, token);
    }

    public String[] get(String apiName, String params, DevicesListRequest request) {
        try {
            log.info("get: setting registry cred");
            authParams.setRegistryCredentials(request.getParent().getProject(), request.getParent().getRegistry(), request.getParent().getLocation());
        } catch (Exception e) {
            log.info("get: error");
            throw new ApplicationException(e);
        }
        String finalURL = generateURL(authParams, apiName, params);
        String token = authParams.getUserToken();
        log.info("get: finalURL: " + finalURL);
        log.info("get: token: " + token);
        return get(finalURL, token);
    }

    public String[] get(String apiName, String params, ListDeviceStatesRequest request) {
        try {
            authParams.setRegistryCredentials(request.getName().getProject(), request.getName().getRegistry(), request.getName().getLocation());
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        String finalURL = generateURL(authParams, apiName, params);
        String token = authParams.getUserToken();
        return get(finalURL, token);
    }

    public String[] get(String apiName, String params, GetDeviceRegistryRequest request) {
        try {
            authParams.setRegistryCredentials(request.getName().getProject(), request.getName().getRegistry(), request.getName().getLocation());
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        String finalURL = generateURL(authParams, apiName, params);
        String token = authParams.getUserToken();
        return get(finalURL, token);
    }

    public String[] get(String apiName, String params, ListDeviceConfigVersionsRequest request) {
        try {
            authParams.setRegistryCredentials(request.getName().getProject(), request.getName().getRegistry(), request.getName().getLocation());
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        String finalURL = generateURL(authParams, apiName, params);
        String token = authParams.getUserToken();
        return get(finalURL, token);
    }

    public String[] get(String apiName, String params, boolean isAdmin) {
        String finalURL = "";
        String token = "";
        if (isAdmin) {
            try {
                authParams.setAdminCredentials();
            } catch (IOException e) {
                throw new ApplicationException(e);
            }
            finalURL = generateAdminURL(authParams, apiName, params);
            token = authParams.getAdminToken();
        }
        return this.get(finalURL, token);
    }

    /**
     * Method used to Calls HTTP Get request
     *
     * @return String[] containing responseCode, responseMessage and response object
     * @throws IOException
     * @throws ApplicationException
     */
    public String[] get(String finalURL, String token) {
        String[] responseArray = new String[3];
        log.info("get");
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(finalURL)).headers(Constants.HTTP_REQUEST_PROPERTY_CONTENT_TYPE_KEY, Constants.HTTP_REQUEST_PROPERTY_CONTENT_TYPE_ACCEPT_VALUE, Constants.HTTP_REQUEST_PROPERTY_TOKEN_KEY, token, Constants.HTTP_REQUEST_PROPERTY_ACCEPT_KEY, Constants.HTTP_REQUEST_PROPERTY_CONTENT_TYPE_ACCEPT_VALUE).GET().build();

            HttpResponse<String> response = HttpClient.newBuilder().proxy(ProxySelector.getDefault()).build().send(request, BodyHandlers.ofString());

            responseArray[0] = String.valueOf(response.statusCode());
            responseArray[1] = "";
            responseArray[2] = response.body();
            log.info("get: response.body(): " + response.body());
        } catch (InterruptedException e) {
            log.log(Level.SEVERE, e.getMessage());
            Thread.currentThread().interrupt();
            throw new ApplicationException(e);
        } catch (Exception ex) {
            log.log(Level.SEVERE, ex.getMessage());
            throw new ApplicationException(ex);
        }
        return responseArray;
    }

    public String[] post(String apiName, String params, String body, CreateDeviceRequest request) {
        try {
            log.info("post: body: ");
            authParams.setRegistryCredentials(request.getParent().getProject(), request.getParent().getRegistry(), request.getParent().getLocation());
            log.info("post: authParams received");
        } catch (Exception e) {
            log.info("post: Error");
            throw new ApplicationException(e);
        }
        String finalURL = generateURL(authParams, apiName, params);
        String token = authParams.getUserToken();
        log.info("post: finalURL: " + finalURL);
        log.info("post: token: " + token);
        return post(finalURL, body, token);
    }

    public String[] post(String apiName, String params, String body, SendCommandToDeviceRequest request) {
        try {
            authParams.setRegistryCredentials(request.getDeviceName().getProject(), request.getDeviceName().getRegistry(), request.getDeviceName().getLocation());
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        String finalURL = generateURL(authParams, apiName, params);
        String token = authParams.getUserToken();
        return post(finalURL, body, token);
    }

    public String[] post(String apiName, String params, String body, BindDeviceToGatewayRequest request) {
        try {
            authParams.setRegistryCredentials(request.getParent().getProject(), request.getParent().getRegistry(), request.getParent().getLocation());
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        String finalURL = generateURL(authParams, apiName, params);
        String token = authParams.getUserToken();
        return post(finalURL, body, token);
    }

    public String[] post(String apiName, String params, String body, ModifyCloudToDeviceConfigRequest request) {
        try {
            authParams.setRegistryCredentials(request.getDeviceName().getProject(), request.getDeviceName().getRegistry(), request.getDeviceName().getLocation());
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        String finalURL = generateURL(authParams, apiName, params);
        String token = authParams.getUserToken();
        return post(finalURL, body, token);
    }

    public String[] post(String apiName, String params, String body, UnbindDeviceFromGatewayRequest request) {
        try {
            authParams.setRegistryCredentials(request.getParent().getProject(), request.getParent().getRegistry(), request.getParent().getLocation());
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        String finalURL = generateURL(authParams, apiName, params);
        String token = authParams.getUserToken();
        return post(finalURL, body, token);
    }

    public String[] post(String apiName, String params, String body, boolean isAdmin, CreateDeviceRegistryRequest request) {
        String finalURL = "";
        String token = "";
        if (isAdmin) {
            try {
                authParams.setAdminCredentials();
            } catch (IOException e) {
                throw new ApplicationException(e);
            }
            finalURL = generateAdminURL(authParams, apiName, params);
            token = authParams.getAdminToken();
        }
        return post(finalURL, body, token);
    }

    /**
     * Method used to call HTTP Post request
     *
     * @param body
     * @return String[] containing responseCode, responseMessage and response object
     * @throws IOException
     * @throws ApplicationException
     */
    public String[] post(String finalURL, String body, String token) {
        String[] responseArray = new String[3];
        try {
            BodyPublisher jsonPayload = BodyPublishers.ofString(body);
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(finalURL)).method(Constants.HTTP_REQUEST_METHOD_TYPE_POST, jsonPayload).headers(Constants.HTTP_REQUEST_PROPERTY_CONTENT_TYPE_KEY, Constants.HTTP_REQUEST_PROPERTY_CONTENT_TYPE_ACCEPT_VALUE, Constants.HTTP_REQUEST_PROPERTY_TOKEN_KEY, token, Constants.HTTP_REQUEST_PROPERTY_ACCEPT_KEY, Constants.HTTP_REQUEST_PROPERTY_CONTENT_TYPE_ACCEPT_VALUE).build();

            HttpResponse<String> response = HttpClient.newBuilder().proxy(ProxySelector.getDefault()).build().send(request, BodyHandlers.ofString());

            responseArray[0] = String.valueOf(response.statusCode());
            responseArray[1] = "";
            responseArray[2] = response.body();
            log.info("post: response.body(): " + response.body());
        } catch (InterruptedException e) {
            log.log(Level.SEVERE, e.getMessage());
            Thread.currentThread().interrupt();
            throw new ApplicationException(e);
        } catch (Exception ec) {
            log.log(Level.SEVERE, ec.getMessage());
            throw new ApplicationException(ec);
        }
        return responseArray;
    }

    public String[] delete(String apiName, String params, boolean isAdmin, DeleteDeviceRequest request) {
        try {
            authParams.setRegistryCredentials(request.getName().getProject(), request.getName().getRegistry(), request.getName().getLocation());
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        String finalURL = generateURL(authParams, apiName, params);
        String token = authParams.getUserToken();
        return this.delete(finalURL, token);
    }

    public String[] delete(String apiName, String params, boolean isAdmin) {
        try {
            authParams.setAdminCredentials();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        String finalURL = generateAdminURL(authParams, apiName, params);
        String token = authParams.getAdminToken();
        return this.delete(finalURL, token);
    }

    /**
     * Method used to call HTTP delete request
     *
     * @return String[] containing responseCode, responseMessage and response object
     * @throws IOException
     * @throws ApplicationException
     */
    public String[] delete(String finalURL, String token) {
        String[] responseArray = new String[3];
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(finalURL)).headers(Constants.HTTP_REQUEST_PROPERTY_CONTENT_TYPE_KEY, Constants.HTTP_REQUEST_PROPERTY_CONTENT_TYPE_ACCEPT_VALUE, Constants.HTTP_REQUEST_PROPERTY_TOKEN_KEY, token, Constants.HTTP_REQUEST_PROPERTY_ACCEPT_KEY, Constants.HTTP_REQUEST_PROPERTY_CONTENT_TYPE_ACCEPT_VALUE).DELETE().build();

            HttpResponse<String> response = HttpClient.newBuilder().proxy(ProxySelector.getDefault()).build().send(request, BodyHandlers.ofString());

            responseArray[0] = String.valueOf(response.statusCode());
            responseArray[1] = "";
            responseArray[2] = response.body();
        } catch (InterruptedException e) {
            log.log(Level.SEVERE, e.getMessage());
            Thread.currentThread().interrupt();
            throw new ApplicationException(e);
        } catch (Exception ex) {
            log.log(Level.SEVERE, ex.getMessage());
            throw new ApplicationException(ex);
        }

        return responseArray;
    }

    public String[] update(String apiName, String params, String body, UpdateDeviceRequest request) throws InterruptedException {
        try {
            authParams.setRegistryCredentials(request.getDeviceName().getProject(), request.getDeviceName().getRegistry(), request.getDeviceName().getLocation());
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        String finalURL = generateURL(authParams, apiName, params);
        String token = authParams.getUserToken();
        return update(finalURL, body, token);
    }

    public String[] update(String apiName, String params, String body, UpdateDeviceRegistryRequest request) throws InterruptedException {
        try {
            authParams.setRegistryCredentials(request.getParent().getProject(), request.getParent().getRegistry(), request.getParent().getLocation());
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        String finalURL = generateURL(authParams, apiName, params);
        String token = authParams.getUserToken();
        return update(finalURL, body, token);
    }


    public String[] update(String finalURL, String body, String token) throws InterruptedException {
        String[] responseArray = new String[3];
        try {
            BodyPublisher jsonPayload = BodyPublishers.ofString(body);
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(finalURL)).method(Constants.HTTP_REQUEST_METHOD_TYPE_PATCH, jsonPayload).headers(Constants.HTTP_REQUEST_PROPERTY_CONTENT_TYPE_KEY, Constants.HTTP_REQUEST_PROPERTY_CONTENT_TYPE_ACCEPT_VALUE, Constants.HTTP_REQUEST_PROPERTY_TOKEN_KEY, token, Constants.HTTP_REQUEST_PROPERTY_ACCEPT_KEY, Constants.HTTP_REQUEST_PROPERTY_CONTENT_TYPE_ACCEPT_VALUE).build();

            HttpResponse<String> response = HttpClient.newBuilder().proxy(ProxySelector.getDefault()).build().send(request, BodyHandlers.ofString());

            responseArray[0] = String.valueOf(response.statusCode());
            responseArray[1] = "";
            responseArray[2] = response.body();

        } catch (Exception ex) {
            log.log(Level.SEVERE, ex.getMessage());
            throw new ApplicationException(ex);
        }
        return responseArray;
    }
}
