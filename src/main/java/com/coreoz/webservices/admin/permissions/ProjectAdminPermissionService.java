package com.coreoz.webservices.admin.permissions;

import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.coreoz.plume.admin.services.permission.LogApiAdminPermissions;
import com.coreoz.plume.admin.services.permission.SystemAdminPermissions;
import com.coreoz.plume.admin.services.permissions.AdminPermissionService;
import com.coreoz.plume.admin.services.permissions.AdminPermissions;
import com.google.common.collect.ImmutableSet;

@Singleton
public class ProjectAdminPermissionService implements AdminPermissionService {

	private final Set<String> permissionsAvailable;

	@Inject
	public ProjectAdminPermissionService() {
		this.permissionsAvailable = ImmutableSet.of(
			AdminPermissions.MANAGE_USERS,
			AdminPermissions.MANAGE_ROLES,
			LogApiAdminPermissions.MANAGE_API_LOGS,
			SystemAdminPermissions.MANAGE_SYSTEM
		);
	}

	@Override
	public Set<String> permissionsAvailable() {
		return permissionsAvailable;
	}

}
