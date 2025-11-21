package com.comptablealamanapi.entity;

import com.comptablealamanapi.enums.StatutDocument;
import com.comptablealamanapi.enums.TypeDocument;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String numeroPiece;
    private String categorieComptable;
    private LocalDate datePiece;
    private Double montant;
    private String fournisseur;
    private String fichierPiece;
    private String filename;
    private LocalDateTime dateValidation;
    private String commentaireComptable;

    @Enumerated(EnumType.STRING)
    private TypeDocument type;

    @Enumerated(EnumType.STRING)
    private StatutDocument statut;

    @ManyToOne
    @JoinColumn(name = "societe_id")
    private Societe societe;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() { createdAt = LocalDateTime.now(); }
    
    @PreUpdate
    protected void onUpdate() { updatedAt = LocalDateTime.now(); }
}