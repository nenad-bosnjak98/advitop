package com.njt.advitop.repository;

import com.njt.advitop.model.Category;
import com.njt.advitop.model.Post;
import com.njt.advitop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findAllByCategory(Category category);
    List<Post> findByUser(User user);
}
