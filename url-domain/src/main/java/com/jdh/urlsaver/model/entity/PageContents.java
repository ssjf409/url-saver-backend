package com.jdh.urlsaver.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageContents<T> {

    private List<T> content;
    private int totalPages;
    private long totalElements;
}
