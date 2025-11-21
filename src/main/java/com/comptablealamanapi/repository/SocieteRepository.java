package com.comptablealamanapi.repository;

import com.comptablealamanapi.entity.Societe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocieteRepository extends JpaRepository<Societe, Long> {
}