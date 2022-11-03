package com.clearblade.cloud.iot.v1.utils;

public class RegistryName {
	
	private final String project;
	private final String location;
	private final String registry;
	
	protected RegistryName() {
		project = null;
		location = null;
		registry = null;
	}

	private RegistryName(Builder builder) {
		project = builder.getProject();
		location = builder.getLocation();
		registry = builder.getRegistry();
	}

	public String getProject() {
		return project;
	}

	public String getLocation() {
		return location;
	}

	public String getRegistry() {
		return registry;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public Builder toBuilder() {
		return new Builder(this);
	}

	public static RegistryName of(String project, String location, String registry) {
		return newBuilder().setProject(project).setLocation(location).setRegistry(registry).build();
	}

	public static String format(String project, String location, String registry) {
		return newBuilder().setProject(project).setLocation(location).setRegistry(registry).build()
				.toString();
	}

	/**
	 * Builder for
	 * projects/{project}/locations/{location}/registries/{registry}.
	 */
	public static class Builder {
		private String project;
		private String location;
		private String registry;

		protected Builder() {
		}

		public String getProject() {
			return project;
		}

		public String getLocation() {
			return location;
		}

		public String getRegistry() {
			return registry;
		}

		public Builder setProject(String project) {
			this.project = project;
			return this;
		}

		public Builder setLocation(String location) {
			this.location = location;
			return this;
		}

		public Builder setRegistry(String registry) {
			this.registry = registry;
			return this;
		}

		private Builder(RegistryName registryName) {
			this.project = registryName.project;
			this.location = registryName.location;
			this.registry = registryName.registry;
		}

		public RegistryName build() {
			return new RegistryName(this);
		}
	}

}
