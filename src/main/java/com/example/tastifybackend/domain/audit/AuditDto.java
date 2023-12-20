package com.example.tastifybackend.domain.audit;

import lombok.*;

import java.sql.Timestamp;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuditDto {
    private Timestamp created;
    private Timestamp lastModified;
}
