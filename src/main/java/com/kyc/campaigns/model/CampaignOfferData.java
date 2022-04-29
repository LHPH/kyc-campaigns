package com.kyc.campaigns.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class CampaignOfferData {

    private Integer id;
    private String name;
    private List<OfferData> offers;

}
