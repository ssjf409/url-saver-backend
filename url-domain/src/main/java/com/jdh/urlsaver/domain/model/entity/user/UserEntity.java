package com.jdh.urlsaver.domain.model.entity.user;

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
import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE user_id = ?")
@Where(clause = "deleted = false")
@Table
@Entity(name = "users")
public final class UserEntity extends BaseEntity {

    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long userId;

    @Size(max = LengthCondition.EMAIL_LEN)
    @Column(unique = true)
    private String loginId;

    @Size(max = LengthCondition.PASSWORD_LEN)
    @Column
    private String hashedPassword;

    @Size(max = LengthCondition.EMAIL_LEN)
    @Column
    private String email;

    @Size(max = LengthCondition.NAME_LEN)
    @Column
    private String familyName;

    @Size(max = LengthCondition.NAME_LEN)
    @Column
    private String givenName;

    @Column(columnDefinition = "TINYINT(1)", length = 1)
    private boolean emailVerified;

    @Size(max = LengthCondition.IMAGE_URL_LEN)
    @Column
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(length = LengthCondition.PROVIDER_TYPE_LEN)
    private ProviderType providerType;

    @Enumerated(EnumType.STRING)
    @Column(length = LengthCondition.ROLE_LEN)
    private RoleType roleType;

    @Column(columnDefinition = "TINYINT(1)", length = 1)
    private boolean dormant;

    @Column
    private LocalDateTime dormantAt;

    @Column(columnDefinition = "TINYINT(1)", length = 1)
    private boolean withdrawn;

    @Column
    private LocalDateTime withdrawnAt;
}
