package com.sendkite.teatapp.post.service.port;

import com.sendkite.teatapp.post.infrastructure.PostEntity;
import java.util.Optional;

public interface PostRepository {

    Optional<PostEntity> findById(long id);

    PostEntity save(PostEntity postEntity);
}