package com.jdh.urlsaver.api.service;

import com.jdh.urlsaver.api.repository.CategoryRepository;
import com.jdh.urlsaver.domain.common.exception.InvalidInputException;
import com.jdh.urlsaver.domain.model.entity.category.Category;
import com.jdh.urlsaver.domain.model.entity.category.CategoryEntity;
import com.jdh.urlsaver.domain.model.entity.category.CategoryType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public Category findCategory(Long categoryId) {
        return Category.of(categoryRepository.findById(categoryId).orElseThrow(
                () -> new InvalidInputException(String.format("failed to find category, categoryId: %s", categoryId))));
    }

    @Transactional
    public Category createDefault(Long userId) {
        CategoryEntity entity = CategoryEntity.builder()
                                              .userId(userId)
                                              .type(CategoryType.DEFAULT)
                                              .build();
        return Category.of(categoryRepository.save(entity));
    }
}
