package com.workspace.dto.collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhraseStatusRequest {
    @NotBlank
    private Long id;

    @NotBlank
    private Boolean isKnown;
}
