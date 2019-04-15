package com.project.bidding.service;

import com.project.bidding.entity.Costs;
import com.project.bidding.entity.PlayCosts;
import com.project.bidding.repository.CostsRepositories;
import com.project.bidding.repository.PlayCostsRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayService {

    private final CostsRepositories costsRepositories;
    private final PlayCostsRepositories playCostsRepositories;

    @Autowired
    public PlayService(CostsRepositories costsRepositories, PlayCostsRepositories playCostsRepositories) {
        this.costsRepositories = costsRepositories;
        this.playCostsRepositories = playCostsRepositories;
    }

    public Optional<Costs> saveCost(Costs costs) {
        return Optional.of(costsRepositories.save(costs));
    }

    public Optional<PlayCosts> savePlayCost(PlayCosts playCosts) {
        return Optional.of(playCostsRepositories.save(playCosts));
    }

    public void removeCosts(Costs costs) {
        costsRepositories.delete(costs);
    }

    public void removePlayCosts(PlayCosts playCosts) {
        playCostsRepositories.delete(playCosts);
    }
}
