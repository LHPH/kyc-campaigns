package com.kyc.campaigns.controllers;

import com.kyc.campaigns.delegate.CampaignDelegate;
import com.kyc.campaigns.model.CampaignData;
import com.kyc.campaigns.model.CampaignOfferData;
import com.kyc.core.model.web.RequestData;
import com.kyc.core.model.web.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/campaigns")
public class CampaignController {

    @Autowired
    private CampaignDelegate delegate;

    @PostMapping("/{key}")
    public ResponseEntity<ResponseData<Boolean>> confirmCampaign(@PathVariable("key") String key,
                                                   @RequestBody CampaignData data){

        RequestData<CampaignData> req = RequestData.<CampaignData>builder()
                .body(data)
                .pathParams(Collections.singletonMap("key",key))
                .build();
        return delegate.confirmCampaign(req);
    }

    @PostMapping("/activation/{id}")
    public ResponseEntity<ResponseData<Boolean>> activationCampaign(@PathVariable("id") Integer id,
                                                                    @RequestParam("active") Boolean status){

        RequestData<Void> req = RequestData.<Void>builder()
                .pathParams(Collections.singletonMap("id",id))
                .queryParams(Collections.singletonMap("active",status.toString()))
                .build();
        return delegate.activateCampaign(req);
    }

    @GetMapping
    public ResponseEntity<ResponseData<List<CampaignData>>> getCampaigns(@RequestParam(name = "campaignId",required = false) Integer campaignId){

        RequestData<Void> req = RequestData.<Void>builder()
                .queryParams(Collections.singletonMap("campaignId", Objects.toString(campaignId,"0")))
                .build();
        return delegate.getCampaigns(req);
    }

    @GetMapping("/{id}/offers")
    public ResponseEntity<ResponseData<CampaignOfferData>> getOffersByCampaign(@PathVariable("id") Integer id){

        RequestData<Void> req = RequestData.<Void>builder()
                .pathParams(Collections.singletonMap("id",id))
                .build();
        return delegate.getOffersByCampaign(req);
    }
}
