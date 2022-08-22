package com.jdh.urlsaver.api.repository;

import com.jdh.urlsaver.domain.model.entity.category.CategoryEntity;
import com.jdh.urlsaver.domain.model.entity.node.NodeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

}
