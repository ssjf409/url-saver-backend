package com.jdh.urlsaver.api.repository;

import com.jdh.urlsaver.model.entity.node.NodeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NodeRepository extends JpaRepository<NodeEntity, Long> {

    Page<NodeEntity> findByParentNodeId(Long parentNodeId, Pageable pageable);

    NodeEntity deleteByNodeId(Long nodeId);
}
