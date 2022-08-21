package com.jdh.urlsaver.model.entity.node;

import com.jdh.urlsaver.model.entity.PageContents;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

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

    public static PageContents<Node> of(Page<NodeEntity> entities) {
        PageContents<Node> pages = new PageContents<>();
        pages.setContent(of(entities.getContent()));
        pages.setTotalElements(entities.getTotalElements());
        pages.setTotalPages(entities.getTotalPages());
        return pages;
    }
}
