package com.jpc16tuesday.springlibraryproject.library.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public abstract class GenericDTO {
    protected Long id;
    protected String createdBy;
    protected LocalDateTime createdWhen;
    protected LocalDateTime deletedWhen;
    protected String deletedBy;
    protected boolean isDeleted;
}
