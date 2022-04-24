package com.kyc.campaigns.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "KYC_TEMP_OFFERS_ERRORS")
@Setter
@Getter
public class ErrorOffersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TEMP_OFFER",referencedColumnName = "ID")
    private OfferTemporalEntity offerTemporalEntity;

    @Column(name = "ERROR_FIELD")
    private String errorField;

    @Column(name = "ERROR_DETAIL")
    private String errorDetail;
}
