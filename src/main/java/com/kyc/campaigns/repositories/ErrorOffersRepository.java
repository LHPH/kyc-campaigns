package com.kyc.campaigns.repositories;

import com.kyc.campaigns.entity.ErrorOffersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ErrorOffersRepository extends JpaRepository<ErrorOffersEntity,Long> {

    @Query("from ErrorOffersEntity e join fetch e.offerTemporalEntity o where o.keyPreCampaign=:key")
    List<ErrorOffersEntity> getErrors(@Param("key") String keyCampaign);
}
