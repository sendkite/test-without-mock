package com.sendkite.teatapp.mock;

import com.sendkite.teatapp.common.service.port.ClockHolder;
import com.sendkite.teatapp.common.service.port.UuidHolder;
import com.sendkite.teatapp.post.service.PostServiceImpl;
import com.sendkite.teatapp.post.service.port.PostRepository;
import com.sendkite.teatapp.user.controller.UserController;
import com.sendkite.teatapp.user.controller.UserCreateController;
import com.sendkite.teatapp.user.controller.port.AuthenticationService;
import com.sendkite.teatapp.user.controller.port.UserCreateService;
import com.sendkite.teatapp.user.controller.port.UserReadService;
import com.sendkite.teatapp.user.controller.port.UserUpdateService;
import com.sendkite.teatapp.user.service.CertificationService;
import com.sendkite.teatapp.user.service.UserServiceImpl;
import com.sendkite.teatapp.user.service.port.MailSender;
import com.sendkite.teatapp.user.service.port.UserRepository;
import lombok.Builder;

public class TestContainer {

    public final MailSender mailSender;
    public final UserController userController;
    public final UserCreateController userCreateController;
    public final UserRepository userRepository;
    public final PostRepository postRepository;
    public final PostServiceImpl postService;
    public final UserReadService userReadService;
    public final UserCreateService userCreateService;
    public final UserUpdateService userUpdateService;
    public final AuthenticationService authenticationService;
    public final CertificationService certificationService;

    private TestContainer(ClockHolder clockHolder, UuidHolder uuidHolder) {
        this.mailSender = new FakeMailSender();
        this.userRepository = new FakeUserRepository();
        this.postRepository = new FakePostRepository();
        this.postService = PostServiceImpl.builder()
            .postRepository(this.postRepository)
            .userRepository(this.userRepository)
            .clockHolder(clockHolder)
            .build();
        this.certificationService = new CertificationService(this.mailSender);
        UserServiceImpl userService = UserServiceImpl.builder()
            .uuidHolder(uuidHolder)
            .clockHolder(clockHolder)
            .userRepository(this.userRepository)
            .certificationService(this.certificationService)
            .build();
        this.userController = UserController.builder()
            .userReadService(userService)
            .userUpdateService(userService)
            .authenticationService(userService)
            .build();
        this.userCreateController = UserCreateController.builder()
            .userCreateService(userService)
            .build();
        this.userReadService = userService;
        this.userCreateService = userService;
        this.userUpdateService = userService;
        this.authenticationService = userService;

    }

    public static TestContainer create() {
        return new TestContainer(new TestClockHolder(123123L),
            new TestUuidHolder("aaaa-aaaaa-aaaaa-aaaaaab"));
    }
}
