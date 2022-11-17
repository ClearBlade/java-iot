package com.clearblade.cloud.iot.v1;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONObject;

public class SendCommandToDeviceResponse {

	static Logger log = Logger.getLogger(SendCommandToDeviceResponse.class.getName());
	private final SendCommandToDeviceRequest request;
	private int httpStatusCode;
	private String httpStatusResponse;

	protected SendCommandToDeviceResponse(Builder builder) {
		this.request = builder.request;
	}

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	public String getHttpStatusResponse() {
		return httpStatusResponse;
	}

	public void setHttpStatusResponse(String httpStatusResponse) {
		this.httpStatusResponse = httpStatusResponse;
	}

	// Static class Builder
	public static class Builder {

		/// instance fields
		private SendCommandToDeviceRequest request;

		public static Builder newBuilder() {
			return new Builder();
		}

		private Builder() {
		}

		// Setter methods
		public Builder setSendCommandToDeviceRequest(SendCommandToDeviceRequest request) {
			this.request = request;
			return this;
		}

		// build method to deal with outer class
		// to return outer instance
		public SendCommandToDeviceResponse build() {
			return new SendCommandToDeviceResponse(this);
		}
	}
	
	/**
	 * Mehtod used to process the request
	 * Calling processRequestForMethod - sendCommandToDevice
	 */
	
	public void processRequest(SendCommandToDeviceRequest request) {
		String msg = "";
		JSONObject requestParams = new JSONObject();
		JSONObject bodyParams = new JSONObject();
		ProcessRequest processRequest = new ProcessRequest();
		
		if(request != null) {
			String logMsg = "Sending request to device :: "+request.toString();
			log.log(Level.INFO,logMsg);
			requestParams = (JSONObject) request.requestParams.clone();
			bodyParams = (JSONObject) request.bodyParams.clone();
		}
		
		String responseMessage = processRequest.processRequestForMethod("sendCommandToDevice",requestParams,bodyParams);
		this.setHttpStatusResponse(responseMessage);
		if (responseMessage.equals("OK")) {
			msg = "Request processed successfully for SendCommandToDevice method";
			log.log(Level.INFO, msg);
		} else {
			msg = "POST request not worked for sendCommandToDevice Method \n".concat(responseMessage);
			log.log(Level.SEVERE, msg);
		}

	}

	@Override
	public String toString() {
		return "Http Status Code :: " + this.getHttpStatusCode() + " Http Status Response :: "
				+ this.getHttpStatusResponse();
	}

	public SendCommandToDeviceRequest getRequest() {
		return request;
	}
}