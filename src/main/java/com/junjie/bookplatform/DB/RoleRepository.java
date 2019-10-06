/*
 * Copyright (c)-- Created By JUNJIE Lin -> On 2019/10/6
 */

package com.junjie.bookplatform.DB;

import com.junjie.bookplatform.Model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role,Long> {
    Role findByName(String role_name);
}
