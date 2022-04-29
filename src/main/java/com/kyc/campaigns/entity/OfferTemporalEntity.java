package com.kyc.campaigns.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.SequenceGenerator;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name = "KYC_TEMP_OFFERS")
@NamedStoredProcedureQueries(
        @NamedStoredProcedureQuery(
                name = "SP_CLEAN_KYC_TEMP_OFFERS",
                procedureName = "SP_CLEAN_KYC_TEMP_OFFERS",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.IN,name = "P_CHUNK_SIZE",type = Integer.class),
                        @StoredProcedureParameter(mode = ParameterMode.INOUT,name = "P_ERROR_CODE",type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.INOUT,name = "P_ERROR_DETAIL",type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.INOUT,name = "P_DELETED_ROWS",type = Integer.class)
                })
)
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

    @Column(name = "PROCESSED")
    private Boolean processed;

}
