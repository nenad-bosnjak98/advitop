package com.njt.advitop.service;

import com.njt.advitop.exceptions.AdvitopException;
import com.njt.advitop.exceptions.PostNotFoundException;
import com.njt.advitop.mapper.CommentMapper;
import com.njt.advitop.model.Comment;
import com.njt.advitop.model.NotificationEmail;
import com.njt.advitop.model.Post;
import com.njt.advitop.model.User;
import com.njt.advitop.repository.CommentRepository;
import com.njt.advitop.repository.PostRepository;
import com.njt.advitop.repository.UserRepository;
import com.njt.advitop.transferobject.CommentObject;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class CommentService {
    private static final String POST_URL = "";
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;
    private final AuthenticationService authenticationService;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    public void save(CommentObject commentObject) {
        Post post = postRepository.findById(commentObject.getPostID())
                .orElseThrow(()-> new PostNotFoundException("Post with ID number " + commentObject.getPostID().toString() + " was not found!"));
        Comment comment = commentMapper.map(commentObject, post, authenticationService.getCurrentUser());
        commentRepository.save(comment);

        String message = post.getUser().getUsername() + " posted a comment on your post!";
        sendCommentNotificationMessage(message,post.getUser());
    }

    private void sendCommentNotificationMessage(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " commented on your post!", user.getEmail(), message));
    }

    public List<CommentObject> getAllCommentsForPost(Long postID) {
        Post post = postRepository.findById(postID)
                .orElseThrow(()-> new PostNotFoundException("Post with ID number " + postID + " was not found!"));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDTO).collect(toList());
    }

    public List<CommentObject> getAllCommentsForUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User with username" + username + " was not found!"));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDTO).collect(toList());
    }

    public boolean containsSwearWords(String comment) {
        if (comment.contains("shit")) {
            throw new AdvitopException("Comments contains unacceptable language!");
        }
        if (comment.contains("fuck")) {
            throw new AdvitopException("Comments contains unacceptable language!");
        }
        if (comment.contains("hell")) {
            throw new AdvitopException("Comments contains unacceptable language!");
        }
        return false;
    }
}
