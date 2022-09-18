package com.njt.advitop.controller;

import com.njt.advitop.service.PostService;
import com.njt.advitop.transferobject.PostRequest;
import com.njt.advitop.transferobject.PostResponse;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/posts/")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody PostRequest postRequest) {
        postService.save(postRequest);
        return new ResponseEntity<>(CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return status(OK).body(postService.getAllPosts());
    }

    @GetMapping("/{ID}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long ID) {
        return status(OK).body(postService.getPost(ID));
    }

    @GetMapping("/by-category/{ID}")
    public ResponseEntity<List<PostResponse>> getPostsByCategory(@PathVariable Long ID) {
        return status(OK).body(postService.getPostsByCategory(ID));
    }

    @GetMapping("/by-user/{username}")
    public ResponseEntity<List<PostResponse>> getPostsByUsername(@PathVariable String username) {
        return status(OK).body(postService.getPostsByUsername(username));
    }


}
