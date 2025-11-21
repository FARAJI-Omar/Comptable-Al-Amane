package com.comptablealamanapi.dto.request;

import com.comptablealamanapi.enums.TypeDocument;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentRequest {
    @NotBlank(message = "Le numéro de pièce est obligatoire")
    private String numeroPiece;

    @NotBlank(message = "Le type de document est obligatoire")
    private TypeDocument type;

    private String categorieComptable;
    private LocalDate datePiece;
    private Double montant;
    private String fournisseur;

}
