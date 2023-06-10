package com.sendkite.teatapp.user.service.port;

import com.sendkite.teatapp.user.domain.User;
import com.sendkite.teatapp.user.domain.UserStatus;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findByIdAndStatus(long id, UserStatus userStatus);

    Optional<User> findByEmailAndStatus(String email, UserStatus userStatus);

    User save(User userEntity);

    Optional<User> findById(long id);
}
