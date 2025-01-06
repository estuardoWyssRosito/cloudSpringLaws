package com.kappacomputacion.spring.ms.laws.repositories;

import com.kappacomputacion.spring.ms.laws.entities.LawsMain;
import com.kappacomputacion.spring.ms.laws.entities.TextoLey;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TextoLeyRepository extends JpaRepository<TextoLey, String> {
    List<TextoLey> findByItemIdOrderByFuenteArticuloNumero(@NotNull LawsMain itemId);
}