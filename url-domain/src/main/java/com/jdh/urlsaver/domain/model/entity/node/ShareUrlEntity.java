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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE share_urls SET deleted = true WHERE share_url_id = ?")
@Where(clause = "deleted = false")
@Table
@Entity(name = "share_urls")
public class ShareUrlEntity extends BaseEntity {

    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long shareUrlId;

    @Column
    private Long nodeId;

    @Size(max = LengthCondition.URL_LEN)
    @Column
    private String shareUrl;

}
