package com.example.finalexam.entity;

import com.example.finalexam.common.Constants;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Data
@MappedSuperclass
public class CommonEntity {

    @Column(name = "is_deleted",nullable = false)
    @ColumnDefault(value = "0")
    private Integer isDeleted;

    @PrePersist
    @PreUpdate
    public void prePersist() {
        this.isDeleted = Constants.IS_DELETED.FALSE;
    }

    public CommonEntity isDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
        return this;
    }
}
