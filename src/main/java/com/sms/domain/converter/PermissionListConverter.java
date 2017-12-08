package com.sms.domain.converter;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.sms.security.permission.Permission;

@Converter
public class PermissionListConverter implements AttributeConverter<List<Permission>, Long> {
	@Override
	public Long convertToDatabaseColumn(List<Permission> permissions) {
		Long rolePermissions = new Long(0);
		
		for(Permission permission : permissions) {
			rolePermissions += permission.ordinal();
		}
		
		return rolePermissions;
	}

	@Override
	public List<Permission> convertToEntityAttribute(Long rolePermissions) {
		List<Permission> permissions = new ArrayList<Permission>();
		
		for(Permission permission : Permission.values()) {
			if((rolePermissions | permission.ordinal()) == permission.ordinal()) {
				permissions.add(permission);
			}
		}
		
		return permissions;
	}
}
