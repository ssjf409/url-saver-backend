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
import javax.validation.constraints.Size;
import java.util.Date;

import static com.jdh.urlsaver.utils.LengthCondition.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE token SET deleted = true WHERE token_id = ?")
@Where(clause = "deleted = false")
@Table
@Entity(name = "token")
public class TokenEntity extends BaseEntity {

    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long tokenId;

    @Size(max = KEY_LEN)
    @Column
    private String secret;

    @Size(max = TOKEN_TYPE_LEN)
    @Column
    private String type;

    @Size(max = ATTRIBUTE_LEN)
    @Column
    private String header;

    @Size(max = ATTRIBUTE_VALUE_LEN)
    @Column
    private String headerValue;

    @Size(max = TOKEN_LEN)
    @Column
    private String value;

    @Column
    private Date expiredTime;
}
