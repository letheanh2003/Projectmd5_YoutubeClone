package rikkei.academy.service;

import rikkei.academy.model.RoleName;
import rikkei.academy.model.Roles;

import java.util.Optional;

public interface IRoleService extends IGenericService<Roles,Long> {
    Roles findByRoleName(RoleName roleName);
}
