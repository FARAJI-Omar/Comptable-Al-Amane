package com.comptablealamanapi.repository;

import com.comptablealamanapi.entity.Document;
import com.comptablealamanapi.entity.Societe;
import com.comptablealamanapi.enums.StatutDocument;
import com.comptablealamanapi.enums.TypeDocument;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;


import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class DocumentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DocumentRepository documentRepository;

    @Test
    void societeDocumentsbyYear_ShouldReturnDocuments_WhenSocieteAndYearMatch() {
        Societe societe = new Societe();
        societe.setRaisonSociale("Test Societe");
        entityManager.persistAndFlush(societe);

        Document doc1 = createDocument("DOC001", societe, LocalDate.of(2025, 5, 15));
        Document doc2 = createDocument("DOC002", societe, LocalDate.of(2025, 8, 20));
        Document doc3 = createDocument("DOC003", societe, LocalDate.of(2024, 3, 10));
        entityManager.persistAndFlush(doc1);
        entityManager.persistAndFlush(doc2);
        entityManager.persistAndFlush(doc3);

        List<Document> documents = documentRepository.societeDocumentsbyYear(societe.getId(), 2025);

        assertThat(documents).hasSize(2);
        assertThat(documents).extracting(Document::getNumeroPiece).containsExactlyInAnyOrder("DOC001", "DOC002");
    }

    @Test
    void findByNumeroPiece_ShouldReturnDocument_WhenNumeroPieceExists() {
        Societe societe = new Societe();
        societe.setRaisonSociale("Test Societe");
        entityManager.persistAndFlush(societe);

        Document document = createDocument("DOC123", societe, LocalDate.now());
        entityManager.persistAndFlush(document);

        Document found = documentRepository.findByNumeroPiece("DOC123");

        assertThat(found).isNotNull();
        assertThat(found.getNumeroPiece()).isEqualTo("DOC123");
    }

    @Test
    void findByStatutOrderByCreatedAtDesc_ShouldReturnDocuments_WhenStatutMatches() {
        Societe societe = new Societe();
        societe.setRaisonSociale("Test Societe");
        entityManager.persistAndFlush(societe);

        Document doc1 = createDocument("DOC001", societe, LocalDate.now());
        doc1.setStatut(StatutDocument.EN_ATTENTE);
        Document doc2 = createDocument("DOC002", societe, LocalDate.now());
        doc2.setStatut(StatutDocument.EN_ATTENTE);
        Document doc3 = createDocument("DOC003", societe, LocalDate.now());
        doc3.setStatut(StatutDocument.VALIDE);
        entityManager.persistAndFlush(doc1);
        entityManager.persistAndFlush(doc2);
        entityManager.persistAndFlush(doc3);

        List<Document> documents = documentRepository.findByStatutOrderByCreatedAtDesc(StatutDocument.EN_ATTENTE);

        assertThat(documents).hasSize(2);
        assertThat(documents).extracting(Document::getStatut).containsOnly(StatutDocument.EN_ATTENTE);
    }

    @Test
    void findBySocieteIdAndStatutOrderByCreatedAtDesc_ShouldReturnDocuments_WhenSocieteAndStatutMatch() {
        Societe societe1 = new Societe();
        societe1.setRaisonSociale("Societe 1");
        entityManager.persistAndFlush(societe1);

        Societe societe2 = new Societe();
        societe2.setRaisonSociale("Societe 2");
        entityManager.persistAndFlush(societe2);

        Document doc1 = createDocument("DOC001", societe1, LocalDate.now());
        doc1.setStatut(StatutDocument.EN_ATTENTE);
        Document doc2 = createDocument("DOC002", societe1, LocalDate.now());
        doc2.setStatut(StatutDocument.VALIDE);
        Document doc3 = createDocument("DOC003", societe2, LocalDate.now());
        doc3.setStatut(StatutDocument.EN_ATTENTE);
        entityManager.persistAndFlush(doc1);
        entityManager.persistAndFlush(doc2);
        entityManager.persistAndFlush(doc3);

        List<Document> documents = documentRepository.findBySocieteIdAndStatutOrderByCreatedAtDesc(societe1.getId(), StatutDocument.EN_ATTENTE);

        assertThat(documents).hasSize(1);
        assertThat(documents.get(0).getNumeroPiece()).isEqualTo("DOC001");
    }

    @Test
    void findBySocieteIdOrderByCreatedAtDesc_ShouldReturnAllDocuments_WhenSocieteMatches() {
        Societe societe = new Societe();
        societe.setRaisonSociale("Test Societe");
        entityManager.persistAndFlush(societe);

        Document doc1 = createDocument("DOC001", societe, LocalDate.now());
        Document doc2 = createDocument("DOC002", societe, LocalDate.now());
        entityManager.persistAndFlush(doc1);
        entityManager.persistAndFlush(doc2);

        List<Document> documents = documentRepository.findBySociete(societe.getId());

        assertThat(documents).hasSize(2);
    }

    private Document createDocument(String numeroPiece, Societe societe, LocalDate datePiece) {
        Document document = new Document();
        document.setNumeroPiece(numeroPiece);
        document.setSociete(societe);
        document.setDatePiece(datePiece);
        document.setType(TypeDocument.FACTURE_ACHAT);
        document.setMontant(100.00);
        document.setFournisseur("Test Fournisseur");
        document.setStatut(StatutDocument.EN_ATTENTE);
        return document;
    }
}
