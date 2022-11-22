package com.clearblade.cloud.iot.v1.updatedeviceregistry;

import org.json.simple.JSONObject;

import com.clearblade.cloud.iot.v1.utils.DeviceRegistry;

public class UpdateDeviceRegistryRequest {

	private String name;
    private String updateMask;
    private DeviceRegistry deviceRegistry;
	JSONObject requestParams;
	JSONObject bodyParams;
    
	private UpdateDeviceRegistryRequest(Builder builder) {
		this.name = builder.name;
		this.updateMask = builder.updateMask;
		this.deviceRegistry = builder.deviceRegistry;
	}
	public JSONObject getRequestParams() {
		return requestParams;
	}

	public void setRequestParams(JSONObject requestParams) {
		this.requestParams = requestParams;
	}

	public JSONObject getBodyParams() {
		return bodyParams;
	}

	public void setBodyParams(JSONObject bodyParams) {
		this.bodyParams = bodyParams;
	}

	// Static class Builder
	public static class Builder {

		/// instance fields
		private String name;
		private String updateMask;
	    private DeviceRegistry deviceRegistry;

		public static Builder newBuilder() {
			return new Builder();
		}

		private Builder() {
		}

		// Setter methods
		public Builder setName(String name) {
			this.name = name;
			return this;
		}
		
		public Builder setDeviceRegistry(DeviceRegistry deviceRegistry) {
			this.deviceRegistry = deviceRegistry;
			return this;
		}

		public Builder setUpdateMask(String updateMask) {
			this.updateMask = updateMask;
			return this;
		}
		
		// build method to deal with outer class
		// to return outer instance
		public UpdateDeviceRegistryRequest build() {
			return new UpdateDeviceRegistryRequest(this);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String toString() {
		requestParams.put("name", this.name);
		requestParams.put("updateMask", this.updateMask);
		
		bodyParams.put("id", this.deviceRegistry.toBuilder().getId());
		bodyParams.put("name", this.deviceRegistry.toBuilder().getName());
		bodyParams.put("logLevel", this.deviceRegistry.toBuilder().getLogLevel());
		
		return "name=" + this.name + ",updateMask=" + this.updateMask+", logLevel= "+this.deviceRegistry.toBuilder().getLogLevel();
	}
	
	public String[] getBodyAndParams() {
		String[] output = new String[2];
		
		String params = "name="+this.name+"&updateMask="+this.updateMask;
 		
		output[0] = params;
		output[1] = this.deviceRegistry.createDeviceJSONObject("");
		return output;
	}


}
