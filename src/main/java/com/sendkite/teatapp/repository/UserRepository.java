package com.sendkite.teatapp.repository;

import com.sendkite.teatapp.model.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

        Optional<UserEntity> findByIdAndStatus(long id, UserStatus userStatus);

        Optional<UserEntity> findByEmailAndStatus(String email, UserStatus userStatus);
}
