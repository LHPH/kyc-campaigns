package com.kyc.campaigns.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    private Date startCampaignDate;

    @Column(name = "END_CAMPAIGN_DATE")
    @Temporal(TemporalType.DATE)
    private Date endCampaignDate;

    @Column(name = "ACTIVE")
    private Boolean active;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "campaignEntity")
    private List<OfferEntity> offerList;
}
