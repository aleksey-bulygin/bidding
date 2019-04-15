package com.project.bidding.service;

import com.project.bidding.entity.Nomenclature;

import java.util.List;
import java.util.Optional;

public interface NomenclatureService {

    Optional<Nomenclature> save(Nomenclature nomenclature);

    Optional<List<Nomenclature>> saveAll(List<Nomenclature> nomenclatures);

    List<Nomenclature> findAll();

    Optional<Nomenclature> findById(Long id);

    Boolean remove(Nomenclature nomenclature);

    Boolean removeAll();
}
