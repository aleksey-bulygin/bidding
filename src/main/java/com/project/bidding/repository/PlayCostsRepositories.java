package com.project.bidding.repository;

import com.project.bidding.entity.PlayCosts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayCostsRepositories extends JpaRepository<PlayCosts, Long> {
}
