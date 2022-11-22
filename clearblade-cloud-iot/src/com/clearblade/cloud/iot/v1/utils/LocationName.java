package com.clearblade.cloud.iot.v1.utils;

public class LocationName {

	private final String project;
	private final String location;

	protected LocationName() {
		project = null;
		location = null;
	}

	private LocationName(Builder builder) {
		project = builder.getProject();
		location = builder.getLocation();
	}

	public String getProject() {
		return project;
	}

	public String getLocation() {
		return location;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public Builder toBuilder() {
		return new Builder(this);
	}

	public static LocationName of(String project, String location) {
		return newBuilder().setProject(project).setLocation(location).build();
	}

	public static String format(String project, String location) {
		return newBuilder().setProject(project).setLocation(location).build()
				.toString();
	}

	/**
	 * Builder for
	 * projects/{project}/locations/{location}/registries/{registry}.
	 */
	public static class Builder {
		private String project;
		private String location;

		protected Builder() {
		}

		public String getProject() {
			return project;
		}

		public String getLocation() {
			return location;
		}

		public Builder setProject(String project) {
			this.project = project;
			return this;
		}

		public Builder setLocation(String location) {
			this.location = location;
			return this;
		}

		private Builder(LocationName locationName) {
			this.project = locationName.project;
			this.location = locationName.location;
		}

		public LocationName build() {
			return new LocationName(this);
		}
	}

	@Override
	public String toString() {
		return "projects/" + this.getProject() + "/locations/" + this.getLocation();
	}

	public String getLocationFullName() {
		return "projects/" + this.getProject() + "/locations/" + this.getLocation();
	}
}
