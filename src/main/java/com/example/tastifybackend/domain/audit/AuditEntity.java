package com.example.tastifybackend.domain.audit;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@MappedSuperclass
@Getter
public class AuditEntity {
    @Column(name = "created", updatable = false)
    private Timestamp created;
    @Column(name = "last_modified")
    private Timestamp lastModified;

    /*
    @Column(name = "created_by", updatable = false)
    private String createdBy;
    @Column(name = "modified_by")
    private String modifiedBy;
     */

    @PrePersist
    void prePersist(){
        created = Timestamp.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));
    }

    @PreUpdate
    void preUpdate(){
        lastModified = Timestamp.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));
    }


}
