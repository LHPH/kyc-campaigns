package com.kyc.campaigns.services;

import com.kyc.campaigns.entity.ErrorOffersEntity;
import com.kyc.campaigns.mappers.ErrorDetailMapper;
import com.kyc.campaigns.model.ErrorPreOfferDetail;
import com.kyc.campaigns.model.ResultPreLoadOffers;
import com.kyc.campaigns.repositories.ErrorOffersRepository;
import com.kyc.campaigns.repositories.OfferTemporalRepository;
import com.kyc.core.exception.KycRestException;
import com.kyc.core.model.web.RequestData;
import com.kyc.core.model.web.ResponseData;
import com.kyc.core.properties.KycMessages;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PreCampaignServiceTest {

    @InjectMocks
    private PreCampaignService service;

    @Mock
    private OfferTemporalRepository offerTemporalRepository;

    @Mock
    private ErrorOffersRepository errorOffersRepository;

    @Mock
    private ErrorDetailMapper errorDetailMapper;

    @Mock
    private KycMessages kycMessages;

    private RequestData<MultipartFile> req;
    private RequestData<MultipartFile> reqZero;

    @BeforeEach
    public void setUp() throws IOException {

        MockitoAnnotations.openMocks(PreCampaignServiceTest.class);

        req = RequestData.<MultipartFile>builder()
                .body(getMockMultiPartFile("CAMPAIGN.xlsx"))
                .build();

        reqZero = RequestData.<MultipartFile>builder()
                .body(getMockMultiPartFile("CAMPAIGN_ZERO.xlsx"))
                .build();
    }


    @Test
    public void uploadFile_uploadTemporalCampaign_successfulUpload(){

        when(errorOffersRepository.getErrors(any(String.class))).thenReturn(new ArrayList<>());

        ResponseData<ResultPreLoadOffers> result = service.uploadFile(req);

        verify(offerTemporalRepository,times(1)).saveAll(any(List.class));
        Assertions.assertEquals(HttpStatus.OK,result.getHttpStatus());
    }

    @Test
    public void uploadFile_uploadZeroRecords_returnError(){

        KycRestException ex = Assertions.assertThrows(KycRestException.class,()->{
            service.uploadFile(reqZero);
        });
        Assertions.assertEquals(HttpStatus.NO_CONTENT,ex.getStatus());
    }

    @Test
    public void uploadFile_uploadTemporalCampaign_uploadWithErrors(){

        when(errorOffersRepository.getErrors(any(String.class))).thenReturn(Collections.singletonList(new ErrorOffersEntity()));
        when(errorDetailMapper.mapperToModel(any(ErrorOffersEntity.class))).thenReturn(new ErrorPreOfferDetail());

        ResponseData<ResultPreLoadOffers> result = service.uploadFile(req);

        verify(offerTemporalRepository,times(1)).saveAll(any(List.class));
        Assertions.assertEquals(HttpStatus.OK,result.getHttpStatus());
        Assertions.assertFalse(result.getData().getErrorPreOffers().isEmpty());
    }

    @Test
    public void uploadFile_databaseUnavailable_returnError(){

        KycRestException ex = Assertions.assertThrows(KycRestException.class,()->{

            when(offerTemporalRepository.saveAll(any(List.class)))
                    .thenThrow(new InvalidDataAccessResourceUsageException("test db error"));
            service.uploadFile(req);
        });
        Assertions.assertEquals(HttpStatus.SERVICE_UNAVAILABLE,ex.getStatus());
    }

    @Test
    public void uploadFile_fileCannotProcessed_returnError(){

        KycRestException ex = Assertions.assertThrows(KycRestException.class,()->{

            MultipartFile mockMultiPart = Mockito.mock(MultipartFile.class);
            RequestData<MultipartFile> req = RequestData.<MultipartFile>builder()
                    .body(mockMultiPart)
                    .build();

            when(mockMultiPart.getInputStream()).thenThrow(new IOException("test IO"));
            service.uploadFile(req);
        });
        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY,ex.getStatus());
    }

    @Test
    public void cleanPreOffers_cleaningOffers_returnSuccessfulResponse(){

        Map<String,Object> inoutParams = new HashMap<>();
        inoutParams.put("P_ERROR_CODE",null);

        when(offerTemporalRepository.cleanTempOffers(any(Integer.class))).thenReturn(inoutParams);
        ResponseData<Boolean> response = service.cleanPreOffers();
        Assertions.assertEquals(HttpStatus.OK,response.getHttpStatus());
    }
    
    @Test
    public void cleanPreOffers_errorDatabase_returnError(){

        KycRestException ex = Assertions.assertThrows(KycRestException.class, () -> {

            Map<String,Object> inoutParams = new HashMap<>();
            inoutParams.put("P_ERROR_CODE","ERROR");

            when(offerTemporalRepository.cleanTempOffers(any(Integer.class))).thenReturn(inoutParams);
            service.cleanPreOffers();
        });
        Assertions.assertEquals(HttpStatus.SERVICE_UNAVAILABLE,ex.getStatus());
    }

    private MultipartFile getMockMultiPartFile(String nameFile) throws IOException{

        ClassPathResource cl = new ClassPathResource(nameFile);
        try(InputStream in = cl.getInputStream()){
             return new MockMultipartFile("file",in);
        }
        catch(IOException ex){
            throw ex;
        }
    }

}

