package com.sms.domain;

import java.io.IOException;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
public class Role {
	@Id
	private Long roleId;
	
	@Column
	private String roleName;
	
	@Column
	private String rolePermissions;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<Integer> getRolePermissions() throws JsonParseException, JsonMappingException, IOException {
		return new ObjectMapper().readValue(rolePermissions, List.class);
	}
	
	public void addRolePermission(Integer rolePermission) throws JsonParseException, JsonMappingException, IOException {
		List<Integer> rolePermissions = getRolePermissions();
		
		rolePermissions.add(rolePermission);
		
		setRolePermissions(rolePermissions);
	}

	public void setRolePermissions(List<Integer> rolePermissions) throws JsonProcessingException {
		this.rolePermissions = new ObjectMapper().writeValueAsString(rolePermissions);
	}
}
