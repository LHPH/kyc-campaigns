package com.kyc.campaigns.mappers;

import com.kyc.campaigns.entity.OfferEntity;
import com.kyc.campaigns.entity.OfferStatus;
import com.kyc.campaigns.model.OfferData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface OfferMapper {

    @Mappings({
            @Mapping(target = "id",source = "source.id"),
            @Mapping(target = "customerNumber",source = "source.customerNumber"),
            @Mapping(target = "customerEmail",source = "source.customerEmail"),
            @Mapping(target = "offerName",source = "source.offerName"),
            @Mapping(target = "offerDescription",source = "source.offerDescription"),
            @Mapping(target = "promotionalCode",source = "source.promotionalCode"),
            @Mapping(target = "discount",source = "source.discount"),
            @Mapping(target = "reward",source = "source.reward"),
            @Mapping(target = "startDate",source = "source.startDate",dateFormat = "yyyy-MM-dd"),
            @Mapping(target = "finishDate",source = "source.endDate",dateFormat = "yyyy-MM-dd"),
            @Mapping(target = "status",source = "source.offerStatus",qualifiedByName = "getStatusAsDescription"),
            @Mapping(target = "termAndConditionsLink",source = "source.termAndConditionsLink"),
    })
    OfferData toOfferData(OfferEntity source);

    @Named("getStatusAsDescription")
    static String getStatusAsDescription(OfferStatus status){

        if(status!=null){
            return status.getDescription();
        }
        return null;
    }
}
