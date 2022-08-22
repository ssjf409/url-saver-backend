package com.jdh.urlsaver.domain.model.entity.category;

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

import static com.jdh.urlsaver.domain.utils.LengthCondition.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE categories SET deleted = true WHERE category_id = ?")
@Where(clause = "deleted = false")
@Table
@Entity(name = "categories")
public class CategoryEntity extends BaseEntity {

    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long categoryId;

    @Column
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(length = TYPE_LEN)
    private CategoryType type;

    @Size(max = LengthCondition.NAME_LEN)
    @Column
    private String name;

//    @Size(max = CONTENT_LEN)
//    @Column
//    private String content;
//
//    @Size(max = DESCRIPTION_LEN)
//    @Column
//    private String description;
//
//    @Column(columnDefinition = "TINYINT(1)", length = 1)
//    private boolean favorite;
}
