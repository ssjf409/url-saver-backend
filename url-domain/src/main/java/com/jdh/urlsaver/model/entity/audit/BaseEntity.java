package com.jdh.urlsaver.model.entity.audit;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity extends AuditEntity {

    @Column(columnDefinition = "TINYINT(1)", length = 1)
    private boolean deleted;
}