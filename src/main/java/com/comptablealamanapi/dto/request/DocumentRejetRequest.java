package com.comptablealamanapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentRejetRequest {
    @NotBlank(message = "Le motif de rejet est obligatoire")
    private String motif;
}
