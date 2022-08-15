package com.jdh.urlsaver.api.service.dto;

import com.jdh.urlsaver.model.entity.user.ProviderType;
import com.jdh.urlsaver.model.entity.user.RoleType;
import com.jdh.urlsaver.model.entity.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long userId;

    private String loginId;

    private String email;

    private String familyName;

    private String givenName;

    private boolean emailVerified;

    private String profileImageUrl;

    private ProviderType providerType;

    private RoleType roleType;

//    private boolean dormant;
//
//    private LocalDateTime dormantAt;
//
//    private boolean withdrawn;
//
//    private LocalDateTime withdrawnAt;

    public static User convert(UserEntity userEntity) {
        return User.builder()
                   .userId(userEntity.getUserId())
                   .loginId(userEntity.getLoginId())
                   .email(userEntity.getEmail())
                   .familyName(userEntity.getFamilyName())
                   .givenName(userEntity.getGivenName())
                   .emailVerified(userEntity.isEmailVerified())
                   .profileImageUrl(userEntity.getProfileImageUrl())
                   .providerType(userEntity.getProviderType())
                   .roleType(userEntity.getRoleType())
                   .build();
    }
}
