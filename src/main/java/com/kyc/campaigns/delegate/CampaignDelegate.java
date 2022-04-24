package com.kyc.campaigns.delegate;

import com.kyc.campaigns.model.ResultPreLoadOffers;
import com.kyc.campaigns.services.PreCampaignService;
import com.kyc.core.model.web.RequestData;
import com.kyc.core.model.web.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class CampaignDelegate {

    @Autowired
    private PreCampaignService service;

    public ResponseEntity<ResponseData<ResultPreLoadOffers>> uploadFileOfPreOffers(RequestData<MultipartFile> req){

        return service.uploadFile(req).toResponseEntity();
    }
}
