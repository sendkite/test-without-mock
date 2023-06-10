package com.sendkite.teatapp.user.service.port;

import com.sendkite.teatapp.user.domain.UserStatus;
import com.sendkite.teatapp.user.infrastructure.UserEntity;
import java.util.Optional;

public interface UserRepository {

    Optional<UserEntity> findByIdAndStatus(long id, UserStatus userStatus);

    Optional<UserEntity> findByEmailAndStatus(String email, UserStatus userStatus);

    UserEntity save(UserEntity userEntity);

    Optional<UserEntity> findById(long id);
}
