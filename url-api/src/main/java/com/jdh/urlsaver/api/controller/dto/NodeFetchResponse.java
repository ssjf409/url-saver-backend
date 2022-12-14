package com.jdh.urlsaver.api.controller.dto;

import com.jdh.urlsaver.domain.model.entity.PageContents;
import com.jdh.urlsaver.domain.model.entity.node.Node;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class NodeFetchResponse {
    private PageContents<Node> nodes;
}
