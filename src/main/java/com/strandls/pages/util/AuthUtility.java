package com.strandls.pages.util;

import javax.servlet.http.HttpServletRequest;

import org.pac4j.core.profile.CommonProfile;

import com.strandls.authentication_utility.util.AuthUtil;
import com.strandls.user.pojo.Role;
import com.strandls.user.pojo.User;

import net.minidev.json.JSONArray;

public class AuthUtility {

	private AuthUtility() {
	}

	public static boolean isAdmin(HttpServletRequest request) {
		CommonProfile profile = AuthUtil.getProfileFromRequest(request);
		JSONArray roles = (JSONArray) profile.getAttribute("roles");
		return roles.contains("ROLE_ADMIN");
	}

	public static boolean isAdmin(User user) {
		for(Role role : user.getRoles()) {
			if(role.getAuthority().equals("ROLE_ADMIN")) {
				return true;
			}
		}
		return false;
	}
}
