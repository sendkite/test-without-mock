package com.sendkite.teatapp.mock;

import com.sendkite.teatapp.user.domain.User;
import com.sendkite.teatapp.user.domain.UserStatus;
import com.sendkite.teatapp.user.service.port.UserRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakeUserRepository implements UserRepository {

    private final AtomicLong idGenerator = new AtomicLong(0);
    private final List<User> data = Collections.synchronizedList(new ArrayList<>());

    @Override
    public Optional<User> findById(long id) {
        return data.stream()
            .filter(u -> u.getId().equals(id))
            .findAny();
    }

    @Override
    public Optional<User> findByIdAndStatus(long id, UserStatus userStatus) {
        return data.stream()
            .filter(u -> u.getId().equals(id) && u.getStatus().equals(userStatus))
            .findAny();
    }

    @Override
    public Optional<User> findByEmailAndStatus(String email, UserStatus userStatus) {
        return data.stream()
            .filter(u -> u.getEmail().equals(email) && u.getStatus().equals(userStatus))
            .findAny();
    }

    @Override
    public User save(User user) {
        if (user.getId() == null || user.getId() == 0) {
            User newUser = User.builder()
                .id(idGenerator.incrementAndGet())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .address(user.getAddress())
                .status(user.getStatus())
                .certificationCode(user.getCertificationCode())
                .lastLoginAt(user.getLastLoginAt())
                .build();
            data.add(newUser);
            return newUser;
        } else {
            data.removeIf(u -> Objects.equals(u.getId(), user.getId()));
            data.add(user);
            return user;
        }
    }
}
