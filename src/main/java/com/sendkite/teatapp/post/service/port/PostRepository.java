package com.sendkite.teatapp.post.service.port;

import com.sendkite.teatapp.post.domain.Post;
import java.util.Optional;

public interface PostRepository {

    Optional<Post> findById(long id);

    Post save(Post postEntity);
}
