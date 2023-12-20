package com.example.tastifybackend.domain.category.dto;

import com.example.tastifybackend.domain.audit.AuditDto;

import java.io.Serializable;

public record CategoryDto(
        String id,
        String value,
        AuditDto audit
) implements Serializable {
}
