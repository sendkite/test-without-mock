package com.sendkite.teatapp.service;

import com.sendkite.teatapp.exception.ResourceNotFoundException;
import com.sendkite.teatapp.model.dto.PostCreateDto;
import com.sendkite.teatapp.model.dto.PostUpdateDto;
import com.sendkite.teatapp.repository.PostEntity;
import com.sendkite.teatapp.repository.PostRepository;
import com.sendkite.teatapp.repository.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public PostEntity getPostById(long id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Posts", id));
    }

    public PostEntity createPost(PostCreateDto postCreateDto) {
        UserEntity userEntity = userService.getByIdOrElseThrow(postCreateDto.getWriterId());
        PostEntity postEntity = new PostEntity();
        postEntity.setWriter(userEntity);
        postEntity.setContent(postCreateDto.getContent());
        postEntity.setCreatedAt(Clock.systemUTC().millis());
        return postRepository.save(postEntity);
    }

    public PostEntity updatePost(long id, PostUpdateDto postUpdateDto) {
        PostEntity postEntity = getPostById(id);
        postEntity.setContent(postUpdateDto.getContent());
        postEntity.setModifiedAt(Clock.systemUTC().millis());
        return postRepository.save(postEntity);
    }


}
