package com.kyc.campaigns.controllers;

import com.kyc.campaigns.delegate.CampaignDelegate;
import com.kyc.campaigns.model.ResultPreLoadOffers;
import com.kyc.core.model.web.RequestData;
import com.kyc.core.model.web.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class CampaignController {

    @Autowired
    private CampaignDelegate delegate;

    @PostMapping("/pre-offers")
    public ResponseEntity<ResponseData<ResultPreLoadOffers>> uploadPreOffers(@RequestParam("file") MultipartFile file){

        RequestData<MultipartFile> req = RequestData.<MultipartFile>builder()
                .body(file)
                .build();
        return delegate.uploadFileOfPreOffers(req);
    }

    @PostMapping("/campaign/{key}")
    public void confirmCampaign(@PathVariable("key") String key){

        RequestData<String> req = RequestData.<String>builder()
                .body(key)
                .build();
    }
}
