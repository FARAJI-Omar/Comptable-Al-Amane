package com.comptablealamanapi.repository;

import com.comptablealamanapi.entity.Societe;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SocieteRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SocieteRepository societeRepository;

    @Test
    void save_ShouldPersistSociete() {
        Societe societe = new Societe();
        societe.setRaisonSociale("Test Company");
        societe.setAdresse("123 Test Street");
        societe.setTelephone("0123456789");

        Societe saved = societeRepository.save(societe);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getRaisonSociale()).isEqualTo("Test Company");
    }

    @Test
    void findById_ShouldReturnSociete_WhenIdExists() {
        Societe societe = new Societe();
        societe.setRaisonSociale("Test Company");
        entityManager.persistAndFlush(societe);

        Optional<Societe> found = societeRepository.findById(societe.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getRaisonSociale()).isEqualTo("Test Company");
    }

    @Test
    void findById_ShouldReturnEmpty_WhenIdDoesNotExist() {
        Optional<Societe> found = societeRepository.findById(999L);

        assertThat(found).isEmpty();
    }

    @Test
    void delete_ShouldRemoveSociete() {
        Societe societe = new Societe();
        societe.setRaisonSociale("Test Company");
        entityManager.persistAndFlush(societe);
        Long id = societe.getId();

        societeRepository.deleteById(id);

        Optional<Societe> found = societeRepository.findById(id);
        assertThat(found).isEmpty();
    }
}
