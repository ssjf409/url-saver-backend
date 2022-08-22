package com.jdh.urlsaver.domain.model.entity.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    private Long categoryId;
    private Long userId;
    private String name;

    public static Category of(CategoryEntity entity) {
        return Category.builder()
                       .categoryId(entity.getCategoryId())
                       .userId(entity.getUserId())
                       .name(entity.getName())
                       .build();
    }
}
