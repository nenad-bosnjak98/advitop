package com.njt.advitop.mapper;


import com.njt.advitop.model.Category;
import com.njt.advitop.model.Post;
import com.njt.advitop.model.User;
import com.njt.advitop.transferobject.CategoryObject;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(category.getPosts()))")
    CategoryObject mapCategoryToDTO(Category category);

    default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    Category map(CategoryObject categoryObject, User user);
}

