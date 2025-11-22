package com.comptablealamanapi.repository;

import com.comptablealamanapi.entity.Utilisateur;
import com.comptablealamanapi.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UtilisateurRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Test
    void findByEmail_ShouldReturnUtilisateur_WhenEmailExists() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail("test@example.com");
        utilisateur.setMotDePasse("password");
        utilisateur.setNomComplet("Test User");
        utilisateur.setRole(Role.SOCIETE);
        utilisateur.setStatut(true);
        entityManager.persistAndFlush(utilisateur);

        Optional<Utilisateur> found = utilisateurRepository.findByEmail("test@example.com");

        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("test@example.com");
        assertThat(found.get().getNomComplet()).isEqualTo("Test User");
    }

    @Test
    void findByEmail_ShouldReturnEmpty_WhenEmailDoesNotExist() {
        Optional<Utilisateur> found = utilisateurRepository.findByEmail("nonexistent@example.com");

        assertThat(found).isEmpty();
    }
}
