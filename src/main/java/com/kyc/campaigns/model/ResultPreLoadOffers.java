package com.kyc.campaigns.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class ResultPreLoadOffers {

    private String keyPreCampaign;
    private Integer successfullyLoadedPreOffers = 0;
    private Integer unsuccessfullyLoadedPreOffers = 0;
    private List<ErrorPreOfferDetail> errorPreOffers = new ArrayList<>();
}
