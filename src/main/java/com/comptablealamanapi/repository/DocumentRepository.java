package com.comptablealamanapi.repository;

import com.comptablealamanapi.dto.response.DocumentResponse;
import com.comptablealamanapi.entity.Document;
import com.comptablealamanapi.entity.Societe;
import com.comptablealamanapi.enums.StatutDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    @Query("SELECT d FROM Document d WHERE d.societe.id = :societeID and YEAR(d.datePiece) = :exerciseComptable ORDER BY d.createdAt DESC")
    List<Document> societeDocumentsbyYear(@Param("societeID") Long societeID, @Param("exerciseComptable") int exerciseComptable);
    
    Document findByNumeroPiece(String numeroPiece);
    
    List<Document> findByStatutOrderByCreatedAtDesc(StatutDocument statut);
    
    List<Document> findBySocieteIdAndStatutOrderByCreatedAtDesc(Long societeId, StatutDocument statut);
}
