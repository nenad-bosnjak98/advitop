package com.njt.advitop.repository;

import com.njt.advitop.model.Post;
import com.njt.advitop.model.User;
import com.njt.advitop.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIDDesc(Post post, User user);
}
