package com.jdh.urlsaver.model.entity.node;

import com.jdh.urlsaver.model.entity.audit.BaseEntity;
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

import static com.jdh.urlsaver.utils.LengthCondition.*;

// TODO: 2022/08/21 카딜널리티 설계 중요. userId -> node type
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE node SET deleted = true WHERE node_id = ?")
@Where(clause = "deleted = false")
@Table
@Entity(name = "node")
public class NodeEntity extends BaseEntity {

    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long nodeId;

    @Column
    private Long userId;

    @Column
    private Long parentNodeId;

    @Enumerated(EnumType.STRING)
    @Column(length = NODE_TYPE_LEN)
    private NodeType type;

    @Size(max = NAME_LEN)
    @Column
    private String name;

    @Size(max = CONTENT_LEN)
    @Column
    private String content;

    @Size(max = DESCRIPTION_LEN)
    @Column
    private String description;

    @Column(columnDefinition = "TINYINT(1)", length = 1)
    private boolean favorite;

}