package com.jdh.urlsaver.api.repository;

import com.jdh.urlsaver.domain.model.entity.node.NodeEntity;
import com.jdh.urlsaver.domain.model.entity.node.NodeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NodeRepository extends JpaRepository<NodeEntity, Long> {

    Page<NodeEntity> findByParentNodeId(Long parentNodeId, Pageable pageable);

    NodeEntity deleteByNodeId(Long nodeId);

    NodeEntity findByUserIdAndType(Long userId, NodeType type);
}
