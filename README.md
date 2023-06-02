# ClearBlade Internet of Things (IoT) Core Client for Java

Java library and samples for [ClearBlade Internet of Things (IoT) Core][product-docs].

- [Product Documentation][product-docs]
- [Client Library Documentation][javasdk]
- [Quickstart for Java][quickstart]

## Supported Java Versions

Java 11 or above is required for using this client. Java 17 is the best choice for new development.

# Usage

You can either use the Maven artifact or do a local installation and setup of Maven.

## Maven artifact

If you are using Maven artifact, simply add the following dependency to your pom.xml file in a newly created/generated Maven App:

```xml
<dependency>
  <groupId>io.github.clearblade</groupId>
  <artifactId>clearblade-cloud-iot</artifactId>
  <version>1.0.0</version>
</dependency>
```

## Local Installation and setup

For local installation of Maven:

In the [clearblade-cloud-iot](./clearblade-cloud-iot) folder, run the following command to install the libraries and build the sample with
Maven:

    mvn clean install

## Quickstart

1. From the [ClearBlade Migration from Google IoT Core section](https://clearblade.atlassian.net/wiki/spaces/IC/pages/2202664969/Migration+from+Google+IoT+Core)
   migrate your existing Google IoT Core registries and/or devices into ClearBlade IoT Core or using the
   ClearBlade IoT Core Console, [create a device registry and devices](https://clearblade.atlassian.net/wiki/spaces/IC/pages/2202206388/Creating+registries+and+devices).

2. [Add service accounts to a project](https://clearblade.atlassian.net/wiki/spaces/IC/pages/2240675843/Add+service+accounts+to+a+project) and download the json file with your service
   account’s credentials.

3. Use the following to set your environment variables keys in your terminal or IDE environment configurations:

   ```
    export CLEARBLADE_CONFIGURATION=/path/to/file.json
   ```

   Optional

   ```
    export CLEARBLADE_REGISTRY=[your-registry]
    export CLEARBLADE_REGION=[your-region]
   export BINARYDATA_AND_TIME_GOOGLE_FORMAT=true
   ```

4. Use the HTTP or MQTT samples in the [samples](./clearblade-cloud-iot/samples) folder.

## ClearBlade IoT Core Samples Java

The sample apps demonstrates registry and device creation for ClearBlade IoT Core. The [samples](./clearblade-cloud-iot/samples) folder contains all the Java samples that demonstrate an overview of the ClearBlade IoT Core platform.

Note that before you can run the sample, you must configure your development environment
and terminal as described in the Quickstart above or the [samples](./clearblade-cloud-iot/samples) folder.

Before running the samples, you must set the `CLEARBLADE_CONFIGURATION`. Optionally set the `CLEARBLADE_REGISTRY` and
`CLEARBLADE_REGION` environment variables to avoid changing them in the the sample app every time you run it.

If you set `BINARYDATA_AND_TIME_GOOGLE_FORMAT` environment variable then it will give binaryData object's response in binary
form and time in Timestamp format which will have seconds and nanos in it which is following google's structure. Basically
it's applicable on get device state list, modify config, device config versions methods. To get the data in proper format
it should be cast in proper format.

```
	ListDeviceStatesResponse response = deviceManagerClient.listDeviceStates(request);
	if(response != null) {
		for (DeviceState element : response.getDeviceStatesList()) {
			System.out.println(((ByteString)element.getBinaryData()).toByteArray());
			System.out.println(((ByteString)element.getBinaryData()).toStringUtf8());
			System.out.println(((Timestamp)element.getUpdateTime()).getSeconds());
			System.out.println(((Timestamp)element.getUpdateTime()).getNanos());
		}
	}
```

When switching to use new registries and/or regions, either:

1. Update the `CLEARBLADE_REGISTRY` and `CLEARBLADE_REGION` environment variables; or
2. Change the `REGISTRY` and/or `REGION` variables in the sample code/app.

## Using the client library within an Example app

```
public class App {

	public static String PROJECT = "";
	public static String LOCATION = "";
	public static String REGISTRY = "";
	static ConfigParameters configParameters = ConfigParameters.getInstance();

	public static void main(String[] args) {
		PROJECT = "your-project-id";
		LOCATION = "your-region";
		REGISTRY = "your-registry";
		if (REGISTRY != null) {
			configParameters.setRegistry(REGISTRY);
		}
		if (LOCATION != null) {
			configParameters.setRegion(LOCATION);
		}
		asyncDevicesList();
	}

	public static void asyncDevicesList() {
		DeviceManagerAsyncClient deviceManagerAsyncClient = new DeviceManagerAsyncClient();
		RegistryName parent = RegistryName.of(PROJECT, LOCATION, REGISTRY);
		DevicesListRequest request = DevicesListRequest.Builder.newBuilder().setParent(parent.toString())
				.setGatewayListOptions(GatewayListOptions.newBuilder().setGatewayType(GatewayType.NON_GATEWAY).build())
				.setPageSize(10)
				.build();
		DevicesListResponse response = deviceManagerAsyncClient.listDevices(request);
```

## Running the sample - Command Line

The following command summarizes the sample usage:

First compile the sample.

Run registry example:

    mvn exec:java \
    	-Dexec.cleanupDaemonThreads=false \
        -Dexec.mainClass="com.clearblade.cloud.iot.v1.samples.createdeviceregistry.SyncCreateDeviceRegistry" \
    	-DprojectName="your-project-id" \
    	-Dlocation="your-region" \
    	-DregistryName="your-registry"

Run device example:

    mvn exec:java \
    	-Dexec.cleanupDaemonThreads=false \
        -Dexec.mainClass="com.clearblade.cloud.iot.v1.samples.createdevice.AsyncCreateDevice" \
    	-DprojectName="your-project-id" \
    	-Dlocation="your-region" \
    	-DregistryName="your-registry" \
    	-DdeviceName="your-device-id" \

[product-docs]: https://clearblade.atlassian.net/wiki/spaces/IC/overview
[javasdk]: https://clearblade.atlassian.net/wiki/spaces/IC/pages/2231173185/Java
[quickstart]: https://clearblade.atlassian.net/wiki/spaces/IC/pages/2352611329/Java+SDK+quick+start