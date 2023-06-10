package com.sendkite.teatapp.post.infrastructure;

import com.sendkite.teatapp.post.service.port.PostRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private final PostJpaRepository postJpaRepository;

    @Override
    public Optional<PostEntity> findById(long id) {
        return postJpaRepository.findById(id);
    }

    @Override
    public PostEntity save(PostEntity postEntity) {
        return postJpaRepository.save(postEntity);
    }
}
