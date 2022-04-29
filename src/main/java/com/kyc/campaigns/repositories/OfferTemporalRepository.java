package com.kyc.campaigns.repositories;

import com.kyc.campaigns.entity.OfferTemporalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.Map;
import java.util.Optional;

public interface OfferTemporalRepository extends JpaRepository<OfferTemporalEntity,Integer> {

    Optional<OfferTemporalEntity> findByKeyPreCampaign(String keyPreCampaign);

    @Procedure(name = "SP_CLEAN_KYC_TEMP_OFFERS")
    Map<String,Object> cleanTempOffers(@Param("P_CHUNK_SIZE") Integer chunkSize);
}
