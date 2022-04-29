package com.kyc.campaigns.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CampaignData {

    private Integer id;
    private String name;
    private String creationDate;
    private String startDate;
    private String finishDate;
    private Boolean active;
}
