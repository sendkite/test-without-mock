package com.sendkite.teatapp.post.service;

import com.sendkite.teatapp.common.domain.exception.ResourceNotFoundException;
import com.sendkite.teatapp.post.domain.PostCreate;
import com.sendkite.teatapp.post.domain.PostUpdate;
import com.sendkite.teatapp.post.infrastructure.PostEntity;
import com.sendkite.teatapp.post.infrastructure.PostJpaRepository;
import com.sendkite.teatapp.post.service.port.PostRepository;
import com.sendkite.teatapp.user.infrastructure.UserEntity;
import com.sendkite.teatapp.user.service.UserService;
import java.time.Clock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public PostEntity getById(long id) {
        return postRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Posts", id));
    }

    public PostEntity create(PostCreate postCreate) {
        UserEntity userEntity = userService.getById(postCreate.getWriterId());
        PostEntity postEntity = new PostEntity();
        postEntity.setWriter(userEntity);
        postEntity.setContent(postCreate.getContent());
        postEntity.setCreatedAt(Clock.systemUTC().millis());
        return postRepository.save(postEntity);
    }

    public PostEntity update(long id, PostUpdate postUpdate) {
        PostEntity postEntity = getById(id);
        postEntity.setContent(postUpdate.getContent());
        postEntity.setModifiedAt(Clock.systemUTC().millis());
        return postRepository.save(postEntity);
    }
}
