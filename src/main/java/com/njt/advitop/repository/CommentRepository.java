package com.njt.advitop.repository;

import com.njt.advitop.model.Comment;
import com.njt.advitop.model.Post;
import com.njt.advitop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByPost(Post post);
    List<Comment> findAllByUser(User user);
}
