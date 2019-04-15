package com.project.bidding.repository;

import com.project.bidding.entity.AdminResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminResourceRepositories extends JpaRepository<AdminResource, Long> {
}
