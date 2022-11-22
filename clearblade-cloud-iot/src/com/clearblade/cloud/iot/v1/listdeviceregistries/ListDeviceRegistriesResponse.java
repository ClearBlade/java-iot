package com.clearblade.cloud.iot.v1.listdeviceregistries;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.clearblade.cloud.iot.v1.utils.DeviceRegistry;

public class ListDeviceRegistriesResponse {
	static Logger log = Logger.getLogger(ListDeviceRegistriesResponse.class.getName());

	private String nextPageToken;
	private List<DeviceRegistry> deviceRegistriesList;

	protected ListDeviceRegistriesResponse(Builder builder) {
		this.deviceRegistriesList = builder.deviceRegistriesList;
		this.nextPageToken = builder.nextPageToken;
	}

	public String getNextPageToken() {
		return nextPageToken;
	}

	public void setNextPageToken(String nextPageToken) {
		this.nextPageToken = nextPageToken;
	}

	public List<DeviceRegistry> getDeviceRegistriesList() {
		return deviceRegistriesList;
	}

	public void setDeviceRegistriesList(List<DeviceRegistry> deviceRegistriesList) {
		this.deviceRegistriesList = deviceRegistriesList;
	}

	// Static class Builder
	public static class Builder {

		/// instance fields
		private String nextPageToken;
		private List<DeviceRegistry> deviceRegistriesList = new ArrayList<>();

		public static Builder newBuilder() {
			return new Builder();
		}

		private Builder() {
		}

		// Setter methods
		public Builder buildResponse(String jsonString) {
			try {
				JSONObject jsonObj = new JSONObject();
				JSONParser jsonParser = new JSONParser();
				jsonObj = (JSONObject) (jsonParser.parse(jsonString));

				JSONArray deviceRegistriesArray = (JSONArray) jsonObj.get("deviceRegistries");
				@SuppressWarnings("rawtypes")
				Iterator deviceRegistryIterate = deviceRegistriesArray.iterator();
				while (deviceRegistryIterate.hasNext()) {
					JSONObject deviceRegistryJson = (JSONObject) deviceRegistryIterate.next();
					DeviceRegistry deviceRegistryObj = DeviceRegistry.newBuilder().build();
					deviceRegistryObj.loadFromString(deviceRegistryJson.toString());
					deviceRegistriesList.add(deviceRegistryObj);
				}

				if (jsonObj.containsKey("nextPageToken"))
					nextPageToken = jsonObj.get("nextPageToken").toString();

			} catch (Exception e) {
				e.printStackTrace();
			}
			return this;
		}

		// build method to deal with outer class
		// to return outer instance
		public ListDeviceRegistriesResponse build() {
			return new ListDeviceRegistriesResponse(this);
		}
	}

}
