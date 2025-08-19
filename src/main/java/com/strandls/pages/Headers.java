package com.strandls.pages;

import com.strandls.activity.controller.ActivityServiceApi;

import jakarta.ws.rs.core.HttpHeaders;

public class Headers {

	public ActivityServiceApi addActivityHeaders(ActivityServiceApi activityService, String authHeader) {
		activityService.getApiClient().addDefaultHeader(HttpHeaders.AUTHORIZATION, authHeader);
		return activityService;
	}
}
