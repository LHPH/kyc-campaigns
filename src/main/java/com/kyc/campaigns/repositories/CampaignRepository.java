package com.kyc.campaigns.repositories;

import com.kyc.campaigns.entity.CampaignEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignRepository extends JpaRepository<CampaignEntity,Integer> {
}
