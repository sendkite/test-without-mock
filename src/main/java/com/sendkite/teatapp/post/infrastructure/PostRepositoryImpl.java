package com.sendkite.teatapp.post.infrastructure;

import com.sendkite.teatapp.post.domain.Post;
import com.sendkite.teatapp.post.service.port.PostRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private final PostJpaRepository postJpaRepository;

    @Override
    public Optional<Post> findById(long id) {
        return postJpaRepository.findById(id).map(PostEntity::toDomain);
    }

    @Override
    public Post save(Post post) {
        return postJpaRepository.save(PostEntity.fromDomain(post)).toDomain();
    }
}
