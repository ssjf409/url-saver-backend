package com.jdh.urlsaver.api.application;

import com.jdh.urlsaver.api.controller.dto.NodeDeleteRequest;
import com.jdh.urlsaver.api.controller.dto.NodeFetchResponse;
import com.jdh.urlsaver.api.controller.dto.NodeUpsertRequest;
import com.jdh.urlsaver.api.service.NodeService;
import com.jdh.urlsaver.domain.model.entity.node.Node;
import com.jdh.urlsaver.domain.model.entity.node.NodeEntity;
import com.jdh.urlsaver.domain.model.entity.node.NodeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class NodeApplication {
    private static final String DEFAULT = "Default";
    private final AccountApplication accountApplication;
    private final CategoryApplication categoryApplication;
    private final NodeService nodeService;

    public NodeFetchResponse fetch(Long parentNodeId, Integer pageNumber, Integer pageSize) {
        return NodeFetchResponse.builder()
                                .nodes(nodeService.fetch(parentNodeId, pageNumber, pageSize))
                                .build();
    }

    public Node create(NodeUpsertRequest request) {
        // TODO: 2022/08/22 에러 명확하게 정의

        accountApplication.findUser(request.getUserId());
        categoryApplication.findCategory(request.getCategoryId());

        return nodeService.create(request.toEntity());
    }

    public Node update(NodeUpsertRequest request) {
        accountApplication.findUser(request.getUserId());
        categoryApplication.findCategory(request.getCategoryId());
        return nodeService.update(request.toEntity());
    }

    public void delete(Long userId, NodeDeleteRequest request) {
        accountApplication.findUser(userId);
        nodeService.delete(request.getNodeId());
    }

    public void createDefaultNode(Long userId, Long categoryId) {
        accountApplication.findUser(userId);
        categoryApplication.findCategory(categoryId);
        nodeService.create(NodeEntity.builder()
                                     .userId(userId)
                                     .name(DEFAULT)
                                     .categoryId(categoryId)
                                     .type(NodeType.HEAD)
                                     .build());
    }
}
