package com.kyc.campaigns.services;

import com.kyc.campaigns.entity.CampaignEntity;
import com.kyc.campaigns.entity.ErrorOffersEntity;
import com.kyc.campaigns.entity.OfferTemporalEntity;
import com.kyc.campaigns.mappers.CampaignMapper;
import com.kyc.campaigns.model.CampaignData;
import com.kyc.campaigns.model.CampaignOfferData;
import com.kyc.campaigns.repositories.CampaignRepository;
import com.kyc.campaigns.repositories.ErrorOffersRepository;
import com.kyc.campaigns.repositories.OfferRepository;
import com.kyc.campaigns.repositories.OfferTemporalRepository;
import com.kyc.core.exception.KycRestException;
import com.kyc.core.model.web.RequestData;
import com.kyc.core.model.web.ResponseData;
import com.kyc.core.properties.KycMessages;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CampaignServiceTest {

    @InjectMocks
    private CampaignService service;

    @Mock
    private CampaignRepository campaignRepository;

    @Mock
    private OfferRepository offerRepository;

    @Mock
    private CampaignMapper campaignMapper;

    @Mock
    private OfferTemporalRepository offerTemporalRepository;

    @Mock
    private ErrorOffersRepository errorOffersRepository;

    @Mock
    private KycMessages kycMessages;


    @BeforeAll
    public static void init(){
        MockitoAnnotations.openMocks(CampaignServiceTest.class);
    }

    @BeforeEach
    public void setUp(){

        CampaignData campaignData = new CampaignData();
        campaignData.setName("campaign");
        campaignData.setCreationDate("2020-10-10");
        campaignData.setFinishDate("2020-10-11");


    }

    @Test
    public void confirmCampaign_processingValidCampaign_successfulGeneratingNewCampaign(){

        CampaignData campaignData = new CampaignData();
        campaignData.setName("campaign");
        campaignData.setStartDate("2020-10-10");
        campaignData.setFinishDate("2020-10-11");

        RequestData<CampaignData> req = RequestData.<CampaignData>builder()
                .body(campaignData)
                .pathParams(Collections.singletonMap("key","key"))
                .build();

        given(offerTemporalRepository.findFirstPreOfferByKeyPreCampaign("key"))
                .willReturn(Optional.of(new OfferTemporalEntity()));
        given(errorOffersRepository.getErrors("key")).willReturn(new ArrayList<>());


        service.confirmCampaign(req);
        verify(campaignRepository,times(1))
                .createNewKycCampaign(anyString(),anyString(),any(Date.class),any(Date.class),anyInt());
    }

    @Test
    public void confirmCampaign_preCampaignKeyNonExistent_throwError(){

        KycRestException ex = Assertions.assertThrows(KycRestException.class,()->{

            CampaignData campaignData = new CampaignData();
            campaignData.setName("campaign");
            campaignData.setStartDate("2020-10-10");
            campaignData.setFinishDate("2020-10-11");

            RequestData<CampaignData> req = RequestData.<CampaignData>builder()
                    .body(campaignData)
                    .pathParams(Collections.singletonMap("key","key"))
                    .build();

            given(offerTemporalRepository.findFirstPreOfferByKeyPreCampaign("key"))
                    .willReturn(Optional.empty());

            service.confirmCampaign(req);
        });
        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY,ex.getStatus());
    }

    @Test
    public void confirmCampaign_campaignWithErrors_throwError(){

        KycRestException ex = Assertions.assertThrows(KycRestException.class,()->{

            CampaignData campaignData = new CampaignData();
            campaignData.setName("campaign");
            campaignData.setStartDate("2020-10-10");
            campaignData.setFinishDate("2020-10-11");

            RequestData<CampaignData> req = RequestData.<CampaignData>builder()
                    .body(campaignData)
                    .pathParams(Collections.singletonMap("key","key"))
                    .build();

            given(offerTemporalRepository.findFirstPreOfferByKeyPreCampaign("key"))
                    .willReturn(Optional.of(new OfferTemporalEntity()));
            given(errorOffersRepository.getErrors("key"))
                    .willReturn(Collections.singletonList(new ErrorOffersEntity()));

            service.confirmCampaign(req);
        });
        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY,ex.getStatus());
    }

    @Test
    public void confirmCampaign_databaseThrowError_throwError(){

        KycRestException ex = Assertions.assertThrows(KycRestException.class,()->{

            CampaignData campaignData = new CampaignData();
            campaignData.setName("campaign");
            campaignData.setStartDate("2020-10-10");
            campaignData.setFinishDate("2020-10-11");

            RequestData<CampaignData> req = RequestData.<CampaignData>builder()
                    .body(campaignData)
                    .pathParams(Collections.singletonMap("key","key"))
                    .build();

            given(offerTemporalRepository.findFirstPreOfferByKeyPreCampaign("key"))
                    .willReturn(Optional.of(new OfferTemporalEntity()));
            given(errorOffersRepository.getErrors("key")).willReturn(new ArrayList<>());
            given(campaignRepository.createNewKycCampaign(anyString(),anyString(),any(Date.class),any(Date.class),anyInt()))
                    .willThrow(new InvalidDataAccessResourceUsageException("error db"));

            service.confirmCampaign(req);
        });
        Assertions.assertEquals(HttpStatus.SERVICE_UNAVAILABLE,ex.getStatus());
    }

    @Test
    public void getCampaigns_retrieveAllCampaigns_returnCampaignsRegistered(){

        RequestData<Void> req = RequestData.<Void>builder()
                .build();

        given(campaignRepository.findAll()).willReturn(Collections.singletonList(new CampaignEntity()));
        given(campaignMapper.toCampaignData(any(CampaignEntity.class))).willReturn(new CampaignData());

        ResponseData<List<CampaignData>> response = service.getCampaigns(req);

        Assertions.assertEquals(HttpStatus.OK,response.getHttpStatus());
        verify(campaignRepository,times(1)).findAll();
    }

    @Test
    public void getCampaigns_retrieveCampaignById_returnCampaignRegistered(){

        RequestData<Void> req = RequestData.<Void>builder()
                .queryParams(Collections.singletonMap("campaignId","1"))
                .build();
        given(campaignRepository.findById(any(Integer.class))).willReturn(Optional.of(new CampaignEntity()));
        given(campaignMapper.toCampaignData(any(CampaignEntity.class))).willReturn(new CampaignData());

        ResponseData<List<CampaignData>> response = service.getCampaigns(req);

        Assertions.assertEquals(HttpStatus.OK,response.getHttpStatus());
        verify(campaignRepository,times(1)).findById(any(Integer.class));
    }

    @Test
    public void getCampaigns_databaseThrowError_throwError(){

        KycRestException ex = Assertions.assertThrows(KycRestException.class,()->{

            RequestData<Void> req = RequestData.<Void>builder()
                    .queryParams(Collections.singletonMap("campaignId","1"))
                    .build();
            given(campaignRepository.findById(any(Integer.class)))
                    .willThrow(new InvalidDataAccessResourceUsageException("db error"));

            service.getCampaigns(req);

        });
        Assertions.assertEquals(HttpStatus.SERVICE_UNAVAILABLE,ex.getStatus());
    }

    @Test
    public void activateCampaign_activatingCampaign_successfulActivation(){

        RequestData<Void> req = RequestData.<Void>builder()
                .pathParams(Collections.singletonMap("id","1"))
                .queryParams(Collections.singletonMap("active","true"))
                .build();

        service.activateCampaign(req);
        verify(campaignRepository,times(1)).updateStatusCampaign(1,true);

    }

    @Test
    public void activateCampaign_disablingCampaign_successfulDisable(){

        RequestData<Void> req = RequestData.<Void>builder()
                .pathParams(Collections.singletonMap("id","1"))
                .queryParams(Collections.singletonMap("active","false"))
                .build();

        service.activateCampaign(req);
        verify(campaignRepository,times(1)).updateStatusCampaign(1,false);
    }

    @Test
    public void activateCampaign_databaseThrowError_throwError(){

        KycRestException ex = Assertions.assertThrows(KycRestException.class,()->{

            RequestData<Void> req = RequestData.<Void>builder()
                    .pathParams(Collections.singletonMap("id","1"))
                    .queryParams(Collections.singletonMap("active","true"))
                    .build();

            given(campaignRepository.updateStatusCampaign(1,true))
                    .willThrow(new InvalidDataAccessResourceUsageException("error db"));

            service.activateCampaign(req);
        });
        Assertions.assertEquals(HttpStatus.SERVICE_UNAVAILABLE,ex.getStatus());
    }

    @Test
    public void getOffersByCampaign_retrievingOffersRegistered_returnOffers(){

        RequestData<Void> req = RequestData.<Void>builder()
                .pathParams(Collections.singletonMap("id","1"))
                .build();

        given(campaignRepository.getOffersByCampaign(1)).willReturn(Optional.of(new CampaignEntity()));
        given(campaignMapper.toCampaignOfferData(any(CampaignEntity.class))).willReturn(new CampaignOfferData());

        ResponseData<CampaignOfferData> response = service.getOffersByCampaign(req);
        Assertions.assertEquals(HttpStatus.OK,response.getHttpStatus());
    }

    @Test
    public void getOffersByCampaign_noOffers_returnNoContentResponse(){

        RequestData<Void> req = RequestData.<Void>builder()
                .pathParams(Collections.singletonMap("id","1"))
                .build();

        given(campaignRepository.getOffersByCampaign(1)).willReturn(Optional.empty());

        ResponseData<CampaignOfferData> response = service.getOffersByCampaign(req);
        Assertions.assertEquals(HttpStatus.NO_CONTENT,response.getHttpStatus());
    }

    @Test
    public void getOffersByCampaign_databaseThrowError_throwError(){

        KycRestException ex = Assertions.assertThrows(KycRestException.class,()->{

            RequestData<Void> req = RequestData.<Void>builder()
                    .pathParams(Collections.singletonMap("id","1"))
                    .build();

            given(campaignRepository.getOffersByCampaign(1))
                    .willThrow(new InvalidDataAccessResourceUsageException("db error"));

            service.getOffersByCampaign(req);
        });
        Assertions.assertEquals(HttpStatus.SERVICE_UNAVAILABLE,ex.getStatus());
    }


}