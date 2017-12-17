package com.sms.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.sms.domain.converter.PermissionListConverter;
import com.sms.security.permission.Permission;

@Entity
public class Role {
	@Id
	private Integer roleId;
	
	@Column
	private String roleName;
	
	@Column
	@Convert(converter = PermissionListConverter.class)
	private List<Permission> rolePermissions;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<Permission> getRolePermissions() {
		return rolePermissions;
	}

	public void setRolePermissions(List<Permission> rolePermissions) {
		this.rolePermissions = rolePermissions;
	}
}
