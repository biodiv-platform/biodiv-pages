package com.strandls.pages.util;

import javax.servlet.http.HttpServletRequest;

import org.pac4j.core.profile.CommonProfile;

import com.strandls.authentication_utility.util.AuthUtil;

import net.minidev.json.JSONArray;

public class AuthUtility {

	public static boolean isAdmin(HttpServletRequest request) {
		CommonProfile profile = AuthUtil.getProfileFromRequest(request);
		JSONArray roles = (JSONArray) profile.getAttribute("roles");
		if (roles.contains("ROLE_ADMIN"))
			return true;
		return false;
	}
}
