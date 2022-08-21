package com.jdh.urlsaver.api.controller.dto;

import com.jdh.urlsaver.model.entity.node.Node;
import com.jdh.urlsaver.model.entity.node.NodeType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class NodeUpsertResponse {

    private Long userId;
    private Long nodeId;
    private Long parentNodeId;
    private NodeType type;
    private String name;
    private String content;
    private String description;
    private boolean favorite;

    public static NodeUpsertResponse of(Node node) {
        return NodeUpsertResponse.builder()
                                 .userId(node.getUserId())
                                 .nodeId(node.getNodeId())
                                 .parentNodeId(node.getParentNodeId())
                                 .type(node.getType())
                                 .name(node.getName())
                                 .content(node.getContent())
                                 .description(node.getDescription())
                                 .favorite(node.isFavorite())
                                 .build();
    }
}
