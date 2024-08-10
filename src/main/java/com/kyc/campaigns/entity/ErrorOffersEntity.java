package com.kyc.campaigns.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

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
