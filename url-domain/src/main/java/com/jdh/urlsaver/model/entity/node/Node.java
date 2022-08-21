package com.jdh.urlsaver.model.entity.node;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Node {

    private Long nodeId;
    private Long userId;
    private Long parentNodeId;
    private NodeType type;
    private String name;
    private String content;
    private String description;
    private boolean favorite;

    public static Node of(NodeEntity entity) {
        return Node.builder()
                   .nodeId(entity.getNodeId())
                   .userId(entity.getUserId())
                   .parentNodeId(entity.getParentNodeId())
                   .type(entity.getType())
                   .name(entity.getName())
                   .content(entity.getContent())
                   .description(entity.getDescription())
                   .favorite(entity.isFavorite())
                   .build();
    }

    public static List<Node> of(List<NodeEntity> entities) {
        return entities.stream().map(Node::of).collect(Collectors.toList());
    }
}
