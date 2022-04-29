package com.kyc.campaigns.repositories;

import com.kyc.campaigns.entity.OfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OfferRepository extends JpaRepository<OfferEntity,Integer> {

    @Modifying
    @Query(value = "UPDATE KYC_OFFERS SET STATUS=:status WHERE ID_CAMPAIGN=:campaignId AND STATUS = 1",nativeQuery = true)
    int updateStatusCampaignOffers(@Param("campaignId") Integer campaignId, @Param("status") Integer status);
}
