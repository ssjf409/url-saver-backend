package com.jdh.urlsaver.api.controller.dto;

import com.jdh.urlsaver.common.ValidEnum;
import com.jdh.urlsaver.model.entity.node.NodeEntity;
import com.jdh.urlsaver.model.entity.node.NodeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NodeUpsertRequest {

    private Long userId;

    @NotNull
    private Long parentNodeId;

    @ValidEnum(enumClass = NodeType.class)
    private String type;

    private String name;

    private String content;

    private String description;

    private boolean favorite = false;

    public NodeType getType() {
        return NodeType.valueOf(this.type);
    }

    public NodeEntity toEntity() {
        return NodeEntity.builder()
                         .userId(this.userId)
                         .parentNodeId(this.parentNodeId)
                         .type(this.getType())
                         .name(this.name)
                         .content(this.content)
                         .description(this.description)
                         .favorite(this.favorite)
                         .build();
    }
}
