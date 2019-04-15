package com.project.bidding.repository;

import com.project.bidding.entity.Nomenclature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NomeclatureRepositories extends JpaRepository<Nomenclature, Long> {
}
