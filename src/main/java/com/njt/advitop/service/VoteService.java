package com.njt.advitop.service;

import com.njt.advitop.exceptions.AdvitopException;
import com.njt.advitop.exceptions.PostNotFoundException;
import com.njt.advitop.model.Post;
import com.njt.advitop.model.Vote;
import com.njt.advitop.repository.PostRepository;
import com.njt.advitop.repository.VoteRepository;
import com.njt.advitop.transferobject.VoteObject;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.njt.advitop.model.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthenticationService authenticationService;

    @Transactional
    public void vote(VoteObject voteObject) {
        Post post = postRepository.findById(voteObject.getPostID())
                .orElseThrow(()-> new PostNotFoundException("Post with ID number " + voteObject.getPostID() + " was not found!"));
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIDDesc(post, authenticationService.getCurrentUser());
        if(voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteObject.getVoteType())) {
            throw new AdvitopException("You have already voted for this post!");
        }
        if(UPVOTE.equals(voteObject.getVoteType())) {
            post.setVoteCount(post.getVoteCount()+1);
        }
        else {
            post.setVoteCount(post.getVoteCount()-1);
        }
        voteRepository.save(mapToVote(voteObject,post));
        postRepository.save(post);
    }

    private Vote mapToVote(VoteObject voteObject, Post post) {
        return Vote.builder()
                .voteType(voteObject.getVoteType())
                .post(post)
                .user(authenticationService.getCurrentUser())
                .build();
    }
}
