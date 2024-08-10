package com.kyc.campaigns.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "KYC_CAMPAIGN")
@Setter
@Getter
public class CampaignEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "CAMPAIGN_NAME")
    private String campaignName;

    @Column(name = "CREATION_DATE")
    @Temporal(TemporalType.DATE)
    private Date creationDate;

    @Column(name = "START_CAMPAIGN_DATE")
    @Temporal(TemporalType.DATE)
    private Date campaignStartDate;

    @Column(name = "END_CAMPAIGN_DATE")
    @Temporal(TemporalType.DATE)
    private Date campaignFinishDate;

    @Column(name = "ACTIVE")
    private Boolean active;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "campaignEntity")
    private List<OfferEntity> offerList;
}
