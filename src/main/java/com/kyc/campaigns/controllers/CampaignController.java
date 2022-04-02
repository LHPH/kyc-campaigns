package com.kyc.campaigns.controllers;

import com.kyc.campaigns.delegate.CampaignDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class CampaignController {

    @Autowired
    private CampaignDelegate delegate;

    @PostMapping("/pre-offers")
    public void uploadPreOffers(MultipartFile file){

    }

    @PostMapping("/campaign")
    public void confirmCampaign(){

    }
}
