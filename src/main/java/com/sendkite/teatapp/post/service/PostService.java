package com.sendkite.teatapp.post.service;

import com.sendkite.teatapp.common.domain.exception.ResourceNotFoundException;
import com.sendkite.teatapp.post.domain.Post;
import com.sendkite.teatapp.post.domain.PostCreate;
import com.sendkite.teatapp.post.domain.PostUpdate;
import com.sendkite.teatapp.post.service.port.PostRepository;
import com.sendkite.teatapp.user.domain.User;
import com.sendkite.teatapp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public Post getById(long id) {
        return postRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Posts", id));
    }

    public Post create(PostCreate postCreate) {
        User user = userService.getById(postCreate.getWriterId());
        return postRepository.save(Post.from(user, postCreate));
    }

    public Post update(long id, PostUpdate postUpdate) {
        Post post = getById(id);
        post = post.update(postUpdate);
        return postRepository.save(post);
    }
}
