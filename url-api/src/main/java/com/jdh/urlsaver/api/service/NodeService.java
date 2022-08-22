package com.jdh.urlsaver.api.service;

import com.jdh.urlsaver.api.repository.NodeRepository;
import com.jdh.urlsaver.domain.model.entity.PageContents;
import com.jdh.urlsaver.domain.model.entity.node.Node;
import com.jdh.urlsaver.domain.model.entity.node.NodeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class NodeService {

    private final NodeRepository nodeRepository;

    @Transactional(readOnly = true)
    public PageContents<Node> fetch(Long parentNodeId, Integer pageNumber, Integer pageSize) {
        Page<NodeEntity> nodePages = nodeRepository.findByParentNodeId(
                parentNodeId, PageRequest.of(pageNumber, pageSize, Sort.by("createdAt").descending()));
        return Node.of(nodePages);
    }

    @Transactional
    public Node create(NodeEntity entity) {
        return Node.of(nodeRepository.save(entity));
    }

    @Transactional
    public Node update(NodeEntity entity) {
        NodeEntity existing = nodeRepository.findById(entity.getNodeId())
                                            .orElseThrow(() -> new RuntimeException("unknown"));
        entity.setUserId(existing.getUserId());
        return Node.of(nodeRepository.save(entity));
    }

    @Transactional
    public void delete(Long nodeId) {
        nodeRepository.findById(nodeId).ifPresent(nodeRepository::delete);
    }
}
