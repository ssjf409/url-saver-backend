package com.jdh.urlsaver.domain.model.entity.auth;

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
import javax.validation.constraints.Size;

import static com.jdh.urlsaver.domain.utils.LengthCondition.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE sign_up_histories SET deleted = true WHERE sign_up_history_id = ?")
@Where(clause = "deleted = false")
@Table
@Entity(name = "sign_up_histories")
public class SignUpHistoryEntity extends BaseEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long signUpHistoryId;

    @Size(max = LOGIN_ID_LEN)
    @Column
    private String loginId;

    @Size(max = KEY_LEN)
    @Column
    private String keyValue;

    @Size(max = RANDOM_STRING_LEN)
    @Column
    private String emailCode;


    // TODO: 2022/06/14 have to add more code
}
