package com.njt.advitop.controller;


import com.njt.advitop.service.CategoryService;
import com.njt.advitop.transferobject.CategoryObject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/category")
@AllArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryObject> createCategory(@RequestBody CategoryObject categoryObject) {
        return new ResponseEntity<>(categoryService.save(categoryObject), OK);
    }

    @GetMapping
    public ResponseEntity<List<CategoryObject>> getAllCategories() {
        return new ResponseEntity<>(categoryService.getAll(), OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryObject> getCategory(@PathVariable Long id) {
        return new ResponseEntity<>(categoryService.getCategory(id), OK);
    }
}
