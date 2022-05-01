package com.kyc.campaigns.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
