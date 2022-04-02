package com.kyc.campaigns.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "KYC_TEMPORAL_OFFERS")
@Setter
@Getter
public class OfferTemporalEntity extends BaseOfferEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SEQ_KYC_TEMPORAL_OFFERS")
    private Integer id;

    @Column(name = "ERROR_DETAIL")
    private String errorDetail;
}
