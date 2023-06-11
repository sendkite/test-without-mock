package com.sendkite.teatapp.user.service;

import com.sendkite.teatapp.common.domain.exception.ResourceNotFoundException;
import com.sendkite.teatapp.common.service.port.ClockHolder;
import com.sendkite.teatapp.common.service.port.UuidHolder;
import com.sendkite.teatapp.user.domain.User;
import com.sendkite.teatapp.user.domain.UserCreate;
import com.sendkite.teatapp.user.domain.UserStatus;
import com.sendkite.teatapp.user.domain.UserUpdate;
import com.sendkite.teatapp.user.service.port.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Builder
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CertificationService certificationService;
    private final UuidHolder uuidHolder;
    private final ClockHolder clockHolder;


    public User getByEmail(String email) {
        return userRepository.findByEmailAndStatus(email, UserStatus.ACTIVE)
            .orElseThrow(() -> new ResourceNotFoundException("Users", email));
    }

    public User getById(long id) {
        return userRepository.findByIdAndStatus(id, UserStatus.ACTIVE)
            .orElseThrow(() -> new ResourceNotFoundException("Users", id));
    }

    @Transactional
    public void login(long id) {
        User user = userRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Users", id)
        );
        user = user.login(clockHolder);
        userRepository.save(user);
    }

    @Transactional
    public void verifyEmail(long id, String certificationCode) {
        User user = userRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Users", id)
        );
        user = user.certificate(certificationCode);
        userRepository.save(user);
    }

    @Transactional
    public User create(UserCreate userCreate) {
        User user = User.from(userCreate, uuidHolder);
        user = userRepository.save(user);
        certificationService.send(userCreate.getEmail(), user.getId(), user.getCertificationCode());
        return user;
    }

    @Transactional
    public User update(long id, UserUpdate userUpdate) {
        User user = getById(id);
        user = user.update(userUpdate);
        user = userRepository.save(user);
        return user;
    }
}
