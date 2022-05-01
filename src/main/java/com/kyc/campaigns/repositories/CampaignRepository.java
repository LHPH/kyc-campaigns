package com.kyc.campaigns.repositories;

import com.kyc.campaigns.entity.CampaignEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;

public interface CampaignRepository extends JpaRepository<CampaignEntity,Integer> {

    @Modifying
    @Query(value = "UPDATE KYC_CAMPAIGN SET ACTIVE=:active WHERE ID=:id",nativeQuery = true)
    int updateStatusCampaign(@Param("id") Integer id, @Param("active") Boolean active);

    @Procedure(procedureName = "SP_CREATE_NEW_KYC_CAMPAIGN",outputParameterName = "P_SUCCESS")
    Boolean createNewKycCampaign(@Param("P_KEY_PRE_CAMPAIGN") String keyPrecampaign,
                           @Param("P_CAMPAIGN_NAME") String campaignName,
                           @Param("P_START_DATE_CAMPAIGN") Date startDate,
                           @Param("P_END_DATE_CAMPAIGN") Date endDate,
                           @Param("P_CHUNK_SIZE") Integer chunkSize);

    @Query("from CampaignEntity c join fetch c.offerList where c.id=:campaignId")
    Optional<CampaignEntity> getOffersByCampaign(@Param("campaignId") Integer campaignId);
}
