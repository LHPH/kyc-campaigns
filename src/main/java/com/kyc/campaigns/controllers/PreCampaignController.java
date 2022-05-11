package com.kyc.campaigns.controllers;

import com.kyc.campaigns.delegate.PreCampaignDelegate;
import com.kyc.campaigns.model.ResultPreLoadOffers;
import com.kyc.core.model.web.RequestData;
import com.kyc.core.model.web.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/pre-offers")
public class PreCampaignController {

    @Autowired
    private PreCampaignDelegate delegate;

    @PostMapping
    public ResponseEntity<ResponseData<ResultPreLoadOffers>> uploadPreOffers(@RequestParam("file") MultipartFile file){

        RequestData<MultipartFile> req = RequestData.<MultipartFile>builder()
                .body(file)
                .build();
        return delegate.uploadFileOfPreOffers(req);
    }

    @PostMapping("/cleanness")
    public ResponseEntity<ResponseData<Boolean>> cleanness(){

        return delegate.cleanness();
    }
}
