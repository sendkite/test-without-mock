package com.sendkite.teatapp.post.infrastructure;

import com.sendkite.teatapp.post.domain.Post;
import com.sendkite.teatapp.user.infrastructure.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "posts")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "modified_at")
    private Long modifiedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity writer;

    public static PostEntity fromDomain(Post post) {
        PostEntity postEntity = new PostEntity();
        postEntity.id = post.getId();
        postEntity.content = post.getContent();
        postEntity.createdAt = post.getCreatedAt();
        postEntity.modifiedAt = post.getModifiedAt();
        postEntity.writer = UserEntity.fromDomain(post.getWriter());
        return postEntity;
    }

    public Post toDomain() {
        return Post.builder()
            .id(id)
            .content(content)
            .createdAt(createdAt)
            .modifiedAt(modifiedAt)
            .writer(writer.toDomain())
            .build();
    }
}
