package com.kyc.campaigns.services;

import com.kyc.campaigns.entity.CampaignEntity;
import com.kyc.campaigns.entity.ErrorOffersEntity;
import com.kyc.campaigns.entity.OfferTemporalEntity;
import com.kyc.campaigns.mappers.CampaignMapper;
import com.kyc.campaigns.model.CampaignData;
import com.kyc.campaigns.model.CampaignOfferData;
import com.kyc.campaigns.repositories.CampaignRepository;
import com.kyc.campaigns.repositories.ErrorOffersRepository;
import com.kyc.campaigns.repositories.OfferRepository;
import com.kyc.campaigns.repositories.OfferTemporalRepository;
import com.kyc.core.exception.KycRestException;
import com.kyc.core.model.MessageData;
import com.kyc.core.model.web.RequestData;
import com.kyc.core.model.web.ResponseData;
import com.kyc.core.properties.KycMessages;
import com.kyc.core.util.DateUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.kyc.campaigns.constants.AppConstants.MSG_CODE_003;
import static com.kyc.campaigns.constants.AppConstants.MSG_CODE_004;
import static com.kyc.campaigns.constants.AppConstants.MSG_CODE_005;

@Service
public class CampaignService {

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private CampaignMapper campaignMapper;

    @Autowired
    private OfferTemporalRepository offerTemporalRepository;

    @Autowired
    private ErrorOffersRepository errorOffersRepository;

    @Autowired
    private KycMessages kycMessages;

    public ResponseData<Boolean> confirmCampaign(RequestData<CampaignData> req){

        CampaignData campaignData = req.getBody();
        Map<String,Object> pathParams = req.getPathParams();
        String keyPreCampaign = pathParams.get("key").toString();

       try{
           Optional<OfferTemporalEntity> opOffersTemporal = offerTemporalRepository.findFirstPreOfferByKeyPreCampaign(keyPreCampaign);
           if(opOffersTemporal.isPresent()){

               List<ErrorOffersEntity> errors = errorOffersRepository.getErrors(keyPreCampaign);
               if(errors.isEmpty()){

                   String campaignName = campaignData.getName();
                   Date startDate = DateUtil.stringToDate(campaignData.getStartDate(),"yyyy-MM-dd");
                   Date endDate = DateUtil.stringToDate(campaignData.getFinishDate(),"yyyy-MM-dd");

                   campaignRepository.createNewKycCampaign(keyPreCampaign,campaignName,startDate,endDate,5);
                   return ResponseData.of(true);
               }
               MessageData messageData = kycMessages.getMessage(MSG_CODE_005);
               throw KycRestException.builderRestException()
                       .errorData(messageData)
                       .status(HttpStatus.UNPROCESSABLE_ENTITY)
                       .inputData(req)
                       .build();
           }
           MessageData messageData = kycMessages.getMessage(MSG_CODE_004);
           throw KycRestException.builderRestException()
                   .errorData(messageData)
                   .status(HttpStatus.UNPROCESSABLE_ENTITY)
                   .inputData(req)
                   .build();
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

    public ResponseData<List<CampaignData>> getCampaigns(RequestData<Void> req){

        try{
            ;
            Map<String,String> queryParams = ObjectUtils.defaultIfNull(req.getQueryParams(),new HashMap<>());
            Integer campaignId = NumberUtils.toInt(queryParams.get("campaignId"),0);
            List<CampaignData> campaigns;

            if(campaignId!=0){

                Optional<CampaignEntity> opCampaign = campaignRepository.findById(campaignId);
                campaigns = new ArrayList<>();
                opCampaign.ifPresent(campaignEntity -> campaigns.add(campaignMapper.toCampaignData(campaignEntity)));
            }
            else{
                List<CampaignEntity> list = campaignRepository.findAll();

                campaigns = list.stream()
                        .map(e -> campaignMapper.toCampaignData(e))
                        .collect(Collectors.toList());
            }
            return ResponseData.of(campaigns);
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

    @Transactional
    public ResponseData<Boolean> activateCampaign(RequestData<Void> req){

        try{
            Map<String,Object> pathParams = req.getPathParams();
            Map<String,String> queryParams = req.getQueryParams();

            Integer id = Integer.parseInt(pathParams.get("id").toString());
            Boolean status = Boolean.parseBoolean(queryParams.get("active"));

            if(Boolean.TRUE.equals(status)){

                campaignRepository.updateStatusCampaign(id,true);
                offerRepository.updateStatusCampaignOffers(id,2);
            }
            else{
                campaignRepository.updateStatusCampaign(id,false);
            }
            return ResponseData.of(true);
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

    public ResponseData<CampaignOfferData> getOffersByCampaign(RequestData<Void> req){

        try{

            Map<String,Object> pathParams = ObjectUtils.defaultIfNull(req.getPathParams(),new HashMap<>());
            Integer campaignId = NumberUtils.toInt(pathParams.get("id").toString(),0);

            Optional<CampaignEntity> opCampaign = campaignRepository.getOffersByCampaign(campaignId);
            if(opCampaign.isPresent()){

                CampaignOfferData result = campaignMapper.toCampaignOfferData(opCampaign.get());
                return ResponseData.of(result);
            }
            return ResponseData.of(null,HttpStatus.NO_CONTENT);
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
