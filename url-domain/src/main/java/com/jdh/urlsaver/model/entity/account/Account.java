package com.jdh.urlsaver.model.entity.account;

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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import static com.jdh.urlsaver.utils.LengthConstant.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE account SET deleted = true WHERE account_id = ?")
@Where(clause = "deleted = false")
@Entity
@Table
public class Account extends BaseEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column
    private Long userId;

    @Size(max = EMAIL)
    @Column(unique = true)
    private String loginId;

    @Size(max = EMAIL)
    @Column
    private String email;

    @Size(max = PASSWORD)
    @Column
    private String hashedPassword;
}
