package com.kyc.campaigns.mappers;

import com.kyc.campaigns.entity.CampaignEntity;
import com.kyc.campaigns.model.CampaignData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
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
}
