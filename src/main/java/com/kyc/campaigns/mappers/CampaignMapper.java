package com.kyc.campaigns.mappers;

import com.kyc.campaigns.entity.CampaignEntity;
import com.kyc.campaigns.model.CampaignData;
import com.kyc.campaigns.model.CampaignOfferData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring",uses = {OfferMapper.class})
public interface CampaignMapper {

    @Mappings({
            @Mapping(target = "id",source = "source.id"),
            @Mapping(target = "name",source = "source.campaignName"),
            @Mapping(target = "creationDate",source = "source.creationDate",dateFormat = "yyyy-MM-dd"),
            @Mapping(target = "startDate",source = "source.campaignStartDate",dateFormat = "yyyy-MM-dd"),
            @Mapping(target = "finishDate",source = "source.campaignFinishDate",dateFormat = "yyyy-MM-dd"),
            @Mapping(target = "active",source = "source.active")
    })
    CampaignData toCampaignData(CampaignEntity source);

    @Mappings({
            @Mapping(target = "id",source = "source.id"),
            @Mapping(target = "name",source = "source.campaignName"),
            @Mapping(target = "offers",source = "source.offerList")
    })
    CampaignOfferData toCampaignOfferData(CampaignEntity source);
}
