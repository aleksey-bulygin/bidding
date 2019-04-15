package com.project.bidding.repository;

import com.project.bidding.entity.Costs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CostsRepositories extends JpaRepository<Costs, Long> {
}
