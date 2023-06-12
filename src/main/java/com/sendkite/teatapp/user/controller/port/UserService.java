package com.sendkite.teatapp.user.controller.port;

import com.sendkite.teatapp.user.domain.User;
import com.sendkite.teatapp.user.domain.UserCreate;
import com.sendkite.teatapp.user.domain.UserUpdate;

public interface UserService {

    User getByEmail(String email);

    User getById(long id);

    User create(UserCreate userCreate);

    User update(long id, UserUpdate userUpdate);

    void login(long id);

    void verifyEmail(long id, String certificationCode);
}
