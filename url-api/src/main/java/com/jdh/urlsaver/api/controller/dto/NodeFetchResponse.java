package com.jdh.urlsaver.api.controller.dto;

import com.jdh.urlsaver.common.dto.PageContents;
import com.jdh.urlsaver.model.entity.node.Node;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class NodeFetchResponse {
    private PageContents<Node> nodes;
}
