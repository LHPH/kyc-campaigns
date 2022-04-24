package com.kyc.campaigns.mappers;

import com.kyc.campaigns.entity.ErrorOffersEntity;
import com.kyc.campaigns.model.ErrorPreOfferDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ErrorDetailMapper {


    @Mappings({
            @Mapping(target = "record",source = "source.offerTemporalEntity.recordExcel"),
            @Mapping(target = "errorField",source = "source.errorField"),
            @Mapping(target = "errorDetail",source = "source.errorDetail")
    })
    ErrorPreOfferDetail mapperToModel(ErrorOffersEntity source);
}
