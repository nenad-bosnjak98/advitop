package com.njt.advitop.controller;

import com.njt.advitop.service.CommentService;
import com.njt.advitop.transferobject.CommentObject;
import lombok.AllArgsConstructor;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments/")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentObject commentObject) {
        commentService.save(commentObject);
        return new ResponseEntity<>(CREATED);
    }

    @GetMapping("/by-post/{postID}")
    public ResponseEntity<List<CommentObject>> getAllCommentsForPost(@PathVariable Long postID) {
        return ResponseEntity.status(OK).body(commentService.getAllCommentsForPost(postID));
    }

    @GetMapping("/by-user/{username}")
    public ResponseEntity<List<CommentObject>> getAllCommentsForUser(@PathVariable String username) {
        return ResponseEntity.status(OK).body(commentService.getAllCommentsForUser(username));
    }

}
