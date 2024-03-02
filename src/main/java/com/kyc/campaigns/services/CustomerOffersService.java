package com.kyc.campaigns.services;

import com.kyc.campaigns.entity.OfferEntity;
import com.kyc.campaigns.mappers.OfferMapper;
import com.kyc.campaigns.model.OfferData;
import com.kyc.campaigns.repositories.OfferRepository;
import com.kyc.core.exception.KycRestException;
import com.kyc.core.model.MessageData;
import com.kyc.core.model.web.RequestData;
import com.kyc.core.model.web.ResponseData;
import com.kyc.core.properties.KycMessages;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.kyc.campaigns.constants.AppConstants.MSG_CODE_003;

@Service
public class CustomerOffersService {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private OfferMapper offerMapper;

    @Autowired
    private KycMessages kycMessages;

    public ResponseData<List<OfferData>> getOffersByCustomer(RequestData<Void> req){

       try{
           Map<String,Object> pathParams = req.getPathParams();
           Integer customerId = NumberUtils.toInt(pathParams.get("customerId").toString());

           List<OfferEntity> offers = offerRepository.getOffersFromActiveCampaignForCustomer(customerId);

           List<OfferData> result = offers.stream()
                   .map(offerMapper::toOfferData)
                   .collect(Collectors.toList());

           return ResponseData.of(result);
       }
       catch(DataAccessException ex){

           MessageData messageData = kycMessages.getMessage(MSG_CODE_003);
           throw KycRestException.builderRestException()
                   .errorData(messageData)
                   .status(HttpStatus.SERVICE_UNAVAILABLE)
                   .exception(ex)
                   .inputData(req)
                   .build();
       }
    }

    public ResponseData<OfferData> getOfferByCustomer(RequestData<Void> req){

        try{
            Map<String,Object> pathParams = req.getPathParams();
            Integer customerId = NumberUtils.toInt(pathParams.get("customerId").toString());
            Integer offerId = NumberUtils.toInt(pathParams.get("offerId").toString());

            Optional<OfferEntity> opOffer = offerRepository.getCustomerOffer(customerId,offerId);

            if(opOffer.isPresent()){
                return ResponseData.of(offerMapper.toOfferData(opOffer.get()));
            }
            else{
                return ResponseData.of(null,HttpStatus.NO_CONTENT);
            }
        }
        catch(DataAccessException ex){

            MessageData messageData = kycMessages.getMessage(MSG_CODE_003);
            throw KycRestException.builderRestException()
                    .errorData(messageData)
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .exception(ex)
                    .inputData(req)
                    .build();
        }
    }
}
