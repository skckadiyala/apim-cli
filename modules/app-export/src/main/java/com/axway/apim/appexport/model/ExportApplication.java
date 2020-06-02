package com.axway.apim.appexport.model;

import java.util.List;

import com.axway.apim.api.model.APIQuota;
import com.axway.apim.api.model.Image;
import com.axway.apim.api.model.apps.ClientAppCredential;
import com.axway.apim.api.model.apps.ClientApplication;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "name", "organization", "description", "state", "image", "enabled", "email", "phone", "credentials", "appQuota" })
public class ExportApplication {
	
	ClientApplication clientApp;

	public ExportApplication(ClientApplication clientApp) {
		super();
		this.clientApp = clientApp;
	}
	
	public String getOrganization() {
		return this.clientApp.getOrganization().getName();
	}

	public String getName() {
		return clientApp.getName();
	}

	public List<ClientAppCredential> getCredentials() {
		return clientApp.getCredentials();
	}

	public String getDescription() {
		return clientApp.getDescription();
	}

	public String getEmail() {
		return clientApp.getEmail();
	}


	public String getPhone() {
		return clientApp.getPhone();
	}

	public boolean isEnabled() {
		return clientApp.isEnabled();
	}

	public String getState() {
		return clientApp.getState();
	}

	public Image getImage() {
		return clientApp.getImage();
	}

	public APIQuota getAppQuota() {
		return clientApp.getAppQuota();
	}
	
	
}