package com.clearblade.cloud.iot.v1.utils;

public enum GatewayAuthMethod {
	
	GATEWAY_AUTH_METHOD_UNSPECIFIED(0),
	ASSOCIATION_ONLY(1),
	DEVICE_AUTH_TOKEN_ONLY(2),
	ASSOCIATION_AND_DEVICE_AUTH_TOKEN(3),
	UNRECOGNIZED(-1),
	  ;

	  public static final int GATEWAY_AUTH_METHOD_UNSPECIFIED_VALUE = 0;
	  public static final int ASSOCIATION_ONLY_VALUE = 1;
	  public static final int DEVICE_AUTH_TOKEN_ONLY_VALUE = 2;
	  public static final int ASSOCIATION_AND_DEVICE_AUTH_TOKEN_VALUE = 3;


	  public final int getNumber() {
	    if (this == UNRECOGNIZED) {
	      throw new java.lang.IllegalArgumentException(
	          "Can't get the number of an unknown enum value.");
	    }
	    return value;
	  }

	  /**
	   * @param value The numeric wire value of the corresponding enum entry.
	   * @return The enum associated with the given numeric wire value.
	   */
	  public static GatewayAuthMethod valueOf(int value) {
	    return forNumber(value);
	  }

	  /**
	   * @param value The numeric wire value of the corresponding enum entry.
	   * @return The enum associated with the given numeric wire value.
	   */
	  public static GatewayAuthMethod forNumber(int value) {
	    switch (value) {
	      case 0:
	        return GATEWAY_AUTH_METHOD_UNSPECIFIED;
	      case 1:
	        return ASSOCIATION_ONLY;
	      case 2:
	        return DEVICE_AUTH_TOKEN_ONLY;
	      case 3:
		        return ASSOCIATION_AND_DEVICE_AUTH_TOKEN;
	      default:
	        return null;
	    }
	  }

	    public GatewayAuthMethod findValueByNumber(int number) {
	      return GatewayAuthMethod.forNumber(number);
	    }
	  
	  private final int value;

	  private GatewayAuthMethod(int value) {
	    this.value = value;
	  }

}
