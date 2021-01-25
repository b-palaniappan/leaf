package io.c12.bala.web.leaf.service;

import io.c12.bala.web.leaf.entity.UserEntity;
import io.c12.bala.web.leaf.form.RegisterUser;

public interface UserService {

    UserEntity saveUser(RegisterUser user);
}
