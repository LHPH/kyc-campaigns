package com.kyc.campaigns.delegate;

import com.kyc.campaigns.model.CampaignData;
import com.kyc.campaigns.model.CampaignOfferData;
import com.kyc.campaigns.model.ResultPreLoadOffers;
import com.kyc.campaigns.services.CampaignService;
import com.kyc.campaigns.services.PreCampaignService;
import com.kyc.core.model.web.RequestData;
import com.kyc.core.model.web.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class CampaignDelegate {

    @Autowired
    private CampaignService campaignService;

    public ResponseEntity<ResponseData<Boolean>> confirmCampaign(RequestData<CampaignData> req){

        return campaignService.confirmCampaign(req).toResponseEntity();
    }

    public ResponseEntity<ResponseData<List<CampaignData>>> getCampaigns(RequestData<Void> req){

        return campaignService.getCampaigns(req).toResponseEntity();
    }

    public ResponseEntity<ResponseData<Boolean>> activateCampaign(RequestData<Void> req){

        return campaignService.activateCampaign(req).toResponseEntity();
    }

    public ResponseEntity<ResponseData<CampaignOfferData>> getOffersByCampaign(RequestData<Void> req){

        return campaignService.getOffersByCampaign(req).toResponseEntity();
    }
}
