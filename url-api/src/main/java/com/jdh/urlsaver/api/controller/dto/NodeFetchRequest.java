package com.jdh.urlsaver.api.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NodeFetchRequest {

    private Long userId;
    private Long parentNodeId;
    private Integer pageSize;
    private Integer pageNumber;
}
