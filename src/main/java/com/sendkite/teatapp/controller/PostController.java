package com.sendkite.teatapp.controller;

import com.sendkite.teatapp.model.dto.PostResponse;
import com.sendkite.teatapp.model.dto.PostUpdateDto;
import com.sendkite.teatapp.repository.PostEntity;
import com.sendkite.teatapp.service.PostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "게시물(posts)")
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserController userController;

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable long id) {
        return ResponseEntity
                .ok()
                .body(toResponse(postService.getPostById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable long id, @RequestBody PostUpdateDto postUpdateDto) {
        return ResponseEntity
                .ok()
                .body(toResponse(postService.updatePost(id, postUpdateDto)));
    }

    public PostResponse toResponse(PostEntity postEntity) {
        PostResponse PostResponse = new PostResponse();
        PostResponse.setId(postEntity.getId());
        PostResponse.setContent(postEntity.getContent());
        PostResponse.setCreatedAt(postEntity.getCreatedAt());
        PostResponse.setModifiedAt(postEntity.getModifiedAt());
        PostResponse.setWriter(userController.toResponse(postEntity.getWriter()));
        return PostResponse;
    }

}
