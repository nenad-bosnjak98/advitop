package com.njt.advitop.service;

import com.njt.advitop.exceptions.AdvitopException;
import com.njt.advitop.mapper.CategoryMapper;
import com.njt.advitop.model.Category;
import com.njt.advitop.repository.CategoryRepository;
import com.njt.advitop.transferobject.CategoryObject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;
@Service
@AllArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final AuthenticationService authenticationService;

    @Transactional
    public CategoryObject save(CategoryObject categoryObject) {
        Category save = categoryRepository.save(categoryMapper.map(categoryObject, authenticationService.getCurrentUser() ));
        categoryObject.setID(save.getID());
        return categoryObject;
    }

    @Transactional(readOnly = true)
    public List<CategoryObject> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::mapCategoryToDTO)
                .collect(toList());
    }

    public CategoryObject getCategory(Long ID) {
        Category category = categoryRepository.findById(ID)
                .orElseThrow(()->new AdvitopException("No category found with given ID!"));
        return categoryMapper.mapCategoryToDTO(category);
    }
}
