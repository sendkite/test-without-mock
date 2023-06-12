package com.sendkite.teatapp.user.controller.port;

import com.sendkite.teatapp.user.domain.User;
import com.sendkite.teatapp.user.domain.UserCreate;
import com.sendkite.teatapp.user.domain.UserUpdate;

public interface UserCreateService {

    User create(UserCreate userCreate);
}
