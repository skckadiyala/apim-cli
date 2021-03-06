package com.axway.apim.api.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.axway.apim.adapter.APIManagerAdapter;
import com.axway.apim.adapter.apis.jackson.PolicyDeserializer;
import com.axway.apim.adapter.apis.jackson.PolicySerializer;
import com.axway.apim.lib.errorHandling.AppException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class OutboundProfile extends Profile {
	
	protected static Logger LOG = LoggerFactory.getLogger(OutboundProfile.class);
	
	String routeType;
	
	@JsonDeserialize( using = PolicyDeserializer.class)
	@JsonSerialize ( using = PolicySerializer.class)
	Policy requestPolicy;
	
	@JsonDeserialize( using = PolicyDeserializer.class)
	@JsonSerialize ( using = PolicySerializer.class)
	Policy responsePolicy;
	
	@JsonDeserialize( using = PolicyDeserializer.class)
	@JsonSerialize ( using = PolicySerializer.class)
	Policy routePolicy;
	
	@JsonDeserialize( using = PolicyDeserializer.class)
	@JsonSerialize ( using = PolicySerializer.class)
	Policy faultHandlerPolicy;
	
	String authenticationProfile;
	
	List<Object> parameters = new ArrayList<Object>();

	public OutboundProfile() throws AppException {
		super();
	}

	public String getAuthenticationProfile() {
		return this.authenticationProfile;
	}

	public void setAuthenticationProfile(String authenticationProfile) {
		this.authenticationProfile = authenticationProfile;
	}

	public String getRouteType() {
		if(this.routePolicy!=null && !this.routePolicy.equals("")) {
			return "policy";
		} else {
			return "proxy";
		}
	}

	public void setRouteType(String routeType) {
		this.routeType = routeType;
	}

	public Policy getRequestPolicy() {
		return requestPolicy;
	}
	
	public void setRequestPolicy(Policy requestPolicy) throws AppException {
		this.requestPolicy = requestPolicy;
	}

	public Policy getResponsePolicy() {
		return responsePolicy;
	}

	public void setResponsePolicy(Policy responsePolicy) throws AppException {
		this.responsePolicy = responsePolicy;
	}

	public Policy getRoutePolicy() {
		return routePolicy;
	}

	public void setRoutePolicy(Policy routePolicy) throws AppException {
		this.routePolicy = routePolicy;
	}

	public Policy getFaultHandlerPolicy() {
		return faultHandlerPolicy;
	}

	public void setFaultHandlerPolicy(Policy faultHandlerPolicy) throws AppException {
		this.faultHandlerPolicy = faultHandlerPolicy;
	}

	public List<Object> getParameters() {
		return parameters;
	}
	
	@JsonIgnore
	public List<Policy> getAllPolices() {
		List<Policy> usedPolicies = new ArrayList<Policy>();
		if(this.requestPolicy!=null) usedPolicies.add(this.requestPolicy);
		if(this.routePolicy!=null) usedPolicies.add(this.routePolicy);
		if(this.responsePolicy!=null) usedPolicies.add(this.responsePolicy);
		if(this.faultHandlerPolicy!=null) usedPolicies.add(this.faultHandlerPolicy);
		return usedPolicies;
	}

	@SuppressWarnings("unchecked")
	public void setParameters(List<Object> parameters) {
		if(APIManagerAdapter.hasAPIManagerVersion("7.7.20200130")) {
			// We need to inject the format as default
			for(Object params : parameters) {
				if(params instanceof Map<?, ?>) {
					if(!((Map<?, ?>)params).containsKey("format")) {
						((Map<String, ?>) params).put("format", null);
					}
				}
			}
		}
		this.parameters = parameters;
	}

	@Override
	public boolean equals(Object other) {
		if(other == null) return false;
		if(other instanceof OutboundProfile) {
			OutboundProfile otherOutboundProfile = (OutboundProfile)other;
			List<Object> otherParameters = otherOutboundProfile.getParameters();
			List<Object> thisParameters = this.getParameters();
			if(APIManagerAdapter.hasAPIManagerVersion("7.7 SP1") || APIManagerAdapter.hasAPIManagerVersion("7.6.2 SP5")) {
				// Passwords no longer exposed by API-Manager REST-API - Can't use it anymore to compare the state
				otherParameters.remove("password");
				thisParameters.remove("password");
			}
			boolean rc = 
				(this.getFaultHandlerPolicy()==null || this.getFaultHandlerPolicy().equals(otherOutboundProfile.getFaultHandlerPolicy())) &&
				(this.getRequestPolicy()==null || this.getRequestPolicy().equals(otherOutboundProfile.getRequestPolicy())) &&
				(this.getRequestPolicy()==null || this.getResponsePolicy().equals(otherOutboundProfile.getResponsePolicy())) &&
				(this.getRoutePolicy()==null || this.getRoutePolicy().equals(otherOutboundProfile.getRoutePolicy())) &&
				StringUtils.equals(otherOutboundProfile.getRouteType(), this.getRouteType()) &&
				otherParameters.equals(thisParameters);
			return rc;
		} else {
			return false;
		}
	}
}
