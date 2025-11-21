package com.comptablealamanapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Societe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String raisonSociale;
    
    @Column(unique = true)
    private String ice;
    
    private String adresse;
    private String telephone;
    private String emailContact;
    
    @OneToMany(mappedBy = "societe", cascade = CascadeType.ALL)
    private List<Utilisateur> utilisateurs;
    
    @OneToMany(mappedBy = "societe", cascade = CascadeType.ALL)
    private List<Document> documents;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() { createdAt = LocalDateTime.now(); }
    
    @PreUpdate
    protected void onUpdate() { updatedAt = LocalDateTime.now(); }
}
