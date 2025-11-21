package com.comptablealamanapi.entity;

import com.comptablealamanapi.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String email;
    
    private String motDePasse;
    private String nomComplet;
    private Boolean statut;

    @Enumerated(EnumType.STRING)
    private Role role;

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
