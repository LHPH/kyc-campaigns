package com.kyc.campaigns.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "KYC_TEMP_OFFERS")
@Setter
@Getter
public class OfferTemporalEntity extends BaseOfferEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SEQ_KYC_TEMP_OFFERS")
    @SequenceGenerator(name = "SEQ_KYC_TEMP_OFFERS",sequenceName = "SEQ_KYC_TEMP_OFFERS",allocationSize = 10)
    @Column(name ="ID")
    private Integer id;

    @Column(name = "RECORD_EXCEL")
    private Integer recordExcel;

    @Column(name = "KEY_PRE_CAMPAIGN")
    private String keyPreCampaign;

}
