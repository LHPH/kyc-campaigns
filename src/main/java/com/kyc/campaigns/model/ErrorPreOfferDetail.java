package com.kyc.campaigns.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ErrorPreOfferDetail {

    private Integer record;
    private String errorField;
    private String errorDetail;
}
