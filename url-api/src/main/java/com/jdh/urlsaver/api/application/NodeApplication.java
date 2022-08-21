package com.jdh.urlsaver.api.application;

import com.jdh.urlsaver.api.controller.dto.NodeDeleteRequest;
import com.jdh.urlsaver.api.controller.dto.NodeFetchResponse;
import com.jdh.urlsaver.api.controller.dto.NodeUpsertRequest;
import com.jdh.urlsaver.api.service.NodeService;
import com.jdh.urlsaver.model.entity.node.Node;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class NodeApplication {

    private final NodeService nodeService;

    public NodeFetchResponse fetch(Long parentNodeId, Integer pageNumber, Integer pageSize) {
        return NodeFetchResponse.builder()
                                .nodes(nodeService.fetch(parentNodeId, pageNumber, pageSize))
                                .build();
    }

    public Node create(NodeUpsertRequest request) {
        return nodeService.create(request.toEntity());
    }

    public Node update(NodeUpsertRequest request) {
        return nodeService.update(request.toEntity());
    }

    public void delete(NodeDeleteRequest request) {
        nodeService.delete(request.getNodeId());
    }
}
