package com.kyc.campaigns.repositories;

import com.kyc.campaigns.entity.OfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<OfferEntity,Integer> {
}
