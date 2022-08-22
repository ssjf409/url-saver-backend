package com.jdh.urlsaver.domain.model.entity.node;

import com.jdh.urlsaver.domain.model.entity.audit.BaseEntity;
import com.jdh.urlsaver.domain.utils.LengthCondition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

// TODO: 2022/08/21 카딜널리티 설계 중요. userId -> node type
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE nodes SET deleted = true WHERE node_id = ?")
@Where(clause = "deleted = false")
@Table
@Entity(name = "nodes")
public class NodeEntity extends BaseEntity {

    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long nodeId;

    @Column
    private Long userId;

    @Column
    private Long parentNodeId;

    @Column
    private Long categoryId;

    @Enumerated(EnumType.STRING)
    @Column(length = LengthCondition.TYPE_LEN)
    private NodeType type;

    @Size(max = LengthCondition.NAME_LEN)
    @Column
    private String name;

    @Size(max = LengthCondition.CONTENT_LEN)
    @Column
    private String content;

    @Size(max = LengthCondition.DESCRIPTION_LEN)
    @Column
    private String description;

    @Column(columnDefinition = "TINYINT(1)", length = 1)
    private boolean favorite;

}