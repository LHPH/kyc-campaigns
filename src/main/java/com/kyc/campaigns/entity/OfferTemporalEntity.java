package com.kyc.campaigns.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedStoredProcedureQueries;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.StoredProcedureParameter;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

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
