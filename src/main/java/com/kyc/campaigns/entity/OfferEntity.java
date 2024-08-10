package com.kyc.campaigns.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "KYC_OFFERS")
@Setter
@Getter
public class OfferEntity extends BaseOfferEntity{

    @Id
    private Integer id;

    @OneToOne
    @JoinColumn(name = "STATUS",referencedColumnName = "ID")
    private OfferStatus offerStatus;

    @ManyToOne
    @JoinColumn(name = "ID_CAMPAIGN",referencedColumnName = "ID")
    private CampaignEntity campaignEntity;
}
