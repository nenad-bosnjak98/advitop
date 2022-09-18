package com.njt.advitop.mapper;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.njt.advitop.model.*;
import com.njt.advitop.repository.CommentRepository;
import com.njt.advitop.repository.VoteRepository;
import com.njt.advitop.service.AuthenticationService;
import com.njt.advitop.transferobject.PostRequest;
import com.njt.advitop.transferobject.PostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import static com.njt.advitop.model.VoteType.UPVOTE;
import static com.njt.advitop.model.VoteType.DOWNVOTE;
import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private AuthenticationService authenticationService;

    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "voteCount", constant = "0")
    @Mapping(target = "user", source = "user")
    public abstract Post map(PostRequest postRequest, Category category, User user);

    @Mapping(target = "ID", source = "postID")
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "commentCount", expression = "java(commentCount(post))")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
    @Mapping(target = "upVote", expression = "java(isPostUpVoted(post))")
    @Mapping(target = "downVote", expression = "java(isPostDownVoted(post))")
    public abstract PostResponse mapToDTO(Post post);

    Integer commentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }

    String getDuration(Post post) {
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }

    boolean isPostUpVoted(Post post) {
        return checkVoteType(post, UPVOTE);
    }

    boolean isPostDownVoted(Post post) {
        return checkVoteType(post, DOWNVOTE);
    }

    public boolean checkVoteType(Post post, VoteType voteType) {
        if(authenticationService.isLoggedIn()) {
            Optional<Vote> voteForPost = voteRepository.findTopByPostAndUserOrderByVoteIDDesc(post, authenticationService.getCurrentUser());
            return voteForPost.filter(vote -> vote.getVoteType().equals(voteType)).isPresent();
        }
        return false;
    }







}
