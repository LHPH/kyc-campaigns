package com.kyc.campaigns.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

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
