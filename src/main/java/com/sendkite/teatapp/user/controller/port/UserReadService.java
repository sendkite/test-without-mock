package com.sendkite.teatapp.user.controller.port;

import com.sendkite.teatapp.user.domain.User;

public interface UserReadService {

    User getByEmail(String email);

    User getById(long id);
}
