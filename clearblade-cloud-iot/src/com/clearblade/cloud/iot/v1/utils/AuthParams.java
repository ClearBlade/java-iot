package com.clearblade.cloud.iot.v1.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class AuthParams {
	static Logger log = Logger.getLogger(AuthParams.class.getName());

	private static String adminSystemKey = null; 
	private static String project = null;
	private static String baseURL = null;
	private static String adminToken = null;
	private static String userSystemKey = null;
	private static String userToken = null;
	private static String apiBaseURL = null;

	public static String getAdminSystemKey() {
		return adminSystemKey;
	}

	public static String getProject() {
		return project;
	}

	public static String getBaseURL() {
		return baseURL;
	}

	public static String getAdminToken() {
		return adminToken;
	}

	public static String getUserSystemKey() {
		return userSystemKey;
	}

	public static String getUserToken() {
		return userToken;
	}

	public static String getApiBaseURL() {
		return apiBaseURL;
	}

	public static void setAdminCredentials() {

		if(adminSystemKey != null) {
			return;
		}
		String pathToAuthFile = System.getenv(Constants.AUTH_ACCESS);

		JSONParser jsonParser = new JSONParser();
		try (FileReader authReader = new FileReader(pathToAuthFile)) {
			// Read JSON file
			Object obj = jsonParser.parse(authReader);
			JSONObject authJSONObject = (JSONObject) obj;
			if (authJSONObject != null) {

				adminSystemKey = (authJSONObject.get(Constants.ADMIN_SYSTEM_KEY)).toString();
				adminToken = (authJSONObject.get(Constants.ADMIN_TOKEN)).toString();
				baseURL = (authJSONObject.get(Constants.BASE_URL)).toString();
				project = (authJSONObject.get(Constants.PROJECT_NAME)).toString();
			}

		} catch (Exception e) {
			log.log(Level.SEVERE, "Access Denied - ClearBlade Configuration variable is not set.");
			e.printStackTrace();
		}
		System.out.println(adminSystemKey);
		System.out.println(adminToken);
	}
	
	@SuppressWarnings("unchecked")
	public static void setRegistryCredentials() {
			if(userSystemKey != null) {				
				return;
			}
		try {
			setAdminCredentials();
			ConfigParameters configParameters = new ConfigParameters();
			String finalURL = baseURL .concat(configParameters.getGetSystemCredentialsExtension())					  
					  					.concat(adminSystemKey)
					  					.concat("/"+Constants.GET_REGISTRY_CREDENTIALS);			 
			URL obj = new URL(finalURL);
			SetHttpConnection setCon = new SetHttpConnection();
			HttpsURLConnection con = setCon.getConnection(obj);
			con.setRequestProperty(Constants.HTTP_REQUEST_PROPERTY_TOKEN_KEY, adminToken);
			con.setRequestProperty(Constants.HTTP_REQUEST_PROPERTY_CONTENT_TYPE_KEY,
					Constants.HTTP_REQUEST_PROPERTY_CONTENT_TYPE_ACCEPT_VALUE);
			con.addRequestProperty(Constants.HTTP_REQUEST_PROPERTY_ACCEPT_KEY,
					Constants.HTTP_REQUEST_PROPERTY_CONTENT_TYPE_ACCEPT_VALUE);
			con.setRequestMethod(Constants.HTTP_REQUEST_METHOD_TYPE_POST);
			con.setDoOutput(true);			
			StringBuilder response = new StringBuilder();
			try (OutputStream os = con.getOutputStream()) {
				JSONObject js = new JSONObject();
				js.put("region", configParameters.getRegion());
				js.put("registry", configParameters.getRegistry());
				js.put("project", project);
				
				byte[] input = js.toString().getBytes(Constants.UTF8);
				os.write(input, 0, input.length);
				os.flush();
				int responseCode = con.getResponseCode();
				BufferedReader in = null;
				if (con.getErrorStream() != null) {
					in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
				} else if (con.getInputStream() != null) {
					in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				}
				JSONObject responseJSONObject = new JSONObject();
				JSONParser responseParser = new JSONParser();
				String inputLine;
				String responseMessage = "";
				if (in != null) {
					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}
					responseMessage = response.toString();
					System.out.println(responseMessage);
				}
				if (responseCode == 200) {
					if(responseMessage != null && responseMessage.length() > 0) {
						Object responseObj = responseParser.parse(responseMessage);
						if(responseObj != null) {
							responseJSONObject = (JSONObject) responseObj;
							userSystemKey = responseJSONObject.get(Constants.USER_SYSTEM_KEY).toString();
							userToken = responseJSONObject.get(Constants.USER_TOKEN).toString();
							apiBaseURL = responseJSONObject.get(Constants.API_BASE_URL).toString();
						}
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	} catch (Exception e) {
		e.printStackTrace();
	}
  }
	
}
