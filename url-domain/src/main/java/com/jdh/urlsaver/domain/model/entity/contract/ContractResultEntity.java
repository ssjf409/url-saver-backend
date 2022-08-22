package com.jdh.urlsaver.domain.model.entity.contract;

import com.jdh.urlsaver.domain.model.entity.audit.BaseEntity;
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

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE contract_revisions SET deleted = true WHERE contract_revision_id = ?")
@Where(clause = "deleted = false")
@Table
@Entity(name = "contract_results")
public class ContractResultEntity extends BaseEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contractResultId;

    // TODO: 2022/06/14 have to add more code
}
