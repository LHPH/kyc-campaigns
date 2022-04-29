package com.kyc.campaigns.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class OfferData {

    private Integer id;
    private Integer customerNumber;
    private String customerEmail;
    private String offerName;
    private String offerDescription;
    private String promotionalCode;
    private Integer discount;
    private String reward;
    private String startDate;
    private String finishDate;
    private String status;
    private String termAndConditionsLink;
}
