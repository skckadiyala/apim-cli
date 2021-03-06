package com.axway.apim.api.export.impl;

import java.util.List;

import com.axway.apim.adapter.APIStatusManager;
import com.axway.apim.adapter.apis.APIFilter;
import com.axway.apim.api.API;
import com.axway.apim.api.export.lib.APIExportParams;
import com.axway.apim.lib.errorHandling.AppException;

public class UnpublishAPIHandler extends APIResultHandler {

	public UnpublishAPIHandler(APIExportParams params) {
		super(params);
	}

	@Override
	public void execute(List<API> apis) throws AppException {
		APIStatusManager statusManager = new APIStatusManager();
		System.out.println(apis.size() + " selected to unpublish.");
		if(askYesNo("Do you wish to proceed? (Y/N)")) {
			System.out.println("Okay, going to unpublish: " + apis.size() + " API(s)");
			for(API api : apis) {
				try {
					statusManager.update(api, API.STATE_UNPUBLISHED, true);
				} catch(Exception e) {
					LOG.error("Error unpublishing API: " + api.getName());
				}
			}
			System.out.println("Done!");
		}
	}

	@Override
	public APIFilter getFilter() {
		return getBaseAPIFilterBuilder().build();
	}

}
