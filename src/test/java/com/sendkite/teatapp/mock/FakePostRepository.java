package com.sendkite.teatapp.mock;

import com.sendkite.teatapp.post.domain.Post;
import com.sendkite.teatapp.post.service.port.PostRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakePostRepository implements PostRepository {

    private final AtomicLong idGenerator = new AtomicLong(0);
    private final List<Post> data = Collections.synchronizedList(new ArrayList<>());

    @Override
    public Optional<Post> findById(long id) {
        return data.stream()
            .filter(u -> u.getId().equals(id))
            .findAny();
    }

    @Override
    public Post save(Post post) {
        if (post.getId() == null || post.getId() == 0) {
            Post newPost = Post.builder()
                .id(idGenerator.incrementAndGet())
                .writer(post.getWriter())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .build();
            data.add(newPost);
            return newPost;
        } else {
            data.removeIf(u -> u.getId().equals(post.getId()));
            data.add(post);
            return post;
        }
    }
}
