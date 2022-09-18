package com.njt.advitop.service;


import com.njt.advitop.exceptions.CategoryNotFoundException;
import com.njt.advitop.exceptions.PostNotFoundException;
import com.njt.advitop.mapper.PostMapper;
import com.njt.advitop.model.Category;
import com.njt.advitop.model.Post;
import com.njt.advitop.model.User;
import com.njt.advitop.repository.CategoryRepository;
import com.njt.advitop.repository.PostRepository;
import com.njt.advitop.repository.UserRepository;
import com.njt.advitop.transferobject.PostRequest;
import com.njt.advitop.transferobject.PostResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;
    private final AuthenticationService authenticationService;

    public void save(PostRequest postRequest) {
        Category category = categoryRepository.findByName(postRequest.getCategoryName())
                .orElseThrow(()-> new CategoryNotFoundException("Category " + postRequest.getCategoryName() + " was not found!"));
        postRepository.save(postMapper.map(postRequest, category, authenticationService.getCurrentUser()));
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long ID) {
        Post post = postRepository.findById(ID)
                .orElseThrow(()-> new PostNotFoundException("Post with ID number " + ID + " was not found!"));
        return postMapper.mapToDTO(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDTO)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByCategory(Long ID) {
        Category category = categoryRepository.findById(ID)
                .orElseThrow(()-> new CategoryNotFoundException("Category with ID number " + ID + " was not found!"));
        List<Post> posts = postRepository.findAllByCategory(category);
        return posts.stream().map(postMapper::mapToDTO).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User with username " + username + " was not found!"));
        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::mapToDTO)
                .collect(toList());
    }
}
