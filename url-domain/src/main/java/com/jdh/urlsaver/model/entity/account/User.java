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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static com.jdh.urlsaver.utils.LengthConstant.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE user SET deleted = true WHERE user_id = ?")
@Where(clause = "deleted = false")
@Entity
@Table
public class User extends BaseEntity {

    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long userId;

    @Size(max = EMAIL)
    @Column(unique = true)
    private String loginId;

    @Size(max = PASSWORD)
    @Column
    private String hashedPassword;

    @Size(max = EMAIL)
    @Column
    private String email;

    @Size(max = NAME)
    @Column
    private String username;

    @Column(columnDefinition = "TINYINT(1)", length = 1)
//    @Column(length = 1)
    private boolean emailVerifiedYn;

    @Size(max = IMAGE_URL)
    @Column
    private String profileImageUrl;

    //    @Size(max = PROVIDER_TYPE)
    @Enumerated(EnumType.STRING)
    @Column(length = PROVIDER_TYPE)
    private ProviderType providerType;

    //    @Size(max = ROLE)
    @Enumerated(EnumType.STRING)
    @Column(length = ROLE)
    private RoleType roleType;

    @Column(columnDefinition = "TINYINT(1)", length = 1)
//    @Column(length = 1)
    private boolean dormant;

    @Column
    private LocalDateTime dormantAt;

    @Column(columnDefinition = "TINYINT(1)", length = 1)
//    @Column(length = 1)
    private boolean withdrawn;

    @Column
    private LocalDateTime withdrawnAt;
}
