package com.sms.security;

public class AccessAuthorizer {
	public static boolean authorize(Long roleId, AccessType[] accessType) {
		return false;
	}
	
	public static boolean authorize(Long roleId, AccessType accessType) {
		return authorize(roleId, new AccessType[] {accessType});
	}
}
