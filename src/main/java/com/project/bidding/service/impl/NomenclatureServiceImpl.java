package com.project.bidding.service.impl;

import com.project.bidding.entity.Nomenclature;
import com.project.bidding.repository.NomeclatureRepositories;
import com.project.bidding.service.NomenclatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service(value = "NomenclatureService")
public class NomenclatureServiceImpl implements NomenclatureService {

    private final NomeclatureRepositories nomeclatureRepositories;

    @Autowired
    public NomenclatureServiceImpl(NomeclatureRepositories nomeclatureRepositories) {
        this.nomeclatureRepositories = nomeclatureRepositories;
    }

    @Override
    public Optional<Nomenclature> save(Nomenclature nomenclature) {
        return Optional.of(nomeclatureRepositories.save(nomenclature));
    }

    @Override
    public Optional<List<Nomenclature>> saveAll(List<Nomenclature> nomenclatures) {
        return Optional.of(nomeclatureRepositories.saveAll(nomenclatures));
    }

    @Override
    public List<Nomenclature> findAll() {
        return nomeclatureRepositories.findAll();
    }

    @Override
    public Optional<Nomenclature> findById(Long id) {
        return nomeclatureRepositories.findById(id);
    }

    @Override
    public Boolean remove(Nomenclature nomenclature) {
        nomeclatureRepositories.delete(nomenclature);

        return true;
    }

    @Override
    public Boolean removeAll() {
        nomeclatureRepositories.deleteAll();

        return true;
    }
}
