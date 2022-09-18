package com.njt.advitop.mapper;

import com.njt.advitop.model.Comment;
import com.njt.advitop.model.Post;
import com.njt.advitop.model.User;
import com.njt.advitop.transferobject.CommentObject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "ID", ignore = true)
    @Mapping(target = "text", source = "commentObject.text")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
    Comment map(CommentObject commentObject, Post post, User user);

    @Mapping(target = "postID", expression = "java(comment.getPost().getPostID())")
    @Mapping(target = "username", expression = "java(comment.getUser().getUsername())")
    CommentObject mapToDTO(Comment comment);
}
