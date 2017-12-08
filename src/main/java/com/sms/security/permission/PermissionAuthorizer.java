package com.sms.security.permission;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sms.domain.Role;

@Component
public class PermissionAuthorizer {
	public boolean authorize(Role role, Permission[] permissions) throws JsonParseException, JsonMappingException, IOException {
		return role.getRolePermissions().containsAll(Arrays.asList(permissions));
	}
	
	public boolean authorize(Role role, Permission permission) throws JsonParseException, JsonMappingException, IOException {
		return authorize(role, new Permission[] {permission});
	}
}
