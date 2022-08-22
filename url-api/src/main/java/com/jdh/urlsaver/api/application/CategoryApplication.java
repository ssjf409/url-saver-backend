package com.jdh.urlsaver.api.application;

import com.jdh.urlsaver.api.service.CategoryService;
import com.jdh.urlsaver.domain.model.entity.category.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CategoryApplication {

    private final CategoryService categoryService;

    public Category findCategory(Long categoryId) {
        return categoryService.findCategory(categoryId);
    }

    public Category createDefaultCategory(Long userId) {
        return categoryService.createDefault(userId);
    }
}
