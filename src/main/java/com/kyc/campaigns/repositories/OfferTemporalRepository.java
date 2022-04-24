package com.kyc.campaigns.repositories;

import com.kyc.campaigns.entity.OfferTemporalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface OfferTemporalRepository extends JpaRepository<OfferTemporalEntity,Integer> {
}
