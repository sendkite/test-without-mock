package com.sendkite.teatapp.post.controller.port;

import com.sendkite.teatapp.post.domain.Post;
import com.sendkite.teatapp.post.domain.PostCreate;
import com.sendkite.teatapp.post.domain.PostUpdate;

public interface PostService {

    Post getById(long id);

    Post create(PostCreate postCreate);

    Post update(long id, PostUpdate postUpdate);
}
