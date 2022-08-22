package com.jdh.urlsaver.domain.model.entity.audit;

import com.jdh.urlsaver.domain.utils.LengthCondition;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditEntity {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @Size(max = LengthCondition.NAME_LEN)
    @CreatedBy
    @Column(updatable = false)
    private String createdBy = "SYSTEM";

    @Size(max = LengthCondition.NAME_LEN)
    @LastModifiedBy
    private String modifiedBy = "SYSTEM";

}