package com.comptablealamanapi.dto.response;

import com.comptablealamanapi.enums.StatutDocument;
import com.comptablealamanapi.enums.TypeDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentResponse {
    private Long id;
    private String numeroPiece;
    private TypeDocument type;
    private String categorieComptable;
    private LocalDate datePiece;
    private Double montant;
    private String fournisseur;
    private String fichierPiece;
    private StatutDocument statut;
    private LocalDateTime dateValidation;
    private String commentaireComptable;
    private Long societeId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
