package com.jdh.urlsaver.model.entity.auth;

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

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE sign_in_history SET deleted = true WHERE sign_in_history_id = ?")
@Where(clause = "deleted = false")
@Entity
@Table
public class SignInHistory extends BaseEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long signInHistoryId;

    // TODO: 2022/06/14 have to add more code
}