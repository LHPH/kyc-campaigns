package com.kyc.campaigns.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Setter
@Getter
@MappedSuperclass
public class BaseOfferEntity {

    @Column(name = "CUSTOMER_NUMBER")
    private Integer customerNumber;

    @Column(name = "CUSTOMER_EMAIL")
    private String customerEmail;

    @Column(name = "OFFER_NAME")
    private String offerName;

    @Column(name = "OFFER_DESCRIPTION")
    private String offerDescription;

    @Column(name = "PROMOTIONAL_CODE")
    private String promotionalCode;

    @Column(name = "DISCOUNT")
    private Integer discount;

    @Column(name = "REWARD")
    private String reward;

    @Column(name = "START_DATE")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name = "END_DATE")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column(name = "TERM_AND_CONDITIONS_LINK")
    private String termAndConditionsLink;


}
