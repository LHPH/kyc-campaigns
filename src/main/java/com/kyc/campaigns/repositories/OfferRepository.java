package com.kyc.campaigns.repositories;

import com.kyc.campaigns.entity.OfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OfferRepository extends JpaRepository<OfferEntity,Integer> {

    @Modifying
    @Query(value = "UPDATE KYC_OFFERS SET STATUS=:status WHERE ID_CAMPAIGN=:campaignId AND STATUS = 1",nativeQuery = true)
    int updateStatusCampaignOffers(@Param("campaignId") Integer campaignId, @Param("status") Integer status);

    @Query("from OfferEntity o where o.offerStatus.id!=1 and o.customerNumber=:customerId")
    List<OfferEntity> getOffersFromActiveCampaignForCustomer(@Param("customerId") Integer customerId);

    @Query("from OfferEntity o where o.customerNumber=:customerId and o.id=:offerId")
    Optional<OfferEntity> getCustomerOffer(@Param("customerId") Integer customerId, @Param("offerId") Integer offerId);
}
