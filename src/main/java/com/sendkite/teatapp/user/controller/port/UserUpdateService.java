package com.sendkite.teatapp.user.controller.port;

import com.sendkite.teatapp.user.domain.User;
import com.sendkite.teatapp.user.domain.UserUpdate;

public interface UserUpdateService {

    User update(long id, UserUpdate userUpdate);
}
