package com.jdh.urlsaver.model.entity.audit;

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

import static com.jdh.urlsaver.utils.LengthConstant.*;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditEntity {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @Size(max = NAME)
    @CreatedBy
    @Column(updatable = false)
    private String createdBy = "SYSTEM";

    @Size(max = NAME)
    @LastModifiedBy
    private String modifiedBy = "SYSTEM";

}