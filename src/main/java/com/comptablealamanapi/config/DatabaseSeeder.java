package com.comptablealamanapi.config;

import com.comptablealamanapi.entity.Societe;
import com.comptablealamanapi.entity.Utilisateur;
import com.comptablealamanapi.enums.Role;
import com.comptablealamanapi.repository.SocieteRepository;
import com.comptablealamanapi.repository.UtilisateurRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DatabaseSeeder {

    @Bean
    CommandLineRunner seedData(
            SocieteRepository societeRepository,
            UtilisateurRepository utilisateurRepository,
            PasswordEncoder passwordEncoder
    ) {
        return (args) -> {
            // societes
            Societe s1 = new Societe();
            s1.setRaisonSociale("Test Company1");
            s1.setIce("123456789");
            s1.setAdresse("Casablanca");
            s1.setTelephone("0600000000");
            s1.setEmailContact("contact1@gmail.com");
            societeRepository.save(s1);

            Societe s2 = new Societe();
            s2.setRaisonSociale("Test Company2");
            s2.setIce("123456700");
            s2.setAdresse("Nador");
            s2.setTelephone("0610000000");
            s2.setEmailContact("contact2@gmail.com");
            societeRepository.save(s2);

            Societe s3 = new Societe();
            s3.setRaisonSociale("Test Company3");
            s3.setIce("123456000");
            s3.setAdresse("Nador");
            s3.setTelephone("0612000000");
            s3.setEmailContact("contact3@gmail.com");
            societeRepository.save(s3);

            // societe user
            Utilisateur su1 = new Utilisateur();
            su1.setEmail("admin1@gmail.com");
            su1.setMotDePasse(passwordEncoder.encode("123456"));
            su1.setNomComplet("Admin societe 1");
            su1.setRole(Role.SOCIETE);
            su1.setStatut(true);
            su1.setSociete(s1);
            utilisateurRepository.save(su1);

            Utilisateur su2 = new Utilisateur();
            su2.setEmail("admin2@gmail.com");
            su2.setMotDePasse(passwordEncoder.encode("123456"));
            su2.setNomComplet("Admin societe 2");
            su2.setRole(Role.SOCIETE);
            su2.setStatut(true);
            su2.setSociete(s2);
            utilisateurRepository.save(su2);

            // 3) comptable user
            Utilisateur comptable1 = new Utilisateur();
            comptable1.setEmail("comptable1@gmail.com");
            comptable1.setMotDePasse(passwordEncoder.encode("123456"));
            comptable1.setNomComplet("Omar Comptable");
            comptable1.setRole(Role.COMPTABLE);
            comptable1.setStatut(true);
            comptable1.setSociete(null);
            utilisateurRepository.save(comptable1);

            Utilisateur comptable2 = new Utilisateur();
            comptable2.setEmail("comptable2@gmail.com");
            comptable2.setMotDePasse(passwordEncoder.encode("123456"));
            comptable2.setNomComplet("Adil Comptable");
            comptable2.setRole(Role.COMPTABLE);
            comptable2.setStatut(true);
            comptable2.setSociete(null);
            utilisateurRepository.save(comptable2);

            System.out.println(">>> SEEDING DONE <<<");
        };
    }
}
