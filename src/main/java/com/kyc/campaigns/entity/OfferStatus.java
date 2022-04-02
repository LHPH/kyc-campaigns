package com.kyc.campaigns.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "KYC_STATUS_OFFER")
@Setter
@Getter
public class OfferStatus {

    @Id
    private Integer id;

    @Column(name = "DESCRIPTION")
    private String description;
}
