package com.jdh.urlsaver.api.repository;

import com.jdh.urlsaver.domain.model.entity.category.CategoryEntity;
import com.jdh.urlsaver.domain.model.entity.category.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    CategoryEntity findByUserIdAndType(Long userId, CategoryType type);

}
